package com.exercise.rest_assured;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.utils.TxtData;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;

public class Exercise {
	@Test
	public void httpTest(){
		
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("Content-Type", " multipart/form-data; boundary=----WebKitFormBoundary045YodAm5x6cjAg9");
		headerMap.put("Cookie", "UM_distinctid=15b71f24cc2101-020ee61b4f9c14-8373f6a-100200-15b71f24cc312d; PHPSESSID=cv70bbv70rq31iopq66rpr1r93");
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("r", "job/examine/update");
		queryMap.put("id", "2");
		
		String requestURL = "http://nchr.release.microfastup.com/";
		TxtData text = new TxtData();
		String body = text.readTxtFile("C:\\Users\\sam\\Desktop\\body.txt");
		
		given()
//			.proxy("127.0.0.1", 8888)
//			.log().all()
			.log().uri()
			.log().params()
			.headers(headerMap)
			.config(RestAssured.config()
					  .encoderConfig(EncoderConfig.encoderConfig()
							    .defaultContentCharset("UTF-8")
							    .defaultQueryParameterCharset("GBK")
							    .encodeContentTypeAs(" multipart/form-data", ContentType.TEXT)))
			.queryParams(queryMap)
			.body(body)
		.when()
			.post(requestURL)
		.then()
//			.log().body()
//			.statusCode(200)
		.extract()
			.response();
	}
	
}
