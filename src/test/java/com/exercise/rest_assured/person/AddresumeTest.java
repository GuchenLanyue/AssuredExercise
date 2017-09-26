package com.exercise.rest_assured.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.util.apis.Login;
import com.exercise.rest_assured.util.apis.admin.Examine;
import com.exercise.rest_assured.util.apis.enterprise.EnterpriseJob;
import com.exercise.rest_assured.util.apis.person.Delivery;

import io.restassured.path.json.JsonPath;

public class AddresumeTest extends BaseTest{
	
	@Test(dataProvider="SingleCase")
	public void add_Resume_Test(Map<String, Object> params){
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		String job_id = null;
		
		if(job.getUserJobList(2).size()==0){
			EnterpriseJob eJob = new EnterpriseJob(getBaseURL());
			eJob.addJob();
			
			List<String> ids = new ArrayList<>();
			ids = eJob.getUserJobList(1);
			job_id = ids.get(0);
			JsonPath jobPath = eJob.getUserJobShow(Integer.valueOf(job_id).intValue());
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("jobID", jobPath.getString("id"));
			paramMap.put("title", jobPath.getString("title"));
			paramMap.put("des", jobPath.getString("des"));
			paramMap.put("status", 2);
			
			Login login = new Login();
			login.adminSingin(new User().getAdmin());
			Examine examine = new Examine();
			examine.job(login.getCookie(), paramMap);
		}else{
			job_id = job.getUserJobList(2).get(0);
		}
		
		Delivery delivery = new Delivery(getBaseURL());
		setRequest("addresume", delivery.setParams(job_id, params));
	}
}
