package com.exercise.rest_assured.util;

import java.util.Map;

import com.exercise.rest_assured.utils.ExcelReader;

import io.qameta.allure.Description;
import io.qameta.allure.Step;

public class Parameter {
	public Parameter() {
		// TODO Auto-generated constructor stub
	}
	
	@Step
	@Description("从excel中读取url数据")
	public Map<String, String> setUrlData(String filePath,String api){
		ExcelReader baseExcel = new ExcelReader(filePath, "Base", api);
		Map<String, String> baseMap = baseExcel.getCaseMap();
		
		return baseMap;
	}
	
	@Step
	@Description("从excel中读取params数据")
	public Map<String, String> setParams(String filePath,String caseName){
		ExcelReader paramsExcel = new ExcelReader(filePath, "Params", caseName);
		Map<String, String> paramsMap = paramsExcel.getCaseMap();
		paramsMap.remove("Case");
		
		return paramsMap;
	}
	
	@Step
	@Description("从excel中读取expected数据")
	public Map<String, String> setExpectedMap(String filePath,String caseName){
		ExcelReader expectedExcel = new ExcelReader(filePath, "Expected", caseName);
		Map<String, String> expectedMap = expectedExcel.getCaseMap();
		
		return expectedMap;
	}
}
