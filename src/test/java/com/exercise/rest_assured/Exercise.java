package com.exercise.rest_assured;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.TextUtils;
import org.testng.annotations.Test;

import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.util.apis.Login;
import com.exercise.rest_assured.utils.TxtData;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.MultiPartConfig;
import io.restassured.http.ContentType;

public class Exercise {
	
	public void multiPart(){
			Map<String, Object> cookieMap = new HashMap<>();
			cookieMap.put("UM_distinctid", "15b71f24cc2101-020ee61b4f9c14-8373f6a-100200-15b71f24cc312d");
			cookieMap.put("PHPSESSID", "j837kg1lhijnsi83k7vmg0osh5");
			cookieMap.put("b63b1fb839ee068df8ff09168838e74c", "b63b1fb839ee068df8ff09168838e74c=29af9f5b912ddc52f665a09c8baa935016f57191a%3A4%3A%7Bi%3A0%3Bs%3A1%3A%221%22%3Bi%3A1%3Bs%3A5%3A%22admin%22%3Bi%3A2%3Bi%3A3600%3Bi%3A3%3Ba%3A4%3A%7Bs%3A8%3A%22username%22%3Bs%3A5%3A%22admin%22%3Bs%3A8%3A%22is_super%22%3Bs%3A1%3A%221%22%3Bs%3A7%3A%22role_id%22%3Bs%3A1%3A%220%22%3Bs%3A8%3A%22realname%22%3Bs%3A5%3A%22admin%22%3B%7D%7D");
			Map<String, Object> queryMap = new HashMap<>();
			String jobID = "47";
			queryMap.put("r", "job/examine/update");
			queryMap.put("id", jobID);
			
			String requestURL = "http://nchr.release.microfastup.com/";
			
			new MultiPartConfig();
			given()
				.proxy("127.0.0.1", 8888)
				.cookies(cookieMap)
				.config(RestAssured.config()
						  .encoderConfig(EncoderConfig.encoderConfig()
								    .defaultContentCharset("UTF-8")
								    .encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
				.queryParams(queryMap)
				.multiPart("JobSet[id]", jobID)
				.multiPart("JobSet[user_id]", "2")
				.multiPart(new MultiPartSpecBuilder("测试发布职位接口").controlName("JobSet[title]").charset("UTF-8").build())
				.multiPart(new MultiPartSpecBuilder("不通过").controlName("JobSet[des]").charset("UTF-8").build())
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
	
	@Test
	public void adminLogin(){
		Login login = new Login();
		User user = new User();
		login.adminSingin(user.getAdmin());
		Map<String, Object> cookieMap = login.getCookie();
		String cookie = null;
		for(String key:cookieMap.keySet()){
			cookie+=(key+"="+cookieMap.get(key));
			cookie+="\r\n";
		}
		
		TxtData textData = new TxtData();
		String fPath = System.getProperty("user.dir") + "\\src\\test\\resources\\case\\";
		textData.writerText(fPath + "adminToken.txt", cookie.substring(4, cookie.length()));
	}
}
