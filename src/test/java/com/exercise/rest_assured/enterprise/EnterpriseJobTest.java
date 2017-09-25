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
		setRequest("addjob", job.setParams(getBaseURL(), params));
		String path = getSrcDir()+getExpectedJson();
		int id = Integer.valueOf(job.getUserJobList(1).get(0)).intValue();
		JsonPath response = job.getUserJobShow(id);
		job.checkInfo(path, response);
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
}
