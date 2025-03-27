package com.musinsa.backend.repository;

import com.musinsa.backend.domain.Product;
import com.musinsa.backend.domain.ProductPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductPk> {

}
