package com.supershopee.product.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supershopee.product.entity.Product;
import com.supershopee.product.model.ProductInfo;
import com.supershopee.product.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerMvcTest {

	
	@MockBean
	ProductService productService;
	
	@MockBean
	MessageSource messageSource;

    @Autowired
    private MockMvc mockMvc;
    
    
    @Test
    @DisplayName("GET /product/101")
    void testGetProductByCode() throws Exception {
        // Setup our mocked service
        Product product = new Product();
        	product.setCode("101");
        	product.setName("Suger");
        	product.setPrice(75.50);
        
        doReturn(Optional.of(product)).when(productService).findProductInfo("101");
        
        // Execute the GET request
        mockMvc.perform(get("/product/{code}", "101"))
        
                // Validate the response code and content type
                .andExpect(status().isOk())
                // Validate headers
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/json"))
                //.andExpect(header().string(HttpHeaders.ETAG, "\"101\""))

                // Validate the returned fields
                .andExpect(jsonPath("$.result.code", is("101")))
                .andExpect(jsonPath("$.result.name", is("Suger")))
                .andExpect(jsonPath("$.result.price", is(75.50)));
    }
    
    @Test
    @DisplayName("GET /product/102- Not Found")
    void testGetProductByCodeNotFound() throws Exception {
        // Setup our mocked service
        doReturn(Optional.empty()).when(productService).findProductInfo("102");
        
        // Execute the GET request
        mockMvc.perform(get("/product/{code}", "102"))
                // Validate the response code
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("POST /product/saveProduct")
    void testCreateWidget() throws Exception {
        // Setup our mocked service
    	Optional<ProductInfo> productInfo = Optional.of(new ProductInfo("101", "Suger",80.10));
        doReturn(productInfo).when(productService).save(ArgumentMatchers.any());
        
        // Execute the POST request
        mockMvc.perform(post("/product/saveProduct")
        		
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(productInfo.get())))
                // Validate the response code and content type
                .andExpect(status().isOk())

                // Validate the returned fields
                .andExpect(jsonPath("$.result.code", is("101")))
                .andExpect(jsonPath("$.result.name", is("Suger")))
                .andExpect(jsonPath("$.result.price", is(80.10)));
    }
    
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
