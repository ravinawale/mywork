package com.supershopee.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.supershopee.product.entity.Product;
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
		} else {
			return Optional.empty();
		}
	}

	public Optional<ProductInfo> save(ProductInfo productInfo) {

		Optional<Product> product = Optional
				.of(productRepository.saveAndFlush(modelMapper.map(productInfo, Product.class)));

		return Optional.of(new ProductInfo(product.get()));
	}

	public List<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage) {

		Pageable pageable = PageRequest.of(page - 1, maxResult);
		Page<Product> productPage = productRepository.findAll(pageable);
		List<Product> listProduct = productPage.getContent();
		
		return mapList(listProduct,ProductInfo.class);
	}

	public List<ProductInfo> queryAllProducts() {
		List<Product> listProduct = productRepository.findAll();
		return mapList(listProduct,ProductInfo.class);
	}
	
	<S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	    		.stream()
	    		.map(element -> modelMapper.map(element, targetClass))
	    		.collect(Collectors.toList());
	}
}
