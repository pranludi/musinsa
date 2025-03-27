package com.musinsa.backend.service;

import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.domain.ProductPk;
import com.musinsa.backend.repository.ProductRepository;
import com.musinsa.backend.web.errors.CommonException;
import com.musinsa.backend.web.errors.ErrorConstants;
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

  public Product findById(String brand, Category category) {
    Optional<Product> opt = productRepository.findById(new ProductPk(brand, category));
    if (opt.isEmpty()) {
      throw new CommonException(ErrorConstants.NotFound, "상품이 없습니다.");
    }
    return opt.get();
  }

  public Optional<Product> getById(String brand, Category category) {
    return productRepository.findById(new ProductPk(brand, category));
  }

  public Product save(Product product) {
    return productRepository.save(product);
  }

  public void delete(String brand, Category category) {
    productRepository.deleteById(new ProductPk(brand, category));
  }

}
