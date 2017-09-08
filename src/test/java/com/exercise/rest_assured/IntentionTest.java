package com.exercise.rest_assured;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.Intention;

public class IntentionTest extends BaseTest {

	@Test(dataProvider = "SingleCase", description = "增加求职意向")
	public void add_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention();
		params.put("industry", intention.getIndustry());
		params.put("position", intention.getPosition());
		params.put("salary", intention.getSalary());
		params.put("positions", intention.getPositions());
		params.put("provice", intention.getProvince());
		params.put("city", intention.getCity());
		params.put("district", intention.getDistrict());
		params.put("token", getToken());
		
		setRequest("intention", params);

		intention.checkIntention(getBodyStr());

		String actualJson = intention.getIntention(getToken(), getSrcDir());
		intention.checkIntention(actualJson);
	}

	@Test(dataProvider = "SingleCase", description = "编辑求职意向")
	public void edit_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention();
		intention.setID(getToken());

		params.put("industry", intention.getIndustry());
		params.put("position", intention.getPosition());
		params.put("salary", intention.getSalary());
		params.put("positions", intention.getPositions());
		params.put("provice", intention.getProvince());
		params.put("city", intention.getCity());
		params.put("district", intention.getDistrict());
		params.put("token", getToken());
		params.put("id", intention.getID());

		setRequest("intention", params);

		String actualJson = intention.getIntention(getToken(), getSrcDir());
		intention.checkIntention(actualJson);
	}

	@Test(description = "删除求职意向")
	public void delIntentionTest() {
		Intention intention = new Intention();
		List<String> list = intention.getIntentions(getToken());
		for (int i = 0; i < list.size(); i++) {
			intention.delIntention(getToken(), list.get(i));
		}
	}
}
