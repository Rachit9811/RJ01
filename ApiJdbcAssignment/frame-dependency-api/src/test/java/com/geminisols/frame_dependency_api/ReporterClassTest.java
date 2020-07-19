package com.geminisols.frame_dependency_api;

import org.junit.Assert;
import org.junit.Test;


public class ReporterClassTest {
	@Test
	public void testReportCreation() {
		boolean blnFlag = false;
		
		try {
			ReporterClass reporterClass = new ReporterClass();
			reporterClass.extent = reporterClass.startReport();
			
			reporterClass.testCaseName = "Verify Reporter Class";
			reporterClass.testDescription = "To verify the Reporter class will be able to create a report or not";
			reporterClass.authors="Rachit";
			reporterClass.category = "Test";
			
			reporterClass.test = reporterClass.startTestCase();
			reporterClass.report("Pass","Verify the first step","First Step is executed successfully");
			reporterClass.report("Pass","Verify the second step","Second Step is executed successfully");
			reporterClass.report("Info","Verify the third step","Result is unknown");
			reporterClass.report("Fail","Verify the fourth step","Fourth Step is not executed properly");
			
			reporterClass.endTestcase();
			reporterClass.endReport();
			blnFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(blnFlag);
	}
}
