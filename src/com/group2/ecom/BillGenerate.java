package com.group2.ecom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillGenerate extends DatabaseConnection{

	private static Integer billNo = 0; 
	private static double totalBillAmount = 0;
	private static boolean flag = false;
	BillGenerate(){
		billNo++;
	}

	public void billGenerate(int userId, String todayDate) {
		System.out.println("Entering billing section");
		System.out.println("Today Date: " + todayDate);
		int prodId;
		int prodQuantity;
		String prodName;
		float totalPrice;
		float prodPrice;
		int purchaseId;
		String billStatus;
		try {
			displayBillHeader(todayDate);
			dbConnect();
			query = "SELECT ph.product_id, ph.product_name, pm.product_price, ph.purchase_quantity, ph.product_price, ph.bill_status, ph.purchase_id FROM purchase_history ph INNER JOIN product_master pm ON ph.product_id = pm.product_id where user_id = ? and purchase_date = ? and bill_status = 'Pending';";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userId);
			pStmt.setString(2, todayDate);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				prodId = rs.getInt(1);
				prodName = rs.getString(2);
				prodPrice = rs.getFloat(3);
				prodQuantity = rs.getInt(4);
				totalPrice = rs.getFloat(5);
				billStatus = rs.getString(6);
				purchaseId = rs.getInt(7);
				displayBillInfo(prodId, prodName, prodPrice, prodQuantity, totalPrice, billStatus, purchaseId);
				flagBill(purchaseId);
			}
			displayFinalBill();
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void displayBillInfo(int prodId, String prodName, float prodPrice, int prodQuantity, float totalPrice, String billStatus, int purchaseId) {
		totalBillAmount = totalBillAmount + totalPrice;
		System.out.println(prodId + "\t\t" + prodName + "\t\t" + prodPrice + "\t\t" + prodQuantity + "\t\t\t" + totalPrice);
	}
	public void displayBillHeader(String todayDate) {
		System.out.println("*********************************************************************************");
		System.out.println("Bill No: " + billNo + "\t\t\t\t\t\t" + todayDate);
		System.out.println("*********************************************************************************");
		System.out.println("\nProduct ID\tProduct Name\tProduct Price\tProduct Quantity\tTotal Price");
		System.out.println("*********************************************************************************");
	}
	public void displayFinalBill() {
		System.out.println("*********************************************************************************");
		System.out.println("\t\t\t\t\t\tTotal Bill Amount: " + totalBillAmount);
		System.out.println("*********************************************************************************");
	}
	public void flagBill(int id){
		try {
			dbConnect();
			query = "update purchase_history set bill_status = 'Done' where purchase_id = ?;";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, id);
			int i = pStmt.executeUpdate();
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void calculate(int prodId, int prodQuantity) {
		dbConnect();
		query = "select price from product_master where product_id = ?";
		try {
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}
