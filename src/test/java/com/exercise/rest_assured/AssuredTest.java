package com.exercise.rest_assured;

import java.util.List;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.DelEducation;
import com.exercise.rest_assured.util.GetEducations;
import com.exercise.rest_assured.utils.TextData;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;

public class AssuredTest extends BaseTest {
	
//	@Test(dataProvider = "CaseList",description="南昌人力资源项目PC")
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
	
	@Test
	@Description("删除教育背景信息")
	public void deleducationTest(){
		GetEducations educations = new GetEducations();
		TextData data = new TextData();
		String token = data.readTxtFile("C:\\Users\\sam\\git\\AssuredExercise\\src\\test\\resources\\case\\token.txt");
		List<String> list = educations.getEducationList(token);
		DelEducation delEdu = new DelEducation();
		for (int i = 0; i < list.size(); i++) {
			delEdu.deleducation(token, list.get(i));
		}
	}
}
