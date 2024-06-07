package com.group2.ecom;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class UserBuy extends DatabaseConnection{
	static int userId = CheckPrive.getUserId();
	Scanner scan = new Scanner(System.in);
	static String dateTime;
	LocalDateTime todayDate = LocalDateTime.now();
	public void addToCart() {
		CheckPrive check = new CheckPrive();
		BillGenerate bill = new BillGenerate();
		ProductOperations product = new ProductOperations();
		System.out.println("User ID: " + userId);
		int choice = 0;
		int prodId = 0;
		int prodQuantity = 0;
		while(choice != 9) {
			product.displayProductsUserOnly();
			System.out.println("*********************************************************************************");
			System.out.println("Product Purchase");
			System.out.print("Enter Product code: ");
			prodId = scan.nextInt();
			System.out.print("Enter Quantity: ");
			prodQuantity = scan.nextInt();
			try {
				dbConnect();
				query = "INSERT INTO user_cart (user_id, product_id, quantity)" + " values (?,?,?);";
				pStmt = con.prepareStatement(query);
				pStmt.setInt(1, userId);
				pStmt.setInt(2, prodId);
				pStmt.setInt(3, prodQuantity);
				int i = pStmt.executeUpdate();
				pStmt.close();
				con.close();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			System.out.print("Enter 9 to Exit and any other Key to Continue: ");
			choice = scan.nextInt();
			System.out.println("*********************************************************************************");
		}
		if(choice == 9) {
			Boolean flag = displayCart(userId);
			if(flag == true) {
				purchaseProduct();
				clearCart();
				bill.billGenerate(userId, dateTime);
			}
			else {
				System.out.println("Please continue purchasing");
				addToCart();
			}
		}
	}
	private void purchaseProduct() {
		int userIdInfo = 0;
		int productIdInfo = 0;
		int productQuantityInfo = 0;
		float productPrice = 0;
		String productName = null;
		try {
			dbConnect();

			query = "SELECT uc.user_id, uc.product_id, uc.quantity, pm.product_name, pm.product_price FROM user_cart uc INNER JOIN product_info pm ON uc.product_id = pm.product_id where user_id = ?;";

			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userId);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				userIdInfo = rs.getInt(1);
				productIdInfo = rs.getInt(2);
				productQuantityInfo = rs.getInt(3);
				productName = rs.getString(4);
				productPrice = rs.getFloat(5);
				float totalPrice = productQuantityInfo * productPrice;
				addPurchaseHistory(userIdInfo,productIdInfo,productQuantityInfo,totalPrice,productName);
			}
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	private boolean displayCart(int userId) {
		int productIdInfo = 0;
		int productQuantityInfo = 0;
		boolean purchaseInfo = false;
		try {
			dbConnect();
			query = "SELECT product_id, quantity FROM user_cart where user_id = ?;";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userId);
			ResultSet rs = pStmt.executeQuery();
			System.out.println(userId + " Cart");
			while (rs.next()) {	
				productIdInfo = rs.getInt(1);
				productQuantityInfo = rs.getInt(2);
				System.out.println("Product Name: " + productIdInfo + "\tProduct Quantity: " + productQuantityInfo);
			}
			System.out.println("*********************************************************************************");
			pStmt.close();
			con.close();
			System.out.println("1 to Complete Purchase/0 to Continue Purchase): ");
			int choice = scan.nextInt();
			if(choice == 1) {
				purchaseInfo = true;
			}
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		return purchaseInfo;
	}
	private void addPurchaseHistory(int userIdInfo, int productIdInfo, int productQuantityInfo, float productPrice, String productName) {
		LocalDateTime todayDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
        dateTime = todayDateTime.format(formatter);
		try {
			dbConnect();
			query = "INSERT INTO purchase_history (user_id, product_id, product_name, purchase_quantity, product_price, purchase_date)" + " values (?,?,?,?,?,?);";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userIdInfo);
			pStmt.setInt(2, productIdInfo);
			pStmt.setString(3, productName);
			pStmt.setInt(4, productQuantityInfo);
			pStmt.setFloat(5, productPrice);
			pStmt.setString(6, dateTime);
			int i = pStmt.executeUpdate();
			updateQuantity(productIdInfo,productQuantityInfo);
			System.out.println(i + " Record Saved in Product Table.");
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	private void updateQuantity(int productIdInfo, int productQuantityInfo) {
		try {
			dbConnect();
			query = "update product_info set product_quantity = product_quantity - ? where product_id = ?;";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, productQuantityInfo);
			pStmt.setInt(2, productIdInfo);
			int i = pStmt.executeUpdate();
			System.out.println("Excuted successfully " + i);
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	private void clearCart() {
		try {
			dbConnect();
			query = "TRUNCATE TABLE user_cart;";
			pStmt = con.prepareStatement(query);
			pStmt.executeUpdate();
			System.out.println("Cart Cleared");
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}