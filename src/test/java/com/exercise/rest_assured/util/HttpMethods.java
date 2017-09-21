package com.exercise.rest_assured.util;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Allure;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.util.BaseTest.RequestMethod;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;

public class HttpMethods {
	private Map<String, Object> params = null;
	
	@Step
	public Response request(Map<String, Object> baseMap,Map<String, Object> paramsMap) {
		params = paramsMap;
		String requestURL = baseMap.get("baseURL").toString() + baseMap.get("path").toString();
		Response response = null;
		RequestMethod method = null;
		Allure.addAttachment("RequestURL:", requestURL);
		requestLog();
		
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
//					.log().all()
					.header("Accept", "application/json")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("Cache-Control", "no-cache")
					.config(RestAssured.config()
							  .encoderConfig(EncoderConfig.encoderConfig()
									    .defaultContentCharset("UTF-8")
									    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
//					.contentType(ContentType.URLENC)
					.formParams(paramsMap)
				.when()
					.post(requestURL)
				.then()
//					.log().body()
//					.statusCode(200)
				.extract()
					.response();
			
			break;
		case GET:	
			response = given()
//						.proxy("127.0.0.1", 8888)
//						.log().all()
					.contentType(baseMap.get("contentType").toString())
					.params(paramsMap)
				.when()
					.get(requestURL)
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
	
	public void requestLog(){
		String requestBody = " ";
		for(String key:params.keySet()){
			String str = key+"="+params.get(key);
			requestBody=requestBody+"&"+str;
		}
		
		Allure.addAttachment("RequestBody:", requestBody.substring(requestBody.indexOf('&')+1, requestBody.length()));
	}
}
