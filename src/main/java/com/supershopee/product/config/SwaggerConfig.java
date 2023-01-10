package com.supershopee.product.config;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.supershopee.product.controller.ProductController;
import com.supershopee.product.model.ProductInfo;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {ProductController.class})
public class SwaggerConfig {

	    @Bean
	    public Docket apiDocket() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.any())
	                .build()
	                .pathMapping("/") 
	                .directModelSubstitute(ProductInfo.class, com.supershopee.product.model.ProductInfo.class) 
	                .directModelSubstitute(LocalDate.class, String.class) 
	                .genericModelSubstitutes(ResponseEntity.class)
	                .apiInfo(metadata());
	    }

	    private ApiInfo metadata() {
	        return new ApiInfoBuilder()
	                .title("Product Service")
	                .description("Product Service")
	                .version("1.0.0")
	                .contact(new Contact(null, null, "this.developer"))
	                .build();
	    }
	    
	    @Bean
	    UiConfiguration uiConfig() {
	      return UiConfigurationBuilder.builder() 
	          .deepLinking(true)
	          .displayOperationId(false)
	          .defaultModelsExpandDepth(1)
	          .defaultModelExpandDepth(1)
	          .defaultModelRendering(ModelRendering.EXAMPLE)
	          .displayRequestDuration(false)
	          .docExpansion(DocExpansion.NONE)
	          .filter(false)
	          .maxDisplayedTags(null)
	          .operationsSorter(OperationsSorter.ALPHA)
	          .showExtensions(false)
	          .showCommonExtensions(false)
	          .tagsSorter(TagsSorter.ALPHA)
	          .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
	          .validatorUrl(null)
	          .build();
	    }
}
