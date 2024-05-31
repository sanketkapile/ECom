package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CheckPrive {
	static Connection con;
	static String query;
	static PreparedStatement pStmt;
	static ResultSet rs;
	public static String useRight;
	public static int accessNumber;
	public static int userId;
	//public static void main(String[] args) {
	//	CheckPrive check = new CheckPrive();
	//	check.authenticateUser();
	//	System.out.println("Right: "+useRight);
	//	System.out.println(accessNumber);
	//}
	public void authenticateUser() {
		String userName;
		String password;
		Scanner scan = new Scanner(System.in);
		AddProduct ap = new AddProduct();
		System.out.print("Enter Username:");
		userName = scan.next();
		System.out.print("Enter Passowrd:");
		password = scan.next();
		query = "select user_right from user_master where user_name = ? and password = ? ;";
		try {
			con = ap.dbConnect();
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, userName);
            pStmt.setString(2, password);
            rs = pStmt.executeQuery();
            if(rs.next()) {
            	useRight = rs.getString(1);
            	if(useRight.equals("admin")) {
            		accessNumber = 1;
            	}
            	else {
            		accessNumber = 2;
            	}
            }
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void authenticateUser2() {
		String username;
		String password;
		Scanner scan = new Scanner(System.in);
		AddProduct ap = new AddProduct();
		System.out.print("Enter Username:");
		username = scan.next();
		System.out.print("Enter Passowrd:");
		password = scan.next();
		query = "select user_id from user_master where user_name = ? and password = ? ;";
		try {
			con = ap.dbConnect();
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, username);
            pStmt.setString(2, password);
            rs = pStmt.executeQuery();
            if(rs.next()) {
            	userId = rs.getInt(1);
            }
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
