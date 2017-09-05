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
	private String enterprisenature = null;
	private String companysize = null;
	private String joblevel = null;
	private String healthy = null;
	private String worklife = null;
	private String currentstate = null;
	private String goodatlanguage = null;
	private String maritalstatus = null;

	@Step("getenterprisenature() 获取企业性质")
	@Description("获取企业性质")
	public String getenterprisenature(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getenterprisenature")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getenterprisenature.Response.body:", body);
			Assert.fail("/basics/getenterprisenature 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setenterprisenature() 设置企业性质")
	@Description("设置企业性质")
	public void setenterprisenature(){
		JsonPath json = new JsonPath(getenterprisenature());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		enterprisenature = keys.get(index);
	}
	
	@Step("getcompanysize() 获取公司规模")
	@Description("获取公司规模")
	public String getcompanysize(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getcompanysize")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getcompanysize.Response.body:", body);
			Assert.fail("/basics/getcompanysize 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setcompanysize() 设置公司规模")
	@Description("设置公司规模")
	public void setcompanysize(){
		JsonPath json = new JsonPath(getcompanysize());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		companysize = keys.get(index);
	}
	
	@Step("getjoblevel() 获取职位级别")
	@Description("获取职位级别")
	public String getjoblevel(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getjoblevel")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getjoblevel.Response.body:", body);
			Assert.fail("/basics/getjoblevel 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setjoblevel() 设置职位级别")
	@Description("设置职位级别")
	public void setjoblevel(){
		JsonPath json = new JsonPath(getjoblevel());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		joblevel = keys.get(index);
	}
	
	@Step("gethealthy() 获取健康状况")
	@Description("获取健康状况")
	public String gethealthy(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/gethealthy")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/gethealthy.Response.body:", body);
			Assert.fail("/basics/gethealthy 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("sethealthy() 设置健康状况")
	@Description("设置健康状况")
	public void sethealthy(){
		JsonPath json = new JsonPath(gethealthy());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		healthy = keys.get(index);
	}
	
	@Step("getworklife() 获取工作年限")
	@Description("获取工作年限")
	public String getworklife(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getworklife")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getworklife.Response.body:", body);
			Assert.fail("/basics/getworklife 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setworklife() 设置工作年限")
	@Description("设置工作年限")
	public void setworklife(){
		JsonPath json = new JsonPath(getworklife());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		worklife = keys.get(index);
	}
	
	@Step("getgoodatlanguage() 获取擅长外语")
	@Description("获取擅长外语")
	public String getgoodatlanguage(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getgoodatlanguage")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getgoodatlanguage.Response.body:", body);
			Assert.fail("/basics/getgoodatlanguage 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setgoodatlanguage() 设置擅长外语")
	@Description("设置擅长外语")
	public void setgoodatlanguage(){
		JsonPath json = new JsonPath(getgoodatlanguage());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		goodatlanguage = keys.get(index);
	}
	
	
	@Step("getmaritalstatus() 获取擅长外语")
	@Description("获取擅长外语")
	public String getmaritalstatus(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getmaritalstatus")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getmaritalstatus.Response.body:", body);
			Assert.fail("/basics/getmaritalstatus 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setmaritalstatus() 设置擅长外语")
	@Description("设置擅长外语")
	public void setmaritalstatus(){
		JsonPath json = new JsonPath(getmaritalstatus());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		maritalstatus = keys.get(index);
	}
	
	@Step("getcurrentstate() 获取当前状态")
	@Description("获取当前状态")
	public String getcurrentstate(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getcurrentstate")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getcurrentstate.Response.body:", body);
			Assert.fail("/basics/getcurrentstate 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setcurrentstate() 设置当前状态")
	@Description("设置当前状态")
	public void setcurrentstate(){
		JsonPath json = new JsonPath(getenterprisenature());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		currentstate = keys.get(index);
	}
	
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
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
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
	
	@Description("获取当前状况")
	public String getCurrentstate() {
		return currentstate;
	}
	
	@Description("获取擅长外语")
	public String getGoodatlanguage() {
		return goodatlanguage;
	}
	
	@Description("获取婚育状况")
	public String getMaritalstatus() {
		return maritalstatus;
	}
	
	@Description("获取工作年限")
	public String getWorklife() {
		return worklife;
	}

	@Description("获取健康状况")
	public String getHealthy() {
		return healthy;
	}

	@Description("获取企业规模")
	public String getCompanysize() {
		return companysize;
	}
	
	@Description("获取企业性质")
	public String getEnterprisenature() {
		return enterprisenature;
	}
	
	@Description("获取地区-省")
	public int getProvice() {
		return provice;
	}

	@Description("获取地区-市")
	public int getCity() {
		return city;
	}

	@Description("获取地区-区")
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
	
	@Description("获取期望薪资")
	public String getSalary(){
		return salary;
	}
	
	@Description("获取职位级别")
	public String getJoblevel() {
		return joblevel;
	}
}
