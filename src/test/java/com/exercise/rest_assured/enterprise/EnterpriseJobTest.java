package com.exercise.rest_assured.enterprise;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.util.apis.Login;
import com.exercise.rest_assured.util.apis.admin.Examine;
import com.exercise.rest_assured.util.apis.enterprise.EnterpriseJob;

import io.restassured.path.json.JsonPath;

public class EnterpriseJobTest extends BaseTest {
	
	@Test(dataProvider="SingleCase")
	public void add_EnterpriseJob_Test(Map<String, Object> params){
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		setRequest("addjob", job.setParams(params));
		String path = getSrcDir()+getExpectedJson();
		int id = Integer.valueOf(job.getUserJobList(1).get(0)).intValue();
		JsonPath response = job.getUserJobShow(id);
		job.checkInfo(path, response);
		
		//后台审核
		Login login = new Login();
		login.adminSingin(new User().getAdmin());
		Examine examine = new Examine();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("jobID", id);
		paramMap.put("title", response.getString("title"));
		paramMap.put("des", "不通过");
		paramMap.put("status", "4");
		examine.job(login.getCookie(), paramMap);
	}
	
	@Test(dataProvider="SingleCase")
	public void up_EnterpriseJob_Test(Map<String, Object> params){
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		Map<String, Object> param = new HashMap<>();
		param.put("id", "");
		setRequest("upjob", job.setParams(param));
	}
	
	@Test
	public void up_EnterpriseJobStatus_Test(){
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		job.upStatus(job.getUserJobList(4), "1");
		
		//后台审核
		Login login = new Login();
		login.adminSingin(new User().getAdmin());
		Examine examine = new Examine();
		for(String id:job.getUserJobList(1)){
			Map<String, Object> paramMap = new HashMap<>();
			
			paramMap.put("jobID", id);
			paramMap.put("title", job.getUserJobShow(Integer.valueOf(id).intValue()).getString("title"));
			paramMap.put("des", "不通过");
			paramMap.put("status", "2");
			examine.job(login.getCookie(), paramMap);
		}
		
		job.upStatus(job.getUserJobList(2), "3");
	}
}
