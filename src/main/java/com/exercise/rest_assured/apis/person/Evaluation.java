package com.exercise.rest_assured.apis.person;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.utils.HttpMethods;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Evaluation {
	private String url = null;
	
	public Evaluation(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	@Step("getEvaluation() 获取自我评价")
	public String getEvaluation(){

		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		//baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/getevaluation");
		Map<String, Object> paramsMap = new HashMap<>();
		//paramsMap.put("token", "");
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/getevaluation.Response.body:", body);
			Assert.fail("/personresume/getevaluation 请求失败！");
		}
		
		String body = response.asString();
		
		body = body.substring(body.indexOf("{"), body.lastIndexOf("}")+1);
		
		JsonPath json = new JsonPath(body);
		Map<String, Object> value = json.getMap("value");
		if (value.containsKey("id")) {
			return value.get("id").toString();
		}else{
			return null;
		}
	}
}
