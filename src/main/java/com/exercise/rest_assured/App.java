package com.exercise.rest_assured;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import java.util.HashMap;
import java.util.Map;


import io.restassured.http.ContentType;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		exercise();
	}

	public static void exercise() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rand", "zd1nYJJNZxbroVQn");
		map.put("plat", "android");
		map.put("phoneNo", "18612819427");
		map.put("ver", "1.6");
		map.put("model", "OD105");
		
		given().
			contentType(ContentType.JSON).
			body(map).
		when().
			post("http://wanjia.microfastup.com/chetu/app/login")
		.then().
			statusCode(200);
	}
}
