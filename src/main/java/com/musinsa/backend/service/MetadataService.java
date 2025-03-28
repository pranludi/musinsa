package com.musinsa.backend.service;

import com.musinsa.backend.config.MetadataComponent;
import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Metadata;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.domain.ProductPk;
import com.musinsa.backend.repository.ProductRepository;
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
public class MetadataService {

  private final Logger logger = LoggerFactory.getLogger(MetadataService.class);

  private final MetadataComponent metadataComponent;

  public MetadataService(MetadataComponent metadataComponent) {
    this.metadataComponent = metadataComponent;
  }

  // 추가된 상품을 Metadata 에 저장
  public void add(Product product) {
    List<Product> productList = metadataComponent.get().productList();
    productList.add(product);
    Map<Category, List<Product>> collectByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
    metadataComponent.set(new Metadata(productList, collectByCategory));
  }

  // 수정된 상품을 Metadata 에 저장
  public void update(Product product) {
    List<Product> productList = metadataComponent.get().productList();
    productList.remove(product);
    productList.add(product);
    Map<Category, List<Product>> collectByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
    metadataComponent.set(new Metadata(productList, collectByCategory));
  }

  // 삭제한 상품을 Metadata 에서 제거
  public void delete(Product product) {
    List<Product> productList = metadataComponent.get().productList();
    productList.remove(product);
    Map<Category, List<Product>> collectByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
    metadataComponent.set(new Metadata(productList, collectByCategory));
  }

}
