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
		Job job = new Job(getBaseURL());
		job.cleanJobs();
		Intention intention = new Intention(getBaseURL());
		intention.cleanIntentions();
		Education education = new Education(getBaseURL());
		education.cleanEducations();
		Train train = new Train(getBaseURL());
		train.cleanTrains();
	}
}
