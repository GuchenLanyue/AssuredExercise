package com.exercise.rest_assured.testcase.person;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Train;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import io.qameta.allure.Description;

public class TrainTest extends BaseTest{
	@Test(dataProvider = "SingleCase",description="创建培训信息")
	@Description("创建培训信息")
	public void add_Train_Test(Map<String,Object> params){
		Train train = new Train(getbasePath());
		params.remove("id");
		params.put("token", "");
		setRequest("train", train.setParam(params));
		
		checkResponse(train.getParams());
	}
	
	@Test(dataProvider = "SingleCase",description="修改培训信息")
	@Description("修改培训信息")
	public void edit_Train_Test(Map<String,Object> params){
		Train train = new Train(getbasePath());
		List<String> ids = train.getTrains();
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			Assert.fail("当前没有任何培训信息，无法编辑");
		}
		params.put("id", id);
		params.put("token", "");
		params = train.setParam(params);
		setRequest("train", params);
		
		checkResponse(train.getParams());
	}
	
	@Test(dataProvider="SingleCase",description="删除培训信息")
	@Description("删除培训信息")
	public void del_Train_Test(Map<String, Object> params){
		Train train = new Train(getbasePath());
		List<String> list = train.getTrains();
		if(list.size()==0){
			train.addTrain(params);
			list = train.getTrains();
		}
		
		Random random = new Random();
		int index = random.nextInt(list.size());
		String id = list.get(index);
		train.delTrain(id);
	}
	
	@Test
	public void clean(){
		Train train = new Train(getbasePath());
		train.cleanTrains();
	}
}
