package com.exercise.rest_assured.apis.person;

import static io.restassured.path.json.JsonPath.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.utils.HttpMethods;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Education {
	private Map<String,Object> educationParam = new HashMap<>();
	private BaseInfo baseInfo = null;
	private String url = null;
	
	public Education() {
		// TODO Auto-generated constructor stub
	}
	
	public Education(String basePath) {
		// TODO Auto-generated constructor stub
		url = basePath;
		baseInfo = new BaseInfo(basePath);
	}
	
	public Map<String,Object> getParams(){
		return educationParam;
	}
	
	@Step
	public Map<String,Object> setParams(Map<String,Object> params){
		Map<String,Object> param = new HashMap<>();
//		String start_time = baseInfo.randomDate("1900-1-1", "2017-9-12");
		String end_time = baseInfo.randomDate("1900-1-1", "2017-9-12").toString();

		int education = baseInfo.getEducation();
		
		educationParam.put("end_time", end_time);
		int[] majorData = baseInfo.getMajor();
		if (majorData.length>1) {
			int magor = majorData[0];
			int magors = majorData[1];
			educationParam.put("magor", magor);
			educationParam.put("magors", magors);
		}else{
			int magor = majorData[0];
			educationParam.put("magor", magor);
		}

		educationParam.put("education", education);
		param = params;
		for(String key:educationParam.keySet()){
			param.put(key, educationParam.get(key));
		}
		
		return param;
	}
	
	@Step
	@Description("添加教育背景信息")
	public String addEducation(Map<String, Object> params){
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
//		baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/education");
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("修改教育背景信息")
	public Response editEducation(Map<String, Object> params,String id){
		getEducation(id);
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
//		baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/education");
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("id", id);
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		
		return response;
	}
	
	@Step
	@Description("获取教育背景信息")
	public String getEducation(String id){
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
//		baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/geteducation");

		Map<String, Object> paramsMap = new HashMap<>();
//		paramsMap.put("token", "");
		paramsMap.put("id", id);
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("获取教育背景信息列表")
	public List<String> getEducations(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
//		baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/geteducations");
		Map<String, Object> paramsMap = new HashMap<>();
//		paramsMap.put("token", "");
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/geteducations.Response.body:", body);
			Assert.fail("/personresume/geteducations 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		List<String> educationIDs = from(json).getList("value.id");
		
		return educationIDs;
	}
	
	@Step
	@Description("删除教育背景信息")
	public void delEducation(String id) {		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
//		baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/deleducation");
		Map<String, Object> paramsMap = new HashMap<>();
//		paramsMap.put("token", "");
		paramsMap.put("id", id);
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/deleducation.Response.body:", body);
			Assert.fail("/personresume/deleducation 请求失败！");
		}
	}
	
	@Step
	@Description("清空所有教育背景信息")
	public void cleanEducations(){
		for(String id:getEducations()){
			delEducation(id);
		}
	}
	public int[] majorData(String basePath){
		return new BaseInfo(basePath).getMajor();
	}
	
	@Step()
	public void checkEducation(JsonPath response){
		for (Map.Entry<String,Object> mapEntry:educationParam.entrySet()) {
			String actual = null;
			String expected = null;
			if (response.getString(mapEntry.getKey())!=null)
				actual = response.getString(mapEntry.getKey());
			
			if (mapEntry.getValue()!=null) 
				expected = mapEntry.getValue().toString();
			else{
				Assert.fail(mapEntry.getKey()+"的值为 null！");
			}
			
			Assert.assertEquals(actual, expected,"["+mapEntry.getKey()+"]:的预期值为："+expected+"，实际值为："+actual);
		}
	}
}
