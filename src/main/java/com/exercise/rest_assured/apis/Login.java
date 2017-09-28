package com.exercise.rest_assured.apis;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exercise.rest_assured.utils.JsonUtils;
import com.exercise.rest_assured.utils.TxtData;
import com.exercise.rest_assured.utils.testutils.User;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Login {
	private Response response = null;
	private String token = null;
	private Map<String, Object> cookieMap = new HashMap<>();
	private String src = System.getProperty("user.dir")+"/src/test/resources";
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	public Login(String path) {
		// TODO Auto-generated constructor stub
		singin(analysis(path));
	}
	
	public Login(Role role) {
		// TODO Auto-generated constructor stub
		singin(role);
	}
	
	//接口路径
	public enum Role {
		person, enterprise, admin
	}

	/**
	 * @description 解析接口路径，对接口进行分类
	 * @param path 接口路径
	 * */
	public Role analysis(String path) {
		JsonUtils json = new JsonUtils();
		JsonPath apis = json.jsonReader(src+"/base/apis.json");

		Role role = null;
		String[] strs = null;
		if (path != null) {
			strs = path.split("/");
			String category = strs[1];
			String pathStr = category+".path";
			String roleStr = null;
			List<String> paths = new ArrayList<String>();
			paths = apis.getList(pathStr);
			if(paths.contains(path)){
				int index = paths.indexOf(path);
				roleStr = apis.getList(category+".role").get(index).toString();
			}else{
				Assert.fail("没有找到接口："+path);
			}
			if (roleStr.equals("person")) {
				role = Role.person;
			} else if (roleStr.equals("enterprise")) {
				role = Role.enterprise;
			} else if (roleStr.equals("admin")) {
				role = Role.admin;
			} else{
				return null;
			}
		}else{
			role = Role.admin;
		}
		
		return role;
	}
	
	@Step("登录")
	public void singin(JsonPath user){
		response = given()
			.proxy("localhost", 8888)
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
	
	@Description("对接口分类，不同类别的接口请求不同的登录接口")
	@Step
	public void singin(Role role){
		User user = new User();
		TxtData textData = new TxtData();
		String fPath = System.getProperty("user.dir") + "\\src\\test\\resources\\case\\";

		switch (role) {
		case person:
			File pfile = new File(fPath + "personToken.txt");
			if (!pfile.exists() || System.currentTimeMillis() - pfile.lastModified() > 120000) {
				singin(user.getPerson());
				String body = getBody();
				JsonPath json = new JsonPath(body).setRoot("value");
				token = json.getString("token");
				textData.writerText(fPath + "personToken.txt", token);
				textData.writerText(fPath + "person_txt", body);
			} else {
				TxtData data = new TxtData();
				token = data.readTxtFile(fPath + "personToken.txt");
			}

			break;
		case enterprise:
			File efile = new File(fPath + "enterpriseToken.txt");
			if (!efile.exists() || System.currentTimeMillis() - efile.lastModified() > 120000) {
				singin(user.getEnterprise());
				String body = getBody();
				JsonPath json = new JsonPath(body).setRoot("value");
				token = json.getString("token");
				textData.writerText(fPath + "enterpriseToken.txt", token);
				textData.writerText(fPath + "enterprise_txt", body);
			} else {
				TxtData data = new TxtData();
				token = data.readTxtFile(fPath + "enterpriseToken.txt");
			}

			break;
			
		case admin:
			File afile = new File(fPath + "adminCookie.txt");
			if (!afile.exists() || System.currentTimeMillis() - afile.lastModified() > 120000) {
				singin(user.getAdmin());
				setCookie();

				String cookie = null;
				for (String key : cookieMap.keySet()) {
					cookie += (key + "=" + cookieMap.get(key));
					cookie += ";";
				}

				textData.writerText(fPath + "adminCookie.txt", cookie.substring(4, cookie.length()));
			} else {
				TxtData data = new TxtData();
				String cookieStr = data.readTxtFile(fPath + "adminCookie.txt");
				for(String cookie:cookieStr.split(";")){
					String[] cookieList=cookie.split("=");
						cookieMap.put(cookieList[0], cookieList[1]);
				}
			}
			
			break;
		default:
			break;
		}
	}
	
	@Description("获取登录接口返回的数据")
	public String getBody(){
		String body = response.getBody().asString();
		body = body.substring(body.indexOf("{"), body.lastIndexOf("}")+1);
		return body;
	}
	
	@Description("获取token")
	public String getToken(){
		return token;
	}
	
	public Map<String, Object> getCookie(){
		return cookieMap;
	}
	
	@Description("获取cookie数据")
	public Map<String, Object> setCookie(){
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
