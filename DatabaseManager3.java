package com.prosper.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class DatabaseManager3 {

	static Logger logger = Logger.getLogger(DatabaseManager3.class);
	Connection conn=null;

	DatabaseManager3(String connectionString) {
		// Initiating MySQL driver
		// Create and return a connection
		try {

			logger.info("Creating D/B Connection");

			Class.forName(PropertyManager.getProperty("DBDriver")).newInstance();
					
			conn = DriverManager.getConnection(connectionString);
			
			logger.info("D/B Connection Sucessfull");

		} catch (Exception e) {
			logger.info("Exception - D/B Connection:\t" + e.getMessage());
		}
	}

	
	public ResultSet DBQuery(String Query){
		try{
			ResultSet rs = conn.createStatement().executeQuery(Query);
			return rs;//.getString(Column);


		} catch (Exception e) {
			logger.info("Exception - DB Query:\t" + e.getMessage());
			logger.info("Exception - DB QUery:\t" + e.getMessage() + "\n");
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<HashMap<String, Object>> getQueryResult(String query,String database) {

		logger.info("Getting rows from Database...");

		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();

		DatabaseManager3 dm = new DatabaseManager3(database);

		ResultSet dbResult = null;

		try {
			dbResult = dm.DBQuery(query);

			ResultSetMetaData meta = null;
			meta = dbResult.getMetaData();

			// get column names
			int colCount = meta.getColumnCount();

			ArrayList<String> cols = new ArrayList<String>();

			for (int index = 1; index <= colCount; index++)
				cols.add(meta.getColumnName(index));

			// fetch rows
			while (dbResult.next()) {
				// try{

				HashMap<String, Object> row = new HashMap<String, Object>();

				for (String colName : cols) {
					Object val = dbResult.getObject(colName);
					if (dbResult.wasNull()) {
						val = "NULL";
					}
					row.put(colName, val);
				}
				rows.add(row);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return rows;

	}
	
	public void executeQuery(String query, String param) {
	       try {
	           PreparedStatement ps = conn.prepareStatement(query);
	           ps.setString(1, param);
	           ps.executeUpdate();
	           logger.info("DDL Statement has been executed.");
	       } catch (Exception e) {
	           logger.info("Exception - DB Query:\t" + e.getMessage());
	           e.printStackTrace();
	       }
	   }

}

