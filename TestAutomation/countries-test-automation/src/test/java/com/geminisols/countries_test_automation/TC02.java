package com.geminisols.countries_test_automation;

import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC02 extends TestBase {
	private Logger logger = Logger.getLogger(TC02.class);

	@BeforeMethod
	public void beforeTest() {
		reporter.testCaseName = "TC02";
		reporter.testDescription = "To verify all the countries with different Capital/Currency data in between DB & API";
		reporter.authors = "Rachit";
		reporter.category = "Functional";
		reporter.startTestCase();
	}

	@Test
	public void validateAllMissingCountryInDB(ITestContext testContext) {
		boolean blnStepFlag = false;

		// Step 1
		String[] api_C_Id = js.get("alpha3Code").toString().replace("[", "").replace("]", "").split(", ");

		blnStepFlag = (api_C_Id.length > 0);
		addVaidationStep(blnStepFlag, "Verify API Executed:<br>https://restcountries.eu/rest/v2/all", "API executed",
				"API not executed");
		if (!blnStepFlag)
			return;

		// Step 2
		blnStepFlag = (jdbc != null);
		addVaidationStep(blnStepFlag, "Verify Connection Made to DB", "Connection established",
				"JDBC Connection failed");
		if (!blnStepFlag)
			return;

		// Step 3
		blnStepFlag = (dbCountries_C_Id.size() > 0 && dbBorders_C_Id.size() > 0);
		addVaidationStep(blnStepFlag, "Query Executed: <br>SELECT * FROM countries<br>SELECT * FROM borders",
				"All the columns values are retieved successfully from countries and borders table",
				"All the columns values are not retieved from countries and borders table");
		if (!blnStepFlag)
			return;

		// Step 4
		try {
			File myObj = new File("./reportingOutput/TC02_Mismatch.txt");
			if (myObj.createNewFile()) {
				logger.info("File created: " + myObj.getName());
			} else {
				logger.info("File already exists.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		addVaidationStep(blnStepFlag, "Create a file for reporting difference 'TC02_Mismatch.txt'", "File created",
				"File Not created", "Info");

		// Step 5
		int count1 = 0, count2 = 0;
		try {
			FileWriter fw = new FileWriter("./reportingOutput/TC02_Mismatch.txt");
			for (int i = 0; i < dbCountries_C_Id.size(); i++) {
				for (int j = 0; j < api_C_Id.length; j++) {
					if (dbCountries_C_Id.get(i).equals(api_C_Id[j])) {
						if (js.get("capital[" + j + "]") != null) {
							if (!js.get("capital[" + j + "]").toString().equals(dbCountries_Capital.get(i))) {
								fw.write("Country Name: " + dbCountries_Country.get(i)+"\n");
								fw.write("Data: Capital"+"\n");
								fw.write("API Value: " + js.get("capital[" + j + "]").toString()+"\n");
								fw.write("DB Value: " + dbCountries_Capital.get(i)+"\n\n");
								count1++;
							}
						}
						if (js.get("currencies[" + j + "].code[0]") != null) {
							if (!js.get("currencies[" + j + "].code[0]").toString()
									.equals(dbCountries_Currency_Code.get(i))) {
								fw.write("Country Name: " + dbCountries_Country.get(i)+"\n");
								fw.write("Data: Currency"+"\n");
								fw.write("API Value: " + js.get("currencies[" + j + "].code[0]").toString()+"\n");
								fw.write("DB Value: " + dbCountries_Currency_Code.get(i)+"\n\n");
								count2++;
							}
						}
					}
				}
			}
			fw.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		blnStepFlag = count1 == 0 && count2 == 0;
		addVaidationStep(blnStepFlag,
				"Verify all the countries with different Capital/Currency data in between DB & API",
				"There are no country with different Capital/Currency data in between DB & API",
				count1 + " countries data are mismatching in Capital & " + count2
						+ " countries data are mismatching in Currency. Check the result in TC02_Mismatch.txt");
		if (!blnStepFlag)
			return;
	}
	
	@AfterMethod
	public void afterTest() {
		reporter.endTestcase();
	}

}
