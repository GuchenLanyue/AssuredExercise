package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.Parameter;

import io.qameta.allure.Allure;
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
	public Intention(String baseURL) {
		// TODO Auto-generated constructor stub
		baseInfo = new BaseInfo(baseURL);
	}
	
	@Step("getIntentions() 获取求职意向列表")
	public List<String> getIntentions(String token){
		
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token", token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/getintentions")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/getintentions.Response.body:", body);
			Assert.fail("/personresume/getintentions 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		List<String> intentionIDs = from(json).getList("value.id");
		
		return intentionIDs;
	}

	@Step
	@Description("获取求职意向")
	public String getIntention(String token, String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\getIntention.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "getintention");
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", token);
		paramsMap.put("id", id);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("删除求职意向")
	public void delIntention(String token, String id) {
		Response response = given()
//			.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/delintention")
		.then()
//			.statusCode(200)
		.extract()
			.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/delintention.Response.body:", body);
			Assert.fail("/personresume/delintention 请求失败！");
		}
	}
	
	public void setID(String token){
		List<String> ids = getIntentions(token);

		if (ids.size() > 0) {
			id = ids.get(0);
		} else {
			Assert.fail("当前没有添加任何求职意向，无法编辑");
		}
	}
	
	public Map<String, Object> setParams(String baseURL,Map<String, Object> params){
		Map<String, Object> param = new HashMap<>();
		param = params;
		baseInfo.setIndustry();
		intentionParam.put("industry", baseInfo.getIndustry());
		setPosition(baseURL);
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
	
	public void setPosition(String baseURL){
		int[] positionData = new BaseInfo(baseURL).getPositionData();
		position = positionData[0];
		positions = positionData[1];
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
