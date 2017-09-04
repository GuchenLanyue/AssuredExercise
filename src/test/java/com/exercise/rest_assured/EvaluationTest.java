package com.exercise.rest_assured;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Evaluation;

public class EvaluationTest extends BaseTest{

	@Test(dataProvider = "SingleCase",description="创建或修改自我评价")
	public void evaluationTest(Map<String, Object> params){
		Evaluation evaluation = new Evaluation();
		String id = evaluation.getEvaluation(getToken());
		if (id==null) {
			params.remove("id");
		}else{
			params.put("id", id);
		}
		params.put("token", getToken());
		setParams("evaluation", params);
	}
}
