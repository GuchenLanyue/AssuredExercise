package com.exercise.rest_assured;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Evaluation;

public class EvaluationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="创建自我评价")
	public void add_Evaluation_Test(Map<String, Object> params){
		params.put("token", getToken());
		setRequest("evaluation", params);
	}
	
	@Test(dataProvider = "SingleCase",description="修改自我评价")
	public void edit_Evaluation_Test(Map<String, Object> params){
		Evaluation evaluation = new Evaluation();
		String id = evaluation.getEvaluation(getToken());
		params.put("id", id);
		params.put("token", getToken());
		setRequest("evaluation", params);
	}
}
