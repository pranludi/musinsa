package com.musinsa.backend.service;

import static org.mockito.Mockito.verify;

import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @InjectMocks
  private ProductService productService;
  @Mock
  ProductRepository productRepository;

  @Test
  void productSetup() {
    List<Product> productList = List.of(
      new Product("Brand", Category.TOP, 10_000L),
      new Product("Brand", Category.PANTS, 20_000L)
    );
    productService.productSetup(productList);
    verify(productRepository).saveAll(productList);
  }

  @Test
  void add() {
    Product product = new Product("NewBrand", Category.TOP, 10_000L);
    productService.save(product);
    verify(productRepository).save(product);
  }
}