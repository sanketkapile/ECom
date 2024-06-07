package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRegistration extends DatabaseConnection {

	public void createUse() {

		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter 1 for  Registration");
		System.out.print("Enter the First Name: ");
		String firstName = sc.next();
		System.out.print("Enter the Last Name: ");
		String lastName = sc.next();
		System.out.print("Enter the Username: ");
		String username = sc.next();
		System.out.print("Enter the Password: ");
		String userpassword = sc.next();
		System.out.print("Enter the City: ");
		String city = sc.next();
		System.out.print("Enter the Email Id: ");
		String mailId = sc.next();
		System.out.print("Enter the Contact Number: ");
		int mobileNumber = sc.nextInt();
		
		try {
			dbConnect();
			query = "insert into user_master (first_name,last_name,username,password,city,mail_id,contact_number)  value(?,?,?,?,?,?,?)";
			pStmt = con.prepareStatement(query);

			pStmt.setString(1, firstName);
			pStmt.setString(2, lastName);
			pStmt.setString(3, username);
			pStmt.setString(4, userpassword);
			pStmt.setString(5, city);
			pStmt.setString(6, mailId);
			pStmt.setInt(7, mobileNumber);

			int i = pStmt.executeUpdate();

			System.out.println(i + "Row updated ");
			con.close();
			pStmt.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}
	}
}
