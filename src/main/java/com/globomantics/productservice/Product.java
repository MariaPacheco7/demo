package com.globomantics.productservice;

public class Product {
	
	private Integer id;
	
	private String name;
	
	private Integer quantity;
	
	private Integer version = 1;
	
	public Product() {
	}

	public Product(Integer id) {
		this.id = id;
	}
	
	public Product(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Product(Integer id, String name, Integer quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}
	
	public Product(String name, Integer quantity) {
		this.id = 1;
		this.name = name;
		this.quantity = quantity;
		this.version = 1;
	}

	public Product(Integer id, String name, Integer quantity, Integer version) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
