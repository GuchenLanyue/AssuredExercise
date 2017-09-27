package com.exercise.rest_assured.testcase.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.admin.Examine;
import com.exercise.rest_assured.apis.enterprise.EnterpriseJob;
import com.exercise.rest_assured.apis.person.Basic;
import com.exercise.rest_assured.apis.person.Delivery;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;

public class AddresumeTest extends BaseTest{
	
	@Description("投递简历接口测试")
	@Test(dataProvider="SingleCase")
	public void add_Resume_Test(Map<String, Object> params){
		EnterpriseJob job = new EnterpriseJob(getBaseURL());
		String job_id = null;
		
		//如果没有发布中的职位就上线一个职位
		if(job.getUserJobList(2).size()==0){
			EnterpriseJob eJob = new EnterpriseJob(getBaseURL());
			//如果没有审核中的职位就新增一个职位
			if(job.getUserJobList(1).size()==0){
				eJob.addJob();
			}
			//获取所有审核中的职位
			List<String> ids = new ArrayList<>();
			ids = eJob.getUserJobList(1);
			//随机获取一个职位
			Random random = new Random();
			int index = random.nextInt(ids.size());
			job_id = ids.get(index);
			//获取职位详情
			JsonPath jobPath = eJob.getUserJobShow(job_id).setRoot("value");
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("jobID", jobPath.getString("id"));
			paramMap.put("title", jobPath.getString("title"));
			paramMap.put("des", jobPath.getString("des"));
			
			//审核通过该职位
			Examine examine = new Examine();
			examine.job(job_id,"2");
		}else{
			//获取所有发布中职位
			List<String> ids = job.getUserJobList(2);
			//随机获取一个职位
			Random random = new Random();
			int index = random.nextInt(ids.size());
			job_id = ids.get(index);
		}
		
		//投递简历
		Delivery delivery = new Delivery(getBaseURL());
		setRequest("addresume", delivery.setParams(job_id, params));
		//获取职位详情
		String body = job.getUserResume(job_id);
		//获取投递的简历总数
		int list_nums = JsonPath.with(body).setRoot("value").getInt("list_nums");
		if(list_nums>0){
			List<String> resumes = JsonPath.with(body).setRoot("value").getList("list.id");
			Basic basic = new Basic(getBaseURL());
			basic.getbasic();
			String id = String.valueOf(basic.getID());
			//验证简历id是否存在于list中
			if(!resumes.contains(id)){
				Assert.fail("投递简历失败，在job_id:"+job_id+"中没有找到简历id："+basic.getID());
			}
		}
		
	}
}
