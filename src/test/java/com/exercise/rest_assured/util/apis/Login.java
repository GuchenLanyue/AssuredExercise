package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Login {
	@Step()
	/**
	 * 官网登录
	 * */
	public String singin(JsonPath user){
		
		Response response = given()
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
}
