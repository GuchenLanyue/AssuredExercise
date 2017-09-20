package com.exercise.rest_assured;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Intention;

import io.restassured.path.json.JsonPath;

public class IntentionTest extends BaseTest {

	@Test(dataProvider = "SingleCase", description = "增加求职意向")
	public void add_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention(getBaseURL());
		params.remove("id");
		params.put("token", getPersonToken());
		setRequest("intention", intention.setParams(getBaseURL(), params));
		checkResponse(getBodyStr(), getExpectedJson());
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		intention.checkIntention(json);
	}

	@Test(dataProvider = "SingleCase", description = "编辑求职意向",dependsOnMethods={"add_Intention_Test"})
	public void edit_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention(getBaseURL());
		intention.setID(getPersonToken());
		params.put("token", getPersonToken());
		params.put("id", intention.getID());
		setRequest("intention", intention.setParams(getBaseURL(), params));
		checkResponse(getBodyStr(), getExpectedJson());
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		intention.checkIntention(json);
	}

	@Test(description = "删除求职意向",dependsOnMethods={"add_Intention_Test"})
	public void delIntentionTest() {
		Intention intention = new Intention(getBaseURL());
		List<String> list = intention.getIntentions(getPersonToken());
		for (int i = 0; i < list.size(); i++) {
			intention.delIntention(getPersonToken(), list.get(i));
		}
	}
}
