package com.globomantics.productservice.service;

import java.util.List;
import java.util.Optional;

import com.globomantics.productservice.Product;

public interface ProductService {
	
	Optional<Product> findById(Integer id);
	
	List<Product> findAll();
	
	boolean update(Product product);
	
	Product save(Product product);
	
	boolean delete(Integer id);

}
