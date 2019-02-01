package com.globomantics.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.globomantics.productservice.Product;
import com.globomantics.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}
	
	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	@Override
	public boolean update(Product product) {
		return productRepository.update(product);
	}
	
	@Override
	public Product save(Product product) {
		product.setVersion(1);
		return productRepository.save(product);
	}
	
	@Override
	public boolean delete(Integer id) {
		return productRepository.delete(id);
	}
	
}
