package com.supershopee.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.supershopee.product.entity.Product;
import com.supershopee.product.exceptions.InternalApplicationException;
import com.supershopee.product.model.ProductInfo;
import com.supershopee.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@InjectMocks
	ProductService productService;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
    ModelMapper modelMapper;
	
	@Test
	void testFindProductInfo() {
		// given
		Optional<String> pCode101=Optional.of("101");
		Optional<String> NotFound=Optional.of("10");
		
		// when - 
        when(productRepository.findProduct(pCode101.get())).thenReturn(getProduct(pCode101.get()));
        when(productRepository.findProduct(NotFound.get())).thenReturn(getProduct(NotFound.get()));
        when(modelMapper.map(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(new ProductInfo("101", "sugar",80.10));
        
        ////Product test case
		Optional<ProductInfo> result101 = productService.findProductInfo(pCode101.get());
        assertThat(result101.get()).extracting("code", "name", "price")
        				   		   .containsExactly("101", "sugar",80.10);
    	////Product test case
		Optional<ProductInfo> resultNotFound = productService.findProductInfo(NotFound.get());
        assertThat(resultNotFound.isEmpty()).isTrue();
        
	}

	@Test
	void testSave() throws InternalApplicationException {
		
		// given
		Product product = new Product();
		product.setCode("101");
		product.setName("sugar");
		product.setPrice(80.10);
		
		//when
		when(productRepository.saveAndFlush(ArgumentMatchers.any())).thenReturn(product);
		when(modelMapper.map(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(product);
		
		Optional<ProductInfo> result = productService.save(new ProductInfo("101", "sugar",80.10));
        assertThat(result.get()).extracting("code", "name", "price")
        				   .containsExactly("101", "sugar",80.10);
	}

	@Test
	void testQueryProducts() {
		//Given
		Page<Product> productPage = new PageImpl<>(getData());
		
		//when 
		when(productRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(productPage);
		when(modelMapper.map(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(new ProductInfo("101", "sugar",80.10));
		
		List<ProductInfo> result = productService.queryProducts(1, 5, 10);
		
		assertThat(result.size()).isEqualTo(2);
		/*assertThat(result).extracting("currentPage", "maxResult", "totalPages")
				.containsExactly(1,5,1); 
		assertThat(result.getTotalRecords()).isEqualTo(3);
		assertThat(result.getNavigationPages().size()).isEqualTo(2);*/
	}
	
	Optional<Product> getProduct(String pCode) {
		Optional<Product> result =  
				getData().stream()
					.filter(p -> p.getCode().equals(pCode))
					.findFirst();
		return result;
	}
	
	List<Product> getData(){
		
		Product p1 = new Product();
		p1.setCode("101");
		p1.setName("sugar");
		p1.setPrice(80.10);
			
		Product p2 = new Product();
		p2.setCode("102");
		p2.setName("wheat");
		p2.setPrice(40.50);	
		
		List<Product> pList = new ArrayList<Product>();
		pList.add(p1);
		pList.add(p2);
		
		return pList;
	}

}
