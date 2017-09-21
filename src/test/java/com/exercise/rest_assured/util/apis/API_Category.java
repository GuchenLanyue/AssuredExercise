package com.exercise.rest_assured.util.apis;

import java.io.File;

import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.utils.FileData;

public class API_Category {

	public enum category {
		person, personresume, enterprise
	}

	public void analysis(String path) {
		category role = null;
		String[] strs = path.split("/");
		if (strs[1].equals("person")) {
			role = category.person;
		} else if (strs[1].equals("personresume")) {
			role = category.personresume;
		} else if (strs[1].equals("enterprise")) {
			role = category.enterprise;
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
				token = login.singin(user.getPerson());
				textData.writerText(fPath + "personToken.txt", token);
			}
			break;
		case enterprise:
			File efile = new File(fPath + "enterpriseToken.txt");
			if (!efile.exists() || System.currentTimeMillis() - efile.lastModified() > 120000) {
				token = login.singin(user.getEnterprise());
				textData.writerText(fPath + "enterpriseToken.txt", token);
			}

			break;
		default:
			break;
		}
	}
}
