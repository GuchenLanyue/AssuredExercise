package com.exercise.rest_assured.testcase.enterprise;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.admin.Examine;
import com.exercise.rest_assured.apis.enterprise.EnterpriseJob;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import io.restassured.path.json.JsonPath;

public class EnterpriseJobTest extends BaseTest {
	
	@Test(dataProvider="SingleCase")
	public void add_EnterpriseJob_Test(Map<String, Object> params){
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		setRequest("addjob", job.setParams(params));
		String path = getSrcDir()+getExpectedJson();
		String id = job.getUserJobList(1).get(0);
		JsonPath response = job.getUserJobShow(id);
		job.checkInfo(path, response);
		
		//后台审核
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("jobID", id);
		paramMap.put("title", response.getString("title"));
		paramMap.put("des", "不通过");
		paramMap.put("status", "4");
		
		Examine examine = new Examine();
		examine.job(paramMap);
	}
	
	@Test(dataProvider="SingleCase",description="更新职位")
	public void up_EnterpriseJob_Test(Map<String, Object> params){
		Map<String, Object> param = new HashMap<>();
		param = params;
		
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		if(job.getUserJobList(4).size()==0){
			job.addJob();
		}
		
		String id = job.getUserJobList(4).get(0);
		param.put("id", id);
		param.put("token", "");
		param.put("content", "重新发布职位");
		param.put("title", "编辑职位功能测试");
		param.put("address", "新华科技大厦A座15楼1501");
		job.upJob(param);
	}
	
	@Test
	public void up_EnterpriseJobStatus_Test(){		
		//后台审核
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		String id = job.getUserJobList(1).get(0);
		JsonPath jobShow = job.getUserJobShow(id);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("jobID", id);
		paramMap.put("title", jobShow.setRoot("value").getString("title"));
		paramMap.put("des", "通过");
		paramMap.put("status", "2");
		Examine examine = new Examine();
		examine.job(paramMap);

		job.upStatus(job.getUserJobList(2), "3");
	}
}
