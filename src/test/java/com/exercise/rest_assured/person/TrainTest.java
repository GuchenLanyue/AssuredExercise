package com.exercise.rest_assured.person;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.apis.person.Train;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;

public class TrainTest extends BaseTest{
	@Test(dataProvider = "SingleCase",description="创建培训信息")
	@Description("创建培训信息")
	public void add_Train_Test(Map<String,Object> params){
		Train train = new Train(getBaseURL());
		params.remove("id");
		params.put("token", getToken());
		setRequest("train", train.setParam(getBaseURL(), params));
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		train.checkTrain(json);
		checkResponse();
	}
	
	@Test(dataProvider = "SingleCase",description="修改培训信息",dependsOnMethods={"add_Train_Test"})
	@Description("修改培训信息")
	public void edit_Train_Test(Map<String,Object> params){
		Train train = new Train(getBaseURL());
		List<String> ids = train.getTrains(getBaseURL(),getToken());
		String id = null;
		if (ids.size()>0) {
			id = ids.get(0);
		}else{
			Assert.fail("当前没有任何培训信息，无法编辑");
		}
		params.put("id", id);
		params.put("token", getToken());
		params = train.setParam(getBaseURL(), params);
		setRequest("train", params);
		
		JsonPath json = new JsonPath(getBodyStr()).setRoot("value");
		train.checkTrain(json);
		
		checkResponse();
	}
	
	@Test(description="删除培训信息",dependsOnMethods={"add_Train_Test"})
	@Description("删除培训信息")
	public void delTrainTest(){
		Train train = new Train(getBaseURL());
		List<String> list = train.getTrains(getBaseURL(),getToken());
		for (int i = 0; i < list.size(); i++) {
			train.delTrain(getToken(), list.get(i));
		}
	}
}
