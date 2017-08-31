package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;
import java.util.Random;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Job {
	public Job() {
		// TODO Auto-generated constructor stub
	}
	
	public void addJob(){
		
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
	
	public int[] getPositionData(){
		JsonPath json = new JsonPath(getposition());
		List<String> positionList = json.getList("value.value");
		Random random = new Random();
		
		int index = random.nextInt(positionList.size());
        int position = Integer.valueOf(positionList.get(index).toString()).intValue();
        
        String path = "value["+index+"].children";
        List<Map<String, String>> positionsList = json.getList(path);
        index = random.nextInt(positionsList.size());
        Map<String, String> children = positionsList.get(index);
        int positions = Integer.valueOf(children.get("value")).intValue();
        int[] values = {position,positions};
        
		return values;
	}
}
