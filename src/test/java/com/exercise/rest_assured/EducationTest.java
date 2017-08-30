package com.exercise.rest_assured;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.apis.Education;
import com.exercise.rest_assured.util.apis.GetEducations;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class EducationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="增加教育背景信息")
	@Description("增加教育背景信息")
	public void addEducationTest(Map<String,String> params){
		
		Map<String, String> baseMap = new HashMap<String, String>();
		baseMap.put("Method", "POST");
		baseMap.put("Protocol", "http");
		baseMap.put("Host", "nchr.release.microfastup.com");
		baseMap.put("path", "/nchr/personresume/education");
		
		params.put("token", getToken());
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, params);
		String body = http.getBody(response);
		
		JsonPath json = new JsonPath(body).setRoot("value");
		String id = json.getString("id");
		
		Education education = new Education();
		education.addEducation(params,getToken(),getSrcDir());
	}
	
	@Test(dataProvider = "SingleCase",description="编辑教育背景信息")
	@Description("编辑教育背景信息")
	public void editEducationTest(Map<String,String> params){
		Education education = new Education();
		education.editEducation(params, getToken(),getSrcDir());
	}
	
	@Test
	@Description("删除教育背景信息")
	public void delEducationTest(){
		System.out.println("删除教育背景信息");
		GetEducations educations = new GetEducations();
		
		List<String> list = educations.getEducationList(getToken());
		Education education = new Education();
		for (int i = 0; i < list.size(); i++) {
			education.delEducation(getToken(), list.get(i));
		}
	}
}
