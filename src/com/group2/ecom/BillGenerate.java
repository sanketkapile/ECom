package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillGenerate {

	public static void main(String[] args) {
		

	}
	public void billGenerate(int userId) {
		AddProduct addProduct = new AddProduct();
		String query;
		PreparedStatement pStmt;
		ResultSet rs;
		int prodId;
		int prodQuantity;
		try {
			Connection con = addProduct.dbConnect();
			query = "select product_id, quantity from user_cart where userId = ?;";
			pStmt = con.prepareStatement(query);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				prodId=rs.getInt(1);
				prodQuantity=rs.getInt(2);
				calculate(prodId,prodQuantity);
			}
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
			rs = pStmt.executeQuery()
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}
