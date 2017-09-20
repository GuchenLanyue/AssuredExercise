package com.exercise.rest_assured;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Basic;

import io.restassured.path.json.JsonPath;

public class BasicTest extends BaseTest{
	@Test(dataProvider="SingleCase",description="创建基本信息")
	public void add_Basic_Test(Map<String, Object> params){
		Basic basic = new Basic(getBaseURL());
		Map<String, Object> basicParams = basic.setParams(params);
		basicParams.put("token", getPersonToken());
		setRequest("basic", basicParams);
		checkResponse(getBodyStr(), getExpectedJson());
		basic.checkBasic(new JsonPath(getBodyStr()).setRoot("value"));
	}
	
	@Test(dataProvider="SingleCase",description="修改基本信息")
	public void edit_Basic_Test(Map<String, Object> params){
		Basic basic = new Basic(getBaseURL());
		basic.getbasic(getPersonToken());
		Map<String, Object> basicParams = null;
		basicParams = basic.setParams(params);
		basicParams.put("token", getPersonToken());
		basicParams.put("id", basic.getID());
		setRequest("basic", basicParams);
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		checkResponse(getBodyStr(), getExpectedJson());
		basic.checkBasic(json);
	}
}
