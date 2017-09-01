package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.Parameter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class Train {
	public Train() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getTrains(String token){
		Response response = given()
				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token",token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/gettrains")
			.then()
				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		List<String> trainIDs = from(json).getList("value.id");
		
		return trainIDs;
	}
	
	@Step
	@Description("获取培训信息")
	public String getTrain(String token,String id,String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\getTrain.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "gettrain");
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", token);
		paramsMap.put("id", id);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("删除教育背景信息")
	public void delTrain(String token, String id) {
		given()
//			.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/deltrain")
		.then()
			.statusCode(200);
	}
}
