package com.exercise.rest_assured;

import static org.junit.Assert.*;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.path.json.JsonPath.from;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import com.exercise.rest_assured.utils.ExcelReader;

public class AssuredTest {

//	@Test
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
			proxy("localhost",8888).
//			contentType(ContentType.JSON).
			contentType("application/x-www-form-urlencoded;charset=UTF-8").
			params(map).
//			body(map).
		when().
			post("http://wanjia.microfastup.com/chetu/app/login").
		then().
			statusCode(200).
//			body("userId", equalTo(0)).
		extract().
			response();
		response.getBody().prettyPrint();
	}
	
	
	public static void FormParamers(){
		Response response = null;
		response = given().
			proxy("127.0.0.1",8888).
			contentType("application/x-www-form-urlencoded;charset=UTF-8").
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
			post("http://wanjia.microfastup.com/chetu/app/login").
		then().
			log().all().
//			statusCode(200).
//			body("userId", equalTo(0)).
		extract().
			response();
		JsonPath jsonPath = new JsonPath(response.asString()).setRoot("user");
		int userId = jsonPath.getInt("userId");
//		String body = response.asString();
		assertThat(userId, is(46));
	}
	
	public static void URL_Body(){
		Response response = null;
		response = given().
//			log().all().
			proxy("localhost",8888).
			header("Accept-Encoding", "gzip, deflate").
			header("Accept", "application/json").
			header("Host", "nchr.release.microfastup.com").
			header("Origin", "http://nchr.release.microfastup.com").
			contentType("application/x-www-form-urlencoded;charset=UTF-8").
			body("name=%E5%BE%AE%E8%BF%85%E5%A4%A7%E5%AD%A6&education=3&magor=1&end_time=2017-08-16T11%3A46%3A29%2B08%3A00&magors=29&id=15&token=97d2a33a990d380771f09448fa149805").
		when().
			post("http://nchr.release.microfastup.com/nchr/personresume/education").
		then().
			log().all().
//			root("value").
//			body("id", is("15")).
		extract().
			response();	
		response.getBody().prettyPrint();
		JsonPath jsonPath = from(response.toString()).getJsonObject("$.value");
//		String json = response.body().asString();
//		JsonPath jsonPath = new JsonPath(json).setRoot("value");
		String name = jsonPath.getString("name");
		System.out.println(name);
	}
	
	public static void JsonPath(){
		String json = "{\"status\":1,\"msg\":\"success\",\"value\":{\"id\":15,\"bid\":\"12\",\"name\":\"微迅大学\",\"start_time\":\"\",\"end_time\":\"2017-08-16T11:46:29+08:00\",\"magor\":\"1\",\"magors\":29,\"education\":\"3\",\"provice\":null,\"city\":null,\"district\":null,\"certificate\":\"\",\"des\":\"\",\"is_deleted\":\"0\"}}";
		JsonPath jsonPath = new JsonPath(json).setRoot("");
		System.out.println(jsonPath.getString("status"));
	}
	
	public static void main(String[] args) {
//		System.out.println("Hello rest-assured");
//		FormParamers();
//		JsonParamers();
//		URL_Body();
//		JsonPath();
	}
	
	@Test
	public void basic(){
		String path = System.getProperty("user.dir");
		String filePath = path + "\\resources\\case\\Basic.xlsx";
		String sheetName = "Base";
		String caseName = "Params";
		Map<String, String> map = null;
		Map<String, String> paramsMap = null;
		
		ExcelReader excelReader = new ExcelReader(filePath, sheetName, "Basic");
		ExcelReader paramsExcel = new ExcelReader(filePath, sheetName, "Basic");
		
		map = excelReader.mapFromSheet(filePath, sheetName, caseName);
		paramsMap = paramsExcel.mapFromSheet(filePath, sheetName, caseName);
		
		Response response = null;
		response = given().
			proxy("localhost",8888).
//			contentType(ContentType.JSON).
			contentType("application/x-www-form-urlencoded;charset=UTF-8").
			params(paramsMap).
//			body(map).
		when().
			post(map.get("Protocol")+"://"+map.get("Host")+map.get("path")).
		then().
			statusCode(200).
//			body("userId", equalTo(0)).
		extract().
			response();
		response.getBody().prettyPrint();
		
//		System.out.println(map.get("Desc"));
	}
	
}
