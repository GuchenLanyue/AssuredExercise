package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.utils.FileData;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Login {
	@Step()
	/**
	 * 官网登录
	 * */
	public void singin(User user){
		Response response = given()
		.proxy("localhost", 8888)
//		.log().all()
		.contentType("application/x-www-form-urlencoded;charset=UTF-8")
		.param("username",user.getPUsername())
		.param("password",user.getPPassword())
		.param("v_code",user.getPV_code())
		.param("uuid",user.getPUuid())
	.when()
		.post("http://nchr.release.microfastup.com/nchr/person/login")
	.then()
//		.log().all()
		.statusCode(200)
	.extract()
		.response();
		
		String body = response.getBody().asString();
		while (body.charAt(0) != '{') {
			body = body.substring(1, body.length());
		};
		
		JsonPath json = new JsonPath(body).setRoot("value");
		
		String token = json.getString("token");
		
		FileData textData = new FileData();
		String path =System.getProperty("user.dir")+"\\src\\test\\resources\\case\\";
		textData.writerText(path+"token.txt", token);
	}
}
