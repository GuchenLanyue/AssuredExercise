package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.Map;

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
	public void addEducation(Map<String, String> params,String token,String srcDir){
		Response response = given()
	//		.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("name", params.get("name"))
			.param("education", params.get("education"))
			.param("magor", params.get("magor"))
			.param("end_time", params.get("end_time"))
			.param("magors", params.get("magors"))
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/education")
		.then()
			.statusCode(200)
		.extract()
			.response();
		
		String body = response.getBody().asString();
		while (body.charAt(0) != '{') {
			body = body.substring(1, body.length());
		};
		
		JsonPath json = new JsonPath(body).setRoot("value");
		
		String id = json.getString("id");
		
		TextData textData = new TextData();
		String path = srcDir+params.get("id");
		textData.writerText(path, "eduID.txt", id);
	}
	
	@Step
	@Description("修改教育背景信息")
	public Response editEducation(Map<String, String> params,String token,String srcDir){
		TextData textData = new TextData();
		String path = srcDir+params.get("id");
		String id = textData.readTxtFile(path);
		Response response = given()
	//		.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("name", params.get("name"))
			.param("education", params.get("education"))
			.param("magor", params.get("magor"))
			.param("end_time", params.get("end_time"))
			.param("magors", params.get("magors"))
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/education")
		.then()
			.statusCode(200)
		.extract()
			.response();
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
