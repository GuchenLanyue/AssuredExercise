package com.exercise.rest_assured.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.util.BaseTest.RequestMethod;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class HttpMethods {
	@Step
	public Response request(Map<String, String> baseMap,Map<String, String> paramsMap) {
		Response response = null;
		RequestMethod method = null;
		
		if (baseMap.get("Method").equals("POST")) {
			method = RequestMethod.POST;
		} else if (baseMap.get("Method").equals("GET")) {
			method = RequestMethod.GET;
		} else {
			Assert.fail("目前只支持POST和GET方法");
		}

		switch (method) {
		case POST:
			response = given()
				.proxy("localhost", 8888)
//				.log().params()
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.params(paramsMap)
			.when()
				.post(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
			.then()
//				.log().body()
				.statusCode(200)
			.extract()
				.response();

			break;
		case GET:
			response = given()
				.proxy("localhost", 8888)
//				.log().params()
				.contentType(baseMap.get("contentType"))
				.params(paramsMap)
			.when()
				.get(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
			.then()
//				.log().body()
				.statusCode(200)
			.extract()
				.response();

			break;
		default:
			break;
		}
		
		return response;
	}
	
	public String getBody(Response response){
		String body = response.asString();
		
		while(body.charAt(0)!='{'){
			body = body.substring(1, body.length());
		}
		
		return body;
	}
}
