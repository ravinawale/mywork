package com.supershopee.product.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

import com.supershopee.product.exceptions.InternalApplicationException;
import com.supershopee.product.exceptions.InvalidInputException;
import com.supershopee.product.exceptions.ProductNotFoundException;
import com.supershopee.product.model.ApiResponse;
import com.supershopee.product.model.PaginationResult;
import com.supershopee.product.model.ProductInfo;
import com.supershopee.product.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductService productService;
	
	@Mock
	MessageSource messageSource;	
	
	@Test
    void testGetProduct() throws InvalidInputException, ProductNotFoundException {
        // given
		Optional<String> pCode101=Optional.of("101");
		Optional<String> pCode10=Optional.of("10");
		
		// when - 
        when(productService.findProductInfo(pCode101.get())).thenReturn(getProduct(pCode101.get()));
        when(productService.findProductInfo(pCode10.get())).thenReturn(getProduct(pCode10.get()));
        
        ////Product Present test case
		ResponseEntity<ApiResponse<Optional<ProductInfo>>> result101 = productController.getProduct(pCode101);
		Optional<?> pInfo = (Optional<?>) result101.getBody().getResult();
		
        assertThat(result101.getStatusCodeValue()).isEqualTo(200);
        assertThat((ProductInfo)pInfo.get()).extracting("code", "name", "price")
        				   .containsExactly("101", "sugar",80.10);
        
        //Product not present test case
        assertThrows(ProductNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
            	ResponseEntity<ApiResponse<Optional<ProductInfo>>> result101 = productController.getProduct(pCode10);
            }
        });
    }
	
	@Test
	void testProductSave() throws InternalApplicationException {
		// given
		Optional<ProductInfo> productInfo = Optional.of(new ProductInfo("101", "sugar",80.10));
		
		when(productService.save(productInfo.get())).thenReturn(productInfo);
		
		ResponseEntity<ApiResponse<Optional<ProductInfo>>> result = productController.productSave(productInfo.get());
		Optional<?> pInfo = (Optional<?>) result.getBody().getResult();
		
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat((ProductInfo)pInfo.get()).extracting("code", "name", "price")
        				   .containsExactly("101", "sugar",80.10);
	}

	@Test
	void testAllProducts() {
		
		 when(productService.queryAllProducts()).thenReturn(getData());
		 
		 ResponseEntity<ApiResponse<List<ProductInfo>>> result = productController.allProducts("",1);
		 List<ProductInfo> pInfo = result.getBody().getResult();
		 
		 int expectedSize=2;
		 
		 assertThat(pInfo.size()).isEqualTo(expectedSize);
	}
	
	Optional<ProductInfo> getProduct(String pCode) {
		
		Optional<ProductInfo> result =  getData().stream()
				.map(prd -> Optional.of(prd))
				.filter(p -> p.get().getCode().equals(pCode))
				.findAny().orElse(Optional.empty());
		
		return result;
	}
	
	List<ProductInfo> getData(){
		
		ProductInfo p1 = new ProductInfo("101","sugar",80.10);
		p1.setCode("101");
		p1.setName("sugar");
		p1.setPrice(80.10);
		
		ProductInfo p2 = new ProductInfo("102","wheat",40.50);
		
		List<ProductInfo> pList = new ArrayList<ProductInfo>();
		pList.add(p1);
		pList.add(p2);
		
		return pList;
	}
}
