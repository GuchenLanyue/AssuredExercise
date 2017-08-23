package com.exercise.rest_assured;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;

public class AssuredTest extends BaseTest {
	@Test(dataProvider = "CaseList", description = "南昌人力")
	public void nanchangPC(Object[] data) {

		String filePath = data[1].toString();
		String caseName = data[2].toString();
		request(filePath, caseName);
	}
}
