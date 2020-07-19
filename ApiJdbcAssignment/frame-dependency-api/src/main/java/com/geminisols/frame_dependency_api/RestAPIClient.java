package com.geminisols.frame_dependency_api;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAPIClient {
	public String ErrDescription = "";
	private Logger logger = Logger.getLogger(RestAPIClient.class);
	public RequestSpecification request = null;
	public Response response = null;

	/**
	 * This function is used to set up the URI and EndPoint in API Request
	 * 
	 * @return Boolean
	 * @param uri      - baseURI of RestAssured
	 * @param endPoint - basePath of RestAssured
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setUp(String uri, String endPoint) {
		ErrDescription = "";

		try {
			RestAssured.baseURI = uri;
			RestAssured.basePath = endPoint;
			request = RestAssured.given();
			logger.info("baseURI-'" + uri + "' and endPoint-'" + endPoint + "' are set in Request");
			return true;
		} catch (Exception e) {
			Object msg = String.format(
					"Error while setting up the URL and EndPoint in Request because of the below error -\n %s",
					e.getMessage());
			logger.error(msg);
			ErrDescription = msg.toString();
			return false;
		}
	}

	/**
	 * This function is used to add a Header in API Request
	 * 
	 * @return Boolean
	 * @param key   - Header Key
	 * @param value - Header Value
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setHeaders(String key, Object value) {
		boolean blnFlag = false;

		try {
			request = request.header(key, value);
			blnFlag = true;
			logger.info("Header (key-'" + key + "' and value-'" + value + "') is included in Request");
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("Header (key-'" + key + "' and value-'" + value + "') not included in Request, error-"
					+ e.getMessage());
		}

		return blnFlag;
	}

	/**
	 * This function is used to add a Parameter in API Request
	 * 
	 * @return Boolean
	 * @param key         - Parameter Key
	 * @param value       - Parameter Value
	 * @param typeOfParam - query/ param (optional)
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setParameters(String key, Object value, String... typeOfParam) {
		boolean blnFlag = false;

		try {
			if (typeOfParam.length == 0)
				request = request.param(key, value);
			else if (typeOfParam[0].equalsIgnoreCase("query"))
				request = request.queryParam(key, value);
			else if (typeOfParam[0].equalsIgnoreCase("param"))
				request = request.param(key, value);
			blnFlag = true;
			logger.info("Parameter (key-'" + key + "' and value-'" + value + "') is included in Request");
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("Parameter (key-'" + key + "' and value-'" + value + "') not included in Request, error-"
					+ e.getMessage());
		}

		return blnFlag;
	}

	/**
	 * This function is used to set Request Body in API Request
	 * 
	 * @return Boolean
	 * @param json - Parsed json in String format
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setRequestBody(String json) {
		boolean blnFlag = false;

		try {
			request = request.body(json);
			blnFlag = true;
			logger.info("RequestBody is included in Request");
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("RequestBody not included in Request, Error-" + e.getMessage());
		}

		return blnFlag;
	}

	/**
	 * This function is used to set Request Body in API Request
	 * 
	 * @return json in String
	 * @param jsonFilePath - Path of Json Filepath
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public String parseJsonFileToString(String jsonFilePath) {
		String strJson = "";
		try {
			strJson = (new String(Files.readAllBytes(Paths.get(jsonFilePath))));
			logger.info("Json Parsed successfully");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Json not Parsed, Error-" + e.getMessage());
		}
		return strJson;
	}

	/**
	 * This function is used to send GET Request
	 * 
	 * @return Boolean
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setGetRequest() {
		boolean blnFlag = false;

		try {
			request.relaxedHTTPSValidation();
			response = request.when().get();
//			response.then().log().all();
			blnFlag = true;
			logger.info("GET Request hit successfully");
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("GET Request not hit due to error- " + e.getMessage());
		}

		return blnFlag;
	}

	/**
	 * This function is used to send POST Request
	 * 
	 * @return Boolean
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setPostRequest() {
		boolean blnFlag = false;

		try {
			request.relaxedHTTPSValidation();
			response = request.when().post();
			response.then().log().all();
			blnFlag = true;
			logger.info("POST Request hit successfully");
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("POST Request not hit due to error- " + e.getMessage());
		}

		return blnFlag;
	}

	/**
	 * This function is used to send PUT Request
	 * 
	 * @return Boolean
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setPutRequest() {
		boolean blnFlag = false;

		try {
			request.relaxedHTTPSValidation();
			response = request.when().put();
			response.then().log().all();
			blnFlag = true;
			logger.info("PUT Request hit successfully");
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("PUT Request not hit due to error- " + e.getMessage());
		}

		return blnFlag;
	}

	/**
	 * This function is used to send DELETE Request
	 * 
	 * @return Boolean
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean setDeleteRequest() {
		boolean blnFlag = false;

		try {
			request.relaxedHTTPSValidation();
			response = request.when().delete();
			response.then().log().all();
			blnFlag = true;
			logger.info("DELETE Request hit successfully");
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("DELETE Request not hit due to error- " + e.getMessage());
		}

		return blnFlag;
	}

	/**
	 * This function is used to get response for a specific key
	 * 
	 * @return Boolean
	 * @param key - Parameter Key
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public String getResponse(String key) {
		try {
			String respString = response.asString();
			JsonPath js = new JsonPath(respString);
			String resp = js.get(key).toString();
			logger.info("Response Retrieved - " + resp);
			return (resp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Response not Retrieved, Error-" + e.getMessage());
			return "";
		}
	}

	/**
	 * This function is used to validate response for a specific key with an
	 * expected value
	 * 
	 * @return Boolean
	 * @param key   - Parameter Key
	 * @param value - Expected Value
	 * @author Rachit jain
	 * @Since 17th July 2020
	 */
	public boolean validateResponse(String key, String value) {
		boolean blnFlag = false;

		try {
			blnFlag = getResponse(key).equalsIgnoreCase(value);
			logger.info("Response Retrieved for the key-" + key + " is " + getResponse(key));
		} catch (Exception e) {
			blnFlag = false;
			e.printStackTrace();
			logger.error("Response not retrieved for the key-" + key + ", Error-" + e.getMessage());
		}
		return blnFlag;
	}
}
