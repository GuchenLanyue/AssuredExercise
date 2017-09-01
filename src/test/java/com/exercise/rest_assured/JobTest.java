package com.exercise.rest_assured;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Job;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.path.json.JsonPath;

public class JobTest extends BaseTest{
	
	@Test(dataProvider="SingleCase",description="增加工作经验")
	@Issue("080")
	public void addJobTest(Map<String, Object> params){
		Job job = new Job();
		params.put("position", job.getPosition());
		params.put("positions", job.getPositions());
		params.put("token", getToken());
		setParams("job", params);
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		int position = Integer.valueOf(json.getString("position")).intValue();
		int positions = Integer.valueOf(json.getString("positions")).intValue();
		
		Assert.assertTrue(job.getPosition()==position);
		Assert.assertTrue(job.getPositions()==positions);
		
		String id = json.getString("id");
		String actualJson = job.getJob(getToken(), id, getSrcDir());
		equalResponse(actualJson, getExpectedJson());
	}
	
	@Test(dataProvider="SingleCase",description="修改工作经验")
	public void editJobTest(Map<String, Object> params){
		Job job = new Job();
		List<String> ids = job.getJobs(getToken());
		
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			Assert.fail("当前没有添加任何工作经验，无法编辑");
		}
		
		params.put("token", getToken());
		params.put("id", id);
		
		setParams("job", params);
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		id = json.getString("id");
		String actualJson = job.getJob(getToken(), id, getSrcDir());
		equalResponse(actualJson, getExpectedJson());
	}
	
	@Test(description = "删除工作经验")
	@Description("删除工作经验")
	public void delJobTest(){
		Job job = new Job();
		List<String> list = job.getJobs(getToken());
		for (int i = 0; i < list.size(); i++) {
			job.delJob(getToken(), list.get(i));
		}
	}
}
