package com.webFlux.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class entity of Purchases from users
 * @author Diego Moran
 * @version: 1.0
 */
@Document(collection = "purchases")
public class Purchase {
	
	@Id
	private String id;
	
	private String purchase_detail;
	
	private float total;
	
	private long installments;
	
	private String date_approved;
	
	private String status;
	
	private String Payment_method_id;
	
	private String payment_type_id;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurchase_detail() {
		return purchase_detail;
	}

	public void setPurchase_detail(String purchase_detail) {
		this.purchase_detail = purchase_detail;
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

}
