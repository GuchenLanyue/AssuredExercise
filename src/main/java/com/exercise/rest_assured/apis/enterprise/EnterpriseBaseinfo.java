package com.exercise.rest_assured.apis.enterprise;

import java.io.File;
import java.util.Map;
import java.util.Random;

import com.exercise.rest_assured.apis.UplodeImage;
import com.exercise.rest_assured.apis.person.BaseInfo;
import com.exercise.rest_assured.utils.JsonUtils;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

public class EnterpriseBaseinfo {
	private Map<String, Object> param = null;
	private String url = null;
	private String src = null;
	
	public EnterpriseBaseinfo(String basePath,String srcDir) {
		// TODO Auto-generated constructor stub
		url = basePath;
		src = srcDir;
	}
	
	@Step
	public Map<String, Object> setParams(Map<String, Object> params){
		BaseInfo baseinfo = new BaseInfo(url);
		param = params;
		//设置企业性质
		baseinfo.setenterprisenature();
		param.put("nature", baseinfo.getEnterprisenature());
		//设置行业
		baseinfo.setIndustry();
		param.put("industry", baseinfo.getIndustry());
		Random random = new Random();
		//设置是否已入驻(1:已入驻 2:未入驻)
		int settled = random.nextInt(1)+1;
		param.put("settled", settled);
		File id_img = new File(src+"/img/Wolf_track_128px_1084084_easyicon.net.png");
		File license_img = new File(src+"/img/search_cat_128px_1141878_easyicon.net.png");
		File logo = new File(src+"/img/wolf_128px_504554_easyicon.net.png");
		//上传图片
		UplodeImage img = new UplodeImage();
		param.put("id_img", img.upload(url, id_img));
		param.put("license_img", img.upload(url, license_img));
		param.put("logo", img.upload(url, logo));
		
		return param;
	}
	
	@Step
	public void checkInfo(String path,JsonPath responseJson){
		JsonUtils jutil = new JsonUtils();
		jutil.equalsJson(param, path, responseJson);
	}
}
