package com.exercise.rest_assured.util;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.exercise.rest_assured.utils.ExcelReader;
import com.exercise.rest_assured.utils.JsonUtils;
import com.exercise.rest_assured.utils.TextData;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;

public class BaseTest {
	private String srcDir = null;
	public enum Method {
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
	}
	
	@DataProvider(name = "CaseList")
	public Iterator<Object[]> caseData(ITestContext context) {
		System.out.println(context.getName());
		String casePath = getSrcDir()+"\\case\\";
		String filePath = casePath + "CaseList.xlsx";
		String sheetName = "CaseList";
		ExcelReader excel = new ExcelReader();
		List<Map<String, String>> caseList = excel.mapList(filePath, sheetName);
		List<Object[]> test_IDs = new ArrayList<Object[]>();

		for (int i = 1; i < caseList.size(); i++) {
			String api = caseList.get(i).get("API");
			String path = casePath + caseList.get(i).get("FilePath");
			String apiName = caseList.get(i).get("Case");

			if (!api.equals("") && !path.equals("") && !apiName.equals("")) {
				test_IDs.add(new Object[] { api, path, apiName });
			}
		}

		return test_IDs.iterator();
	}
	
	@Step
	public void request(String api,String filePath, String caseName) {
		
		ExcelReader baseExcel = new ExcelReader(filePath, "Base", api);
		Map<String, String> baseMap = baseExcel.getRowMap();

		ExcelReader paramsExcel = new ExcelReader(filePath, "Params", caseName);
		Map<String, String> paramsMap = paramsExcel.getRowMap();
		paramsMap.remove("Case");
		
		if(paramsMap.containsKey("token")){	
			TextData data = new TextData();
			String token = data.readTxtFile(getSrcDir()+paramsMap.get("token"));
			paramsMap.put("token", token);
		}
		
		ExcelReader expectedExcel = new ExcelReader(filePath, "Expected", caseName);
		Map<String, String> expectedMap = expectedExcel.getRowMap();

		Response response = null;
		Method method = null;

		if (baseMap.get("Method").equals("POST")) {
			method = Method.POST;
		} else if (baseMap.get("Method").equals("GET")) {
			method = Method.GET;
		} else {
			Assert.fail("目前只支持POST和GET方法");
		}

		switch (method) {
		case POST:
			response = given()
				.proxy("localhost", 8888)
//				.log().params()
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.params(paramsMap)
			.when()
				.post(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
			.then()
//				.log().body()
				.statusCode(200)
			.extract()
				.response();

			break;
		case GET:
			response = given()
				.proxy("localhost", 8888)
//				.log().params()
				.contentType(baseMap.get("contentType"))
				.params(paramsMap)
			.when()
				.get(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
			.then()
//				.log().body()
				.statusCode(200)
			.extract()
				.response();

			break;
		default:
			break;
		}
		
		saveResponseBody(response);
		equalResponse(response.getBody().asString(), expectedMap.get("Path"));
	}

	@Step
	public void equalResponse(String response, String path) {
		
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
