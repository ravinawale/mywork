package com.supershopee.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supershopee.product.entity.Product;
 
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
 
	@Query("select p from Product p where p.code=:code")
    Optional<Product> findProduct(@Param("code") String code);
	
}
