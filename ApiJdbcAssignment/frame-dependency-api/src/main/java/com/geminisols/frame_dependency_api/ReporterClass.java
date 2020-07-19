package com.geminisols.frame_dependency_api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReporterClass {
	private Logger logger = Logger.getLogger(ReporterClass.class);
	public Properties prop;
	public static ExtentReports extent;
	public static ExtentTest test;
	public String testCaseName, testDescription, category, authors;
	public SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss.SSS");

	public ExtentReports startReport() {
		try {
			Calendar cal = Calendar.getInstance();
			Date currentTimeStamp = cal.getTime();

			String timeInFormat = formater.format(currentTimeStamp);
			String reportLocation = System.getProperty("user.dir") + "/reportingOutput/TestRun_" + timeInFormat
					+ ".html";
			extent = new ExtentReports(reportLocation, false);
			logger.info("Report is created at the location - " + reportLocation);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Report is not created due to the following error - " + e.getMessage());
		}
		return extent;
	}

	public void report(String status, String expected, String actual) {
		if (status.equalsIgnoreCase("Pass")) {
			test.log(LogStatus.PASS, expected, actual);
		} else if (status.equalsIgnoreCase("Fail")) {
			test.log(LogStatus.FAIL, expected, actual);
		} else if (status.equalsIgnoreCase("Warning")) {
			test.log(LogStatus.WARNING, expected, actual);
		} else {
			test.log(LogStatus.INFO, expected, actual);
		}
		logger.info("Status-" + status + ",  Result-" + actual);
	}

	public ExtentTest startTestCase() {
		test = extent.startTest(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);
		logger.info("Test is started with following configuration: -\ntestCaseName-" + testCaseName
				+ ",\ntestDescription-" + testDescription + ",\nauthors-" + authors + ",\ncategory-" + category);
		return test;
	}

	public void endTestcase() {
		extent.endTest(test);
		logger.info("Test ended");
	}

	public void endReport() {
		extent.flush();
		logger.info("Report ended");
	}

}
