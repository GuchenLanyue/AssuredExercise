package com.exercise.rest_assured.testcase.person;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Intention;
import com.exercise.rest_assured.utils.testutils.BaseTest;

public class IntentionTest extends BaseTest {

	@Test(dataProvider = "SingleCase", description = "增加求职意向")
	public void add_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention(getbasePath());
		params.remove("id");
		params.put("token", "");
		setRequest("intention", intention.setParams(params));
		checkResponse(intention.getParams());
	}

	@Test(dataProvider = "SingleCase", description = "编辑求职意向")
	public void edit_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention(getbasePath());
		intention.setID();
		params.put("token", "");
		params.put("id", intention.getID());
		setRequest("intention", intention.setParams(params));
		checkResponse(intention.getParams());
	}

	@Test(dataProvider = "SingleCase",description = "删除求职意向")
	public void del_Intention_Test(Map<String, Object> params) {
		Intention intention = new Intention(getbasePath());
		List<String> list = intention.getIntentions();
		if(list.size()==0){
			intention.addIntentions(params);
			list = intention.getIntentions();
		}
		Random random = new Random();
		int index = random.nextInt(list.size());
		String id = list.get(index);
		intention.delIntention(id);
	}
	
	@Test
	public void clean(){
		Intention intention = new Intention(getbasePath());
		intention.cleanIntentions();
	}
}
