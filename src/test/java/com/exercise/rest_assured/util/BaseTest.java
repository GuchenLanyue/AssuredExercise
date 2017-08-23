package com.exercise.rest_assured.util;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.exercise.rest_assured.utils.ExcelReader;
import com.exercise.rest_assured.utils.JsonUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class BaseTest {
	enum Method {
		POST, GET
	}

	@BeforeTest
	public void BeforeTest() {
		System.out.println("开始测试了");
	}

	@DataProvider(name = "CaseList")
	public Iterator<Object[]> caseData() {
		String workPath = System.getProperty("user.dir");
		String casePath = workPath + "\\resources\\case\\";
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

	public void request(String filePath, String caseName) {

		ExcelReader baseExcel = new ExcelReader(filePath, "Base", caseName);
		Map<String, String> baseMap = baseExcel.getRowMap();

		ExcelReader paramsExcel = new ExcelReader(filePath, "Params", caseName);
		Map<String, String> paramsMap = paramsExcel.getRowMap();

		ExcelReader expectedExcel = new ExcelReader(filePath, "Expected", caseName);
		Map<String, String> expectedMap = expectedExcel.getRowMap();

		Response response = null;
		Method Method = null;

		if (baseMap.get("Method").equals("POST")) {
			Method = Method.POST;
		} else if (baseMap.get("Method").equals("GET")) {
			Method = Method.GET;
		} else {
			Assert.fail("目前支持POST和GET方法");
		}

		switch (Method) {
		case POST:
			response = given().
				proxy("localhost", 8888)
				.log().all()
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
				.params(paramsMap)
			.when()
				.post(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
			.then()
				.log().all()
				.statusCode(200)
			.extract()
				.response();

			equalResponse(response, expectedMap.get("Path"));

			break;
		case GET:
			response = given()
				.proxy("localhost", 8888)
				.log().all()
				.contentType(baseMap.get("contentType"))
				.params(paramsMap)
			.when()
				.get(baseMap.get("Protocol") + "://" + baseMap.get("Host") + baseMap.get("path"))
			.then()
				.log().all()
				.statusCode(200)
				.extract().response();

			equalResponse(response, expectedMap.get("Path"));

			break;
		default:
			break;
		}
	}

	public void equalResponse(Response response, String path) {
		String str = response.getBody().asString();

		while (str.charAt(0) != '{') {
			str = str.substring(1, str.length());
		}
		;

		JsonPath jsonPath = new JsonPath(str);

		String jsonFile = System.getProperty("user.dir") + path;
		JsonUtils jsonUtil = new JsonUtils();

		jsonUtil.equalsJson(jsonFile, jsonPath);
	}

	@AfterTest
	public void AfterTest() {
		System.out.println("测试完毕");
	}
}
