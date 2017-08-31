package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Job {
	public Job() {
		// TODO Auto-generated constructor stub
	}
	
	public void addJob(){
		System.out.println("position:"+getPositionData().get("position"));
		System.out.println("positions:"+getPositionData().get("positions"));
	}
	
	public String getposition(){
		Response response = given()
				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getposition")
			.then()
				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	public Map<String, Integer> getPositionData(){
		JsonPath json = new JsonPath(getposition());
		List<String> list = json.getList("value.value");
		List<Integer> positionList = null;
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)==null) {
				Assert.fail("职位的值为空！");
				System.out.println("职位的值为空！");
			}
			try {
				String value = list.get(i).replaceAll(" ", "");
				System.out.println(value);
				positionList.add(Integer.parseInt(value));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		int maxPosition = Collections.max(positionList);
		int minPosition = Collections.min(positionList);
		
		Random random = new Random();
        int position = random.nextInt(maxPosition)%(maxPosition-minPosition+1) + minPosition;
        
        List<Integer> positionsList = json.getList("value.value.value");
		int maxPositions = Collections.max(positionsList);
		int minpositions = Collections.min(positionsList);
        int positions = random.nextInt(maxPositions)%(maxPositions-minpositions+1) + minpositions;
        
        Map<String, Integer> prsitionData= new HashMap<>();
        prsitionData.put("position", position);
        prsitionData.put("positions", positions);
        
		return prsitionData;
	}
}
