package com.exercise.rest_assured;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Evaluation;

public class EvaluationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="创建自我评价")
	public void add_Evaluation_Test(Map<String, Object> params){
		params.put("token", getPersonToken());
		params.remove("id");
		setRequest("evaluation", params);
		checkResponse(getBodyStr(), getExpectedJson());
	}
	
	@Test(dataProvider = "SingleCase",description="修改自我评价",dependsOnMethods={"add_Evaluation_Test"})
	public void edit_Evaluation_Test(Map<String, Object> params){
		Evaluation evaluation = new Evaluation();
		String id = evaluation.getEvaluation(getBaseURL(),getPersonToken());
		params.put("id", id);
		params.put("token", getPersonToken());
		setRequest("evaluation", params);
		checkResponse(getBodyStr(), getExpectedJson());
	}
}
