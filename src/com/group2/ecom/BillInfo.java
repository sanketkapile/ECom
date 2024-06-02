package com.group2.ecom;

import java.sql.Date;

public class BillInfo {
	private int productId;
	private String productName;
	private int purchaseQuantity;
	private float productPrice;
	private String billStatus;
	private float finalProductPrice;
	private int purchaseId;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public float getFinalProductPrice() {
		return finalProductPrice;
	}
	public void setFinalProductPrice(float finalProductPrice) {
		this.finalProductPrice = finalProductPrice;
	}
	@Override
	public String toString() {
		return "BillInfo [productId=" + productId + ", productName=" + productName + ", purchaseQuantity="
				+ purchaseQuantity + ", productPrice=" + productPrice + ", billStatus=" + billStatus
				+ ", finalProductPrice=" + finalProductPrice + ", purchaseId=" + purchaseId + "]";
	}
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
}
