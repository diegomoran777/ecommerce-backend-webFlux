package com.webFlux.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * Class entity of UserProgram
 * @author Diego Moran
 * @version: 1.0
 */
@Document
public class UserProgram {
	
	@Id
	private String id;
	
	private String userName;
	
	private String password;
	
	private String role;
	
	private String email;
	
	private List<Purchase> purchases;
	

	public UserProgram() {
		this.purchases = new ArrayList<Purchase>();
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public void addPurchase(Purchase purchase) {
		this.purchases.add(purchase);
	}

}
