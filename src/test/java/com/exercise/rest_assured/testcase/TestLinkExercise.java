package com.exercise.rest_assured.testcase;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.Test;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;


public class TestLinkExercise {
	@Test
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
}
