package com.exercise.rest_assured.util.apis;

import static io.restassured.RestAssured.given;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;

import io.qameta.allure.Allure;
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
	private String currentstate = null;
	private String goodatlanguage = null;
	private String maritalstatus = null;

	public BaseInfo() {
		// TODO Auto-generated constructor stub
	}
	
	@Step("geteducation() 获取学历")
	@Description("获取学历")
	public String geteducation(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/geteducation")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/geteducation.Response.body:", body);
			Assert.fail("/basics/geteducation 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("seteducation() 设置学历")
	@Description("设置学历")
	public void seteducation(){
		JsonPath json = new JsonPath(geteducation());
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
	public String getenterprisenature(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getenterprisenature")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getenterprisenature.Response.body:", body);
			Assert.fail("/basics/getenterprisenature 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setenterprisenature() 设置企业性质")
	@Description("设置企业性质")
	public void setenterprisenature(){
		JsonPath json = new JsonPath(getenterprisenature());
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
	public String getcompanysize(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getcompanysize")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getcompanysize.Response.body:", body);
			Assert.fail("/basics/getcompanysize 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setcompanysize() 设置公司规模")
	@Description("设置公司规模")
	public void setcompanysize(){
		JsonPath json = new JsonPath(getcompanysize());
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
	public String getjoblevel(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getjoblevel")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getjoblevel.Response.body:", body);
			Assert.fail("/basics/getjoblevel 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setjoblevel() 设置职位级别")
	@Description("设置职位级别")
	public void setjoblevel(){
		JsonPath json = new JsonPath(getjoblevel());
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
	public String gethealthy(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/gethealthy")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/gethealthy.Response.body:", body);
			Assert.fail("/basics/gethealthy 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("sethealthy() 设置健康状况")
	@Description("设置健康状况")
	public void sethealthy(){
		JsonPath json = new JsonPath(gethealthy());
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
	public String getworklife(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getworklife")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getworklife.Response.body:", body);
			Assert.fail("/basics/getworklife 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setworklife() 设置工作年限")
	@Description("设置工作年限")
	public void setworklife(){
		JsonPath json = new JsonPath(getworklife());
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
	public String getgoodatlanguage(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getgoodatlanguage")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getgoodatlanguage.Response.body:", body);
			Assert.fail("/basics/getgoodatlanguage 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setgoodatlanguage() 设置擅长外语")
	@Description("设置擅长外语")
	public void setgoodatlanguage(){
		JsonPath json = new JsonPath(getgoodatlanguage());
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
	public String getmaritalstatus(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getmaritalstatus")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getmaritalstatus.Response.body:", body);
			Assert.fail("/basics/getmaritalstatus 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setmaritalstatus() 设置婚姻状况")
	@Description("设置婚姻状况")
	public void setmaritalstatus(){
		JsonPath json = new JsonPath(getmaritalstatus());
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
	public String getcurrentstate(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getcurrentstate")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getcurrentstate.Response.body:", body);
			Assert.fail("/basics/getcurrentstate 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setcurrentstate() 设置当前状态")
	@Description("设置当前状态")
	public void setcurrentstate(){
		JsonPath json = new JsonPath(getenterprisenature());
		Map<String, Object> list = json.get("value");
		List<String> keys = new ArrayList<>();
		for(String key:list.keySet()){
			keys.add(key);
		}
		
		Random random = new Random();
		int index = random.nextInt(keys.size());
		currentstate = keys.get(index);
	}
	
	@Description("获取行业列表")
	public String getindustry(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getindustry")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getindustry.Response.body:", body);
			Assert.fail("/basics/getindustry 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setIndustry() 设置行业")
	@Description("设置行业")
	public void setIndustry(){
		JsonPath json = new JsonPath(getindustry());
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
	public String getposition(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getposition")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setPositionData() 设置职位")
	@Description("设置职位")
	public int[] setPosition(){
		JsonPath json = new JsonPath(getposition());
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
	public String getmajor(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getmajor")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setPositionData() 设置专业")
	@Description("设置专业")
	public int[] setMajor(){
		JsonPath json = new JsonPath(getmajor());
		List<String> majorList = json.getList("value.value");
		Random random = new Random();
		
		int index = random.nextInt(majorList.size());
        major = Integer.valueOf(majorList.get(index).toString()).intValue();
        
        String path = "value["+index+"].children";
        List<Map<String, Object>> majorsList = json.getList(path);
        index = random.nextInt(majorsList.size());
        
        majors = Integer.valueOf(majorsList.get(index).get("value").toString()).intValue();
        int[] majorData = {major,majors};
        
        return majorData;
	}
	
	@Step
	@Description("获取期望薪资列表")
	public String getsalary(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getsalary")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		
		if (response.getStatusCode()!=200) {
			// TODO: handle exception
			String body = response.getBody().asString();
			Allure.addAttachment("/basics/getsalary.Response.body:", body);
			Assert.fail("/basics/getsalary 请求失败！");
		}
		
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step("setSalary() 设置期望薪资")
	@Description("设置期望薪资")
	public void setSalary(){
		JsonPath json = new JsonPath(getsalary());
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
	public String getarea(){
		Response response = given()
//				.proxy("http://127.0.0.1:8888")
				.contentType("application/x-www-form-urlencoded;charset=UTF-8")
			.when()
				.post("http://nchr.release.microfastup.com/nchr/basics/getarea")
			.then()
//				.statusCode(200)
			.extract()
				.response();
		String json = response.asString();
		
		while (json.charAt(0)!='{') {
			json = json.substring(1, json.length());
		}
		
		return json;
	}
	
	@Step(value = "setArea() 设置地区")
	@Description("设置地区-随机值")
	public int[] setArea(){
		JsonPath json = new JsonPath(getarea());
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
	public String getCurrentstate() {
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
