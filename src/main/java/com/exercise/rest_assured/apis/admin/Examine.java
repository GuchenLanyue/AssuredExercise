package com.exercise.rest_assured.apis.admin;

import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.Map;

import com.exercise.rest_assured.apis.API_Category;

import io.qameta.allure.Step;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;

public class Examine {
	
	@Step("审核职位")
	public void job(Map<String, Object> paramMap){
		API_Category api = new API_Category();
		api.analysis(null);
		
		Map<String, Object> queryMap = new HashMap<>();
		String jobID = paramMap.get("jobID").toString();
		String title = paramMap.get("title").toString();
		String des = null;
		if(paramMap.get("des")!=null){
			des = paramMap.get("des").toString();
		}else{
			des = "通过";
		}
		
		String status = paramMap.get("status").toString();
		queryMap.put("r", "job/examine/update");
		queryMap.put("id", jobID);
		
		String requestURL = "http://nchr.release.microfastup.com/";
		
		RestAssured.given()
//			.proxy("127.0.0.1", 8888)
			.cookies(api.getCookie())
			.config(RestAssured.config()
					  .encoderConfig(EncoderConfig.encoderConfig()
							    .defaultContentCharset("UTF-8")
							    .encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
			.queryParams(queryMap)
			.multiPart("JobSet[id]", jobID)
			.multiPart("JobSet[user_id]", "2")
			.multiPart(new MultiPartSpecBuilder(title).controlName("JobSet[title]").charset("UTF-8").build())
			.multiPart(new MultiPartSpecBuilder(des).controlName("JobSet[des]").charset("UTF-8").build())
			.multiPart("JobSet[status]", status)
			.multiPart("yt0", "")
		.when()
			.post(requestURL)
		.then()
//			.log().body()
//			.statusCode(200)
		.extract()
			.response();
	}
}
