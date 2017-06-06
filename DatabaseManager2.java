package com.prosper.framework;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DatabaseManager2 {
	static Logger logger = Logger.getLogger(DatabaseManager2.class);
	Connection conn = null;

	DatabaseManager2() {
		// Initiating MySQL driver
		// Create and return a connection
		try {
			logger.info("Creating D/B Connection");
			String env = PropertyManager.getProperty("envName");
			if (env.equalsIgnoreCase("qa34")) {
				conn = DriverManager.getConnection(PropertyManager.getProperty("d32DB01Url"));
				
			} else if (env.equalsIgnoreCase("stage")) {
				conn = DriverManager.getConnection(PropertyManager.getProperty("circleoneDBUrl"));
				
			} else if (env.equalsIgnoreCase("qa32")) {
				conn = DriverManager.getConnection(PropertyManager.getProperty("q32DB01Url"));
			
			}else if (env.equalsIgnoreCase("uat")) {
				
				conn = DriverManager.getConnection(PropertyManager.getProperty("uatCircleoneDBUrl"));
			} else if (env.equalsIgnoreCase("stage2")) {
				
				conn = DriverManager.getConnection(PropertyManager.getProperty("stage2CircleoneDBUrl"));
			}

			logger.info("D/B Connection Sucessfull");
			/*
			 * ResultSet rs = conn.createStatement().executeQuery(
			 * "select * from AccountsLender");
			 * 
			 * while (rs.next()) { logger.info(rs.getString("Userid")); }
			 */
		} catch (Exception e) {
			logger.info("Exception - D/B Connection:\t" + e.getMessage());
			e.printStackTrace();
		}
	}

	public ResultSet DBQuery(String Query) {
		try {
			ResultSet rs = conn.createStatement().executeQuery(Query);
			return rs;// .getString(Column);

		} catch (Exception e) {
			logger.info("Exception - DB Query:\t" + e.getMessage());
			logger.info("Exception - DB QUery:\t" + e.getMessage()
					+ "\n");
			e.printStackTrace();
			return null;
		}
	}

	public void executeDDLQuery(String query,String userid,String dbName) {
		try {
			conn = DriverManager.getConnection("jdbc:sqlserver://Q32DB01;DatabaseName="+dbName+";user=api_user;password=Password23");
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(userid));
			  ps.executeUpdate();
			logger.info("DDL Statement has been executed.");
		} catch (Exception e) {
			logger.info("Exception - DB Query:\t" + e.getMessage());
			logger.info("Exception - DB QUery:\t" + e.getMessage()
					+ "\n");
			e.printStackTrace();
		}
	}

	/**
	 * Call Procedure to excute store procedure with input parameter Need to
	 * review for output result
	 * 
	 * @param procedurename
	 * @param input
	 * @throws SQLException
	 */
	public void callProcedure(String dbName,String procedurename, int uid,int ficoScore,String firstName,String lastName,String address,String state,String city,String zipCode,String ssn,String dob)
			throws SQLException {
		Connection connection =null;
		CallableStatement callableStatement = null;
		String sqlProcedure = "{call " + procedurename +"}";
		try {
			String updatedAddress = address;
			connection  = DriverManager.getConnection(PropertyManager
					.getProperty(dbName));
			callableStatement = conn.prepareCall(sqlProcedure);


			callableStatement.setInt(1,uid);
			callableStatement.setInt(2,817);
			callableStatement.setString(3, firstName);
			callableStatement.setString(4, lastName);
			callableStatement.setString(5, address);
			callableStatement.setString(6,city);
			callableStatement.setString(7, state);
			callableStatement.setString(8, zipCode);
			callableStatement.setString(9, ssn);
			callableStatement.setString(10,dob);
		


			logger.info("call " + procedurename );
			int i =callableStatement.executeUpdate();
			logger.info("Stored procedure executed with output as line"+i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}finally{
			connection.close();
		  }
				 
		
	}

	public void invokeProcedure(String connString,String procedurename, String uid)			throws SQLException {
		Connection connection =null;
		CallableStatement callableStatement = null;
		String sqlProcedure = "{call " + procedurename +"}";
		try {
			connection  = DriverManager.getConnection(connString);
			callableStatement = conn.prepareCall(sqlProcedure);


			callableStatement.setInt(1,Integer.parseInt(uid));


			logger.info("call " + procedurename );
			int i =callableStatement.executeUpdate();
			logger.info("Stored procedure executed with output as line"+i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}finally{
			connection.close();
		  }
				 
		
	}

public void executeQuery(String query, String dbName, String param) {
       try {
//           conn = DriverManager
//                   .getConnection("jdbc:sqlserver://Q34DB01;DatabaseName=" + dbName + ";user=api_user;password=Password23");
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
