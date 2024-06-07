
package com.group2.ecom;

import java.util.Scanner;

public class MainProgram {

	public static void main(String[] args) {
		MainProgram main = new MainProgram();

		//Login
		main.loginPage();
	}
	public void loginPage() {
		UserRegistration userRegister = new UserRegistration();
		ProductOperations product = new ProductOperations();
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		while(choice != 9)
		{
			System.out.println("***********************************************************************");
			System.out.println("Enter 1 to User Login");
			System.out.println("Enter 2 to Create User");
			System.out.println("Enter 3 to Guest User");
			System.out.println("Enter 9 to Exit");
			System.out.print("Enter Choice: ");
			choice = scan.nextInt();
			switch(choice) {
				case 1: login();
						break;
				case 2: userRegister.createUse();
						break;
				case 3: 
						System.out.println("***********************************************************************");
						System.out.println("User You Don't have Purchase Right");
						System.out.println("View Product Information");
						product.displayProductsUserOnly();
						System.out.println("***********************************************************************");
						break;
				default:break;
			}
		}
	}
  
	public void login() {
		ProductOperations product = new ProductOperations();
		CheckPrive check = new CheckPrive();
		check.authenticateUser();
		String userName = check.getUserName();
		int accessNumber = CheckPrive.getAccessNumber();
		if(accessNumber == 1) {
			// Welcome Method - Add product / Add quantity / Update Quantity
			product.welcomeAdmin(userName);
			System.out.println("***********************************************************************");
			product.welcomeAdmin(userName);
			System.out.println("***********************************************************************");
		}
		if(accessNumber == 2) {
			//accessNumber is 2 so its normal user add other user duntions here
			System.out.println("***********************************************************************");
			System.out.println("Welcome " + userName);
			System.out.println("You have Right to Purchase Product");
			UserBuy buy = new UserBuy();
			buy.addToCart();
			System.out.println("***********************************************************************");
		}
		else {
			System.out.println("***********************************************************************");
			System.out.println("User You Don't have Purchase Right");
			System.out.println("View Product Information");
			product.displayProductsUserOnly();
			System.out.println("***********************************************************************");
		}
	}
}
