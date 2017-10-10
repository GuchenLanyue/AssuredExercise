package com.exercise.rest_assured.testcase.person;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Education;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import io.qameta.allure.Description;

public class EducationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="创建教育背景信息")
	public void add_Education_Test(Map<String,Object> params){
		Education edu = new Education(getbasePath());
//		params.put("token", "");
		
		setRequest("education", edu.setParams(params));

		checkResponse(edu.getParams());
	}
	
	@Test(dataProvider = "SingleCase",description="编辑教育背景信息")
	@Description("修改教育背景信息")
	public void edit_Education_Test(Map<String,Object> params){
		
		Education edu = new Education(getbasePath());
		List<String> ids = edu.getEducations();
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			edu.addEducation(edu.setParams(params));
		}
		
		params.put("token", "");
		params.put("id", id);
		params = edu.setParams(params);
		setRequest("education", params);
		
		checkResponse(edu.getParams());
	}
	
	@Test(dataProvider = "SingleCase",description = "删除教育背景信息")
	@Description("删除教育背景信息")
	public void del_Education_Test(Map<String, Object> params){
		Education education = new Education(getbasePath());
		List<String> list = education.getEducations();
		if(list.size()==0){
			education.addEducation(params);
			list = education.getEducations();
		}
		Random random = new Random();
		int index = random.nextInt(list.size());
		String id = list.get(index);
		education.delEducation(id);
	}
	
	@Test
	public void clean(){
		Education education = new Education(getbasePath());
		education.cleanEducations();
	}
}
