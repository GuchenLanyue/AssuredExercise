package com.exercise.rest_assured.util.apis.person;

import static io.restassured.path.json.JsonPath.from;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.Parameter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Train {
	private Map<String, Object> trainParam = new HashMap<>();
	private BaseInfo baseInfo = null;
	private String url = null;
	
	public Train(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
		baseInfo = new BaseInfo(url);
	}
	
	public List<String> getTrains(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/gettrains");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");

		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		
		String json = response.getBody().asString();
		json = json.substring(json.indexOf("{"), json.lastIndexOf("}")+1);
		
		List<String> trainIDs = from(json).getList("value.id");
		
		return trainIDs;
	}
	
	@Step
	@Description("获取培训信息")
	public String getTrain(String baseURL,String token,String id,String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\getTrain.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "gettrain");
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
	@Description("删除培训信息")
	public void delTrain(String id) {
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/deltrain");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");
		paramsMap.put("id", id);
		HttpMethods http = new HttpMethods();
		http.request(baseMap, paramsMap);
	}
	
	@Step
	public Map<String, Object> setParam(String baseURL,Map<String, Object> params){
		
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
