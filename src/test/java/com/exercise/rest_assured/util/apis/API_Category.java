package com.exercise.rest_assured.util.apis;

import java.io.File;

import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.util.apis.person.Login;
import com.exercise.rest_assured.utils.FileData;

import io.restassured.path.json.JsonPath;

public class API_Category {

	public enum category {
		person, personresume, enterprise, job
	}

	public String analysis(String path) {
		category role = null;
		String[] strs = path.split("/");
		
		if (strs[1].equals("person")) {
			role = category.person;
		} else if (strs[1].equals("personresume")) {
			role = category.personresume;
		} else if (strs[1].equals("enterprise")) {
			role = category.enterprise;
		} else if (strs[1].equals("job")){
			role = category.job;
		}
		
		Login login = new Login();
		User user = new User();
		FileData textData = new FileData();
		String fPath = System.getProperty("user.dir") + "\\src\\test\\resources\\case\\";
		String token = null;
		switch (role) {
		case person:
		case personresume:
			File pfile = new File(fPath + "personToken.txt");
			if (!pfile.exists() || System.currentTimeMillis() - pfile.lastModified() > 120000) {
				String body = login.singin(user.getPerson());
				JsonPath json = new JsonPath(body).setRoot("value");
				token = json.getString("token");
				textData.writerText(fPath + "personToken.txt", token);
				textData.writerText(fPath+"person_login.txt", body);
			}else{
				FileData data = new FileData();
				token = data.readTxtFile(fPath + "personToken.txt");
			}
			
			break;
		case job:
		case enterprise:
			File efile = new File(fPath + "enterpriseToken.txt");
			if (!efile.exists() || System.currentTimeMillis() - efile.lastModified() > 120000) {
				String body = login.singin(user.getEnterprise());
				JsonPath json = new JsonPath(body).setRoot("value");
				token = json.getString("token");
				textData.writerText(fPath + "enterpriseToken.txt", token);
				textData.writerText(fPath+"enterprise_login.txt", body);
			}else{
				FileData data = new FileData();
				token = data.readTxtFile(fPath + "enterpriseToken.txt");
			}
			
			break;
		default:
			break;
		}
		
		return token;
	}
}
