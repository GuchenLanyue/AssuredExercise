package com.exercise.rest_assured.apis.person;

import static io.restassured.path.json.JsonPath.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.utils.HttpMethods;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Intention {
	
	private String id = null;
	private Map<String, Object> intentionParam = new HashMap<>();
	int[] area = null;
	private int provice = 1;
	private int city = 1;
	private int district = 1;
	private int position = 1;
	private int positions = 1;
	private BaseInfo baseInfo = null;
	private String url = null;
	
	public Intention(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
		baseInfo = new BaseInfo(url);
	}
	
	@Step("新增求职意向")
	public String addIntentions(Map<String, Object> params){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/intention");

		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, setParams(params));
		
		return http.getBody(response);
	}
	
	@Step("getIntentions() 获取求职意向列表")
	public List<String> getIntentions(){
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/getintentions");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		
		String json = response.asString();
		
		json = json.substring(json.indexOf("{"), json.lastIndexOf("}")+1);
		
		List<String> intentionIDs = from(json).getList("value.id");
		
		return intentionIDs;
	}

	@Step
	@Description("获取求职意向")
	public String getIntention(String id){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/getintention");
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");
		paramsMap.put("id", id);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("删除求职意向")
	public void delIntention(String id) {		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/personresume/delintention");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", "");
		paramsMap.put("id", id);
		HttpMethods http = new HttpMethods();
		http.request(baseMap, paramsMap);
	}
	
	@Step
	@Description("清空所有求职意向")
	public void cleanIntentions(){
		for(String id:getIntentions()){
			delIntention(id);
		}
	}
	
	public void setID(){
		List<String> ids = getIntentions();

		if (ids.size() > 0) {
			id = ids.get(0);
		} else {
			Assert.fail("当前没有添加任何求职意向，无法编辑");
		}
	}
	
	@Step
	public Map<String, Object> setParams(Map<String, Object> params){
		Map<String, Object> param = new HashMap<>();
		param = params;
		baseInfo.setIndustry();
		intentionParam.put("industry", baseInfo.getIndustry());
		setPosition();
		intentionParam.put("position", position);
		intentionParam.put("positions", positions);
		baseInfo.setSalary();
		intentionParam.put("salary", baseInfo.getSalary());
		setArea();
		intentionParam.put("provice", provice);
		intentionParam.put("city", city);
		intentionParam.put("district", district);
		if (area.length==2) {
			intentionParam.remove("district");
			param.remove("district");
		}
		
		for(String key:intentionParam.keySet()){
			param.put(key, intentionParam.get(key));
		}
		
		return param;
	}
	
	public void setArea(){
		area = baseInfo.getArea();
		provice = area[0];
		city = area[1];
		if(area.length==3){
			district = area[2];
		}
	}
	
	public String getID(){
		return id;
	}
	
	public void setPosition(){
		int[] positionData = baseInfo.getPositionData();
		position = positionData[0];
		positions = positionData[1];
	}
	
	public Map<String, Object> getParams(){
		return intentionParam;
	}
	
	@Step("checkIntention() 校验求职意向")
	public void checkIntention(JsonPath response){
		for (Map.Entry<String,Object> mapEntry:intentionParam.entrySet()) {
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
