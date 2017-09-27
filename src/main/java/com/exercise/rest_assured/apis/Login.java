package com.exercise.rest_assured.apis;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exercise.rest_assured.apis.API_Category.Category;
import com.exercise.rest_assured.utils.testutils.User;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Login {
	private Response response = null;
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	public Login(String user) {
		// TODO Auto-generated constructor stub
		API_Category api = new API_Category();
		Category role = api.analysis(user);
		switch (role) {
		case person:
		case personresume:
			singin(new User().getPerson());
			break;
		case job:
		case enterprise:
			singin(new User().getEnterprise());
			break;
		case admin:
		case site:
			singin(new User().getAdmin());
			break;
		default:
			break;
		}
	}
	
	@Step("登录")
	public void singin(JsonPath user){
		response = given()
	//		.proxy("localhost", 8888)
	//		.log().all()
			.header("Accept", "application/json")
			.header("Accept-Encoding", "gzip, deflate")
			.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
			.header("Cache-Control", "no-cache")
			.config(RestAssured.config()
					  .encoderConfig(EncoderConfig.encoderConfig()
							    .defaultContentCharset("UTF-8")
							    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
			.formParams(user.getMap("params"))
		.when()
			.post(user.getString("url"))
		.then()
			.statusCode(Integer.valueOf(user.getString("statusCode")).intValue())
		.extract()
			.response();
	}
	
	@Description("获取登录接口返回的数据")
	public String getBody(){
		String body = response.getBody().asString();
		body = body.substring(body.indexOf("{"), body.lastIndexOf("}")+1);
		return body;
	}
	
	@Description("获取token")
	public String getToken(){
		String body = response.getBody().asString();
		body = body.substring(body.indexOf("{"), body.lastIndexOf("}")+1);
		JsonPath json = new JsonPath(body).setRoot("value");
		
		return json.getString("token");
	}
	
	@Description("获取cookie数据")
	public Map<String, Object> getCookie(){
		Map<String, Object> cookieMap = new HashMap<>();
		Headers headers = response.getHeaders();		
		List<Header> cookies = headers.getList("Set-Cookie");
		String PHPSESSID = cookies.get(1).getValue().split(";")[0].split("=")[1];
		String[] md5 = cookies.get(2).getValue().split(";")[0].split("=");
		cookieMap.put("PHPSESSID", PHPSESSID);
		cookieMap.put(md5[0], md5[1]);
		cookieMap.put("UM_distinctid", "15b71f24cc2101-020ee61b4f9c14-8373f6a-100200-15b71f24cc312d");
		return cookieMap;
	}
}
