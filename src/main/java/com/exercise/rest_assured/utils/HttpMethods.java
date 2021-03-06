package com.exercise.rest_assured.utils;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.apis.Login;
import com.exercise.rest_assured.apis.Login.Role;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;

public class HttpMethods {
	private Map<String,Object> headerMap = new HashMap<>();
	private Map<String,Object> cookieMap = new HashMap<>();
	private Map<String,Object> queryMap = new HashMap<>();
	private Map<String,Object> paramMap = new HashMap<>();
	private String requestURL = null;
	private String url = null;
	
	public HttpMethods() {
		// TODO Auto-generated constructor stub
	}
	
	public HttpMethods(String basePath) {
		// TODO Auto-generated constructor stub
		url = basePath;
	}
	
	@Step("request() 发起请求")
	public Response request(Map<String, Map<String,Object>> map){
		Login login = new Login();
		Role role = login.analysis(map.get("base").get("path").toString());
		if(role.equals(Role.admin)){
			login.loginStatus(role);
			map.put("cookies", login.getCookie());
		}else if(role.equals(Role.person)|role.equals(Role.enterprise)) {
			login.loginStatus(role);
			String token = login.getToken();
			Map<String, Object> params = new HashMap<>();
			params = map.get("params");
			params.put("token", token);
			map.put("params", params);
		}
		
		if(map.get("base").get("Method").toString().equals("POST")){
			return post(map);
		}else if(map.get("base").get("Method").toString().equals("GET")){
			return get(map);
		}else{
			Assert.fail("不支持的请求方式"+map.get("base").get("Method").toString()+"目前支持POST及GET请求");
			return null;
		}
	}
	
	public Response post(Map<String, Map<String,Object>> map){
		if(url!=null){
			requestURL = url + map.get("base").get("path").toString();
		}else{
			requestURL = map.get("base").get("basePath").toString()
					+ map.get("base").get("path").toString();
		}
		
		if(map.containsKey("headers")){
			headerMap = map.get("headers");
		}
		if(map.containsKey("cookies")){
			cookieMap = map.get("cookies");
		}
		if(map.containsKey("querys")){
			queryMap = map.get("querys");
		}
		if(map.containsKey("params")){
			paramMap = map.get("params");
		}
		
		Response response = given()
//				.proxy("127.0.0.1", 8888)
//				.log().all()
				.log().uri()
//				.log().params()
				.headers(headerMap)
				.cookies(cookieMap)
				.config(RestAssured.config()
						  .encoderConfig(EncoderConfig.encoderConfig()
								    .defaultContentCharset("UTF-8")
								    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.params(paramMap)
				.queryParams(queryMap)
			.when()
				.post(requestURL)
			.then()
//				.log().body()
//				.statusCode(200)
				.log().status()
			.extract()
				.response();

		responseLog(response);
		
		return response;
	}
	
	public Response get(Map<String, Map<String,Object>> map){
		if(url!=null){
			requestURL = url + map.get("base").get("path").toString();
		}else{
			requestURL = map.get("base").get("basePath").toString()
					+ map.get("base").get("path").toString();
		}
		
		if(map.containsKey("headers")){
			headerMap = map.get("headers");
		}
		if(map.containsKey("cookies")){
			cookieMap = map.get("cookies");
		}
		if(map.containsKey("querys")){
			queryMap = map.get("querys");
		}
		if(map.containsKey("params")){
			paramMap = map.get("params");
		}
		
		Response response = given()
//				.proxy("127.0.0.1", 8888)
//				.log().all()
				.log().uri()
//				.log().params()
				.headers(headerMap)
				.cookies(cookieMap)
				.config(RestAssured.config()
						  .encoderConfig(EncoderConfig.encoderConfig()
								    .defaultContentCharset("UTF-8")
								    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.params(paramMap)
				.queryParams(queryMap)
			.when()
				.get(requestURL)
			.then()
//				.log().body()
				.log().status()
//				.statusCode(200)
			.extract()
				.response();
		
		responseLog(response);
		
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
		if(paramMap==null){
			return;
		}
		
		for(String key:paramMap.keySet()){
			String str = key+"="+paramMap.get(key);
			requestBody=requestBody+"&"+str;
		}
		
		Allure.addAttachment("RequestBody:", requestBody.substring(requestBody.indexOf('&')+1, requestBody.length()));
	}
	
	@Description("将响应数据添加到测试报告中")
	public void responseLog(Response response){		
		Allure.addAttachment("Response Body:", response.getBody().asString());
	}
	
	public static void main(String[] args) {
		Map<String, Map<String,Object>> map = new HashMap<>();
		
		String path = "/enterprise/settledapply/index/ajax/settled-apply-grid/SettledApply_sort/id.desc";
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "GET");
		baseMap.put("basePath", "http://nchr.release.microfastup.com");
		baseMap.put("path", path);
		map.put("base", baseMap);
		
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("ajax", "settled-apply-grid");
		map.put("querys", queryMap);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
		
		String id = response.andReturn().htmlPath().getString("//*[@id=\"settled-apply-grid\"]/table/tbody/tr[1]/td[1]");
		System.out.println(id);
	}
}
