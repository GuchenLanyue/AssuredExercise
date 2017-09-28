package com.exercise.rest_assured.testcase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.exercise.rest_assured.utils.HttpMethods;
import com.exercise.rest_assured.utils.JsonUtils;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TestLinkExercise extends BaseTest{

	public void testlink(){
		TestLinkAPI api = null;
		String url = "http://localhost:8088/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
	    String devKey = "7a0c263e6dde27cd63374731b93067c7";
	    
	    System.out.println("URL=" + url);
	    System.out.println("devKey=" + devKey);
//	    logger.debug("URL=" + url);
//	    logger.debug("devKey=" + devKey);
	    try {
	        api = new TestLinkAPI(new URL(url), devKey);
	    } catch (TestLinkAPIException te) {
	        te.printStackTrace();
	    } catch (MalformedURLException mue) {
	        mue.printStackTrace();
	    }
	    
	    TestPlan tl = api.getTestPlanByName("login","NanChang");
	    System.out.println(tl.getId()+":"+tl.getName());
//	    TestSuite[] testSuitesInTestPlan = api.getTestSuitesForTestPlan(tl.getId());
//	    String details = api.getTestSuitesForTestPlan(testSuitesInTestPlan[0].getId())[0].getDetails();
//	    System.out.println(details);
	    TestCase[] tcs=api.getTestCasesForTestPlan(tl.getId(), null, null,null,null,null,
                                            null,null,ExecutionType.AUTOMATED,null,null);
        
	    System.out.println(tcs.length);
        for(TestCase tc:tcs){
        	TestCase tcase = api.getTestCase(tc.getId(), null, null);
        	System.out.println(tcase.getSummary()+"/"+tcase.getPreconditions());
        	for (TestCaseStep step:tcase.getSteps()) {
				System.out.println("action:"+step.getActions()+"; expected:"+step.getExpectedResults());
			}
        }
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void jsonTest(){
		String file = "C:\\Users\\sam\\Desktop\\api.json";
		JsonUtils jsonUtil = new JsonUtils();
		JsonPath json = jsonUtil.jsonReader(file);
		Map<String, Object> jsonMap = new LinkedHashMap<>();
		for(Object key:json.getMap("apis").keySet()){
			Map<String, Object> baseMap = new LinkedHashMap<>();
			baseMap.put("Method","POST");
			baseMap.put("baseURL", "http://nchr.release.microfastup.com/api_doc/");
			baseMap.put("path", key.toString()+".json");
			
			Map<String, Object> cookieMap = new LinkedHashMap<>();
			cookieMap.put("UM_distinctid", "15b71f24cc2101-020ee61b4f9c14-8373f6a-100200-15b71f24cc312d");
			cookieMap.put("PHPSESSID", "hhc9hfr4rqt90horo8pl8ofa42");


			Response response = RestAssured.given()
					.proxy("127.0.0.1",8888)
					.cookies(cookieMap)
					.header("Accept", "application/json")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("Cache-Control", "no-cache")
					.config(RestAssured.config()
							  .encoderConfig(EncoderConfig.encoderConfig()
									    .defaultContentCharset("UTF-8")
									    .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.when()
					.get(baseMap.get("baseURL").toString()+baseMap.get("path").toString())
				.then()
//					.log().body()
//					.statusCode(200)
				.extract()
					.response();
			HttpMethods http = new HttpMethods();
			JsonPath body = JsonPath.with(http.getBody(response)).setRoot("apis");
			JsonPath res = JsonPath.with(http.getBody(response));
			List<String> apis = body.getList("path");
			List<String> descriptions = body.getList("description");
			List<Map<String, Object>> list = new ArrayList<>();
			for(int i = 0 ;i < apis.size();i++){
				String path = apis.get(i);
				Map<String, Object> chPath = new LinkedHashMap<>();
				chPath.put("description", descriptions.get(i));
				List<String> params = new ArrayList<String>();
				System.out.println(res.getList("apis["+i+"].operations.parameters.name").size());
				for(Object el:res.getList("apis["+i+"].operations.parameters.name")){
					params = (List<String>) el;
				}
				if(params.contains("token")){
					chPath.put("role", "token");
				}else{
					chPath.put("role", "guest");
				}
				chPath.put("path", path);
				list.add(chPath);
//				cPath.put(path, chPath);
			}

			jsonMap.put(key.toString(), list);
		}
		
		RestAssured.given().proxy("127.0.0.1",8888).contentType(ContentType.JSON).body(jsonMap).post("http://nchr.release.microfastup.com/api_doc/info").then().extract().response();
	}
}
