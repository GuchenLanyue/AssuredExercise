package com.exercise.rest_assured.util.apis.enterprise;

import java.util.Map;
import java.util.Random;

import com.exercise.rest_assured.util.apis.person.BaseInfo;
import com.exercise.rest_assured.utils.JsonUtils;

import io.restassured.path.json.JsonPath;

public class EnterpriseBaseinfo {
	private Map<String, Object> param = null;
	
	public Map<String, Object> setParams(String url, Map<String, Object> params){
		BaseInfo baseinfo = new BaseInfo(url);
		param = params;
		baseinfo.setenterprisenature();
		param.put("nature", baseinfo.getEnterprisenature());
		baseinfo.setIndustry();
		param.put("industry", baseinfo.getIndustry());
		Random random = new Random();
		int settled = random.nextInt(1)+1;
		param.put("settled", settled);
		
		return param;
	}
	
	public void checkInfo(String path,JsonPath responseJson){
		JsonUtils jutil = new JsonUtils();
		jutil.equalsJson(param, path, responseJson);
	}
}
