package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BaseInfo {

	private String industry = null;
	private int positions = 1;
	private int position = 1;
	private int provice = 1;
	private int city = 1;
	private int district = 1;
	private String salary = null;
	
	@Description("获取行业列表")
	public String getindustry(){
		
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getindustry")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getindustry.Response.body:", body);
			Assert.fail("/basics/getindustry 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setIndustry() 设置行业")
	@Description("设置行业")
	public void setIndustry(){
		JsonPath json = new JsonPath(getindustry());
		Map<String, Object> industrys = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:industrys.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
        industry = keys.get(index);
	}
	
	@Step
	@Description("获取职位列表")
	public String getposition(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getposition")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setPositionData() 设置职位")
	@Description("设置职位")
	public void setPositionData(){
		JsonPath json = new JsonPath(getposition());
		List<String> positionList = json.getList("value.value");
		Random random = new Random();
		
		int index = random.nextInt(positionList.size());
        position = Integer.valueOf(positionList.get(index).toString()).intValue();
        
        String path = "value["+index+"].children";
        List<Map<String, String>> positionsList = json.getList(path);
        index = random.nextInt(positionsList.size());
        Map<String, String> children = positionsList.get(index);
        positions = Integer.valueOf(children.get("value")).intValue();
	}
	
	@Step
	@Description("获取期望薪资列表")
	public String getsalary(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getsalary")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getsalary.Response.body:", body);
			Assert.fail("/basics/getsalary 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setSalary() 设置期望薪资")
	@Description("设置期望薪资")
	public void setSalary(){
		JsonPath json = new JsonPath(getsalary());
		Map<String, Object> salarys = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:salarys.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		salary = keys.get(index);
	}
	
	
	@Description("获取地区")
	public String getArea(){
		Response response = given()
				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getarea")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step(value = "setArea() 设置地区")
	@Description("设置地区-随机值")
	public void setArea(){
		JsonPath json = new JsonPath(getArea());
		List<Object> provinceList = json.getList("value.value");
		Random random = new Random();
		if (provinceList.size()<1) {
			Assert.fail("地区数据-“省”的值为空！");
		}
		int proviceIndex = random.nextInt(provinceList.size());
		provice = Integer.valueOf(provinceList.get(proviceIndex).toString()).intValue();
        
        String cityPath = "value["+proviceIndex+"].children.value";
        List<String> cityList = json.getList(cityPath);
        if (cityList.size()<1) {
			Assert.fail("地区数据-“市”的值为空！");
		}
        int cityIndex = random.nextInt(cityList.size());
        city = Integer.valueOf(cityList.get(cityIndex).toString()).intValue();
        
        String districtPath = "value["+proviceIndex+"].children["+cityIndex+"].children.value";
        List<String> districtList = json.getList(districtPath);
        if (districtList.size()<1) {
			Assert.fail("地区数据-“区”的值为空！");
		}
        int districtIndex = random.nextInt(districtList.size());
        district = Integer.valueOf(districtList.get(districtIndex).toString()).intValue();
	}
	
	public int getProvice() {
		return provice;
	}

	public int getCity() {
		return city;
	}

	public int getDistrict() {
		return district;
	}

	@Description("获取当前设置的职位-父类")
	public int getPosition() {
		return position;
	}
	
	@Description("获取当前设置的职位-子类")
	public int getPositions() {
		return positions;
	}
	
	@Description("获取行业")
	public String getIndustry() {
		return industry;
	}
	
	public String getSalary(){
		return salary;
	}
}
