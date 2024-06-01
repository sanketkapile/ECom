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
		if(check.accessNumber == 1) {
			// Welcome Method - Add product / Add quantity / Update Quantity
			product.welcomeAdmin();
		}
		else {
			//accessNumber is 2 so its normal user add other user duntions here
			System.out.println("You have Access to Purchase Product");
			System.out.println("Welcome User");
			UserBuy buy = new UserBuy();
			buy.addToCart();
		}
	}
	public void adminLoginCheck() {
		
		
	}

}
