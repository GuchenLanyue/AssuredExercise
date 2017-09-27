package com.exercise.rest_assured.testcase.person;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Job;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.path.json.JsonPath;

public class JobTest extends BaseTest{
	
	@Test(dataProvider="SingleCase",description="增加工作经验")
	@Issue("080")
	public void add_Job_Test(Map<String, Object> params){
		Job job = new Job(getBaseURL());
		setRequest("job", job.setParams(params));
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		int position = Integer.valueOf(json.getString("position")).intValue();
		int positions = Integer.valueOf(json.getString("positions")).intValue();
		
		Assert.assertTrue(job.getPosition()==position);
		Assert.assertTrue(job.getPositions()==positions);
		
		checkResponse();
	}
	
	@Test(dataProvider="SingleCase",description="修改工作经验")
	public void edit_Job_Test(Map<String, Object> params){
		Job job = new Job(getBaseURL());
		List<String> ids = job.getJobs();
		
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			Assert.fail("当前没有添加任何工作经验，无法编辑");
		}
		
		params.put("token", "");
		params.put("id", id);
		
		setRequest("job", params);
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		id = json.getString("id");
		checkResponse();
	}
	
	@Test(dataProvider="SingleCase",description = "删除工作经验")
	@Description("删除工作经验")
	public void del_Job_Test(Map<String, Object> params){
		Job job = new Job(getBaseURL());
		List<String> list = job.getJobs();
		if(list.size()==0){
			job.addJob(params);
			list = job.getJobs();
		}
		Random random = new Random();
		int index = random.nextInt(list.size());
		String id = list.get(index);
		job.delJob(id);
	}
	
	public void clean(){
		Job job = new Job(getBaseURL());
		job.cleanJobs();
	}
}
