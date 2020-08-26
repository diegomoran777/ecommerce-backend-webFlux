package com.webFlux.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class entity of Image
 * @author Diego Moran
 * @version: 1.0
 */

@Document
public class ProductByUserCart {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String userName;
	
	private String productId;
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
