package com.exercise.rest_assured.apis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exercise.rest_assured.utils.HttpMethods;
import com.exercise.rest_assured.utils.JsonUtils;
import com.exercise.rest_assured.utils.TxtData;
import com.exercise.rest_assured.utils.testutils.User;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Login {
	private Response response = null;
	private String body = null;
	private String token = null;
	private Map<String, Object> cookieMap = new HashMap<>();
	private String src = System.getProperty("user.dir")+"/src/test/resources";
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	public Login(String path) {
		// TODO Auto-generated constructor stub
		loginStatus(analysis(path));
	}
	
	public Login(Role role) {
		// TODO Auto-generated constructor stub
		loginStatus(role);
	}
	
	//接口路径
	public enum Role {
		person, enterprise, admin, guest
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
			} else if(roleStr.equals("guest")){
				return Role.guest;
			}else {
				Assert.fail("没有设置角色："+roleStr);
				return null;
			}
		}else{
			role = Role.admin;
		}
		
		return role;
	}
	
	@Step("登录")
	public void signin(JsonPath user){
		Map<String, Map<String,Object>> map = new HashMap<>();
		Map<String,	Object> baseMap = new HashMap<>();
		Map<String,	Object> paramMap = new HashMap<>();
		baseMap = user.getMap("base");
		paramMap = user.getMap("params");
		map.put("base", baseMap);
		map.put("params", paramMap);
		HttpMethods http = new HttpMethods();
		response = http.post(map);
		setBody(response);
	}
	
	@Description("对接口分类，不同类别的接口请求不同的登录接口")
	@Step
	public void loginStatus(Role role){
		User user = new User();
		TxtData textData = new TxtData();
		String fPath = System.getProperty("user.dir") + "\\src\\test\\resources\\case\\";

		switch (role) {
		case person:
			File pfile = new File(fPath + "personToken.txt");
			if (!pfile.exists() || System.currentTimeMillis() - pfile.lastModified() > 120000) {
				signin(user.getPerson());
//				String body = getBody();
				JsonPath json = new JsonPath(body).setRoot("value");
				token = json.getString("token");
				textData.writerText(fPath + "personToken.txt", token);
				textData.writerText(fPath + "person.txt", body);
			} else {
				TxtData data = new TxtData();
				body = data.readTxtFile(fPath + "person.txt");
				token = data.readTxtFile(fPath + "personToken.txt");
			}

			break;
		case enterprise:
			File efile = new File(fPath + "enterpriseToken.txt");
			if (!efile.exists() || System.currentTimeMillis() - efile.lastModified() > 120000) {
				signin(user.getEnterprise());
//				String body = getBody();
				JsonPath json = new JsonPath(body).setRoot("value");
				token = json.getString("token");
				textData.writerText(fPath + "enterpriseToken.txt", token);
				textData.writerText(fPath + "enterprise.txt", body);
			} else {
				TxtData data = new TxtData();
				body = data.readTxtFile(fPath + "enterprise.txt");
				token = data.readTxtFile(fPath + "enterpriseToken.txt");
			}

			break;
		case admin:
			File afile = new File(fPath + "adminCookie.txt");
			if (!afile.exists() || System.currentTimeMillis() - afile.lastModified() > 120000) {
				signin(user.getAdmin());
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
	
	@Description("保存登录接口返回的数据")
	public void setBody(Response response){
		body = response.getBody().asString();
		body = body.substring(body.indexOf("{"), body.lastIndexOf("}")+1);
	}
	
	@Description("获取登录接口返回的数据")
	public String getBody(){
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
