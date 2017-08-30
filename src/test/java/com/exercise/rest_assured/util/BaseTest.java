package com.exercise.rest_assured.util;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.exercise.rest_assured.util.apis.Login;
import com.exercise.rest_assured.utils.ExcelReader;
import com.exercise.rest_assured.utils.JsonUtils;
import com.exercise.rest_assured.utils.TextData;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;

public class BaseTest {
	private String srcDir = null;
	private String token = null;
	
	public enum RequestMethod {
		POST, GET
	}
	
	public enum Platform{
		GuanWang,ZhongDuan,HouTai,WeiZhan
	}
	
	public String setSrcDir(ITestContext context){
		return System.getProperty("user.dir")+context.getCurrentXmlTest().getParameter("sourcesDir");
	}
	
	public String getSrcDir(){
		return srcDir;
	}
	
	public String getToken(){
		return token;
	}
	
	@BeforeTest
	public void BeforeTest(ITestContext context) {
		System.out.println("开始测试");
		srcDir = setSrcDir(context);
		Login login = new Login();
		User user = new User();
		Platform platform = null;
		String platformStr = context.getCurrentXmlTest().getParameter("platform");
		if (platformStr.equals("GuanWang")){
			platform = Platform.GuanWang;
		}else if (platformStr.equals("ZhongDuan")) {
			platform = Platform.ZhongDuan;
		}else if (platformStr.equals("HouTai")) {
			platform = Platform.HouTai;
		}else if (platformStr.equals("WeiZhan")) {
			platform = Platform.WeiZhan;
		}else {
			Assert.fail("平台设置错误："+platformStr+"，请在testng.xml中重新设置。");
		}
		
		switch (platform) {
		case GuanWang:
			login.gwLogin(user);
			break;

		default:
			break;
		}
		
		TextData data = new TextData();
		token = data.readTxtFile(srcDir+"\\case\\token.txt");
	}
	
	@DataProvider(name = "CaseList")
	public Iterator<Object[]> caseData(ITestContext context) {
		String casePath = getSrcDir()+"\\case\\";
		String filePath = casePath + "CaseList.xlsx";
		String sheetName = "CaseList";
		ExcelReader excel = new ExcelReader();
		List<Map<String, String>> caseList = excel.mapList(1,filePath, sheetName);
		List<Object[]> test_IDs = new ArrayList<Object[]>();

		for (Map<String, String> baseData:caseList) {
			test_IDs.add(new Object[]{baseData});
		}
		
		return test_IDs.iterator();
	}
	
	@DataProvider(name = "SingleCase")
	public Iterator<Object[]> singleCase(Method method) {
		String filePath = getSrcDir()+"/case/"+method.getName()+".xlsx";
		String sheetName = "Params";
		ExcelReader excel = new ExcelReader();
		List<Map<String, String>> caseList = excel.mapList(1,filePath, sheetName);
		
		List<Object[]> test_IDs = new ArrayList<Object[]>();
		for (Map<String, String> params:caseList) {
			test_IDs.add(new Object[]{params});
		}
		
		return test_IDs.iterator();
	}
	
	@Step
	public void setParams(String api,String filePath, String caseName) {
		
		ExcelReader baseExcel = new ExcelReader(filePath, "Base", api);
		Map<String, String> baseMap = baseExcel.getCaseMap();

		ExcelReader paramsExcel = new ExcelReader(filePath, "Params", caseName);
		Map<String, String> paramsMap = paramsExcel.getCaseMap();
		paramsMap.remove("Case");
		
		if(paramsMap.containsKey("token")){	
			paramsMap.put("token", token);
		}
		
		ExcelReader expectedExcel = new ExcelReader(filePath, "Expected", caseName);
		Map<String, String> expectedMap = expectedExcel.getCaseMap();
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		saveResponseBody(response);
		equalResponse(response.getBody().asString(), expectedMap.get("Path"));
	}

	
	
	@Step
	public void equalResponse(String response, String path) {
		
		//部分接口返回的response首字母不是“{”，不符合json格式，在这里处理一下
		while (response.charAt(0) != '{') {
			response = response.substring(1, response.length());
		};

		JsonPath jsonPath = new JsonPath(response);

		String jsonFile = getSrcDir() + path;
		JsonUtils jsonUtil = new JsonUtils();
		
		jsonUtil.equalsJson(jsonFile, jsonPath);
	}
	
	@Attachment(value = "Response.Body",type = "String")
	public String saveResponseBody(Response response) {
		String body = response.getBody().asString();
		Allure.addAttachment("Response.body", body);
	    return body;
	}
	
	@AfterTest
	public void AfterTest() {
		System.out.println("测试完毕");
	}
}
