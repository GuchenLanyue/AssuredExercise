package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.utils.TextData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Education {
	public Education() {
		// TODO Auto-generated constructor stub
	}
	
	@Step
	@Description("添加教育背景信息")
	public void addEducation(Map<String, String> params,String srcDir){
		
		Map<String, String> baseMap = new HashMap<String, String>();
		baseMap.put("Method", "POST");
		baseMap.put("Protocol", "http");
		baseMap.put("Host", "nchr.release.microfastup.com");
		baseMap.put("path", "/nchr/personresume/education");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, params);
		String body = http.getBody(response);
		
		JsonPath json = new JsonPath(body).setRoot("value");
		String id = json.getString("id");
		
		TextData textData = new TextData();
		String path = srcDir+"/case/";
		textData.writerText(path, "eduID.txt", id);
	}
	
	@Step
	@Description("修改教育背景信息")
	public Response editEducation(Map<String, String> params){
		Map<String, String> baseMap = new HashMap<String, String>();
		baseMap.put("Method", "POST");
		baseMap.put("Protocol", "http");
		baseMap.put("Host", "nchr.release.microfastup.com");
		baseMap.put("path", "/nchr/personresume/education");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, params);
		String body = http.getBody(response);
		
		return response;
	}
	
	@Step
	@Description("删除教育背景信息")
	public void delEducation(String token, String id) {
		given()
//			.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/deleducation")
		.then()
			.statusCode(200);
	}
}
