package com.geminisols.frame_dependency_api;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.restassured.path.json.JsonPath;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcConnectionTest extends JdbcConnection {
	public static String[] c_Id = null;
	public static String[] country = null;
	public static String[] capital = null;
	public static String[] currency_Code = null;
	public static String[] borders = null;

	@Test
	public void atestFetchDataApi() {
		RestAPIClient api = new RestAPIClient();
		api.request = null;
		api.response = null;
		boolean blnFlag = false;

		String uri = "https://restcountries.eu";
		String endPoint = "/rest/v2/all";
		blnFlag = api.setUp(uri, endPoint);
		if (blnFlag) {
			blnFlag = api.setHeaders("Content-Type", "application/json");
			if (blnFlag) {
				blnFlag = api.setHeaders("Accept", "application/json");
				if (blnFlag) {
					blnFlag = api.setGetRequest();
					if (blnFlag) {
						blnFlag = api.response.getStatusCode() == 200;
						if (blnFlag) {
							blnFlag = api.response.contentType().contains("application/json");
							if (blnFlag) {
								String respString = api.response.asString();
								JsonPath js = new JsonPath(respString);
								String c_Id_Response = js.get("alpha3Code").toString();
								c_Id = c_Id_Response.replace("[", "").replace("]", "").split(", ");
								country = new String[c_Id.length];
								capital = new String[c_Id.length];
								currency_Code = new String[c_Id.length];
								borders = new String[c_Id.length];

								for (int i = 0; i < c_Id.length; i++) {
									try {
										country[i] = js.get("name[" + i + "]");
									} catch (Exception e) {
									}
									try {
										capital[i] = js.get("capital[" + i + "]");
									} catch (Exception e) {
									}
									try {
										currency_Code[i] = js.get("currencies[" + i + "].code[0]");
									} catch (Exception e) {
									}
									try {
										borders[i] = js.get("borders[" + i + "]").toString();
									} catch (Exception e) {
									}
								}
							}
						}
					}
				}
			}
		}
		Assert.assertTrue(blnFlag);
	}

	@Test
	public void btestDeleteCountriesBorders() {
		boolean blnFlag = false;
		try {
			blnFlag = deleteRows("countries");
			blnFlag = deleteRows("borders");
			closeMySqlConnection();
		} catch (Exception e) {
			e.printStackTrace();
			blnFlag = false;
		}
		Assert.assertTrue(blnFlag);
	}

	@Test
	public void ctestInsertCountries() {
		boolean blnFlag = false;
		try {
			for (int i = 0; i < c_Id.length; i++) {
				String columnValues = "\"" + c_Id[i] + "\", \"" + country[i] + "\", \"" + capital[i] + "\", \""
						+ currency_Code[i] + "\"";
				blnFlag = insertDbValues("countries", "`C_ID`, `Country`, `Capital`, `Currency_Code`", columnValues);
				if (!blnFlag) {
					System.out.println(columnValues);
				}
			}

			closeMySqlConnection();
		} catch (Exception e) {
			e.printStackTrace();
			blnFlag = false;
		}
		Assert.assertTrue(blnFlag);
	}

	@Test
	public void dtestInsertBorders() {
		boolean blnFlag = false;
		try {
			for (int i = 0; i < c_Id.length; i++) {
				String[] b_Id = borders[i].replace("[", "").replace("]", "").split(", ");
				String columnValues = "";
				if (b_Id.length == 0) {
					columnValues = "\"" + c_Id[i] + "\", \"\"";
					blnFlag = insertDbValues("borders", "`C_ID`, `B_ID`", columnValues);
					if (!blnFlag) {
						System.out.println(columnValues);
					}
				} else {
					for (String b : b_Id) {
						columnValues = "\"" + c_Id[i] + "\", \"" + b + "\"";
						blnFlag = insertDbValues("borders", "`C_ID`, `B_ID`", columnValues);
						if (!blnFlag) {
							System.out.println(columnValues);
						}
					}
				}
			}

			closeMySqlConnection();
		} catch (Exception e) {
			e.printStackTrace();
			blnFlag = false;
		}
		Assert.assertTrue(blnFlag);
	}
	
	@Test
	public void etestUpdateCountries() {
		boolean blnFlag = false;
		try {
			blnFlag = updateRows("countries", "AFG","Capital","Punjab");
			blnFlag = updateRows("countries", "AGO","Currency_Code","XCD");
			blnFlag = updateRows("countries", "ALB","Capital","Haryana");
			blnFlag = updateRows("countries", "ARE","Currency_Code","INR");
			
			closeMySqlConnection();
		} catch (Exception e) {
			e.printStackTrace();
			blnFlag = false;
		}
		Assert.assertTrue(blnFlag);
	}
	
	@Test
	public void ftestRetrieveCountries() {
		boolean blnFlag = false;
		try {
			String query = "SELECT * FROM countries";
			List<String> arrC_Id = retreiveColumnData(query, "C_ID");
			List<String> arrCountry = retreiveColumnData(query, "Country");
			List<String> arrCapital = retreiveColumnData(query, "Capital");
			List<String> arrCurrency_Code = retreiveColumnData(query, "Currency_Code");
			
			System.out.println(arrC_Id);
			System.out.println(arrCountry);
			System.out.println(arrCapital);
			System.out.println(arrCurrency_Code);
			
			closeMySqlConnection();
			blnFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			blnFlag = false;
		}
		Assert.assertTrue(blnFlag);
	}
	
}
