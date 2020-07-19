package com.geminisols.frame_dependency_api;

import org.junit.Assert;
import org.junit.Test;

public class RestAPIClientTest extends RestAPIClient {

	@Test
	public void testRestCountriesApi() {
		boolean blnFlag = false;

		String uri = "https://restcountries.eu";
		String endPoint = "/rest/v2/all";
		blnFlag = setUp(uri, endPoint);
		if (blnFlag) {
			blnFlag = setHeaders("Content-Type", "application/json");
			if (blnFlag) {
				blnFlag = setHeaders("Accept", "application/json");
				if (blnFlag) {
					blnFlag = setGetRequest();
					if (blnFlag) {
						blnFlag = response.getStatusCode()==200;
						if (blnFlag) {
							blnFlag = response.contentType().contains("application/json");
						}
					}
				}
			}
		}
		Assert.assertTrue(blnFlag);
	}
	
}
