package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.Parameter;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class Job {
	
	private BaseInfo baseInfo = new BaseInfo();
	
	public Job() {
		// TODO Auto-generated constructor stub
		baseInfo.setPositionData();
	}
	
	public void addJob(Map<String, String> params){
		
	}
	
	@Step
	public List<String> getJobs(String token){
		
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token", token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/getjobs")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/getjobs.Response.body:", body);
			Assert.fail("/personresume/getjobs 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		List<String> jobIDs = from(json).getList("value.id");
		
		return jobIDs;
	}

	@Step
	@Description("获取工作经验")
	public String getJob(String token,String id,String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\getJob.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "getjob");
		
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
	public void delJob(String token, String id) {
		Response response = given()
//			.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/deljob")
		.then()
//			.statusCode(200)
		.extract()
			.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/deljob.Response.body:", body);
			Assert.fail("/personresume/deljob 请求失败！");
		}
	}
	
	public int getPositions() {
		return baseInfo.getPositions();
	}

	public int getPosition() {
		return baseInfo.getPosition();
	}
	
}
