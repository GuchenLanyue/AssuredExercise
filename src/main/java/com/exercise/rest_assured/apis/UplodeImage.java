package com.exercise.rest_assured.apis;

import java.io.File;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;

public class UplodeImage {
	
	@Step
	public String upload(String baseURL,File file){
		if(!file.exists()){
			Assert.fail("File not found:"+file.getAbsolutePath());
		}
		
		Response response = given()
			.multiPart("image",file.getName(),"text/plain")
			.multiPart("image",file,"image/jpeg")
		.when()
			.post(baseURL+"/images/uplodeimage")
		.then()
		.extract()
			.response();
		String body = response.getBody().asString();
		while(body.charAt(0)!='{'){
			body = body.substring(1, body.length());
		}
		JsonPath json = JsonPath.with(body).setRoot("value");
		
		return json.getString("imgurl");
	}
}
