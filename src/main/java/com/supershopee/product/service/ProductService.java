package com.supershopee.product.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.supershopee.product.entity.Product;
import com.supershopee.product.exceptions.InternalApplicationException;
import com.supershopee.product.model.PaginationResult;
import com.supershopee.product.model.ProductInfo;
import com.supershopee.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
    public Optional<Product> findProduct(String code) {
        return productRepository.findProduct(code);
    }
    
    public Optional<ProductInfo> findProductInfo(String code) {
    	
    	Optional<Product> product = findProduct(code);
        if (product.isPresent()) {
        	return Optional.of(modelMapper.map(product.get(), ProductInfo.class));
        }else {
        	return Optional.empty();
        }
    }
 
	public Optional<ProductInfo> save(ProductInfo productInfo) throws InternalApplicationException {

		Optional<Product> product = Optional.empty();
		try {
			product = Optional.of(productRepository.saveAndFlush(modelMapper.map(productInfo, Product.class)));
		} catch (Exception e) {
			throw new InternalApplicationException(e.getMessage());
		}
		
		return Optional.of(new ProductInfo(product.get()));
	}
    
    public List<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {
        
    	Pageable pageable = PageRequest.of(page - 1, maxResult);
        Page<Product> productPage = productRepository.findAll(pageable);
        
        List<Product> listProduct = productPage.getContent();
        
        List<ProductInfo> productInfoList = listProduct.stream()
        		   .map(prd -> modelMapper.map(prd, ProductInfo.class))
        		   .toList();
        
        return productInfoList;
    }
    
    public List<ProductInfo> queryAllProducts() {
        List<Product> listProduct = productRepository.findAll();
        
        List<ProductInfo> productInfoList = listProduct.stream()
        		   .map(prd -> modelMapper.map(prd, ProductInfo.class))
        		   .toList();
        
        return productInfoList;
    }
}
