package com.group2.ecom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductOperations extends DatabaseConnection{
	public void welcomeAdmin(String userName) {
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while(choice != 9) {
			System.out.println("*********************************************************************************");
			System.out.println("Welcome " + userName);
			System.out.println("Please select a task to perform:");
			System.out.println("1. View All Products List");
			System.out.println("2. Add New Product");
			System.out.println("3. Refill Existing Product");
			System.out.println("4. Check Product Quantity");
			System.out.println("5. Check Registered User");
			System.out.println("6. Get the User History");
			System.out.println("Enter 9 to EXIT");
			System.out.print("Enter Choice: ");
			choice = scan.nextInt();
			System.out.println("*********************************************************************************");
			switch(choice) {
				case 1:
					displayAllProducts();
					break;
				case 2:
					addProduct();
					break;
				case 3:
					updateQuantity();
					break;
				case 4:
					checkProductQauntity();
					break;
				case 5:
					checkRegUser();
					break;
				case 6:
					userHistory();
					break;
				default:
					break;
			}
		}
	}
	private void displayAllProducts() {
		try {
			dbConnect();
			query = "SELECT product_id, product_name, product_price, product_quantity FROM product_info order by product_id;";
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
			for (Product i : productList) {
				System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity");
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice() + "\t\t"+ i.getProductQuantity());
				System.out.println();
			}
			pStmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void displayProductsUserOnly() {
		try {
			dbConnect();
			query = "SELECT product_id, product_name, product_price, product_quantity FROM product_info order by product_id;";
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
			List<Product> productList = new ArrayList<Product>();
			while (rs.next()) {
				Product product = new Product();
				product.setProdId(rs.getInt(1));
				product.setProdName(rs.getString(2));
				product.setProdPrice(rs.getFloat(3));
				product.setProductQuantity(rs.getInt(4));
				if(product.getProductQuantity() > 1) {
					productList.add(product);
				}
			}
			System.out.println("Product ID\tProduct Name\t\tProduct Price\t\tStock Status");
			for(Product i : productList ) {	
				String stockStatus = getQuantityInfo(i.getProdId());
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice() + "\t\t\t" + stockStatus);
				System.out.println();
			}
			pStmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void displaySingleProducts(int prodId) {
		try {
			dbConnect();
			query = "SELECT product_id, product_name, product_price, product_quantity FROM product_info where product_id = ?;";
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
			for (Product i : productList) {
				System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity");
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice() + "\t\t"+ i.getProductQuantity());
				System.out.println();
			}
			pStmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void addProduct() {
		Product product = new Product();
		int prodId = 0;
		int prodQuantity = 0;
		float prodPrice = 0;
		String prodName = null;
		String prodDesc = null;
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Product Name: ");
		prodName = scan.nextLine();
		System.out.print("\nEnter Product Description: ");
		prodDesc = scan.nextLine();
		System.out.print("\nEnter Product Price: ");
		prodPrice = scan.nextFloat();
		System.out.print("Enter Product Quantity: ");
		prodQuantity = scan.nextInt();
		try {
			dbConnect();
			query = "INSERT INTO product_info (product_name,product_description,product_price, product_quantity)" + " values (?,?,?,?);";
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, prodName);
			pStmt.setString(2, prodDesc);
			pStmt.setFloat(3, prodPrice);
			pStmt.setInt(4, prodQuantity);
			int i = pStmt.executeUpdate();
			prodId = getProductIdInfo();
			System.out.println(i + " Record Saved in Product Table.");
			displayAllProducts();
			pStmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getProductIdInfo() {
		Product product = new Product();
		try {
			dbConnect();
			query = "SELECT product_id from product_master;";
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				product.setProdId(rs.getInt(1));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return product.getProdId();
	}
	private void updateQuantity() {
		int prodId;
		int prodQuantity;
		Scanner scan = new Scanner(System.in);
		displayAllProducts();
		System.out.print("Enter Product ID to refill stock:");
		prodId = scan.nextInt();
		System.out.print("Enter the New Quantity of product:");
		prodQuantity = scan.nextInt();
		try {
			dbConnect();
			query = "update product_info set product_quantity = product_quantity + ? where product_id = ?;";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(2, prodId);
			pStmt.setInt(1, prodQuantity);
			int i = pStmt.executeUpdate();
			System.out.println("Excuted successfully " + i);
			displaySingleProducts(prodId);
			pStmt.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
  
	private void checkProductQauntity() {
		try {
			int prodId;
			int prodQuantity;
			Scanner scan = new Scanner(System.in);
			displayAllProducts();
			System.out.print("Enter Product ID to check stock :");
			prodId = scan.nextInt();
			dbConnect();
			String sqlquery = "select quantity from product_quantity where product_id=?";
			pStmt = con.prepareStatement(sqlquery);
			pStmt.setInt(1, prodId);

			rs=pStmt.executeQuery();
			if(rs.next()==true) {
            int quantity=rs.getInt(1);
			System.out.println("Product Quantity is : "+quantity);
			}
			pStmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void checkRegUser() {
		try {
			DatabaseConnection.dbConnect();
			Scanner sc = new Scanner(System.in);
			System.out.println("Eter the user_name");
			String user_name = sc.nextLine();
			String sqlquery = "select firstname, lastname, city, email, mobileno from user_registration1 where username=?";
			pStmt = con.prepareStatement(sqlquery);
			pStmt.setString(1, user_name);

			rs = pStmt.executeQuery();
			if (rs.next() == true) {
				String firstname = rs.getString(1);
				String lastname = rs.getString(2);
				String city = rs.getString(3);
				String email = rs.getString(4);
				long mobileno=rs.getLong(5);
				System.out.print("First_name :" + firstname + "\nLast_name :"+lastname+"\ncity :"+city+"\nemail :"+email+"\nmobileno :"+mobileno +"\n");
			} else {
				System.out.println("User Dosen't Exist");
			}
			con.close();
			pStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void userHistory() {
		String userName;
		int purchaseId;
		String productName;
		float productPrice;
		int productQuantity;
		String purchaseDate;
		int userId;
		try {
			dbConnect();
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter the users Name to get Purchase Histroy");
			String username=sc.nextLine();
			
			query = "SELECT ph.purchase_id,  CONCAT(um.first_name, ' ', um.last_name) AS NAME, ph.product_name , ph.product_price, ph.purchase_quantity, ph.purchase_date, um.user_id FROM purchase_history ph INNER JOIN user_master um ON ph.user_id = um.user_id and username = ?;";
			pStmt=con.prepareStatement(query);
			pStmt.setString(1, username);
			rs=pStmt.executeQuery();
			System.out.println("USER ID\tUSER NAME\tPURCHASE ID\tPRODUCT NAME\tPRODUCT PRICE\tQUANTITY\tPURCHASE DATE");
			while(rs.next())
			{
				purchaseId = rs.getInt(1);
				userName = rs.getString(2);
				productName = rs.getString(3);
				productPrice = rs.getFloat(4);
				productQuantity = rs.getInt(5);
				purchaseDate = rs.getString(6);
				userId = rs.getInt(7);
				System.out.println(userId+"\t"+userName+"\t"+purchaseId+"\t"+productName+"\t"+productPrice+"\t"+productQuantity+"\t"+purchaseDate);
			}
			pStmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private String getQuantityInfo(int prodId) {
		String stockStatus = null;
		try {
			dbConnect();
			query = "SELECT product_quantity from product_info where product_id = ?;";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, prodId);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				if(rs.getInt(1) < 1) {
					stockStatus = "Out of Stock";
				}
				else {
					stockStatus = "In Stock";
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return stockStatus;
	}
}

