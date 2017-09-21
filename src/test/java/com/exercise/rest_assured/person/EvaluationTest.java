package com.exercise.rest_assured.person;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.person.Evaluation;

public class EvaluationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="创建自我评价")
	public void add_Evaluation_Test(Map<String, Object> params){
		params.put("token", getToken());
		params.remove("id");
		setRequest("evaluation", params);
		checkResponse();
	}
	
	@Test(dataProvider = "SingleCase",description="修改自我评价",dependsOnMethods={"add_Evaluation_Test"})
	public void edit_Evaluation_Test(Map<String, Object> params){
		Evaluation evaluation = new Evaluation();
		String id = evaluation.getEvaluation(getBaseURL(),getToken());
		params.put("id", id);
		params.put("token", getToken());
		setRequest("evaluation", params);
		checkResponse();
	}
}
