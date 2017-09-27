package com.exercise.rest_assured.testcase.admin;

import java.util.List;
import java.util.Random;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.admin.Examine;
import com.exercise.rest_assured.apis.enterprise.EnterpriseJob;
import com.exercise.rest_assured.utils.testutils.BaseTest;

import junit.framework.Assert;

public class ExamineJobTest extends BaseTest{

	@Test
	public void onLine_Test(){
		EnterpriseJob eJob = new EnterpriseJob(getBaseURL());
		List<String> jobs = eJob.getUserJobList(1);
		if(jobs.size()==0){
			eJob.addJob();
			jobs = eJob.getUserJobList(1);
		}
		//审核中的职位随机取一个
		Random random = new Random();
		int index = random.nextInt(jobs.size());
		String job_id = jobs.get(index);
		//审核通过
		Examine examine = new Examine();
		examine.job(job_id,"2");
		String status = eJob.getUserJobShow(job_id).setRoot("value").getString("status");
		
		Assert.assertEquals("2", status,"审核失败，职位【"+job_id+"】的实际状态为："+status);
	}
	
	@Test
	public void offLine_Test(){
		Examine examine = new Examine();
		EnterpriseJob eJob = new EnterpriseJob(getBaseURL());
		List<String> jobs = eJob.getUserJobList(1);
		if(jobs.size()==0){
			eJob.addJob();
			jobs = eJob.getUserJobList(1);
		}
		//审核中的职位随机取一个
		Random random = new Random();
		int index = random.nextInt(jobs.size());
		String job_id = jobs.get(index);
		//审核不通过
		examine.job(job_id,"4");
		String status = eJob.getUserJobShow(job_id).setRoot("value").getString("status");
		
		Assert.assertEquals("4", status,"审核失败，职位【"+job_id+"】的实际状态为："+status);
	}
}
