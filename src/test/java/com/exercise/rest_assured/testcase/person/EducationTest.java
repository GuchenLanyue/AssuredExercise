package com.exercise.rest_assured.testcase.person;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Education;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;

public class EducationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="创建教育背景信息")
	public void add_Education_Test(Map<String,Object> params){
		Education edu = new Education(getBaseURL());
		params.put("token", "");
		params.remove("id");
		
		setRequest("education", edu.setParams(params));
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		edu.checkEducation(json);
		checkResponse(edu.getParams());
	}
	
	@Test(dataProvider = "SingleCase",description="编辑教育背景信息")
	@Description("修改教育背景信息")
	public void edit_Education_Test(Map<String,Object> params){
		
		Education edu = new Education(getBaseURL());
		List<String> ids = edu.getEducations();
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			Assert.fail("当前没有添加任何教育背景，无法编辑");
		}
		
		params.put("token", "");
		params.put("id", id);
		params = edu.setParams(params);
		setRequest("education", params);
		
		checkResponse(edu.getParams());
	}
	
	@Test(description = "删除教育背景信息")
	@Description("删除教育背景信息")
	public void del_Education_Test(){
		Education education = new Education(getBaseURL());
		List<String> list = education.getEducations();
		for (int i = 0; i < list.size(); i++) {
			education.delEducation(getBaseURL(), list.get(i));
		}
	}
}
