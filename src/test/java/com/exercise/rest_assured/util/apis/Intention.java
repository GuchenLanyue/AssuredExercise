package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.Parameter;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Intention {
	
	private String industry;
	
	public Intention() {
		// TODO Auto-generated constructor stub
		setIndustry();
	}
	
	public void addProject(Map<String, String> params){
		
	}
	
	
	public String getindustry(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getindustry")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getindustry.Response.body:", body);
			Assert.fail("/basics/getindustry 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step
	public List<String> getIntentions(String token){
		
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token", token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/getintentions")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/getintentions.Response.body:", body);
			Assert.fail("/personresume/getintentions 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		List<String> jobIDs = from(json).getList("value.id");
		
		return jobIDs;
	}
	
	public void setIndustry(){
		JsonPath json = new JsonPath(getindustry());
		Map<String, Object> industrys = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:industrys.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
        industry = keys.get(index);
	}

	@Step
	@Description("获取项目经验")
	public String getIntention(String token,String id,String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\getIntention.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "getintention");
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", token);
		paramsMap.put("id", id);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("删除工作经验")
	public void delIntention(String token, String id) {
		Response response = given()
//			.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/delintention")
		.then()
//			.statusCode(200)
		.extract()
			.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/delintention.Response.body:", body);
			Assert.fail("/personresume/delintention 请求失败！");
		}
	}
}
