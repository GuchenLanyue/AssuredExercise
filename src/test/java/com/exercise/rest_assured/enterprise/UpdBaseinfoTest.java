package com.exercise.rest_assured.enterprise;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;

public class UpdBaseinfoTest extends BaseTest {
	
	@Test(dataProvider="SingleCase")
	public void updbaseinfo_Test(Map<String, Object> params){
		setRequest("updbaseinfo", params);
	}
}
