package com.group2.ecom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductOperations extends DatabaseConnection {
	int id;
	
	public void welcomeAdmin(String username) {
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (choice != 9) {
			System.out.println("*********************************************************************************");
			System.out.println("Welcome " + username);
			System.out.println("Please select a task to perform:");
			System.out.println("1. View All Products List");
			System.out.println("2. Add New Product");
			System.out.println("3. Refill Existing Product");
			System.out.println("4. Check Product Quantity");
			System.out.println("5. Check Registered User");
			System.out.println("6. Get the User Histroy");
			System.out.println("Enter 9 to EXIT");
			System.out.print("Enter Choice: ");
			choice = scan.nextInt();
			System.out.println("*********************************************************************************");
			switch (choice) {
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
			for (Product i : productList) {
				System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity");
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice() + "\t\t"
						+ i.getProductQuantity());
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
			query = "SELECT product_id, product_name, product_price FROM product_master;";
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
			List<Product> productList = new ArrayList<Product>();
			while (rs.next()) {
				Product product = new Product();
				product.setProdId(rs.getInt(1));
				product.setProdName(rs.getString(2));
				product.setProdPrice(rs.getFloat(3));
				productList.add(product);
			}
			for (Product i : productList) {
				System.out.println("Product ID\tProduct Name\tProduct Price");
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice());
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
			for (Product i : productList) {
				System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity");
				System.out.print(i.getProdId() + "\t\t" + i.getProdName() + "\t\t" + i.getProdPrice() + "\t\t"
						+ i.getProductQuantity());
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
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter Product Name: ");
		prodName = scan.next();
		System.out.print("Enter Product Price: ");
		prodPrice = scan.nextFloat();
		try {
			dbConnect();
			query = "INSERT INTO product_master (Product_Name, Product_Price)" + " values (?,?);";
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, prodName);
			pStmt.setFloat(2, prodPrice);
			int i = pStmt.executeUpdate();
			prodId = getProductIdInfo();
			addQuantity(prodId);
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

	private void addQuantity(int prodId) {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter Product Quantity Available: ");
			int prodQuantity = scan.nextInt();
			System.out.println("Adding quantity for " + prodId + " for " + prodQuantity);
			dbConnect();
			query = "INSERT INTO product_quantity (product_id, quantity) " + " values (?,?);";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, prodId);
			pStmt.setInt(2, prodQuantity);
			int i = pStmt.executeUpdate();
			System.out.println(i + " Records Updated.");
			displaySingleProducts(prodId);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
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
			query = "update product_quantity set quantity = quantity + ? where product_id = ?;";
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

	public void checkProductQauntity() {
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

	public void checkRegUser() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void userHistory() {
		
		try {
			dbConnect();
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter the users Name to get Purchase Histroy");
			String username=sc.nextLine();
			String uid="select user_id from user_registration1 where username=?";
			
			pStmt=con.prepareStatement(uid);
			pStmt.setString(1,username);
			rs=pStmt.executeQuery();
			while(rs.next()==true)
			{
				id=rs.getInt(1);
			}
			
			String sqlquery="select purchase_date, product_id, product_name, purchase_quantity from purchase_history where user_id=?";
			//System.out.println(id);
			pStmt=con.prepareStatement(sqlquery);
			pStmt.setInt(1, id);
			
			rs=pStmt.executeQuery();
			while(rs.next())
			{
				String date=rs.getString(1);
				int product_id=rs.getInt(2);
				String product_name=rs.getString(3);
				int quantity=rs.getInt(4);
				
				System.out.println( "\n"+date+"\nUser_id :"+id+"\nProduct_Id :"+product_id+"\nProduct_name :"+product_name+"\nQuantity :"+quantity);
			}
			
			pStmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		

	}

}