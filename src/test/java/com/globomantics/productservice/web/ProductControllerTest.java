package com.globomantics.productservice.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globomantics.productservice.Product;
import com.globomantics.productservice.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@MockBean
	private ProductService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /product/1 - Found")
	public void testGetProductByIdFound() throws Exception {
		System.out.println("Service -------------- :" + service);
		System.out.println("MockMvc -------------- :" + mockMvc);

		// Setup our mocked service
		Product mockProduct = new Product(1, "Product Name", 10, 1);
		doReturn(Optional.of(mockProduct)).when(service).findById(1);

		// Execute the get request
		mockMvc.perform(get("/product/{id}", 1))

				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

				// Validate the headers
				.andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
				.andExpect(header().string(HttpHeaders.LOCATION, "/product/1"))

				// Validate the returned fields
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Product Name")))
				.andExpect(jsonPath("$.quantity", is(10))).andExpect(jsonPath("$.version", is(1)));
	}

	@Test
	@DisplayName("GET /product/1 - Not Found")
	public void testGetProductByIdNotFound() throws Exception {
		ProductService listMock = Mockito.mock(ProductService.class);
		System.out.println("Service -------------- :" + service);
		System.out.println("MockMvc -------------- :" + mockMvc);

		// Setup our mocked service
		Optional<Product> p = Optional.empty();
		when(listMock.findById(1)).thenReturn(p);

		// Execute the GET request
		mockMvc.perform(get("/product/{id}", 1))

				// Validate that we get a 404 Not Found response
				.andExpect(status().isNotFound());
	}
	
	
	  @Test
	  @DisplayName("PUT /product/1 - Success") 
	  public void testProductPutSuccess() throws Exception { 
		  // Setup mocked service 
		  Product putProduct = new Product("Product Name", 10); 
		  Product mockProduct = new Product(1,"Product Name",10,1);
	
		  doReturn(Optional.of(mockProduct)).when(service).findById(1);
		  doReturn(true).when(service).update(mockProduct);
	  
		  mockMvc.perform(put("/product/{id}",1)
				  .contentType(MediaType.APPLICATION_JSON) 
				  .header(HttpHeaders.IF_MATCH,1)
				  .content(asJsonString(putProduct)))
	  
		  // Validate the response code and content type 
		  		.andExpect(status().isOk())
		  		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	  
		  // Validate the headers 
		  		.andExpect(header().string(HttpHeaders.ETAG, "\"2\"")) 
		  		.andExpect(header().string(HttpHeaders.LOCATION, "/product/1"))
	  
		  // Validate the returned fields 
		  		.andExpect(jsonPath("$.id", is(1)))
		  		.andExpect(jsonPath("$.name", is("Product Name")))
		  		.andExpect(jsonPath("$.quantity", is(10))) 
		  		.andExpect(jsonPath("$.version", is(2)));
	  
	  }
	 
	
	  @Test
	  @DisplayName("PUT /product/1 - Version Mismatch") 
	  public void testProductPutVersionMismatch() throws Exception { 
		  // Setup mocked service
		  Product putProduct = new Product("Product Name", 10); 
		  Product mockProduct = new Product(1,"Product Name",10,2);
	 
	
		  doReturn(Optional.of(mockProduct)).when(service).findById(1);
		  doReturn(true).when(service).update(mockProduct);
	  
		  mockMvc.perform(put("/product/{id}",1)
				  .contentType(MediaType.APPLICATION_JSON) .header(HttpHeaders.IF_MATCH,1)
				  .content(asJsonString(putProduct)))
	  
		  // Validate the response code and content type
		  	.andExpect(status().isConflict());
	  
	  }	 
	
	@Test
	@DisplayName("DELETE /product/1 - Success")
	public void testProductDeleteSuccess() throws Exception {
		// Setup mocked product
		Product mockProduct = new Product (1, "Product Name", 10, 1);
		
		// Setup the mocked service
		doReturn(Optional.of(mockProduct)).when(service).findById(1);
		doReturn(true).when(service).delete(1);
		
		// Execute our DELETE request
		mockMvc.perform(delete("/product/{id}",1))
			.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("DELETE /product/1 - Not Found")
	public void testProductDeleteNotFound() throws Exception {
		// Setup the mocked service
		doReturn(Optional.empty()).when(service).findById(1);
		doReturn(true).when(service).delete(1);
		
		// Execute our DELETE request
		mockMvc.perform(delete("/product/{id}",1))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("DELETE /product/1 - Failure")
	public void testProductDeleteFailure() throws Exception {
		// Setup mocked product
		Product mockProduct = new Product (1, "Product Name", 10, 1);
		
		// Setup the mocked service
		doReturn(Optional.of(mockProduct)).when(service).findById(1);
		doReturn(false).when(service).delete(1);
		
		// Execute our DELETE request
		mockMvc.perform(delete("/product/{id}",1))
			.andExpect(status().isInternalServerError());
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
