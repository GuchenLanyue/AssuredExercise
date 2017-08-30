package com.exercise.rest_assured;

import java.util.Map;

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
	public void NanChang_API_Test(Map<String, String> baseData) {
		String api = baseData.get("API");
		String filePath = getSrcDir()+"/case/"+baseData.get("FilePath");
		String caseName = baseData.get("Case");
		request(api,filePath, caseName);
	}
}
