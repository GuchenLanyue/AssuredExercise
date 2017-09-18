package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.exercise.rest_assured.util.HttpMethods;
import com.exercise.rest_assured.util.Parameter;
import com.exercise.rest_assured.utils.FileData;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Education {
	private Map<String,Object> educationParam = new HashMap<>();
	
	public Education() {
		// TODO Auto-generated constructor stub
	}
	
	@Step
	public Map<String,Object> setParams(Map<String,Object> params){
		Map<String,Object> param = new HashMap<>();
		BaseInfo baseinfo = new BaseInfo();
//		String start_time = baseinfo.randomDate("1900-1-1", "2017-9-12");
		String end_time = baseinfo.randomDate("1900-1-1", "2017-9-12").toString();

		int education = baseinfo.getEducation();
		
		educationParam.put("end_time", end_time);
		int[] majorData = baseinfo.getMajor();
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
	public void addEducation(Map<String, Object> params,String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\addEducationTest.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "education");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, params);
		String body = http.getBody(response);
		
		JsonPath json = new JsonPath(body).setRoot("value");
		String id = json.getString("id");
		
		FileData textData = new FileData();
		String path = srcDir+"/case/";
		textData.writerText(path+"eduID.txt", id);
	}
	
	@Step
	@Description("修改教育背景信息")
	public Response editEducation(Map<String, Object> params,String srcDir){
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\editEducationTest.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "education");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, params);
		
		return response;
	}
	
	@Step
	@Description("删除教育背景信息")
	public void delEducation(String token, String id) {
		Response response = given()
//			.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/deleducation")
		.then()
//			.statusCode(200)
		.extract()
			.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/personresume/deleducation.Response.body:", body);
			Assert.fail("/personresume/deleducation 请求失败！");
		}
	}
	
	@Step
	@Description("获取教育背景信息")
	public String getEducation(String token,String id,String srcDir){
		
		Map<String, Object> baseMap = new HashMap<>();
		String file = srcDir + "\\case\\getEducation.xlsx";
		Parameter parameter = new Parameter();
		baseMap = parameter.setUrlData(file, "geteducation");
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("token", token);
		paramsMap.put("id", id);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		String body = http.getBody(response);
		
		return body;
	}
	
	@Step
	@Description("获取教育背景信息列表")
	public List<String> getEducations(String token){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.param("token",token)
			.when()
				.post("http://nchr.release.microfastup.com/nchr/personresume/geteducations")
			.then()
				.statusCode(200)
			.extract()
				.response();
		
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
	
	public int[] majorData(){
		return new BaseInfo().getMajor();
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
