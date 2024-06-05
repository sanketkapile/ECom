package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import com.mysql.cj.xdevapi.Collection;

public class ProductSorting extends UserRegistration {

	public void Sorting() {
		dbConnect();

		query = "select * from products.products ORDER BY ProductID ASC, ProductName ASC,productDescription ASC,price ASC ";
		try {
			pStmt = con.prepareStatement(query);
			ResultSet resultSet = pStmt.executeQuery();

			System.out.println(
					"-------------------------------------------------------------------------------------------------");
			System.out.print("Product Id ");
			System.out.print("\t Product Name  ");
			System.out.print("\t product Description  ");
			System.out.print("\t Product price  ");
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------");
			while (resultSet.next()) {
				System.out.println(
						"-------------------------------------------------------------------------------------------------");
				System.out.print(resultSet.getInt("ProductID"));
				System.out.print("\t" + resultSet.getString("ProductName"));
				System.out.print("\t" + resultSet.getString("productDescription"));
				System.out.print("\t" + resultSet.getString("price"));
				System.out.println(
						"\n-----------------------------------------------------------------------------------------------");
			}
			System.out.println("Data Sorted by Product ID");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			// Close resources
			try {
				if (pStmt != null) {
					pStmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}