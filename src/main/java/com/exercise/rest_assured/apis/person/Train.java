package com.exercise.rest_assured.apis.person;

import static io.restassured.path.json.JsonPath.from;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.utils.HttpMethods;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Train {
	private Map<String, Object> trainParam = new HashMap<>();
	private BaseInfo baseInfo = null;
	private String url = null;
	
	public Train(String basePath) {
		// TODO Auto-generated constructor stub
		url = basePath;
		baseInfo = new BaseInfo(url);
	}
	
	@Step("新增培训信息")
	public String addTrain(Map<String, Object> params){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		//baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/gettrain");

		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		
		return http.getBody(response);
	}
	
	@Description("获取培训信息列表")
	@Step
	public List<String> getTrains(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		//baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/gettrains");
		Map<String, Object> paramsMap = new HashMap<>();
		//paramsMap.put("token", "");

		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods(url);
		Response response = http.request(map);
		
		String json = response.getBody().asString();
		json = json.substring(json.indexOf("{"), json.lastIndexOf("}")+1);
		
		List<String> trainIDs = from(json).getList("value.id");
		
		return trainIDs;
	}
	
	@Step
	@Description("获取培训信息")
	public String getTrain(String id){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		//baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/gettrain");
		
		Map<String, Object> paramsMap = new HashMap<>();
		//paramsMap.put("token", "");
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
	@Description("删除培训信息")
	public void delTrain(String id) {
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		//baseMap.put("basePath", url);
		baseMap.put("path", "/personresume/deltrain");
		Map<String, Object> paramsMap = new HashMap<>();
		//paramsMap.put("token", "");
		paramsMap.put("id", id);
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", paramsMap);
		
		HttpMethods http = new HttpMethods(url);
		http.request(map);
	}
	
	@Step
	@Description("清空所有培训信息")
	public void cleanTrains(){
		for(String id:getTrains()){
			delTrain(id);
		}
	}
	
	@Step
	public Map<String, Object> setParam(Map<String, Object> params){
		
		int education = baseInfo.getEducation();
		Date start_time = baseInfo.randomDate("1900-1-1", "2017-9-12");
		Date end_time = baseInfo.randomDate((new SimpleDateFormat("yyyy-MM-dd")).format(start_time), "2017-9-12");
		trainParam.put("education", education);
		trainParam.put("start_time", start_time.toString());
		trainParam.put("end_time", end_time.toString());
		
		Map<String, Object> param = new HashMap<>();
		param = params;
		for(String key:trainParam.keySet()){
			param.put(key, trainParam.get(key));
		}
		
		return param;
	}
	
	public Map<String, Object> getParams(){
		return trainParam;
	}
	
	@Step()
	public void checkTrain(JsonPath response){
		for (Map.Entry<String,Object> mapEntry:trainParam.entrySet()) {
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
