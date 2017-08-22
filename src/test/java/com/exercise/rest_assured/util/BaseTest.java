package com.exercise.rest_assured.util;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.exercise.rest_assured.utils.ExcelReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterTest;

public class BaseTest {
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
			String path = casePath+caseList.get(i).get("FilePath");
			String apiName = caseList.get(i).get("Case");
			
			if (!api.equals("") && !path.equals("") && !apiName.equals("")) {
				test_IDs.add(new Object[] { api, path, apiName });
			}
		}
		
		return test_IDs.iterator();
	}

	@AfterTest
	public void AfterTest() {
		System.out.println("测试完毕");
	}
}
