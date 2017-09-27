package com.exercise.rest_assured.apis;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.exercise.rest_assured.utils.TxtData;
import com.exercise.rest_assured.utils.testutils.User;

import io.restassured.path.json.JsonPath;

public class API_Category {

	private String token = null;
	private Map<String, Object> cookieMap = new HashMap<>();
	
	public enum category {
		person, personresume, enterprise, job, site, admin, delivery
	}

	public void analysis(String path) {
		category role = null;
		String[] strs = null;
		if (path != null) {
			strs = path.split("/");
			if (strs[1].equals("person")|strs[1].equals("delivery")) {
				role = category.person;
			} else if (strs[1].equals("personresume")) {
				role = category.personresume;
			} else if (strs[1].equals("enterprise")) {
				role = category.enterprise;
			} else if (strs[1].equals("job")) {
				role = category.job;
			} else if (strs[1].equals("site")) {
				role = category.site;
			}
		}else{
			role = category.admin;
		}
		
		Login login = new Login();
		User user = new User();
		TxtData textData = new TxtData();
		String fPath = System.getProperty("user.dir") + "\\src\\test\\resources\\case\\";

		switch (role) {
		case person:
		case personresume:
			File pfile = new File(fPath + "personToken.txt");
			if (!pfile.exists() || System.currentTimeMillis() - pfile.lastModified() > 120000) {
				String body = login.singin(user.getPerson());
				JsonPath json = new JsonPath(body).setRoot("value");
				token = json.getString("token");
				textData.writerText(fPath + "personToken.txt", token);
				textData.writerText(fPath + "person_login.txt", body);
			} else {
				TxtData data = new TxtData();
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
				textData.writerText(fPath + "enterprise_login.txt", body);
			} else {
				TxtData data = new TxtData();
				token = data.readTxtFile(fPath + "enterpriseToken.txt");
			}

			break;
		case admin:
		case site:
			File afile = new File(fPath + "adminCookie.txt");
			if (!afile.exists() || System.currentTimeMillis() - afile.lastModified() > 120000) {
				login.adminSingin(user.getAdmin());
				cookieMap = login.getCookie();

				String cookie = null;
				for (String key : cookieMap.keySet()) {
					cookie += (key + "=" + cookieMap.get(key));
					cookie += ";";
				}

				textData.writerText(fPath + "adminCookie.txt", cookie.substring(4, cookie.length()));
			} else {
				TxtData data = new TxtData();
				String cookieStr = data.readTxtFile(fPath + "adminCookie.txt");
				for(String cookie:cookieStr.split(";")){
					String[] cookieList=cookie.split("=");
						cookieMap.put(cookieList[0], cookieList[1]);
				}
			}
			
			break;
		default:
			break;
		}
	}
	
	public String getToke(){
		return token;
	}
	
	public Map<String, Object> getCookie(){
		return cookieMap;
	}
}
