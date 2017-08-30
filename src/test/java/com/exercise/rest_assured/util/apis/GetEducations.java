package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.path.json.JsonPath.from;

public class GetEducations {
	public GetEducations() {
		// TODO Auto-generated constructor stub
	}
	
	@Step
	@Description("获取教育背景信息列表")
	public List<String> getEducationList(String token){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token",token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/geteducations")
			.then()
				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		List<String> groceries = from(json).getList("value.id");
		
		return groceries;
	}
}
