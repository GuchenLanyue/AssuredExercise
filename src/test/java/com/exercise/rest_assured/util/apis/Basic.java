package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Basic {
	private int id = 0;
	private int sex = 0;
	private int education = 1;
	private int[] area = null;
	private int h_province = 1;
	private int h_city = 1;
	private int h_district = 1;
	private int l_province = 1;
	private int l_city = 1;
	private int l_district = 1;
	private String healthy = null;
	private String work_life = null;
	private String lang = null;
	private int leve = 1;
	private String lang1 = null;
	private int leve1 = 1;
	private String marriage = null;
	private String birth = null;
	private String id_code = null;
	private Map<String, Object> basicParams = new HashMap<>();
	private int current = 1;
	public Basic() {
		// TODO Auto-generated constructor stub
	}
	
	public void setValue(){
		BaseInfo baseinfo = new BaseInfo();
		sex = baseinfo.getSex();
		education = baseinfo.getEducation();
		healthy = baseinfo.getHealthy();
		work_life = baseinfo.getWorklife();
		lang = baseinfo.getGoodatlanguage();
		leve = baseinfo.getLeve();
		baseinfo.setgoodatlanguage();
		lang1 = baseinfo.getGoodatlanguage();
		leve1 = baseinfo.getLeve();
		marriage = baseinfo.getMaritalstatus();
		birth = baseinfo.randomDate("1990-1-1", "2017-9-8").toString();
		id_code = baseinfo.getRandomID();
		current = baseinfo.getCurrentstate();
	}
	
	public Map<String, Object> setParams(Map<String, Object> params){
		setValue();
		basicParams.put("sex", sex);
		basicParams.put("education", education);
		
		setArea();
		h_province = area[0];
		h_city = area[1];
		if (area.length==3) {
			h_district = area[2];
		}
		basicParams.put("h_province", h_province);
		basicParams.put("h_city", h_city);
		basicParams.put("h_district", h_district);
		
		setArea();
		l_province = area[0];
		l_city = area[1];
		if (area.length==3) {
			l_district = area[2];
		}
		basicParams.put("l_province", l_province);
		basicParams.put("l_city", l_city);
		basicParams.put("l_district", l_district);
		
		basicParams.put("healthy", healthy);
		basicParams.put("work_life", work_life);
		basicParams.put("marriage", marriage);
		basicParams.put("lang", lang);
		basicParams.put("leve", leve);
		basicParams.put("lang1", lang1);
		basicParams.put("leve1", leve1);
		basicParams.put("birth", birth);
		basicParams.put("id_code", id_code);
		basicParams.put("current", current);
		Map<String, Object> param = new HashMap<>();
		param = params;
		for(String key:basicParams.keySet()){
			param.put(key, basicParams.get(key));
		}
		
		return param;
	}
	
	public JsonPath getbasic(String token){
		Response response = given()
				//.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token", token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/getbasic")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/getbasic.Response.body:", body);
			Assert.fail("/personresume/getbasic 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		JsonPath jsonpath = new JsonPath(json).setRoot("value");
		
		id = jsonpath.getInt("id");
		
		return jsonpath;
	}
	
	public int[] setArea(){
		BaseInfo baseinfo = new BaseInfo();
		area = baseinfo.getArea();
		return area;
	}
	
	public int getID(){
		return id;
	}
	
	public void checkBasic(JsonPath response){
		for (Map.Entry<String,Object> mapEntry:basicParams.entrySet()) {
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
