package com.exercise.rest_assured.util;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.exercise.rest_assured.util.apis.API_Category;
import com.exercise.rest_assured.utils.ExcelReader;
import com.exercise.rest_assured.utils.FileData;
import com.exercise.rest_assured.utils.JsonUtils;

import io.qameta.allure.Step;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;

public class BaseTest {
	private String srcDir = null;
	private String token = null;
	private String method = null;
	private String responseStr = null;
	private String expectedJson = null;
	private String baseURL = null;
	
	public enum RequestMethod {
		POST, GET
	}
	
	public enum Platform{
		GuanWang,ZhongDuan,HouTai,WeiZhan
	}
	
	public void setSrcDir(ITestContext context){
		srcDir = System.getProperty("user.dir")+context.getCurrentXmlTest().getParameter("sourcesDir");
	}
	
	public void setBaseURL(ITestContext context){
		baseURL = context.getCurrentXmlTest().getParameter("BaseURL");
	}
	
	@Step
	public String getSrcDir(){
		return srcDir;
	}
	
	@Step
	public String getPersonToken(){
		FileData data = new FileData();
		token = data.readTxtFile(srcDir+"\\case\\personToken.txt");
		return token;
	}
	
	public String getBaseURL(){
		return baseURL;
	}
	
	public String getBodyStr(){
		return responseStr;
	}
	
	public String getExpectedJson(){
		return expectedJson;
	}
	
	@BeforeTest
	public void BeforeTest(ITestContext context) {
		System.out.println(context.getName()+" Start!");
		setSrcDir(context);
		setBaseURL(context);
//		User user = new User();
//		Platform platform = null;
//		String platformStr = context.getCurrentXmlTest().getParameter("platform");
//		if (platformStr.equals("GuanWang")){
//			platform = Platform.GuanWang;
//		}else if (platformStr.equals("ZhongDuan")) {
//			platform = Platform.ZhongDuan;
//		}else if (platformStr.equals("HouTai")) {
//			platform = Platform.HouTai;
//		}else if (platformStr.equals("WeiZhan")) {
//			platform = Platform.WeiZhan;
//		}else {
//			Assert.fail("平台设置错误："+platformStr+"，请在testng.xml中重新设置。");
//		}
//		
//		String tokenPath = srcDir+"/case/token.txt";
//		File tokenFile = new File(tokenPath);
	}
	
	@DataProvider(name = "CaseList")
	public Iterator<Object[]> caseData(ITestContext context) {
		String casePath = getSrcDir()+"\\case\\";
		String filePath = casePath + "ProcessTest.xlsx";
		String sheetName = "CaseList";
		ExcelReader excel = new ExcelReader();
		List<Map<String, Object>> caseList = excel.mapList(1,filePath, sheetName);
		List<Object[]> test_IDs = new ArrayList<Object[]>();

		for (Map<String, Object> baseData:caseList) {
			test_IDs.add(new Object[]{baseData});
		}
		
		return test_IDs.iterator();
	}
	
	@DataProvider(name = "SingleCase")
	public Iterator<Object[]> singleCase(Method testMethod) {
		String methodName = testMethod.getName();
		String caseStr[] = methodName.split("_");
		method = caseStr[1];
		String filePath = getSrcDir()+"/case/"+method+".xlsx";
		String sheetName = "Params";
		ExcelReader excel = new ExcelReader();
		List<Map<String, Object>> caseList = excel.mapList(1,filePath, sheetName);
		List<Object[]> test_IDs = new ArrayList<Object[]>();
		for (Map<String, Object> params:caseList) {
			test_IDs.add(new Object[]{params});
		}
		
		return test_IDs.iterator();
	}
	
	@Step
	public void setRequest(String api,Map<String, Object> paramsMap) {
		String caseName = paramsMap.get("Case").toString();
		String file = getSrcDir()+"/case/"+method+".xlsx";
		Parameter parameter = new Parameter();
		Map<String, Object> baseMap = parameter.setUrlData(file, api);
		baseMap.put("baseURL", baseURL);
		API_Category path = new API_Category();
		if (paramsMap.containsKey("token")) {
			paramsMap.put("token", getPersonToken());
		}
		path.analysis(baseMap.get("path").toString());
		Map<String, Object> expectedMap = parameter.setExpectedMap(file, caseName);
		if (paramsMap.containsKey("Case")) {
			paramsMap.remove("Case");
		}
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		saveResponseBody(response);
		expectedJson = expectedMap.get("Path").toString();
	}

	@Step
	public void setRequest(String api,String filePath,String caseName) {
		Parameter parameter = new Parameter();
		Map<String, Object> baseMap = parameter.setUrlData(filePath, api);
		baseMap.put("baseURL", baseURL);
		
		Map<String, Object> paramsMap = parameter.setParams(filePath, caseName);
		if(paramsMap.containsKey("token")){	
			paramsMap.put("token", token);
		}

		Map<String, Object>expectedMap  = parameter.setExpectedMap(filePath, caseName);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap, paramsMap);
		saveResponseBody(response);
		expectedJson = expectedMap.get("Path").toString();
//		checkResponse(, expectedMap.get("Path").toString());
	}
	
	@Step("checkResponse() 校验response")
	public void checkResponse(String response, String path) {
		
		//部分接口返回的response首字母不是“{”，不符合json格式，在这里处理一下
		while (response.charAt(0) != '{') {
			response = response.substring(1, response.length());
		};

		JsonPath jsonPath = new JsonPath(response);

		String jsonFile = getSrcDir() + path;
		JsonUtils jsonUtil = new JsonUtils();
		
		jsonUtil.equalsJson(jsonFile, jsonPath);
	}
	
//	@Attachment(value = "Response.Body",type = "String")
	public String saveResponseBody(Response response) {
		String body = response.getBody().asString();
//		Allure.addAttachment("Response.body", body);
		
		while(body.charAt(0)!='{'){
			body = body.substring(1, body.length());
		}
		
		responseStr = body;
		return responseStr;
	}
	
	@AfterTest
	public void AfterTest(ITestContext context) {
		System.out.println(context.getName()+" End!");
	}
}
