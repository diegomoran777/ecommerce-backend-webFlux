package com.webFlux.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class entity of Sales from owner
 * @author Diego Moran
 * @version: 1.0
 */

@Document(collection = "sales")
public class Sale {

	@Id
	private String id;

	private String sale_detail;
	
	private float total;
	
	private long installments;
	
	private String status;
	
	private String date_approved;
	
	private String Payment_method_id;
	
	private String payment_type_id;
	
	private String userName;
	
	private String userMail;
	
	private boolean delivered;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSale_detail() {
		return sale_detail;
	}

	public void setSale_detail(String sale_detail) {
		this.sale_detail = sale_detail;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public long getInstallments() {
		return installments;
	}

	public void setInstallments(long installments) {
		this.installments = installments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate_approved() {
		return date_approved;
	}

	public void setDate_approved(String date_approved) {
		this.date_approved = date_approved;
	}

	public String getPayment_method_id() {
		return Payment_method_id;
	}

	public void setPayment_method_id(String payment_method_id) {
		Payment_method_id = payment_method_id;
	}

	public String getPayment_type_id() {
		return payment_type_id;
	}

	public void setPayment_type_id(String payment_type_id) {
		this.payment_type_id = payment_type_id;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	
}
