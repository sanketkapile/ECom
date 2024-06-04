package com.group2.ecom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {
	private static final String DBTYPE = "jdbc:mysql";
	private static final String DATABASENAME = "ECOM";
	private static final String HOSTADDRESS = "://localhost:3306/";
	private static final String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
	private static final String DBNAME = DBTYPE + HOSTADDRESS + DATABASENAME;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	public static Connection con;
	public static String query;
	public static PreparedStatement pStmt;
	public static ResultSet rs;
	public static void dbConnect() {
		try {
			Class.forName(DRIVERNAME);
			con = DriverManager.getConnection(DBNAME, USERNAME, PASSWORD);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
