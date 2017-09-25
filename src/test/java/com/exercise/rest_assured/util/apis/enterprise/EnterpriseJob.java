package com.exercise.rest_assured.util.apis.enterprise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.apis.API_Category;
import com.exercise.rest_assured.util.apis.person.BaseInfo;
import com.exercise.rest_assured.utils.JsonUtils;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

public class EnterpriseJob {
	private Map<String,Object> jobParams = new HashMap<>();
	private String url = null;
	
	public EnterpriseJob(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	@Step
	public Map<String,Object> setParams(Map<String,Object> params){
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
		if (area.length<3) {
			param.remove("country");
		}else{
			jobParams.put("country", area[2]);
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
	
	public List<String> getUserJobList(int status){
		Map<String, Object> params = new HashMap<>();
		String path = "/job/getuserjoblist";
		API_Category apiPath = new API_Category();
		String token = apiPath.analysis(path);
		params.put("status", status);
		params.put("token", token);
		params.put("page", 1);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		HttpMethods http = new HttpMethods();
		
		String body = http.getBody(http.request(baseMap, params));
		
		JsonPath json = JsonPath.with(body).setRoot("value");
		List<String> ids = json.getList("list.id");
		
		return ids;
	}
	
	public JsonPath getUserJobShow(int id){
		Map<String, Object> params = new HashMap<>();
		String path = "/job/getuserjobshow";
		API_Category apiPath = new API_Category();
		String token = apiPath.analysis(path);
		params.put("token", token);
		params.put("id", id);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		HttpMethods http = new HttpMethods();
		
		String body = http.getBody(http.request(baseMap, params));
		
		JsonPath json = JsonPath.with(body).setRoot("value");
		
		return json;
	}
	
	public void upStatus(List<String> list,String status){
		String ids = null;
		if(list.size()==0){
			return;
		}
		
		for(String id:list){
			ids += (id+",");
		}
		ids = ids.substring(4, ids.length()-1);
		
		Map<String, Object> params = new HashMap<>();
		String path = "/job/upstatus";
		API_Category apiPath = new API_Category();
		String token = apiPath.analysis(path);
		params.put("token", token);
		params.put("ids", ids);
		params.put("status", status);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		HttpMethods http = new HttpMethods();
		
		http.getBody(http.request(baseMap, params));
	}
	
	public void checkInfo(String path,JsonPath responseJson){
		JsonUtils jutil = new JsonUtils();
		jutil.equalsJson(jobParams, path, responseJson);
	}
}
