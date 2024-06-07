package com.group2.ecom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillGenerate extends DatabaseConnection{
	private static Integer billNo = 0; 
	private static double totalBillAmount = 0;
	private static boolean flag = false;
	public void billGenerate(int userId, String todayDate) {
		billNo++;
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
			dbConnect();
      
			query = "SELECT ph.product_id, ph.product_name, pm.product_price, ph.purchase_quantity, ph.product_price, ph.bill_status, ph.purchase_id FROM purchase_history ph INNER JOIN product_info pm ON ph.product_id = pm.product_id where user_id = ? and purchase_date = ? and bill_status = 'Pending';";
			pStmt = con.prepareStatement(query);
			pStmt.setInt(1, userId);
			pStmt.setString(2, todayDate);
			rs = pStmt.executeQuery();
			rs = pStmt.executeQuery();
      
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
      
			billCalculate(billInfoList, todayDate);
      
			pStmt.close();
			con.close();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	private void billCalculate(List<BillInfo> billInfoList, String todayDate) {
		System.out.println("*********************************************************************************");
		System.out.println("Bill No: " + billNo + "\t\t\t\t\t\t" + todayDate);
		System.out.println("*********************************************************************************");
		System.out.println("\nProduct ID\tProduct Name\tProduct Price\tProduct Quantity\tTotal Price");
		System.out.println("*********************************************************************************");
		float finalBillAmount = 0;
		for (BillInfo i : billInfoList) {
			System.out.println(i.getProductId()+"\t\t"+i.getProductName()+"\t\t"+i.getProductPrice()+"\t\t"+i.getPurchaseQuantity()+"\t\t"+i.getFinalProductPrice());

			finalBillAmount = finalBillAmount + i.getFinalProductPrice();
			flagBill(i.getPurchaseId());
		}
		System.out.println("*********************************************************************************");
		System.out.println("\t\t\t\t\t\tTotal Bill Amount: " + finalBillAmount);
		System.out.println("*********************************************************************************");
	}
	private void flagBill(int id){
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

}