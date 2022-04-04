package com.exam.shipement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.shipement.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
	
	public List<Product> findByPriceGreaterThan(int price);

	public List<Product> findByCategory(String category);
	
	public List<Product> findByPriceLessThanEqual(int price);
	
	public Product findByProductname(String productname);


}
