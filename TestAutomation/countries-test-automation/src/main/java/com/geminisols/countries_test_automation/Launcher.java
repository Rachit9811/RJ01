package com.geminisols.countries_test_automation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class Launcher {
	private static Logger logger= Logger.getLogger(Launcher.class);

	public static void main(String[] args) {
		// Config File
		Properties prop = null;
		FileReader reader;
		try {
			reader = new FileReader("config.properties");
			prop = new Properties();
			prop.load(reader);
			logger.info("config.properties read successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] testCases = prop.getProperty("TestCases").split(",");

		// Generating a run-time runtimeTestNG.xml
		String classIteratorText = readTemplate("./suiteXml/ClassIterator.tmpl");
		String testngText = readTemplate("./suiteXml/CreateTestNG.tmpl");
		String strFinalClass = "";
		for(int i=0; i<testCases.length;i++) {
			strFinalClass = strFinalClass + classIteratorText.replace("_TestCase_", testCases[i]);
		}
		
		String strFinalTestng = testngText.replace("_ClassStr_", strFinalClass);
		createFile("./suiteXml/suiteFiles/runtimeTestNG.xml", strFinalTestng);
		
		// Run generated runtimeTestNG.xml
		TestNG testngsuite = new TestNG();
		File file = new File("./suiteXml/suiteFiles/");
		List<String> suites = Lists.newArrayList();
		String files[] = file.list();

		// Adding all xmls to Suite
		for (String suiteFile : files) {
			suites.add("./suiteXml/suiteFiles/"+suiteFile);
		}
		if (suites.size() >= 1) {

			// Starting execution
			testngsuite.setTestSuites(suites);
			logger.info("============================================================================================");
			logger.info("                           Starting TEST CASE EXECUTION                                     ");
			logger.info("============================================================================================");
			testngsuite.run();
		} else {
			logger.error("No testng's xml file found");
		}
	}
	
	public static String readTemplate(String file) {
		BufferedReader br = null;
		String strData = "";
		try {
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			strData = sb.toString();
		} catch (Exception e) {
			logger.error(String.format("Error while reading template of file %s.\n Error is %s", file, e.getMessage()));
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error(
						String.format("Error while closing template file %s.\n Error is %s", file, e.getMessage()));
			}
		}
		return strData;
	}
	
	public static void createFile(String fileName, String strContent) {
		File file;
		BufferedWriter output = null;
		try {
			file = new File(fileName);
			output = new BufferedWriter(new FileWriter(file));
			output.write(strContent);
		} catch (Exception ex) {
			logger.error(String.format("Error while writing data to %s.\nError is -: %s .", fileName, ex.getMessage()));
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException e) {
					logger.error(String.format("Error while closing %s.\nError is -: %s .", fileName, e.getMessage()));
				}
		}

	}

}
