package com.webFlux.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class entity of Product
 * @author Diego Moran
 * @version: 1.0
 */

@Document(collection = "products")
public class Product implements Comparable<Product> {
	
	@Id
	private String id;
	
	private String numberExtern;
	
	private String name;
	
	private String type;
	
	private String description;
	
	private String photo;
	
	private float price;
	
	private String categoryId;
	
	private String categoryName;
	
	@Transient
	private int amount;
	

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getNumberExtern() {
		return numberExtern;
	}

	public void setNumberExtern(String numberExtern) {
		this.numberExtern = numberExtern;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object  obj) {
		if(this==obj) {
			return true;
		}
		
		if(!(obj instanceof Product)) {
			return false;
		}
		Product other=(Product) obj;
		return getNumberExtern().equals(other.getName()) && getName().equals(other.getName()); 
	}


	@Override
	public int compareTo(Product product) {
		return this.getId().compareTo(product.getId());
	}
	
	@Override
	public String toString() {
		return "Name: " + getName() + "numberExtern: "+ getNumberExtern(); 
	}
	
	
	
}
