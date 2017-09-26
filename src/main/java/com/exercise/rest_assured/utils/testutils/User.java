package com.exercise.rest_assured.utils.testutils;

import com.exercise.rest_assured.utils.JsonUtils;

import io.restassured.path.json.JsonPath;

public class User {
	private String path = System.getProperty("user.dir")+"\\src\\test\\resources\\base\\user.json";
	private JsonPath person = null;
	private JsonPath enterprise = null;
	private JsonPath admin = null;
	
	public JsonPath getPerson(){
		JsonUtils jsonUtils = new JsonUtils();
		JsonPath json = jsonUtils.jsonReader(path);
		person = json.setRoot("person");
		
		return person;
	}
	
	public JsonPath getEnterprise(){
		JsonUtils jsonUtils = new JsonUtils();
		JsonPath json = jsonUtils.jsonReader(path);
		enterprise = json.setRoot("enterprise");
		
		return enterprise;
	}
	
	public JsonPath getAdmin(){
		JsonUtils jsonUtils = new JsonUtils();
		JsonPath json = jsonUtils.jsonReader(path);
		admin = json.setRoot("admin");
		
		return admin;
	}
}
