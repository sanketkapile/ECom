package com.group2.ecom;

public class MainProgram {

	public static void main(String[] args) {
		MainProgram main = new MainProgram();
		//Login
		main.login();
		//bill display

	}
	public void login() {
		ProductOperations product = new ProductOperations();
		CheckPrive check = new CheckPrive();
		check.authenticateUser();
		String userName = check.getUserName();
		int accessNumber = CheckPrive.getAccessNumber();
		if(accessNumber == 1) {
			// Welcome Method - Add product / Add quantity / Update Quantity
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
