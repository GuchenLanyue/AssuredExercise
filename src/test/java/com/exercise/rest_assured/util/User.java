package com.exercise.rest_assured.util;

import java.util.Map;

import com.exercise.rest_assured.utils.JsonUtils;

import io.restassured.path.json.JsonPath;

public class User {
	String path = System.getProperty("user.dir")+"\\src\\test\\resources\\base\\user.json";
	private Map<String, String> person = null;
	private Map<String, String> enterprise = null;
	
	public User() {
		// TODO Auto-generated constructor stub
		setMap();
	}
	
	public void setMap(){
		JsonUtils jsonUtils = new JsonUtils();
		JsonPath json = jsonUtils.jsonReader(path);
		person =json.getMap("person");
		enterprise = json.get("enterprise");
	}
	
	public String getPUsername() {
		return person.get("name");
	}
	public String getPPassword() {
		return person.get("password");
	}
	public String getPV_code() {
		return person.get("v_code");
	}
	public String getPUuid() {
		return person.get("uuid");
	}
	
	public String getEUsername() {
		return enterprise.get("name");
	}
	public String getEPassword() {
		return enterprise.get("password");
	}
	public String getEV_code() {
		return enterprise.get("v_code");
	}
	public String getEUuid() {
		return enterprise.get("uuid");
	}
}
