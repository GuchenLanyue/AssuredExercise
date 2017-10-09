package com.exercise.rest_assured.apis.enterprise;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.exercise.rest_assured.apis.UplodeImage;
import com.exercise.rest_assured.apis.person.BaseInfo;
import com.exercise.rest_assured.utils.HttpMethods;
import com.exercise.rest_assured.utils.RandomValue;

import io.restassured.response.Response;

public class Settled {
	private String url = null;
	
	public Settled() {
		// TODO Auto-generated constructor stub
	}
	
	public Settled(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	public Map<String, Object> setParams(Map<String, Object> param){
		Map<String, Object> params = param;
		BaseInfo baseinfo = new BaseInfo(url);
		//企业性质
		baseinfo.setenterprisenature();
		params.put("nature", baseinfo.getEnterprisenature());
		//行业
		baseinfo.setIndustry();
		params.put("industry", baseinfo.getIndustry());
		RandomValue random = new RandomValue();
		//联系人
		params.put("contacts", random.getChineseName());
		//联系电话
		params.put("contact_tel", random.getTel());
		//入驻人数
		param.put("number", new Random().nextInt(1000)+20);
		//办公面积
		param.put("area", new Random().nextInt(100)+100);
		//营业执照
		UplodeImage image = new UplodeImage();
		String imgURL = image.upload(url, new File("./src/test/resources/img/search_cat_128px_1141878_easyicon.net.png"));
		param.put("img", imgURL);
		
		return params;
	}
	
	public String addEnterprise(){
		
		String path = "/settledenterprise/addapply";
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		
		Map<String, Object> param = new HashMap<>();
		//企业名称
		param.put("name", "本草纲目");
		//企业地址
		param.put("address", "新华科技大厦A座15楼1501");
		//联系人
		param.put("contacts", "灰太狼大王");
		//联系电话
		param.put("contact_tel", "13412345678");
		//企业性质
		param.put("nature", "");
		//行业
		param.put("industry", "药、器材、教材");
		//经营范围
		param.put("scope_business", "");
		//入驻人数
		param.put("number", "1000");
		//企业简介
		param.put("introduction", "食品级不锈钢烧水壶");
		//办公面积
		param.put("area", "1000");
		//希望园区给予的帮助
		param.put("help", "money，money，money");
		//带来助益
		param.put("benefit", "人，从，众");
		//营业执照
		param.put("img", "/attach/image/object/201709/28/20170928175522_60822.jpg");
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", param);
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
		String body = http.getBody(response);
		
		return body;
	}
	
	public String getSettledList(String page,String pages){
		String path = "/settledenterprise/getselledlist";
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		
		Map<String, Object> param = new HashMap<>();
		param.put("page", page);
		param.put("pages", pages);

		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", param);
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
		String body = http.getBody(response);
		
		return body;
	}
	
	public String getSettledList(){
		String path = "/settledenterprise/getselledlist";
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		
		Map<String, Object> param = new HashMap<>();
		param.put("page", "1");
		param.put("pages", "30");
		
		Map<String, Map<String, Object>> map = new HashMap<>();
		map.put("base", baseMap);
		map.put("params", param);
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(map);
		String body = http.getBody(response);
		
		return body;
	}
}
