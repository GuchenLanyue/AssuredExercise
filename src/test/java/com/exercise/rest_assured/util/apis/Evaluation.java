package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Assert;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Evaluation {
	
	@Step("getEvaluation() 获取求职意向列表")
	public String getEvaluation(String token){
		
		Response response = given()
				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token", token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/getevaluation")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/getevaluation.Response.body:", body);
			Assert.fail("/personresume/getevaluation 请求失败！");
		}
		
		String body = response.asString();
		
		while (body.charAt(0)!='{') {
			body = body.substring(1, body.length());
		}
		
		JsonPath json = new JsonPath(body);
		Map<String, Object> value = json.get("value");
		if (value.containsKey("id")) {
			return value.get("id").toString();
		}else{
			return null;
		}
	}
}
