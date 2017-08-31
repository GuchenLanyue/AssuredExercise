package com.exercise.rest_assured;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Education;
import com.exercise.rest_assured.util.apis.GetEducations;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;

public class EducationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="增加教育背景信息")
	public void addEducationTest(Map<String,String> params){
		params.put("token", getToken());
		setParams("education", params.get("Case"));
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		String id = json.getString("id");
		Education education = new Education();
		String actualJson = education.getEducation(getToken(), id, getSrcDir());
		equalResponse(actualJson, getExpectedJson());
	}
	
	@Test(dataProvider = "SingleCase",description="编辑教育背景信息")
	@Description("编辑教育背景信息")
	public void editEducationTest(Map<String,String> params){
		params.put("token", getToken());
		setParams("education", params.get("Case"));
		
		Education education = new Education();
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		String id = json.getString("id");
		String actualJson = education.getEducation(getToken(), id, getSrcDir());
		equalResponse(actualJson, getExpectedJson());
	}
	
	@Test
	@Description("删除教育背景信息")
	public void delEducationTest(){
		GetEducations educations = new GetEducations();
		
		List<String> list = educations.getEducationList(getToken());
		Education education = new Education();
		for (int i = 0; i < list.size(); i++) {
			education.delEducation(getToken(), list.get(i));
		}
	}
}
