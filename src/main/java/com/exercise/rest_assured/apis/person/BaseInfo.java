package com.exercise.rest_assured.apis.person;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;

import com.exercise.rest_assured.utils.HttpMethods;
import com.exercise.rest_assured.utils.TxtData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BaseInfo {

	private String industry = null;
	private int education = 1;
	private int positions = 1;
	private int position = 1;
	private int major = 1;
	private int majors = 1;
	private int provice = 1;
	private int city = 1;
	private int district = 1;
	private String salary = null;
	private String enterprisenature = null;
	private String companysize = null;
	private String joblevel = null;
	private String healthy = null;
	private String worklife = null;
	private int currentstate = 0;
	private String goodatlanguage = null;
	private String maritalstatus = null;
	private String baseURL = null;
	
	public BaseInfo(String url) {
		// TODO Auto-generated constructor stub
		url = baseURL;
	}
	
	@Step("geteducation() 获取学历")
	@Description("获取学历")
	public String education(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/geteducation");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		
		String body =http.getBody(response);
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";

		fileData.writerText(filePath+"education.json", body);
		
		return body;
	}
	
	@Step("seteducation() 设置学历")
	@Description("设置学历")
	public void seteducation(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/education.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(education());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		education = Integer.valueOf(keys.get(index)).intValue();
	}
	
	@Step("getenterprisenature() 获取企业性质")
	@Description("获取企业性质")
	public String enterprisenature(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getenterprisenature");

		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"enterprisenature.json", body);
		
		return body;
	}
	
	@Step("setenterprisenature() 设置企业性质")
	@Description("设置企业性质")
	public void setenterprisenature(){
		
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/enterprisenature.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(enterprisenature());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		enterprisenature = keys.get(index);
	}
	
	@Step("getcompanysize() 获取公司规模")
	@Description("获取公司规模")
	public String companysize(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getcompanysize");

		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"companysize.json", body);
		
		return body;
	}
	
	@Step("setcompanysize() 设置公司规模")
	@Description("设置公司规模")
	public void setcompanysize(){
		
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/companysize.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(companysize());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		companysize = keys.get(index);
	}
	
	@Step("getjoblevel() 获取职位级别")
	@Description("获取职位级别")
	public String joblevel(){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getjoblevel");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"joblevel.json", body);
		
		return body;
	}
	
	@Step("setjoblevel() 设置职位级别")
	@Description("设置职位级别")
	public void setjoblevel(){
		
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/joblevel.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(joblevel());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		joblevel = keys.get(index);
	}
	
	@Step("gethealthy() 获取健康状况")
	@Description("获取健康状况")
	public String healthy(){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/gethealthy");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"healthy.json", body);
		
		return body;
	}
	
	@Step("sethealthy() 设置健康状况")
	@Description("设置健康状况")
	public void sethealthy(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/healthy.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(healthy());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		healthy = keys.get(index);
	}
	
	@Step("getworklife() 获取工作年限")
	@Description("获取工作年限")
	public String worklife(){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getworklife");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"worklife.json", body);
		
		return body;
	}
	
	@Step("setworklife() 设置工作年限")
	@Description("设置工作年限")
	public void setworklife(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/worklife.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(worklife());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		worklife = keys.get(index);
	}
	
	@Step("getgoodatlanguage() 获取擅长外语")
	@Description("获取擅长外语")
	public String goodatlanguage(){
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getgoodatlanguage");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);

		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"goodatlanguage.json", body);
		
		return body;
	}
	
	@Step("setgoodatlanguage() 设置擅长外语")
	@Description("设置擅长外语")
	public void setgoodatlanguage(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/goodatlanguage.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(goodatlanguage());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		goodatlanguage = keys.get(index);
	}
	
	@Step("getmaritalstatus() 获取婚姻状况")
	@Description("获取婚姻状况")
	public String maritalstatus(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getmaritalstatus");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);

		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"maritalstatus.json", body);
		
		return body;
	}
	
	@Step("setmaritalstatus() 设置婚姻状况")
	@Description("设置婚姻状况")
	public void setmaritalstatus(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/maritalstatus.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(maritalstatus());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		maritalstatus = keys.get(index);
	}
	
	@Step("getcurrentstate() 获取当前状态")
	@Description("获取当前状态")
	public String currentstate(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getcurrentstate");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"currentstate.json", body);
		
		return body;
	}
	
	@Step("setcurrentstate() 设置当前状态")
	@Description("设置当前状态")
	public void setcurrentstate(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/currentstate.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(currentstate());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		
		currentstate = Integer.valueOf(keys.get(index)).intValue();
	}
	
	@Description("获取行业列表")
	public String industry(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getindustry");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);

		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"industry.json", body);
		
		return body;
	}
	
	@Step("setIndustry() 设置行业")
	@Description("设置行业")
	public void setIndustry(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/industry.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(industry());
		}
		
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
        industry = keys.get(index);
	}
	
	@Step
	@Description("获取职位列表")
	public String position(){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getposition");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"position.json", body);
		
		return body;
	}
	
	@Description("设置职位")
	@Step("setPositionData() 设置职位")
	public int[] setPosition(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/position.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(position());
		}
		
		List<String> positionList = json.getList("value.value");
		Random random = new Random();
		
		int index = random.nextInt(positionList.size());
        position = Integer.valueOf(positionList.get(index).toString()).intValue();
        
        String path = "value["+index+"].children.value";
        List<String> positionsList = json.getList(path);
        index = random.nextInt(positionsList.size());
        positions = Integer.valueOf(positionsList.get(index).toString()).intValue();
        int[] positionData = {position,positions};
        
        return positionData;
	}
	
	@Step
	@Description("获取专业列表")
	public String major(){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getmajor");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"major.json", body);
		
		return body;
	}
	
	@Step("setPositionData() 设置专业")
	@Description("设置专业")
	public int[] setMajor(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/major.json";
		File majorFile = new File(filePath);
		long time = System.currentTimeMillis() - majorFile.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(majorFile);
		}else{
			json = new JsonPath(major());
		}
		
		List<String> majorList = json.getList("value.value");
		Random random = new Random();
		
		int majorIndex = random.nextInt(majorList.size());
        major = Integer.valueOf(majorList.get(majorIndex).toString()).intValue();
        
        String path = "value["+majorIndex+"].children";
        List<Map<String, Object>> majorsList = json.getList(path);
        int majorsIndex = 0;
        if (majorsList.size()>0) {
        	majorsIndex = random.nextInt(majorsList.size());
            majors = Integer.valueOf(majorsList.get(majorsIndex).get("value").toString()).intValue();
           
            return new int[]{major,majors};
            
		}else{
			return new int[]{major};
		}
	}
	
	@Step
	@Description("获取期望薪资列表")
	public String salary(){		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getsalary");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);
		
		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"salary.json", body);
		
		return body;
	}
	
	@Step("setSalary() 设置期望薪资")
	@Description("设置期望薪资")
	public void setSalary(){
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/salary.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(salary());
		}
		
		Map<String, Object> salarys = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:salarys.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		salary = keys.get(index);
	}
	
	
	@Description("获取地区")
	public String area(){
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("Method","POST");
		baseMap.put("baseURL", baseURL);
		baseMap.put("path", "/basics/getarea");
		
		HttpMethods http = new HttpMethods();
		Response response = http.request(baseMap);
		String body =http.getBody(response);

		TxtData fileData = new TxtData();
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/";
		fileData.writerText(filePath+"area.json", body);
		
		return body;
	}
	
	@Step(value = "setArea() 设置地区")
	@Description("设置地区-随机值")
	public int[] setArea(){
		//相应数据太大，将数据存在本地
		String filePath = System.getProperty("user.dir")+"/src/test/resources/base/area.json";
		File file = new File(filePath);
		long time = System.currentTimeMillis() - file.lastModified();
		JsonPath json = null;
		if (time<86400000) {
			json = new JsonPath(file);
		}else{
			json = new JsonPath(area());
		}
		
		List<Object> provinceList = json.getList("value.value");
		Random random = new Random();
		if (provinceList.size()<1) {
			Assert.fail("地区数据-“省”的值为空！");
		}
		int proviceIndex = random.nextInt(provinceList.size());
		provice = Integer.valueOf(provinceList.get(proviceIndex).toString()).intValue();
        
        String cityPath = "value["+proviceIndex+"].children.value";
        List<String> cityList = json.getList(cityPath);
        if (cityList.size()<1) {
			Assert.fail("地区数据-“市”的值为空！");
		}
        int cityIndex = random.nextInt(cityList.size());
        city = Integer.valueOf(cityList.get(cityIndex).toString()).intValue();
        
        String districtPath = "value["+proviceIndex+"].children["+cityIndex+"].children.value";
        List<String> districtList = json.getList(districtPath);
        
        if (districtList.size()<1) {
        	int[] area = {provice ,city};
        	return area;
		}
        
        int districtIndex = random.nextInt(districtList.size());
        district = Integer.valueOf(districtList.get(districtIndex).toString()).intValue();
        
        int[] area = {provice ,city , district};
        
        return area;
	}
	
	/** 
     * 获取随机日期 
     *  
     * @param beginDate 
     *            起始日期，格式为：yyyy-MM-dd 
     * @param endDate 
     *            结束日期，格式为：yyyy-MM-dd 
     * @return 
     */  
  
    public Date randomDate(String beginDate, String endDate) {  
        try {  
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
            Date start = format.parse(beginDate);// 构造开始日期  
            Date end = format.parse(endDate);// 构造结束日期  
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。  
            if (start.getTime() >= end.getTime()) {  
                return null;  
            }  
            long date = random(start.getTime(), end.getTime());  
  
            return new Date(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    private static long random(long begin, long end) {  
        long rtn = begin + (long) (Math.random() * (end - begin));  
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值  
        if (rtn == begin || rtn == end) {  
            return random(begin, end);  
        }  
        return rtn;  
    }  
    
    // 18位身份证号码各位的含义:
    // 1-2位省、自治区、直辖市代码；
    // 3-4位地级市、盟、自治州代码；
    // 5-6位县、县级市、区代码；
    // 7-14位出生年月日，比如19670401代表1967年4月1日；
    // 15-17位为顺序号，其中17位（倒数第二位）男为单数，女为双数；
    // 18位为校验码，0-9和X。
    // 作为尾号的校验码，是由把前十七位数字带入统一的公式计算出来的，
    // 计算的结果是0-10，如果某人的尾号是0－9，都不会出现X，但如果尾号是10，那么就得用X来代替，
    // 因为如果用10做尾号，那么此人的身份证就变成了19位。X是罗马数字的10，用X来代替10
    
    public String getRandomID() {
        String id = "";
        // 随机生成省、自治区、直辖市代码 1-2
        String provinces[] = { "11", "12", "13", "14", "15", "21", "22", "23",
                "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
                "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
                "63", "64", "65", "71", "81", "82" };
        String province = provinces[new Random().nextInt(provinces.length - 1)];
        // 随机生成地级市、盟、自治州代码 3-4
        String citys[] = { "01", "02", "03", "04", "05", "06", "07", "08",
                "09", "10", "21", "22", "23", "24", "25", "26", "27", "28" };
        String city = citys[new Random().nextInt(citys.length - 1)];
        // 随机生成县、县级市、区代码 5-6
        String countys[] = { "01", "02", "03", "04", "05", "06", "07", "08",
                "09", "10", "21", "22", "23", "24", "25", "26", "27", "28",
                "29", "30", "31", "32", "33", "34", "35", "36", "37", "38" };
        String county = countys[new Random().nextInt(countys.length - 1)];
        // 随机生成出生年月 7-14
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE,
                date.get(Calendar.DATE) - new Random().nextInt(365 * 100));
        String birth = dft.format(date.getTime());
        // 随机生成顺序号 15-17
        String no = new Random().nextInt(999) + "";
        // 随机生成校验码 18
        String checks[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "X" };
        String check = checks[new Random().nextInt(checks.length - 1)];
        // 拼接身份证号码
        id = province + city + county + birth + no + check;

        return id;
    }
    
    @Description("获取性别")
	public int getSex() {
		int[] sex = {1,2};
		Random random = new Random();
		
		return sex[random.nextInt(sex.length)];
	}
    
	@Description("获取学历")
	public int getEducation(){
		seteducation();
		return education;
	}
	
	@Description("获取当前状况")
	public int getCurrentstate() {
		setcurrentstate();
		return currentstate;
	}
	
	@Description("获取擅长外语")
	public String getGoodatlanguage() {
		setgoodatlanguage();
		return goodatlanguage;
	}
	
	@Description("获取擅长外语级别")
	public int getLeve() {
		int[] leve = {1,2,3,4};
		Random random = new Random();
		
		return leve[random.nextInt(leve.length)];
	}
	
	@Description("获取婚育状况")
	public String getMaritalstatus() {
		setmaritalstatus();
		return maritalstatus;
	}
	
	@Description("获取工作年限")
	public String getWorklife() {
		setworklife();
		return worklife;
	}

	@Description("获取健康状况")
	public String getHealthy() {
		sethealthy();
		return healthy;
	}

	@Description("获取企业规模")
	public String getCompanysize() {
		setcompanysize();
		return companysize;
	}
	
	@Description("获取企业性质")
	public String getEnterprisenature() {
		return enterprisenature;
	}
	
	@Description("获取地区")
	public int[] getArea() {
		return setArea();
	}

	@Description("获取当前设置的职位")
	public int[] getPositionData() {
		return setPosition();
	}
	
	@Description("获取当前设置的专业")
	public int[] getMajor(){
		return setMajor();
	}
	
	@Description("获取行业")
	public String getIndustry() {
		return industry;
	}
	
	@Description("获取期望薪资")
	public String getSalary(){
		return salary;
	}
	
	@Description("获取职位级别")
	public String getJoblevel() {
		return joblevel;
	}
}
