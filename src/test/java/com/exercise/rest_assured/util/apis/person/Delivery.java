package com.exercise.rest_assured.util.apis.person;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Delivery {
	private String url = null;
	
	public Delivery(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	public Map<String, Object> setParams(String job_id, Map<String, Object> params){
		Map<String, Object> param = new HashMap<>();
		param = params;
		param.put("job_id", job_id);
		
		return param;
	}
	
	public void addresume(String token, String job_id){
		RestAssured.given()
			.contentType(ContentType.URLENC)
			.formParam("token", token)
			.formParam("job_id", job_id)
		.when()
			.post(url + "/delivery/addresume")
		.then()
			.statusCode(200)
		.extract()
			.response();
	}
}
