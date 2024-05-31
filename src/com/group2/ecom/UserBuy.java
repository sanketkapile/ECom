package com.group2.ecom;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;


public class UserBuy {
	public static int userId;
	static Connection con;
	static String query;
	static PreparedStatement pStmt;
	AddProduct product = new AddProduct();
	CheckPrive check = new CheckPrive();
	Scanner scan = new Scanner(System.in);
	static LocalDate currentDate;
	static Date todayDate;
	public static void main(String[] args) {

		UserBuy buy = new UserBuy();
		AddProduct addProduct = new AddProduct();
		buy.addToCart();
		addProduct.displayAllProducts();

	}
	public void addToCart() {
		int choice = 0;
		int prodId = 0;
		int prodQuantity = 0;
		check.authenticateUser2();
		currentDate = LocalDate.now();
        todayDate = Date.valueOf(currentDate);
		while(choice != 9) {
			product.displayAllProducts();
			System.out.println("***********************************************");
			System.out.println("Product Purchase");
			System.out.print("Enter Product code: ");
			prodId = scan.nextInt();
			System.out.print("Enter Quantity: ");
			prodQuantity = scan.nextInt();
			try {
				Connection con = product.dbConnect();
				query = "INSERT INTO user_cart (user_id, product_id, quantity)" + " values (?,?,?);";
				pStmt = con.prepareStatement(query);
				pStmt.setInt(1, check.userId);
				pStmt.setInt(2, prodId);
				pStmt.setInt(3, prodQuantity);
				int i = pStmt.executeUpdate();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
			System.out.print("Do you still want to continue purchse: ");
			choice = scan.nextInt();
			System.out.println("***********************************************");
		}
		if(choice == 9) {
			purchaseProduct();
			clearCart();
		}
	}
	public void purchaseProduct() {
		int userIdInfo = 0;
		int productIdInfo = 0;
		int productQuantityInfo = 0;
		float productPrice = 0;
		try {
			Connection con = product.dbConnect();
			query = "SELECT uc.user_id, uc.product_id, uc.quantity, pm.product_price FROM user_cart uc INNER JOIN product_master pm ON uc.product_id = pm.product_id;";
			pStmt = con.prepareStatement(query);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				userIdInfo=rs.getInt(1);
				productIdInfo=rs.getInt(2);
				productQuantityInfo=rs.getInt(3);
				productPrice=rs.getFloat(4);
				float totalPrice = productQuantityInfo * productPrice;
				
				addPurchaseHistory(userIdInfo,productIdInfo,productQuantityInfo,totalPrice);
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void addPurchaseHistory(int userIdInfo, int productIdInfo, int productQuantityInfo, float productPrice) {
		try {
			Connection con = product.dbConnect();
			query = "INSERT INTO purchase_history (user_id, product_id, purchase_quantity, product_price, date)" + " values (?,?,?,?,?);";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userIdInfo);
			pStmt.setInt(2, productIdInfo);
			pStmt.setInt(3, productQuantityInfo);
			pStmt.setFloat(4, productPrice);
			pStmt.setDate(5, todayDate);
			int i = pStmt.executeUpdate();
			updateQuantity(productIdInfo,productQuantityInfo);
			System.out.println(i + " Record Saved in Product Table.");
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void updateQuantity(int productIdInfo, int productQuantityInfo) {
		try {
			Connection con = product.dbConnect();
			query = "update product_quantity set quantity = quantity - ? where product_id = ?;";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, productQuantityInfo);
			pStmt.setInt(2, productIdInfo);
			int i = pStmt.executeUpdate();
			System.out.println("Excuted successfully " + i);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void clearCart() {
		try {
			Connection con = product.dbConnect();
			query = "TRUNCATE TABLE user_cart;";
			pStmt = con.prepareStatement(query);
			pStmt.executeUpdate();
			System.out.println("Cart Cleared");
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
