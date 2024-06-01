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
	public void billGenerate(int userId) {
		System.out.println("Entering billing section");
		int prodId;
		int prodQuantity;
		String prodName;
		float totalPrice;
		int purchaseId;
		try {
			dbConnect();
			query = "SELECT ph.product_id, ph.product_name, ph.purchase_quantity, ph.product_price, ph.bill_status, pm.product_price FROM purchase_history ph INNER JOIN product_master pm ON ph.product_id = pm.product_id where user_id = ? and date = ? and bill_status = 'Pending';";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userId);
			pStmt.setDate(2, UserBuy.todayDate);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				if(rs.next()==true) {
					//purchaseId = rs.getInt(1);
					//prodId=rs.getInt(2);
					//prodName = rs.getString(3);
					//prodQuantity=rs.getInt(4);
					//totalPrice=rs.getFloat(5);
					//totalBillAmount = totalBillAmount + totalPrice;
					//displayBill(prodId,prodQuantity,prodName,totalPrice);
					//flagBill(purchaseId);
					
					List<BillInfo> billInfoList = new ArrayList<BillInfo>();
					while (rs.next()) {
						BillInfo billInfo = new BillInfo();
						billInfo.setProductId(rs.getInt(1));
						billInfo.setProductName(rs.getString(2));
						billInfo.setPurchaseQuantity(rs.getInt(3));
						billInfo.setFinalProductPrice(rs.getFloat(4));
						billInfo.setBillStatus(rs.getString(5));
						billInfo.setProductPrice(rs.getFloat(6));
						billInfoList.add(billInfo);
					}
					displayBill(billInfoList);
				}
				else {
					flag = true;
				}
			}
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void billGenerate(int userId, String todayDate) {
		System.out.println("Entering billing section");
		int prodId;
		int prodQuantity;
		String prodName;
		float totalPrice;
		int purchaseId;
		try {
			dbConnect();
			query = "SELECT ph.product_id, ph.product_name, pm.product_price, ph.purchase_quantity, ph.product_price, ph.bill_status, ph.purchase_id FROM purchase_history ph INNER JOIN product_master pm ON ph.product_id = pm.product_id where date = ? and bill_status = 'Pending';";
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, todayDate);
			System.out.println(query);
			rs = pStmt.executeQuery();
			while (rs.next()) {
					List<BillInfo> billInfoList = new ArrayList<BillInfo>();
					while (rs.next()) {
						BillInfo billInfo = new BillInfo();
						billInfo.setProductId(rs.getInt(1));
						billInfo.setProductName(rs.getString(2));
						billInfo.setProductPrice(rs.getFloat(3));
						billInfo.setPurchaseQuantity(rs.getInt(4));
						billInfo.setFinalProductPrice(rs.getFloat(5));
						billInfo.setBillStatus(rs.getString(6));
						billInfo.setPurchaseId(rs.getInt(7));
						billInfoList.add(billInfo);
					}
					for(BillInfo i : billInfoList ) {
						//System.out.println();
						System.out.print(i.getProductId() + "\t\t" + i.getProductName() + "\t\t" + i.getProductPrice()+ "\t\t" + i.getPurchaseQuantity() + "\t\t\t" + i.getFinalProductPrice());
						System.out.println();
					}
					//displayBill(billInfoList);
			}
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	public void displayBill(List<BillInfo> billInfoList) {
		float totalBill = 0;
		System.out.println("***************************************************");
		System.out.println("Bill No: " + billNo);
		System.out.println("***************************************************");
		System.out.println("\nProduct ID\tProduct Name\tProduct Price\tProduct Quantity\tTotal Price");
		for(BillInfo i : billInfoList ) {
			//System.out.println();
			System.out.print(i.getProductId() + "\t\t" + i.getProductName() + "\t\t" + i.getProductPrice()+ "\t\t" + i.getPurchaseQuantity() + "\t\t\t" + i.getFinalProductPrice());
			System.out.println();
			float price = i.getFinalProductPrice();
			int id = i.getPurchaseId();
			flagBill(id);
			//System.out.println(id);
			totalBill = totalBill + price;
		}
		System.out.println("\t\t\t\t\t\t     Total Bill Amount: "+ totalBill);
		System.out.println("***************************************************");
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
