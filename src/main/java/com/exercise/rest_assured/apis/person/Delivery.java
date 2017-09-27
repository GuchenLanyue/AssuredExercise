package com.exercise.rest_assured.apis.person;

import java.util.HashMap;
import java.util.Map;

import com.exercise.rest_assured.utils.HttpMethods;

import io.qameta.allure.Step;

public class Delivery {
	private String url = null;
	
	public Delivery(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	@Step
	public Map<String, Object> setParams(String job_id, Map<String, Object> params){
		Map<String, Object> param = new HashMap<>();
		param = params;
		param.put("job_id", job_id);
		
		return param;
	}
	
	@Step("投递简历")
	public void addresume(String job_id){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/delivery/addresume");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");
		paramsMap.put("job_id", job_id);
		
		HttpMethods http = new HttpMethods();
		http.request(baseMap, paramsMap);
	}
}
