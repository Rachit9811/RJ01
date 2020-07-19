package com.geminisols.frame_dependency_api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class JdbcConnection {
	// Logger
	private Logger logger = Logger.getLogger(JdbcConnection.class);

	// JDBC database URL
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/countriesdata";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Kiyana@123";

	private Connection conn = null;
	private Statement stmt = null;

	public JdbcConnection() {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			logger.info("JDBC Connection established");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			logger.error("JDBC Connection not established due to the following error - " + se.getMessage());
		} catch (Exception e) {
			// Handle errors for any other error
			e.printStackTrace();
			logger.error("JDBC Connection not established due to the following error - " + e.getMessage());
		}
	}

	public boolean insertDbValues(String tableName, String columnNames, String columnValues) {
		boolean blnFlag = false;
		try {
			String sql = null;
			if (columnNames == null) {
				sql = "INSERT INTO " + tableName + "VALUES (" + columnValues + ")";
			} else {
				sql = "INSERT INTO " + tableName + " (" + columnNames + ")" + "VALUES (" + columnValues + ")";
			}
			stmt.executeUpdate(sql);
			logger.info("Insert Command Executed");
			blnFlag = true;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			logger.error("Insert Command not Executed due to the following error - " + se.getMessage());
		} catch (Exception e) {
			// Handle errors for any other error
			e.printStackTrace();
			logger.error("Insert Command not Executed due to the following error - " + e.getMessage());
		}
		return blnFlag;
	}

	public boolean deleteRows(String table1) {
		boolean blnFlag = false;
		try {
			String sql = "DELETE FROM " + table1;
			stmt.executeUpdate(sql);
			logger.info("DELETE Command Executed");
			blnFlag = true;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			logger.error("DELETE Command not Executed due to the following error - " + se.getMessage());
		} catch (Exception e) {
			// Handle errors for any other error
			e.printStackTrace();
			logger.error("DELETE Command not Executed due to the following error - " + e.getMessage());
		}
		return blnFlag;
	}

	public boolean updateRows(String table, String c_ID, String colName, String colNewValue) {
		boolean blnFlag = false;
		try {
			String sql = "UPDATE `" + table + "` SET `" + colName + "` = '" + colNewValue + "' WHERE (`C_ID` = '" + c_ID
					+ "');";
			stmt.executeUpdate(sql);
			logger.info("UPDATE Command Executed");
			blnFlag = true;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			logger.error("UPDATE Command not Executed due to the following error - " + se.getMessage());
		} catch (Exception e) {
			// Handle errors for any other error
			e.printStackTrace();
			logger.error("UPDATE Command not Executed due to the following error - " + e.getMessage());
		}
		return blnFlag;
	}

	public List<String> retreiveColumnData(String query, String colName) {
		List<String> lstResult = new LinkedList<String>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			logger.info("Query Executed");

			while(rs.next()) {
				lstResult.add(rs.getString(colName));
			}
			logger.info("Result retrieved");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			logger.error("Query not Executed/ Result not retrieved due to the following error - " + se.getMessage());
		} catch (Exception e) {
			// Handle errors for any other error
			e.printStackTrace();
			logger.error("Query not Executed/ Result not retrieved due to the following error - " + e.getMessage());
		}
		return lstResult;
	}

	public void closeMySqlConnection() {
		// Used to close resources
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException se) {
			// do nothing
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		logger.info("JDBC Connection is closed successfully");
	}
}
