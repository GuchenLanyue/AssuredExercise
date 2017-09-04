package com.exercise.rest_assured;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Education;

import io.qameta.allure.Description;

import io.restassured.path.json.JsonPath;

public class EducationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="创建教育背景信息")
	public void addEducationTest(Map<String,Object> params){
		params.put("token", getToken());
		setParams("education", params);
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		String id = json.getString("id");
		Education education = new Education();
		String actualJson = education.getEducation(getToken(), id, getSrcDir());
		checkResponse(actualJson, getExpectedJson());
	}
	
	@Test(dataProvider = "SingleCase",description="编辑教育背景信息")
	@Description("修改教育背景信息")
	public void editEducationTest(Map<String,Object> params){
		
		Education education = new Education();
		List<String> ids = education.getEducations(getToken());
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			Assert.fail("当前没有添加任何教育背景，无法编辑");
		}
		
		params.put("token", getToken());
		params.put("id", id);
		
		setParams("education", params);
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		id = json.getString("id");
		String actualJson = education.getEducation(getToken(), id, getSrcDir());
		checkResponse(actualJson, getExpectedJson());
	}
	
	@Test(description = "删除教育背景信息")
	@Description("删除教育背景信息")
	public void delEducationTest(){
		Education education = new Education();
		List<String> list = education.getEducations(getToken());
		for (int i = 0; i < list.size(); i++) {
			education.delEducation(getToken(), list.get(i));
		}
	}
}
