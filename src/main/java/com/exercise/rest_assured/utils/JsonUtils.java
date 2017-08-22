package com.exercise.rest_assured.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class JsonUtils {
	
	public JsonPath jsonReader(String jsonFile){
		JsonPath json = null;
		
		try {
			json = new JsonPath(new FileInputStream(jsonFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	
	public void equalsJson(String file, JsonPath responseJson){
		JsonPath expectedJson = jsonReader(file);
		List<JsonPath> expectedList = expectedJson.getList("expected");
		FileInputStream jsonFile =null;
		try {
			jsonFile = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i = 0; i < expectedList.size(); i++){

			Map<String, Object> map = JsonPath.from(jsonFile).get("expected["+i+"].values");
			
			String root = expectedJson.get("expected["+i+"].root");
			responseJson = responseJson.setRoot(root);
			for(Map.Entry<String,Object> mapEntry:map.entrySet()){
				String key = mapEntry.getKey();
				
				if (mapEntry.getKey().equals("root")) {
					continue;
				}

				String actual = null;
				String expected = null;
				
				if (responseJson.get(key)!=null) 
					actual = responseJson.get(key).toString();
				
				if (mapEntry.getValue()!=null) 
					expected = mapEntry.getValue().toString();
				
				Assert.assertEquals(actual, expected);
				System.out.println(mapEntry.getKey()+":"+mapEntry.getValue());
			}
		}
	}
}
