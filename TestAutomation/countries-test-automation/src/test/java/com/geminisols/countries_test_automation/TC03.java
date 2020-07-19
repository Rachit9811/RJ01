package com.geminisols.countries_test_automation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC03 extends TestBase {
	@BeforeMethod
	public void beforeTest() {
		reporter.testCaseName = "TC03";
		reporter.testDescription = "To verify all the countries with different Capital/Currency data in between DB & API";
		reporter.authors = "Rachit";
		reporter.category = "Functional";
		reporter.startTestCase();
	}

	@Test
	public void validateAllMissingCountryInDB(ITestContext testContext) {
		boolean blnStepFlag = false;

		// Step 1
		blnStepFlag = (jdbc != null);
		addVaidationStep(blnStepFlag, "Verify Connection Made to DB", "Connection established",
				"JDBC Connection failed");
		if (!blnStepFlag)
			return;

		// Step 2
		String query = "select C_ID, count(*) as Count from borders\n" + "group by C_ID\n" + "order by Count desc";
		String c_id = jdbc.retreiveColumnData(query, "C_ID").get(0);
		String count = jdbc.retreiveColumnData(query, "Count").get(0);

		blnStepFlag = c_id != null && count != null;
		addVaidationStep(blnStepFlag, "Query Executed: <br>" + query,
				"C_ID-" + c_id + " with max borders-" + count + " is retrieved successfully", "Query not run properly");
		if (!blnStepFlag)
			return;

		// Step 3
		blnStepFlag = (respString != null);
		addVaidationStep(blnStepFlag, "Verify API Executed:<br>https://restcountries.eu/rest/v2/all", "API executed",
				"API not executed");
		if (!blnStepFlag)
			return;

		// Step 4
		int allBordersAPILength = 0;
		String country = null;
		JSONArray jsonArray = new JSONArray(respString);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			if (jsonObj.get("alpha3Code").equals(c_id)) {
				allBordersAPILength = jsonObj.get("borders").toString().replace("[", "").replace("]", "")
						.split(",").length;
				country = jsonObj.get("name").toString();
				break;
			}
		}
		blnStepFlag = allBordersAPILength == Integer.parseInt(count);
		addVaidationStep(blnStepFlag, "Fetch all the borders from the API response where C_ID-" + c_id,
				country + " Country found with maximum border and verified with DB result",
				country + " Country found with maximum border, but not verified with DB result");
		if (!blnStepFlag)
			return;

		// Step 4
	}
}