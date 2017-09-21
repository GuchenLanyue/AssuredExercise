package com.exercise.rest_assured.person;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.person.Job;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.path.json.JsonPath;

public class JobTest extends BaseTest{
	
	@Test(dataProvider="SingleCase",description="增加工作经验")
	@Issue("080")
	public void add_Job_Test(Map<String, Object> params){
		Job job = new Job(getBaseURL());
		params.put("position", job.getPosition());
		params.put("positions", job.getPositions());
		params.put("token", getToken());
		setRequest("job", params);
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		int position = Integer.valueOf(json.getString("position")).intValue();
		int positions = Integer.valueOf(json.getString("positions")).intValue();
		
		Assert.assertTrue(job.getPosition()==position);
		Assert.assertTrue(job.getPositions()==positions);
		
		checkResponse();
	}
	
	@Test(dataProvider="SingleCase",description="修改工作经验",dependsOnMethods={"add_Job_Test"})
	public void edit_Job_Test(Map<String, Object> params){
		Job job = new Job(getBaseURL());
		List<String> ids = job.getJobs(getBaseURL(), getToken());
		
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			Assert.fail("当前没有添加任何工作经验，无法编辑");
		}
		
		params.put("token", getToken());
		params.put("id", id);
		
		setRequest("job", params);
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		id = json.getString("id");
		checkResponse();
	}
	
	@Test(description = "删除工作经验",dependsOnMethods={"add_Job_Test"})
	@Description("删除工作经验")
	public void delJobTest(){
		Job job = new Job(getBaseURL());
		List<String> list = job.getJobs(getBaseURL(), getToken());
		for (int i = 0; i < list.size(); i++) {
			job.delJob(getToken(), list.get(i));
		}
	}
}
