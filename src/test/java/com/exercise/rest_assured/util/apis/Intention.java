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
	private BaseInfo baseInfo = new BaseInfo();
	private String id = null;
	private int[] positionData = setPosition();
	private int[] area = null;
	
	public Intention() {
		// TODO Auto-generated constructor stub
		//设置行业
		baseInfo.setIndustry();
		//设置职位
		baseInfo.setPosition();
		//设置地区
		baseInfo.setArea();
		//设置期望薪资
		baseInfo.setSalary();
		
		area = setArea();
	}
	
	@Step("getIntentions() 获取求职意向列表")
	public List<String> getIntentions(String token){
		
		Response response = given()
				.proxy("http://127.0.0.1:8888")
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
			.proxy("http://127.0.0.1:8888")
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
	
	@Step("checkIntention() 校验求职意向")
	public void checkIntention(String jsonStr){
		JsonPath json = new JsonPath(jsonStr).setRoot("value");
		int position = Integer.valueOf(json.getString("position")).intValue();
		int positions = Integer.valueOf(json.getString("positions")).intValue();
		String industry =json.getString("industry");
		String salary = json.getString("salary");
		int provice = Integer.valueOf(json.getString("provice")).intValue();
		int city = Integer.valueOf(json.getString("city")).intValue();
		int district = Integer.valueOf(json.getString("district")).intValue();
		id = json.getString("id");
		
		Assert.assertTrue(getPosition()==position,"position expected:"+getPosition()+"/"+position);
		Assert.assertTrue(getPositions()==positions,"positions expected:"+getPositions()+"/"+positions);
		Assert.assertTrue(getIndustry().equals(industry),"industry expected:"+getIndustry()+"/"+industry);
		Assert.assertTrue(getSalary().equals(salary),"salary expected:"+getSalary()+"/"+salary);
		Assert.assertTrue(getProvince()==provice,"provice expected:"+getProvince()+"/"+provice);
		Assert.assertTrue(getCity()==city,"city expected:"+getCity()+"/"+city);
		Assert.assertTrue(getDistrict()==district,"district expected:"+getDistrict()+"/"+district);
	}
	
	public void setID(String token){
		List<String> ids = getIntentions(token);

		if (ids.size() > 0) {
			id = ids.get(0);
		} else {
			Assert.fail("当前没有添加任何求职意向，无法编辑");
		}
	}
	
	@Description("获取行业")
	public String getIndustry() {

		return baseInfo.getIndustry();
	}
	
	public int[] setPosition() {
		
		return baseInfo.getPositionData();
	}
	
	public int[] setArea() {
		
		return baseInfo.getArea();
	}
	
	public int getPosition() {
		
		return positionData[0];
	}
	
	public int getPositions() {
		
		return positionData[1];
	}
	
	public int getProvince() {
		
		return area[0];
	}
	
	public int getCity() {
		
		return area[1];
	}
	
	public int getDistrict() {
		
		return area[2];
	}
	
	public String getSalary(){
		
		return baseInfo.getSalary();
	}
	
	public String getID(){
		return id;
	}
}
