package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRegistration extends DatabaseConnection {

	public static void main(String[] args) {
		UserRegistration user = new UserRegistration();
		user.createUse();
}
	public void createUse() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Enter 1 for  Registration");
		System.out.println("Enter the first name ");
		String firstName = sc.next();
		System.out.println("Enter the last name");
		String lastName = sc.next();
		System.out.println("Enter the username");
		String username = sc.next();
		System.out.println("Enter the password");
		String userpassword = sc.next();
		System.out.println("Enter the city");
		String city = sc.next();
		System.out.println("Enter the mail id");
		String mailId = sc.next();
		System.out.println("Enter the mobile number");
		long mobileNumber = sc.nextLong();

		try {
			dbConnect();
			query = "insert into user_registration.user_registration (firstName,lastName,username,password,city,mailId,mobileNumber)  value(?,?,?,?,?,?,?)";

			pStmt = con.prepareStatement(query);

			pStmt.setString(1, firstName);
			pStmt.setString(2, lastName);
			pStmt.setString(3, username);
			pStmt.setString(4, userpassword);
			pStmt.setString(5, city);
			pStmt.setString(6, mailId);
			pStmt.setLong(7, mobileNumber);

			int i = pStmt.executeUpdate();

			System.out.println(i + "Row updated ");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		} finally {
			try {
			con.close();
			pStmt.close();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
