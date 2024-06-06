package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin extends UserRegistration {
	boolean result;

<<<<<<< HEAD
	public void loginnfo() {

		String username;
		String password;

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter 2 for  Login");
		System.out.println("------------------------------------------------------------");
		System.out.println("Enter the username");
		username = sc.next();
		System.out.println("Enter the password");
		password = sc.next();
		System.out.println("------------------------------------------------------------");
		dbConnect();

		query = "select * from user_registration.user_registration where username = ? AND password = ? ";

		try {
=======
	public static void main(String[] args) {
		UserLogin login = new UserLogin();
		login.loginnfo();
	}
	public void loginnfo() {
		String username ; 
		String password;
		
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter 2 for  Login");
		System.out.println("---------------------------------------------------------------------------");
	         System.out.println("Enter the username");
	         username = sc.next();
	         System.out.println("Enter the password");
		 password = sc.next();
		 System.out.println("--------------------------------------------------------------------------");
		 dbConnect();
		 query = "select * from user_registration.user_registration where username = ? AND password = ? ";
		try { 
>>>>>>> 7a76463b0414df5c3252d5e572db9d625f9a2409
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, username);
			pStmt.setString(2, password);
			ResultSet i = pStmt.executeQuery();
<<<<<<< HEAD
			if (i.next()) {
				System.out.println(i + "Login successful");
=======
			if (i.next() ) {
				System.out.println( i + "Login successful");
>>>>>>> 7a76463b0414df5c3252d5e572db9d625f9a2409
			} else {
				System.out.println(" Invalid username or password ");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
}
