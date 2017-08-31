package com.exercise.rest_assured.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.qameta.allure.Step;
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
	
	@Step
	public void equalsJson(String file, JsonPath responseJson){
		JsonPath expectedJson = jsonReader(file);
		List<JsonPath> expectedList = expectedJson.getList("expected");
		BufferedReader br = null;
		
		for(int i = 0; i < expectedList.size(); i++){
			try {
				br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, Object> map = JsonPath.with(br).get("expected["+i+"].values");
			
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

				Assert.assertEquals(actual, expected,"文件\""+file+"\"["+key+"]:的预期值为："+expected+"，实际值为："+actual);
			}
		}
	}
}
