package com.exercise.rest_assured.utils.testutils;

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
	public Map<String, Object> setUrlData(String filePath,String api){
		ExcelReader baseExcel = new ExcelReader(filePath, "Base", api);
		Map<String, Object> baseMap = baseExcel.getCaseMap();
		
		return baseMap;
	}
	
	@Step
	@Description("从excel中读取params数据")
	public Map<String, Object> setParams(String filePath,String caseName){
		ExcelReader paramsExcel = new ExcelReader(filePath, "Params", caseName);
		Map<String, Object> paramsMap = paramsExcel.getCaseMap();
		paramsMap.remove("Case");
		
		return paramsMap;
	}
	
	@Step
	@Description("从excel中读取expected数据")
	public Map<String, Object> setExpectedMap(String filePath,String caseName){
		ExcelReader expectedExcel = new ExcelReader(filePath, "Expected", caseName);
		Map<String, Object> expectedMap = expectedExcel.getCaseMap();
		
		return expectedMap;
	}
}
