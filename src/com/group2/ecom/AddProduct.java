package com.group2.ecom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AddProduct {

	private static final String DBTYPE = "jdbc:mysql";
	private static final String DATABASENAME = "ECOM";
	private static final String HOSTADDRESS = "://localhost:3306/";
	private static final String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
	private static final String DBNAME = DBTYPE + HOSTADDRESS + DATABASENAME;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	static Connection con;
	static String query;
	static PreparedStatement pStmt;
	static ResultSet rs;
	public static void main(String[] args) {
		AddProduct ap = new AddProduct();
		ap.welcomeAdmin();
	}

	public void welcomeAdmin() {
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while(choice != 9) {
			System.out.println("**********************************************");
			System.out.println("Welcome Admin");
			System.out.println("Please select a task to perform:");
			System.out.println("1. View All Products List");
			System.out.println("2. Add New Product");
			System.out.println("3. Refill Existing Product");
			System.out.println("Enter 9 to EXIT");
			System.out.print("Enter Choice: ");
			choice = scan.nextInt();
			System.out.println("**********************************************");
			switch(choice) {
				case 1: displayAllProducts();
						break;
				case 2: addProduct();
						break;
				case 3: updateQuantity();
						break;
				default: break;
			}
		}
		
	}
	public Connection dbConnect() {
		try {
			Class.forName(DRIVERNAME);
			con = DriverManager.getConnection(DBNAME, USERNAME, PASSWORD);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return con;
	}
	public void displayAllProducts() {
		try {
			Connection con = dbConnect();
			query = "SELECT p.product_id, p.product_name, p.product_price, pq.quantity FROM product_master p INNER JOIN product_quantity pq ON p.product_id = pq.product_id;";
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
			List<Product> productList = new ArrayList<Product>();
			while (rs.next()) {
				Product product = new Product();
				product.setProdId(rs.getInt(1));
				product.setProdName(rs.getString(2));
				product.setProdPrice(rs.getFloat(3));
				product.setProductQuantity(rs.getInt(4));
				productList.add(product);
			}
			for(Product i : productList ) {
				System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity");
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice()+ "\t\t" + i.getProductQuantity());
				System.out.println();
			}
			pStmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void displaySingleProducts(int prodId) {
		try {
			Connection con = dbConnect();
			query = "SELECT p.product_id, p.product_name, p.product_price, pq.quantity FROM product_master p INNER JOIN product_quantity pq ON p.product_id = pq.product_id and p.product_id = ?;";
			System.out.println(query);
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, prodId);
			rs = pStmt.executeQuery();
			List<Product> productList = new ArrayList<Product>();
			while (rs.next()) {
				Product product = new Product();
				product.setProdId(rs.getInt(1));
				product.setProdName(rs.getString(2));
				product.setProdPrice(rs.getFloat(3));
				product.setProductQuantity(rs.getInt(4));
				productList.add(product);
			}
			for(Product i : productList ) {
				System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity");
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice()+ "\t\t" + i.getProductQuantity());
				System.out.println();
			}
			pStmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void addProduct() {
		Product product = new Product();
		int prodId = 0;
		int prodQuantity = 0;
		float prodPrice = 0;
		String prodName = null;
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Product Name: ");
		prodName = scan.next();
		System.out.print("Enter Product Price: ");
		prodPrice = scan.nextFloat();
		try {
			Connection con = dbConnect();
			query = "INSERT INTO product_master (Product_Name, Product_Price)" + " values (?,?);";
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, prodName);
			pStmt.setFloat(2, prodPrice);
			int i = pStmt.executeUpdate();
			
			prodId = getProductIdInfo();
			addQuantity(prodId);
			System.out.println(i + " Record Saved in Product Table.");
			displayAllProducts();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getProductIdInfo() {
		Product product = new Product();
		try {
			Connection Con = dbConnect();
			query = "SELECT product_id from product_master;";
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				product.setProdId(rs.getInt(1));
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return product.getProdId();
	}

	public void addQuantity(int prodId) {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter Product Quantity Available: ");
			int prodQuantity = scan.nextInt();
			System.out.println("Adding quantity for "+prodId+" for "+prodQuantity);
			Connection con = dbConnect();
			query = "INSERT INTO product_quantity (product_id, quantity) " + " values (?,?);";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, prodId);
			pStmt.setInt(2, prodQuantity);
			int i = pStmt.executeUpdate();
			System.out.println(i + " Records Updated.");
			displaySingleProducts(prodId);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void updateQuantity() {
		int prodId;
		int prodQuantity;
		Scanner scan = new Scanner(System.in);
		displayAllProducts();
		System.out.print("Enter Product ID to refill stock:");
		prodId = scan.nextInt();
		System.out.print("Enter the New Quantity of product:");
		prodQuantity = scan.nextInt();
		try {
			
			Connection con = dbConnect();
			query = "update product_quantity set quantity = quantity + ? where product_id = ?;";
			
			pStmt = con.prepareStatement(query);
			pStmt.setInt(2, prodId);
			pStmt.setInt(1, prodQuantity);

			int i = pStmt.executeUpdate();

			System.out.println("Excuted successfully " + i);
			displaySingleProducts(prodId);
			
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
