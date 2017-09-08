package com.exercise.rest_assured.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.util.BaseTest.RequestMethod;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class HttpMethods {
	@Step
	public Response request(Map<String, Object> baseMap,Map<String, Object> paramsMap) {
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
//					.proxy("127.0.0.1", 8888)
//						.log().all()
					.header("Accept", "application/json")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("Cache-Control", "no-cache")
					.contentType("application/x-www-form-urlencoded;UTF-8")
					.params(paramsMap)
				.when()
					.post(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
				.then()
//					.log().body()
//					.statusCode(200)
				.extract()
					.response();
			
			break;
		case GET:	
			response = given()
//						.proxy("127.0.0.1", 8888)
//						.log().params()
					.contentType(baseMap.get("contentType").toString())
					.params(paramsMap)
				.when()
					.get(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
				.then()
//					.log().body()
//					.statusCode(200)
				.extract()
					.response();
			
			break;
		default:
			break;
		}
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment(baseMap.get("path")+".Response.body:", body);
			Assert.fail(baseMap.get("path")+"请求失败！");
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
