package com.exercise.rest_assured;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.path.json.JsonPath.from;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class AssuredTest {

	public static void JsonParamers(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", "96e79218965eb72c92a549dd5a330112");
		map.put("deviceToken", "AmtSMUgxJ613ZOIBVt5c2N_W2-lmWvJw6nJ1-ir8s1u4");
		map.put("sign", "591ef9d2f4628fc30df63b1039068ea5");
		map.put("phoneType", "android");
		map.put("app_id", "46a9fc7790cf4ce32dc30a7e9bf5aefd");
		map.put("timestamp", "2017-08-15-11:41:40:260");
		map.put("rand", "zd1nYJJNZxbroVQn");
		map.put("plat", "android");
		map.put("phoneNo", "18612819427");
		map.put("ver", "1.6");
		map.put("model", "OD105");
		Response response = null;
		response = given().
			contentType(ContentType.JSON).
			body(map).
		when().
			post("http://wanjia.microfastup.com/chetu/app/login").
		then().
			statusCode(200).
//			body("userId", equalTo(0)).
		extract().
			response();
		String body = response.asString();
		System.out.println(body);
	}
	
	@Test
	public static void FormParamers(){
		Response response = null;
		response = given().
			proxy("127.0.0.1",8888).
			contentType("application/x-www-form-urlencoded").
			formParam("password", "96e79218965eb72c92a549dd5a330112").
			formParam("deviceToken", "AmtSMUgxJ613ZOIBVt5c2N_W2-lmWvJw6nJ1-ir8s1u4").
			formParam("sign", "591ef9d2f4628fc30df63b1039068ea5").
			formParam("phoneType", "android").
			formParam("app_id", "46a9fc7790cf4ce32dc30a7e9bf5aefd").
			formParam("timestamp", "2017-08-15-11:41:40:260").
			formParam("rand", "zd1nYJJNZxbroVQn").
			formParam("plat", "android").
			formParam("phoneNo", "18612819427").
			formParam("ver", "1.6").
			formParam("model", "OD105").
		when().
			post("http://wanjia.microfastup.com/chetu/app/login");
//		then().
//			statusCode(200).
//			body("userId", equalTo(0)).
//		extract().
//			response();
		JsonPath jsonPath = new JsonPath(response.asString()).setRoot("user");
		int userId = jsonPath.getInt("userId");
//		String body = response.asString();
		System.out.println("userId:"+userId);
		assertThat(userId, is(46));
	}
	
	public static void main(String[] args) {
		System.out.println("Hello rest-assured");
		FormParamers();
	}
}
