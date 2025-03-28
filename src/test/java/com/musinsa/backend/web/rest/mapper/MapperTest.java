package com.musinsa.backend.web.rest.mapper;

import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.web.rest.dto.ProductCategoryDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapperTest {

  private ProductCategoryMapper productCategoryMapper;

  @BeforeEach
  public void setUp() {
    productCategoryMapper = new ProductCategoryMapperImpl();
  }

  @DisplayName("MapStruct Mapper Test")
  @Test
  void mapStructTest() {
    Product product = new Product("BRAND", Category.TOP, 10_000L);
    ProductCategoryDTO dto = productCategoryMapper.toDto(product);
    Product entity = productCategoryMapper.toEntity(dto);

    // assert
    Assertions.assertThat(product.getCategory()).isEqualTo(dto.category());
    Assertions.assertThat(dto.category()).isEqualTo(entity.getCategory());
    Assertions.assertThat(product.getCategory()).isEqualTo(entity.getCategory());
  }

}