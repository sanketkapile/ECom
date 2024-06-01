package com.group2.ecom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillGenerate {

	private static Integer billNo = 0; 
	private static double totalBillAmount = 0;
	private static boolean flag = false;
	BillGenerate(){
		billNo++;
	}
	public void billGenerate(int userId) {
		System.out.println("Entering billing section");
		AddProduct addProduct = new AddProduct();
		String query;
		PreparedStatement pStmt;
		ResultSet rs;
		int prodId;
		int prodQuantity;
		String prodName;
		float totalPrice;
		int purchaseId;
		try {
			Connection con = addProduct.dbConnect();
			query = "select purchase_id,product_id, product_name, purchase_quantity, product_price from purchase_history where user_id = ? and date = ? and bill_status <> 'Done';";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userId);
			pStmt.setDate(2, UserBuy.todayDate);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				if(rs.next()==true) {
					purchaseId = rs.getInt(1);
					prodId=rs.getInt(2);
					prodName = rs.getString(3);
					prodQuantity=rs.getInt(4);
					totalPrice=rs.getFloat(5);
					totalBillAmount = totalBillAmount + totalPrice;
					displayBill(prodId,prodQuantity,prodName,totalPrice);
					flagBill(purchaseId);
				}
				else {
					flag = true;
				}
				
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void displayBill(int prodId, int prodQuantity, String prodName, float totalPrice) {
		System.out.println("***************************************************");
		System.out.println("Bill No: " + billNo);
		System.out.println("Product ID\tProduct Name\tQuantity\tPrice");
		System.out.println(prodId+"\t\t"+prodName+"\t\t"+prodQuantity+"\t\t"+totalPrice);
		if(flag == true) {
			System.out.println("\t\t\t\t\t\t\tTotal Bill Amount: "+ totalBillAmount);
			System.out.println("***************************************************");
		}
	}
	public void flagBill(int id){
		AddProduct addProduct = new AddProduct();
		String query;
		PreparedStatement pStmt;
		try {
			Connection con = addProduct.dbConnect();
			query = "update purchase_history set bill_status = 'Done' where product_id = ?;";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, id);
			int i = pStmt.executeUpdate();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void calculate(int prodId, int prodQuantity) {
		AddProduct addProduct = new AddProduct();
		
		Connection con = addProduct.dbConnect();
		String query;
		PreparedStatement pStmt;
		ResultSet rs;
		query = "select price from product_master where product_id = ?";
		try {
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}
