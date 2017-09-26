package com.exercise.rest_assured.testcase.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.Login;
import com.exercise.rest_assured.apis.admin.Examine;
import com.exercise.rest_assured.apis.enterprise.EnterpriseJob;
import com.exercise.rest_assured.apis.person.Basic;
import com.exercise.rest_assured.apis.person.Delivery;
import com.exercise.rest_assured.utils.testutils.BaseTest;
import com.exercise.rest_assured.utils.testutils.User;

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
		String body = job.getUserResume(job_id);

		int list_nums = JsonPath.with(body).setRoot("value").getInt("list_nums");
		if(list_nums>0){
			List<String> resumes = JsonPath.with(body).setRoot("value").getList("list.id");
			Basic basic = new Basic(getBaseURL());
			basic.getbasic();
			String id = String.valueOf(basic.getID());
			if(!resumes.contains(id)){
				Assert.fail("投递简历失败，在job_id:"+job_id+"中没有找到简历id："+basic.getID());
			}
		}
		
	}
}
