package com.geminisols.countries_test_automation;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.geminisols.frame_dependency_api.JdbcConnection;
import com.geminisols.frame_dependency_api.ReporterClass;
import com.geminisols.frame_dependency_api.RestAPIClient;

import io.restassured.path.json.JsonPath;

public class TestBase {
	public JdbcConnection jdbc = new JdbcConnection();
	public RestAPIClient api = new RestAPIClient();
	public ReporterClass reporter = new ReporterClass();

	String respString = null;
	JsonPath js = null;

	public List<String> dbCountries_C_Id = null;
	public List<String> dbCountries_Country = null;
	public List<String> dbCountries_Capital = null;
	public List<String> dbCountries_Currency_Code = null;
	public List<String> dbBorders_C_Id = null;
	public List<String> dbBorders_B_Id = null;

	public TestBase() {
		// API Response
		api.request = null;
		api.response = null;

		String uri = "https://restcountries.eu";
		String endPoint = "/rest/v2/all";
		api.setUp(uri, endPoint);
		api.setHeaders("Content-Type", "application/json");
		api.setHeaders("Accept", "application/json");
		api.setGetRequest();
		api.response.contentType().contains("application/json");

		respString = api.response.asString();
		js = new JsonPath(respString);

		// JDBC Connection
		String query = "SELECT * FROM countries";
		dbCountries_C_Id = jdbc.retreiveColumnData(query, "C_ID");
		dbCountries_Country = jdbc.retreiveColumnData(query, "Country");
		dbCountries_Capital = jdbc.retreiveColumnData(query, "Capital");
		dbCountries_Currency_Code = jdbc.retreiveColumnData(query, "Currency_Code");

		query = "SELECT * FROM borders";
		dbBorders_C_Id = jdbc.retreiveColumnData(query, "C_ID");
		dbBorders_B_Id = jdbc.retreiveColumnData(query, "B_ID");
	}

	@BeforeSuite
	public void createReport() {
		// Start Report
		reporter.startReport();
	}

	@AfterSuite
	public void afterSuite() {
		reporter.endReport();
	}

	public void addVaidationStep(boolean blnStepFlag, String strExpectedResult, String strActualResult,
			String strErrDescription, String... status) {
		if (status.length > 0)
			reporter.report(status[0], strExpectedResult, strActualResult);
		else if (blnStepFlag)
			reporter.report("Pass", strExpectedResult, strActualResult);
		else {
			reporter.report("Fail", strExpectedResult, strErrDescription);
			Assert.assertFalse(true);
		}
	}
}
