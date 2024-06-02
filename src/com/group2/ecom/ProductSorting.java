package com.group2.ecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import com.mysql.cj.xdevapi.Collection;

public class ProductSorting extends UserRegistration {
	
	PreparedStatement pStmt;
	Connection con;

   
	public static void main(String[] args) {
		

	}
   public void Sorting () {
	   
	 ArrayList<String> list = new ArrayList<String>();
	 list.add(query);
	 list.add(query);	 
	 
	 TreeSet<String> treeset = new TreeSet<String>();
	 
	 
	 dbConnect();
	 
	 query = "select * from user_registration where ProductID = ? and  ProductName =  ? order by asc";
	 
	 try {
		pStmt = con.prepareStatement(query);
		 int i = pStmt.executeUpdate();
		 System.out.println(i + "Data Sorted by Product name and Product Id");
		 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	
	 
	 
   }
}
