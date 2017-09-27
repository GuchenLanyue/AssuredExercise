package com.exercise.rest_assured.utils;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.apis.Login;
import com.exercise.rest_assured.utils.testutils.BaseTest.RequestMethod;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;

public class HttpMethods {
	private Map<String, Object> params = null;
	
	@Step("request() 发起请求")
	public Response request(Map<String, Object> baseMap) {		
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
			if (!baseMap.containsKey("QueryString")) {
				response = given()
//						.proxy("127.0.0.1", 8888)
	//					.log().all()
//						.log().uri()
//						.log().params()
						.header("Accept", "application/json")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Cache-Control", "no-cache")
						.config(RestAssured.config()
								  .encoderConfig(EncoderConfig.encoderConfig()
										    .defaultContentCharset("UTF-8")
										    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
					.when()
						.post(requestURL)
					.then()
//						.log().body()
						.statusCode(200)
					.extract()
						.response();
			}else{
				String queryString = baseMap.get("QueryString").toString();
				String[] qParam = queryString.split("&");
				Map<String, Object> queryMap = new HashMap<>();
				for(String param:qParam){
					String[] qparams= param.split("=");
					queryMap.put(qparams[0], qparams[1]);
				}
				
				response = given()
						.log().uri()
						.log().params()
						.header("Accept", "application/json")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Cache-Control", "no-cache")
						.config(RestAssured.config()
								  .encoderConfig(EncoderConfig.encoderConfig()
										    .defaultContentCharset("UTF-8")
										    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
						.queryParams(queryMap)
					.when()
						.post(requestURL)
					.then()
						.log().body()
						.statusCode(200)
					.extract()
						.response();
			}
			break;
		case GET:	
			response = given()
					.config(RestAssured.config()
							  .encoderConfig(EncoderConfig.encoderConfig()
									    .defaultContentCharset("UTF-8")
									    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.when()
					.get(requestURL)
				.then()
				.extract()
					.response();
			
			break;
		default:
			break;
		}
		
		return response;
	}
	
	@Step("request() 发起请求")
	public Response request(Map<String, Object> baseMap,Map<String, Object> paramsMap) {
		params = paramsMap;		
		if (params.containsKey("token")) {
			Login login = new Login(baseMap.get("path").toString());
			String token = login.getToken();
			params.put("token", token);
		}
		
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
			if (!baseMap.containsKey("QueryString")) {
				response = given()
//						.proxy("127.0.0.1", 8888)
	//					.log().all()
//						.log().uri()
//						.log().params()
						.header("Accept", "application/json")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Cache-Control", "no-cache")
						.config(RestAssured.config()
								  .encoderConfig(EncoderConfig.encoderConfig()
										    .defaultContentCharset("UTF-8")
										    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
						.formParams(paramsMap)
					.when()
						.post(requestURL)
					.then()
//						.log().body()
						.statusCode(200)
					.extract()
						.response();
			}else{
				String queryString = baseMap.get("QueryString").toString();
				String[] qParam = queryString.split("&");
				Map<String, Object> queryMap = new HashMap<>();
				for(String param:qParam){
					String[] qparams= param.split("=");
					queryMap.put(qparams[0], qparams[1]);
				}
				
				response = given()
	//					.proxy("127.0.0.1", 8888)
	//					.log().all()
						.log().uri()
						.log().params()
						.header("Accept", "application/json")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Cache-Control", "no-cache")
						.config(RestAssured.config()
								  .encoderConfig(EncoderConfig.encoderConfig()
										    .defaultContentCharset("UTF-8")
										    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
						.formParams(paramsMap)
						.queryParams(queryMap)
					.when()
						.post(requestURL)
					.then()
						.log().body()
						.statusCode(200)
					.extract()
						.response();
			}
			break;
		case GET:	
			response = given()
//						.proxy("127.0.0.1", 8888)
//						.log().all()
					.config(RestAssured.config()
							  .encoderConfig(EncoderConfig.encoderConfig()
									    .defaultContentCharset("UTF-8")
									    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
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
		
		return response;
	}
	
	@Step("request() 发起请求")
	public Response request(Map<String,Object> cookieMap, Map<String, Object> baseMap,Map<String, Object> paramsMap) {
		params = paramsMap;
		
		Login login = new Login(baseMap.get("path").toString());
		
		if (params.containsKey("token")) {
			String token = login.getToken();
			params.put("token", token);
		}
		
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
			if (!baseMap.containsKey("QueryString")) {
				response = given()
						.cookies(cookieMap)
						.header("Accept", "application/json")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Cache-Control", "no-cache")
						.config(RestAssured.config()
								  .encoderConfig(EncoderConfig.encoderConfig()
										    .defaultContentCharset("UTF-8")
										    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
						.formParams(paramsMap)
					.when()
						.post(requestURL)
					.then()
//						.log().body()
						.statusCode(200)
					.extract()
						.response();
			}else{
				String queryString = baseMap.get("QueryString").toString();
				String[] qParam = queryString.split("&");
				Map<String, Object> queryMap = new HashMap<>();
				for(String param:qParam){
					String[] qparams= param.split("=");
					queryMap.put(qparams[0], qparams[1]);
				}
				
				response = given()
	//					.proxy("127.0.0.1", 8888)
	//					.log().all()
						.log().uri()
						.log().params()
						.header("Accept", "application/json")
						.header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
						.header("Cache-Control", "no-cache")
						.config(RestAssured.config()
								  .encoderConfig(EncoderConfig.encoderConfig()
										    .defaultContentCharset("UTF-8")
										    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
						.formParams(paramsMap)
						.queryParams(queryMap)
					.when()
						.post(requestURL)
					.then()
						.log().body()
						.statusCode(200)
					.extract()
						.response();
			}
			break;
		case GET:	
			response = given()
//						.proxy("127.0.0.1", 8888)
//						.log().all()
					.config(RestAssured.config()
							  .encoderConfig(EncoderConfig.encoderConfig()
									    .defaultContentCharset("UTF-8")
									    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
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
		
		return response;
	}
	
	@Description("获取响应数据")
	public String getBody(Response response){
		String body = response.asString();
		return body.substring(body.indexOf("{"), body.lastIndexOf("}")+1);
	}
	
	@Description("将请求数据添加到测试报告中")
	public void requestLog(){
		String requestBody = " ";
		if(params==null){
			return;
		}
		
		for(String key:params.keySet()){
			String str = key+"="+params.get(key);
			requestBody=requestBody+"&"+str;
		}
		
		Allure.addAttachment("RequestBody:", requestBody.substring(requestBody.indexOf('&')+1, requestBody.length()));
	}
}
