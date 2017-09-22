package com.exercise.rest_assured.enterprise;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.util.apis.Login;
import com.exercise.rest_assured.util.apis.enterprise.EnterpriseBaseinfo;

import io.restassured.path.json.JsonPath;

public class UpdBaseinfoTest extends BaseTest {
	
	@Test(dataProvider="SingleCase")
	public void updbaseinfo_Test(Map<String, Object> params){
		EnterpriseBaseinfo eBaseinfo = new EnterpriseBaseinfo();
		
		setRequest("updbaseinfo", eBaseinfo.setParams(getBaseURL(),params));
		User user = new User();
		Login login = new Login();
		
		eBaseinfo.checkInfo(getSrcDir()+getExpectedJson(), new JsonPath(login.singin(user.getEnterprise())));
	}
}
