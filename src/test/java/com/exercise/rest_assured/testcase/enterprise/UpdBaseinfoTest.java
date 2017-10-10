package com.exercise.rest_assured.testcase.enterprise;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.Login;
import com.exercise.rest_assured.apis.Login.Role;
import com.exercise.rest_assured.apis.enterprise.EnterpriseBaseinfo;
import com.exercise.rest_assured.utils.testutils.BaseTest;
import com.exercise.rest_assured.utils.testutils.User;

import io.restassured.path.json.JsonPath;

public class UpdBaseinfoTest extends BaseTest {
	
	@Test(dataProvider="SingleCase")
	public void up_EBaseinfo_Test(Map<String, Object> params){
		EnterpriseBaseinfo eBaseinfo = new EnterpriseBaseinfo(getBaseURL(),getSrcDir());
		
		setRequest("updbaseinfo", eBaseinfo.setParams(params));
		Login login = new Login(Role.enterprise);
		login.login(new User().getEnterprise());
		JsonPath responseJson = new JsonPath(login.getBody());
		eBaseinfo.checkInfo(getSrcDir()+getExpectedJson(), responseJson);
	}
}
