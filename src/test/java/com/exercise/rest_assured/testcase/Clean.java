package com.exercise.rest_assured.testcase;

import org.testng.annotations.Test;

import com.exercise.rest_assured.apis.person.Education;
import com.exercise.rest_assured.apis.person.Intention;
import com.exercise.rest_assured.apis.person.Job;
import com.exercise.rest_assured.apis.person.Train;
import com.exercise.rest_assured.utils.testutils.BaseTest;

public class Clean extends BaseTest{
	@Test
	public void clean(){
		Job job = new Job(getbasePath());
		job.cleanJobs();
		Intention intention = new Intention(getbasePath());
		intention.cleanIntentions();
		Education education = new Education(getbasePath());
		education.cleanEducations();
		Train train = new Train(getbasePath());
		train.cleanTrains();
	}
}
