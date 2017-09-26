package com.exercise.rest_assured.testcase.person;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Intention;
import com.exercise.rest_assured.testcase.BaseTest;

public class IntentionTest extends BaseTest {

	@Test(dataProvider = "SingleCase", description = "增加求职意向")
	public void add_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention(getBaseURL());
		params.remove("id");
		params.put("token", "");
		setRequest("intention", intention.setParams(getBaseURL(), params));
		checkResponse(intention.getParams());
	}

	@Test(dataProvider = "SingleCase", description = "编辑求职意向",dependsOnMethods={"add_Intention_Test"})
	public void edit_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention(getBaseURL());
		intention.setID();
		params.put("token", "");
		params.put("id", intention.getID());
		setRequest("intention", intention.setParams(getBaseURL(), params));
		checkResponse(intention.getParams());
	}

	@Test(description = "删除求职意向",dependsOnMethods={"add_Intention_Test"})
	public void del_Intention_Test() {
		Intention intention = new Intention(getBaseURL());
		List<String> list = intention.getIntentions();
		for (int i = 0; i < list.size(); i++) {
			intention.delIntention(list.get(i));
		}
	}
}
