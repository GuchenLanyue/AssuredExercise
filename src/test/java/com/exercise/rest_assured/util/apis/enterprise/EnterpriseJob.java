package com.exercise.rest_assured.util.apis.enterprise;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.exercise.rest_assured.util.apis.person.BaseInfo;

import io.qameta.allure.Step;

public class EnterpriseJob {
	private Map<String,Object> jobParams = new HashMap<>();
	
	@Step
	public Map<String,Object> setParams(String url,Map<String,Object> params){
		Map<String,Object> param = new HashMap<>();
		param = params;
		BaseInfo info = new BaseInfo(url);
		
		int education = info.getEducation();
		jobParams.put("education", education);
		info.setworklife();
		jobParams.put("experience", info.getWorklife());
		info.setSalary();
		jobParams.put("salary", info.getSalary());
		int[] positionData = info.setPosition();
		jobParams.put("category", positionData[0]);
		jobParams.put("categorys", positionData[1]);
		int[] area = info.setArea();
		jobParams.put("provice", area[0]);
		jobParams.put("city", area[1]);
		if (area.length==3) {
			jobParams.put("country", "");
			param.remove("country");
		}
		jobParams.put("number", new Random().nextInt(100)+1);
		jobParams.put("position_nature", new Random().nextInt(3)+1);
		
		for(String key:jobParams.keySet()){
			param.put(key, jobParams.get(key));
		}
		
		return param;
	}
	
	public Map<String, Object> getParams(){
		return jobParams;
	}
}
