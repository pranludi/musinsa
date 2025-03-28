package com.musinsa.backend.service;

import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.domain.ProductPk;
import com.musinsa.backend.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

  private final Logger logger = LoggerFactory.getLogger(ProductService.class);

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  // 상품 데이터 초기화
  public void productSetup(List<Product> productList) {
    productRepository.saveAll(productList);
  }

  public Optional<Product> getById(String brand, Category category) {
    return productRepository.findById(new ProductPk(brand, category));
  }

  public Product save(Product product) {
    return productRepository.save(product);
  }

  public void delete(Product product) {
    productRepository.deleteById(new ProductPk(product.getBrand(), product.getCategory()));
  }

}
