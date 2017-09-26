package com.exercise.rest_assured.testcase.enterprise;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.Login;
import com.exercise.rest_assured.apis.enterprise.EnterpriseBaseinfo;
import com.exercise.rest_assured.testcase.BaseTest;
import com.exercise.rest_assured.util.User;

import io.restassured.path.json.JsonPath;

public class UpdBaseinfoTest extends BaseTest {
	
	@Test(dataProvider="SingleCase")
	public void updbaseinfo_Test(Map<String, Object> params){
		EnterpriseBaseinfo eBaseinfo = new EnterpriseBaseinfo(getBaseURL(),getSrcDir());
		
		setRequest("updbaseinfo", eBaseinfo.setParams(params));
		User user = new User();
		Login login = new Login();
		
		eBaseinfo.checkInfo(getSrcDir()+getExpectedJson(), new JsonPath(login.singin(user.getEnterprise())));
	}
}
