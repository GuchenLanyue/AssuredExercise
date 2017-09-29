package com.exercise.rest_assured.testcase.enterprise;

import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.enterprise.Settled;
import com.exercise.rest_assured.utils.testutils.BaseTest;

public class SettledTest extends BaseTest {

	@Test(dataProvider="SingleCase")
	public void add_Settled_Test(Map<String, Object> param){
		Settled settled = new Settled(getBaseURL());
		setRequest("addapply", settled.setParams(param));
	}
}
