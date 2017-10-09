package com.exercise.rest_assured.apis.admin;

import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.Map;

import com.exercise.rest_assured.apis.Login;
import com.exercise.rest_assured.apis.Login.Role;
import com.exercise.rest_assured.apis.enterprise.EnterpriseJob;
import com.exercise.rest_assured.utils.HttpMethods;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Examine {
	private String url = null;
	
	public Examine() {
		// TODO Auto-generated constructor stub
	}
	
	public Examine(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	@Description("status 状态(1审核中2发布中3已下线4未通过)")
	@Step("job() 审核职位")
	public void job(String job_id,String status){
		Map<String, Object> paramMap = new HashMap<>();
		EnterpriseJob eJob = new EnterpriseJob(url);
		JsonPath jobShow = eJob.getUserJobShow(job_id);
		paramMap = jobShow.getMap("value");
		//登录后台
		Login login = new Login();
		login.analysis(null);
		
		//获取职位的id及title等属性
		Map<String, Object> queryMap = new HashMap<>();
		String title = paramMap.get("title").toString();
		String des = null;
		//设置描述文本
		if(paramMap.get("des")!=null){
			des = paramMap.get("des").toString();
		}else{
			des = "这里是描述文本";
		}

		//该接口有query参数，设置query参数
		queryMap.put("r", "job/examine/update");
		queryMap.put("id", job_id);
		
		String requestURL = "http://nchr.release.microfastup.com/";
		
		RestAssured.given()
//			.proxy("127.0.0.1", 8888)
			.cookies(login.getCookie())
			.config(RestAssured.config()
					  .encoderConfig(EncoderConfig.encoderConfig()
							    .defaultContentCharset("UTF-8")
							    .encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
			.queryParams(queryMap)
			.multiPart("JobSet[id]", job_id)
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
	
	public void settled(){
		Map<String, Map<String,Object>> map = new HashMap<>();
		
		//登录后台
		Login login = new Login(Role.admin);
		map.put("cookies", login.getCookie());
		
		String path = "/enterprise/settledapply/index/ajax/settled-apply-grid/SettledApply_sort/id.desc";
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "GET");
		baseMap.put("baseURL", "http://nchr.release.microfastup.com");
		baseMap.put("path", path);
		map.put("base", baseMap);
		
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("ajax", "settled-apply-grid");
		map.put("querys", queryMap);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
		String id = response.andReturn().htmlPath().getString("//*[@id=\"settled-apply-grid\"]/table/tbody/tr[1]/td[1]");
		System.out.println(id);
	}
	
	public static void main(String[] args) {
		Examine examine = new Examine("http://nchr.release.microfastup.com");
		examine.settled();
	}
}
