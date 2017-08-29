package com.exercise.rest_assured;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;

public class AssuredTest extends BaseTest {
	
	@Test(dataProvider = "CaseList",description="南昌人力资源项目PC")
	@Description("南昌人力资源项目PC端接口测试")
	@Issue("043")
    @Issue("044")
    @Issue("045")
	public void NanChang_API_Test(Object[] data) {
		String api = data[0].toString();
		String filePath = data[1].toString();
		String caseName = data[2].toString();
		request(api,filePath, caseName);
	}
}
