package com.exercise.rest_assured;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.BaseTest;
import com.exercise.rest_assured.util.User;
import com.exercise.rest_assured.utils.TextData;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AssuredTest extends BaseTest {
	
	@Test(dataProvider = "CaseList",description="南昌人力资源项目PC")
	@Description("南昌人力资源项目PC端接口测试")
	@Issue("043")
    @Issue("044")
    @Issue("045")
	public void NanChang_API_Test(Object[] data) {
		String api = data[0].toString();
		String filePath = data[1].toString();
		String caseName = data[2].toString();
		User user = new User();
		login(user);
		request(api,filePath, caseName);
	}
	
	@Step()
	public void login(User user){
		Response response = given().
		proxy("localhost", 8888)
//		.log().all()
		.contentType("application/x-www-form-urlencoded;charset=UTF-8")
		.param("username",user.getUsername())
		.param("password",user.getPassword())
		.param("v_code",user.getV_code())
		.param("uuid",user.getUuid())
	.when()
		.post("http://nchr.release.microfastup.com/nchr/person/login")
	.then()
//		.log().all()
		.statusCode(200)
	.extract()
		.response();
		
		String body = response.getBody().asString();
		while (body.charAt(0) != '{') {
			body = body.substring(1, body.length());
		};
		
		JsonPath json = new JsonPath(body).setRoot("value");
		
		String token = json.getString("token");
		
		TextData textData = new TextData();
		String path =System.getProperty("user.dir")+"\\src\\test\\resources\\case\\";
		textData.writerText(path, "token.txt", token);
	}
}
