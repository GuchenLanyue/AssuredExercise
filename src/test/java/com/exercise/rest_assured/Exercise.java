package com.exercise.rest_assured;

import static io.restassured.RestAssured.given;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.utils.TxtData;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.MultiPartConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;

public class Exercise {
	
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

	@Test
	public void multiPart(){
			
			Map<String, Object> headerMap = new HashMap<>();
//			headerMap.put("Content-Type", " multipart/form-data; boundary=----WebKitFormBoundary045YodAm5x6cjAg9");
			headerMap.put("UM_distinctid", "15b71f24cc2101-020ee61b4f9c14-8373f6a-100200-15b71f24cc312d");
			headerMap.put("PHPSESSID", "590rc3rv88fr9h6im74s56moo7");
			headerMap.put("b63b1fb839ee068df8ff09168838e74c", "29af9f5b912ddc52f665a09c8baa935016f57191a%3A4%3A%7Bi%3A0%3Bs%3A1%3A%221%22%3Bi%3A1%3Bs%3A5%3A%22admin%22%3Bi%3A2%3Bi%3A3600%3Bi%3A3%3Ba%3A4%3A%7Bs%3A8%3A%22username%22%3Bs%3A5%3A%22admin%22%3Bs%3A8%3A%22is_super%22%3Bs%3A1%3A%221%22%3Bs%3A7%3A%22role_id%22%3Bs%3A1%3A%220%22%3Bs%3A8%3A%22realname%22%3Bs%3A5%3A%22admin%22%3B%7D%7D");
			Map<String, Object> queryMap = new HashMap<>();
			String jobID = "45";
			queryMap.put("r", "job/examine/update");
			queryMap.put("id", jobID);
			
			String requestURL = "http://nchr.release.microfastup.com/";
			TxtData text = new TxtData();
			String body = text.readTxtFile("C:\\Users\\sam\\Desktop\\body.txt");
			
			new MultiPartConfig();
			given()
				.proxy("127.0.0.1", 8888)
//				.headers(headerMap)
//				.cookie("Cookie", "UM_distinctid=15b71f24cc2101-020ee61b4f9c14-8373f6a-100200-15b71f24cc312d", "PHPSESSID=vh5qf6bjdp4tsv2u4qi23099o7")
				.cookies(headerMap)
				.config(RestAssured.config()
						  .encoderConfig(EncoderConfig.encoderConfig()
								    .defaultContentCharset("UTF-8")
								    .encodeContentTypeAs(" multipart/form-data", ContentType.TEXT)))
				.queryParams(queryMap)
	//			.body(body)
				.multiPart("JobSet[id]", jobID)
				.multiPart("JobSet[user_id]", "2")
				.multiPart("JobSet[title]", "")
				.multiPart("JobSet[des]", "nnn")
				.multiPart("JobSet[status]", "2")
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
