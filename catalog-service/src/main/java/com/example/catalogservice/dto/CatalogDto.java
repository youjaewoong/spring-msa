package com.example.catalogservice.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CatalogDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7775606836394384345L;
	private String productId;
	private String qty;
	private String unitPrice;
	private String totalPrice;
	
	private String orderId;
	private String userId;
	
}
