package com.exercise.rest_assured.apis.enterprise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.exercise.rest_assured.apis.person.BaseInfo;
import com.exercise.rest_assured.utils.HttpMethods;
import com.exercise.rest_assured.utils.JsonUtils;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

public class EnterpriseJob {
	private Map<String,Object> jobParams = new HashMap<>();
	private String url = null;
	
	public EnterpriseJob(String baseURL) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	@Step("addJob() 新增职位")
	public String addJob(){
		String path = "/job/addjob";
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		
		Map<String, Object> param = new HashMap<>();
		param.put("title", "添加新职位");
		param.put("address", "新华科技大厦A座15楼1501");
		param.put("content", "测试新增职位接口");
		param.put("token", "");

		HttpMethods http = new HttpMethods();
		String body = http.getBody(http.request(baseMap, setParams(param)));
		
		return body;
	}
	
	@Step
	public Map<String,Object> setParams(Map<String,Object> params){
		Map<String,Object> param = new HashMap<>();
		param = params;
		BaseInfo info = new BaseInfo(url);
		
		//设置学历
		info.seteducation();
		jobParams.put("education", info.getEducation());
		//设置工作年限
		info.setworklife();
		jobParams.put("experience", info.getWorklife());
		//设置期望薪资
		info.setSalary();
		jobParams.put("salary", info.getSalary());
		//设置职位
		int[] positionData = info.setPosition();
		jobParams.put("category", positionData[0]);
		jobParams.put("categorys", positionData[1]);
		//设置地区
		int[] area = info.setArea();
		jobParams.put("provice", area[0]);
		jobParams.put("city", area[1]);
		//地区有可能只有两级没有第三级
		if (area.length<3) {
			param.remove("country");
		}else{
			jobParams.put("country", area[2]);
		}
		//
		//设置招聘人数
		jobParams.put("number", new Random().nextInt(100)+1);
		//设置职位性质(1全职2兼职3实习4校园)
		jobParams.put("position_nature", new Random().nextInt(3)+1);
		
		for(String key:jobParams.keySet()){
			param.put(key, jobParams.get(key));
		}
		
		return param;
	}
	
	public Map<String, Object> getParams(){
		return jobParams;
	}
	
	/**
	 * @description 获取用户职位列表
	 * @param status 状态(1审核中2发布中3已下线4未通过)
	 * */
	@Step("getUserJobList() 获取用户职位列表")
	public List<String> getUserJobList(int status){
		Map<String, Object> params = new HashMap<>();
		String path = "/job/getuserjoblist";
		params.put("status", status);
		params.put("token", "");
		params.put("page", 1);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		HttpMethods http = new HttpMethods();
		
		String body = http.getBody(http.request(baseMap, params));
		
		JsonPath json = JsonPath.with(body).setRoot("value");
		List<String> ids = new ArrayList<>();
		if (json.getInt("list_nums")!=0) {
			ids = json.getList("list.id");
		}
		
		return ids;
	}
	/**
	 * @param id 职位id
	 * @description 获取职位详情
	 * */
	@Step("getUserJobShow() 获取职位详情")
	public JsonPath getUserJobShow(String id){
		Map<String, Object> params = new HashMap<>();
		String path = "/job/getuserjobshow";
		params.put("token", "");
		params.put("id", id);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		HttpMethods http = new HttpMethods();
		
		String body = http.getBody(http.request(baseMap, params));
		
		JsonPath json = JsonPath.with(body);
		
		return json;
	}
	
	/**
	 * @description 更新职位状态
	 * @param list 职位id列表
	 * @param status 职位状态
	 * */
	@Step("upStatus() 更新职位状态")
	public void upStatus(List<String> list,String status){
		String ids = null;
		if(list.size()==0){
			return;
		}
		
		//将id连接成为字符串，中间用逗号隔开（接口要求）
		for(String id:list){
			ids += (id+",");
		}
		ids = ids.substring(4, ids.length()-1);
		
		Map<String, Object> params = new HashMap<>();
		String path = "/job/upstatus";
		params.put("token", "");
		params.put("ids", ids);
		params.put("status", status);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		HttpMethods http = new HttpMethods();
		
		http.getBody(http.request(baseMap, params));
	}
	
	/**
	 * @description 更新职位
	 * @param params Map<String, Object>职位属性
	 * */
	@Step("upJob()更新职位")
	public void upJob(Map<String, Object> params){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", "/job/upjob");

		JsonPath jobShow = getUserJobShow(params.get("id").toString());
		Map<String, Object> param = jobShow.getMap("value");
		
		for(String key:params.keySet()){
			param.put(key, params.get(key));
		}

		HttpMethods http = new HttpMethods();
		http.request(baseMap, param);
	}
	
	/**
	 * @description 获取用户投递的简历
	 * @param job_id 工作id
	 * */
	@Step("getUserResume() 获取用户投递的简历")
	public String getUserResume(String job_id){
		Map<String, Object> params = new HashMap<>();
		String path = "/job/getuserresume";
		params.put("token", "");
		params.put("job_id", job_id);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method", "POST");
		baseMap.put("baseURL", url);
		baseMap.put("path", path);
		HttpMethods http = new HttpMethods();
		
		return http.getBody(http.request(baseMap, params));
	}
	
	@Step
	public void checkInfo(String path,JsonPath responseJson){
		JsonUtils jutil = new JsonUtils();
		jutil.equalsJson(jobParams, path, responseJson);
	}
}
