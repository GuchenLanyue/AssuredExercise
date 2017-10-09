package com.exercise.rest_assured.apis.person;

import static io.restassured.path.json.JsonPath.from;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	}
	
	public Response addJob(Map<String, Object> params){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/job");
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
		
		return response;
	}
	
	public Map<String, Object> setParams(Map<String, Object> param){
		Map<String, Object> params = new HashMap<>();
		params = param;
		params.put("position", getPosition());
		params.put("positions", getPositions());
		List<String> dates = new ArrayList<>();
		dates = getDate();
		params.put("start_time", dates.get(0));
		params.put("end_time", dates.get(1));
		
		return params;
	}
	
	@Description("获取工作经验")
	@Step
	public List<String> getJobs(){
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/getjobs");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");

		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
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
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
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
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods();
		http.request(map);;
	}
	
	@Step
	@Description("清空所有工作经验")
	public void cleanJobs(){
		for(String id:getJobs()){
			delJob(id);
		}
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
	
	public List<String> getDate(){
		List<String> dates = new ArrayList<>();
		Date startDate =  baseInfo.randomDate("2000-01-01", "2017-01-01");
		Date endDate = baseInfo.randomDate((new SimpleDateFormat("yyyy-MM-dd")).format(startDate), "2017-09-01");
		
		dates.add(startDate.toString());
		dates.add(endDate.toString());
		
		return dates;
	}
		
}
