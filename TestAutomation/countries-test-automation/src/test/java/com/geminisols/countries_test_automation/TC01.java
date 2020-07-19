package com.geminisols.countries_test_automation;

import java.util.LinkedList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC01 extends TestBase {

	@BeforeMethod
	public void beforeTest() {
		reporter.testCaseName = "TC01";
		reporter.testDescription = "To verify missing Country column value of countries table, but present in API response";
		reporter.authors = "Rachit";
		reporter.category = "Functional";
		reporter.startTestCase();
	}

	@Test
	public void validateAllMissingCountryInDB(ITestContext testContext) {
		boolean blnStepFlag = false;
		String[] api_C_Id= js.get("alpha3Code").toString().replace("[", "").replace("]", "").split(", ");
		String[] api_Country= js.get("name").toString().replace("[", "").replace("]", "").split(", ");

		blnStepFlag = (api_C_Id.length > 0);
		addVaidationStep(blnStepFlag, "Verify API Executed:<br>https://restcountries.eu/rest/v2/all", "API executed",
				"API not executed");
		if (!blnStepFlag) {

			return;
		}

		blnStepFlag = (jdbc != null);
		addVaidationStep(blnStepFlag, "Verify Connection Made to DB", "Connection established",
				"JDBC Connection failed");

		blnStepFlag = (dbCountries_C_Id.size() > 0 && dbBorders_C_Id.size() > 0);
		addVaidationStep(blnStepFlag, "Query Executed: <br>SELECT * FROM countries<br>SELECT * FROM borders",
				"All the columns values are retieved successfully from countries and borders table",
				"All the columns values are not retieved from countries and borders table");

		List<String> missingCountries = new LinkedList<String>();
		for (String cntr : api_Country) {
			if (!dbCountries_Country.contains(cntr)) {
				missingCountries.add(cntr);
			}
		}
		blnStepFlag = missingCountries.size() == 0;
		addVaidationStep(blnStepFlag,
				"Verify missing Country column value of countries table, but present in API response",
				"Countries are matched between DB and API",
				missingCountries.size() + " countries data are missing in the DB i.e. " + missingCountries);
	}
	
	@AfterMethod
	public void afterTest() {
		reporter.endTestcase();
	}

}
