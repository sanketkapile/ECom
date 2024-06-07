package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CheckPrive extends DatabaseConnection{
	private static String useRight;
	private static int accessNumber;
	private static int userId;
	private static String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		CheckPrive.userName = userName;
	}
	public static String getUseRight() {
		return useRight;
	}
	public static void setUseRight(String useRight) {
		CheckPrive.useRight = useRight;
	}
	public static int getAccessNumber() {
		return accessNumber;
	}
	public static void setAccessNumber(int accessNumber) {
		CheckPrive.accessNumber = accessNumber;
	}
	public static int getUserId() {
		return userId;
	}
	public static void setUserId(int userId) {
		CheckPrive.userId = userId;
	}
	public void authenticateUser() {
		String username;
		String password;
		int no;
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Username:");
		username = scan.next();
		System.out.print("Enter Passowrd:");
		password = scan.next();

		query = "select user_id, user_rights, first_name from user_master where username = ? and password = ? ;";
    
		try {
			dbConnect();
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, username);
            pStmt.setString(2, password);
            rs = pStmt.executeQuery();
            if(rs.next()) {
            	setUserId(rs.getInt(1));
            	setUseRight(rs.getString(2));
            	setUserName(rs.getString(3));
            	if(useRight.equals("admin")) {
            		no = 1;
            		setAccessNumber(no);
            	}
            	else {
            		no = 2;
            		setAccessNumber(no);
            	}
            }
            pStmt.close();
			con.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}


