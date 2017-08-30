package com.exercise.rest_assured.util;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Description;
import io.qameta.allure.Step;

public class DelEducation {
	public DelEducation() {
		// TODO Auto-generated constructor stub
	}

	@Step
	@Description("删除教育背景信息")
	public void deleducation(String token, String id) {
		given()
//			.proxy("http://127.0.0.1:8888")
			.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.param("token", token)
			.param("id", id)
		.when()
			.post("http://nchr.release.microfastup.com/nchr/personresume/deleducation")
		.then()
			.statusCode(200);
	}
}
