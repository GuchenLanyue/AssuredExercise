package com.exercise.rest_assured;

import org.testng.annotations.Test;

import com.exercise.rest_assured.util.apis.Job;

public class JobTest {
	
	@Test
	public void addJob(){
		Job job = new Job();
		job.addJob();
	}
	
	@Test
	public void editJob(){
		Job job = new Job();
		job.addJob();
	}
	
	@Test
	public void delJob(){
		Job job = new Job();
		job.addJob();
	}
}
