package com.musinsa.backend.service;

import com.musinsa.backend.config.MetadataComponent;
import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Metadata;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.domain.ProductPk;
import com.musinsa.backend.repository.ProductRepository;
import com.musinsa.backend.web.errors.CommonException;
import com.musinsa.backend.web.errors.ErrorConstants;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

  private final Logger logger = LoggerFactory.getLogger(ProductService.class);

  private final MetadataComponent metadataComponent;
  private final ProductRepository productRepository;

  public ProductService(MetadataComponent metadataComponent, ProductRepository productRepository) {
    this.metadataComponent = metadataComponent;
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

  public Product add(Product product) {
    Product savedProduct = this.save(product);

    // 추가된 상품을 Metadata 에 저장
    List<Product> productList = metadataComponent.get().productList();
    productList.add(savedProduct);
    Map<Category, List<Product>> collectByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
    metadataComponent.set(new Metadata(productList, collectByCategory));
    return savedProduct;
  }

  public Product update(Product product) {
    Product updatedProduct = this.save(product);

    // 수정된 상품을 Metadata 에 저장
    List<Product> productList = metadataComponent.get().productList();
    productList.remove(updatedProduct);
    productList.add(updatedProduct);
    Map<Category, List<Product>> collectByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
    metadataComponent.set(new Metadata(productList, collectByCategory));

    return updatedProduct;
  }

  public void delete(Product product) {
    productRepository.deleteById(new ProductPk(product.getBrand(), product.getCategory()));

    // 삭제한 상품을 Metadata 에서 제거
    List<Product> productList = metadataComponent.get().productList();
    productList.remove(product);
    Map<Category, List<Product>> collectByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
    metadataComponent.set(new Metadata(productList, collectByCategory));
  }

}
