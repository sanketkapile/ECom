package com.group2.ecom;

public class Product {
	private int prodId;
	private String prodName;
	private float prodPrice;
	private int productQuantity;
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	@Override
	public String toString() {
		return "Product [prodId=" + prodId + ", prodName=" + prodName + ", prodPrice=" + prodPrice
				+ ", productQuantity=" + productQuantity + "]";
	}
	public float getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(float prodPrice) {
		this.prodPrice = prodPrice;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	} 
}