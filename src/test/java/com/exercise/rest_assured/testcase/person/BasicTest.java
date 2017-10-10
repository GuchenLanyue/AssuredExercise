package com.exercise.rest_assured.testcase.person;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Basic;
import com.exercise.rest_assured.utils.testutils.BaseTest;

public class BasicTest extends BaseTest{
	@Test(dataProvider="SingleCase",description="创建基本信息")
	public void add_Basic_Test(Map<String, Object> params){
		Basic basic = new Basic(getbasePath());
		Map<String, Object> basicParams = basic.setParams(params);
		basicParams.put("token", "");
		setRequest("basic", basicParams);
		checkResponse(basic.getParams());
	}
	
	@Test(dataProvider="SingleCase",description="修改基本信息")
	public void edit_Basic_Test(Map<String, Object> params){
		Basic basic = new Basic(getbasePath());
		basic.getbasic();
		Map<String, Object> basicParams = null;
		basicParams = basic.setParams(params);
		basicParams.put("id", basic.getID());
		setRequest("basic", basicParams);
		checkResponse(basic.getParams());
	}
}
