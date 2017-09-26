package com.exercise.rest_assured.apis;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Login {
	private Response response = null;
	
	@Step()
	/**
	 * 官网登录
	 * */
	public String singin(JsonPath user){
		response = given()
//		.proxy("localhost", 8888)
//		.log().all()
		.contentType("application/x-www-form-urlencoded;charset=UTF-8")
		.formParams(user.getMap("params"))
	.when()
		.post(user.getString("url"))
	.then()
//		.log().all()
		.statusCode(200)
	.extract()
		.response();
		
		String body = response.getBody().asString();
		while (body.charAt(0) != '{') {
			body = body.substring(1, body.length());
		};
		
		return body;
	}
	
	
	@Step()
	/**
	 * 官网登录
	 * */
	public void adminSingin(JsonPath user){
		
		response = given()
//		.proxy("localhost", 8888)
//		.log().all()
		.contentType("application/x-www-form-urlencoded;charset=UTF-8")
		.formParams(user.getMap("params"))
	.when()
		.post(user.getString("url"))
	.then()
//		.log().all()
//		.statusCode(200)
	.extract()
		.response();
	}
	
	public Map<String, Object> getCookie(){
		Map<String, Object> cookieMap = new HashMap<>();
		Headers headers = response.getHeaders();		
		List<Header> cookies = headers.getList("Set-Cookie");
		String PHPSESSID = cookies.get(1).getValue().split(";")[0].split("=")[1];
		String[] md5 = cookies.get(2).getValue().split(";")[0].split("=");
		cookieMap.put("PHPSESSID", PHPSESSID);
		cookieMap.put(md5[0], md5[1]);
		cookieMap.put("UM_distinctid", "15b71f24cc2101-020ee61b4f9c14-8373f6a-100200-15b71f24cc312d");
		return cookieMap;
	}
}
