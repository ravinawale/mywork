package com.supershopee.product.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.supershopee.product.exceptions.InternalApplicationException;
import com.supershopee.product.exceptions.ProductNotFoundException;
import com.supershopee.product.model.ApiResponse;
import com.supershopee.product.model.ProductInfo;
import com.supershopee.product.service.ProductService;

@Controller
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product/")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * @param pCode
	 * @return API Response - 
	 * @throws ProductNotFoundException
	 */
	@GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<?>> getProduct(@PathVariable(name = "code") Optional<String> pCode)
			throws ProductNotFoundException {
		Optional<ProductInfo> productInfo = Optional.empty();

		if (pCode.isPresent()) {
			productInfo = productService.findProductInfo(pCode.get());

			if (productInfo.isEmpty()) {
				throw new ProductNotFoundException(messageSource.getMessage("api.error.product.notfound",
						new String[] { pCode.get() }, Locale.ENGLISH));
			}
		}

		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", productInfo), HttpStatus.OK);
	}

	/**
	 * @param productInfo
	 * @return API Response - 
	 * @throws NoSuchMessageException
	 * @throws InternalApplicationException
	 */
	@PostMapping(value = "/saveProduct")
	public ResponseEntity<ApiResponse<?>> productSave(@RequestBody ProductInfo productInfo)
			throws NoSuchMessageException, InternalApplicationException {

		Optional<ProductInfo> pInfo = productService.save(productInfo);

		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Product Saved Successfully", pInfo),
				HttpStatus.OK);
	}

	/**
	 * @param likeName
	 * @param page
	 * @return API Response - 
	 */
	@GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<?>> allProducts(@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		final int maxResult = 5;
		final int maxNavigationPage = 10;

		List<ProductInfo> result = productService.queryProducts(page, maxResult, maxNavigationPage);

		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Success", result), HttpStatus.OK);
	}
	
	/**
	 * @param likeName
	 * @param page
	 * @return API Response - 
	 */
	@GetMapping(value = "/allproducts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<?>> allProductsWithoutPagination() {
		List<ProductInfo> result = productService.queryAllProducts();
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Success", result), HttpStatus.OK);
	}
	
}