package com.exercise.rest_assured.apis.person;

import static io.restassured.path.json.JsonPath.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exercise.rest_assured.utils.HttpMethods;
import com.exercise.rest_assured.utils.testutils.Parameter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class Job {
	
	private BaseInfo baseInfo = null;
	private int[] position =null;
	private String url = null;
	
	public Job(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
		baseInfo = new BaseInfo(url);
		baseInfo.setPosition();
		position = setPosition();
		baseInfo = new BaseInfo(url);
	}
	
	public void addJob(Map<String, String> params){
		
	}
	
	@Step
	public List<String> getJobs(){
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/getjobs");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");

		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		String json = response.getBody().asString();
		json = json.substring(json.indexOf("{"), json.lastIndexOf("}")+1);
		
		List<String> jobIDs = from(json).getList("value.id");
		
		return jobIDs;
	}

	@Step
	@Description("获取工作经验")
	public String getJob(String baseURL,String token,String id,String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\getJob.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "getjob");
		baseMap.put("baseURL", baseURL);
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", token);
		paramsMap.put("id", id);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("删除工作经验")
	public void delJob(String id) {
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/deljob");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");
		paramsMap.put("id", id);
		HttpMethods http = new HttpMethods();
		http.request(baseMap, paramsMap);
	}
	
	public int[] setPosition() {
		
		return baseInfo.getPositionData();
	}
	
	public int getPosition() {
		return position[0];
	}
	
	public int getPositions() {
		return position[1];
	}
		
}
