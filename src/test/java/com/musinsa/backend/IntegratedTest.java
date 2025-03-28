package com.musinsa.backend;

import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.ResultStatus;
import com.musinsa.backend.web.rest.ProductController;
import com.musinsa.backend.web.rest.ServiceController;
import com.musinsa.backend.web.rest.dto.BrandLowestPriceDTO;
import com.musinsa.backend.web.rest.dto.CategoryLowestPriceDTO;
import com.musinsa.backend.web.rest.dto.CommonDTO;
import com.musinsa.backend.web.rest.dto.ProductDTO;
import com.musinsa.backend.web.rest.dto.ProductDeleteDTO;
import com.musinsa.backend.web.rest.dto.ResultDTO;
import com.musinsa.backend.web.rest.dto.SearchDTO;
import com.musinsa.backend.web.rest.dto.SearchResultDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class IntegratedTest {

  @Autowired
  ProductController productController;

  @Autowired
  ServiceController serviceController;

  @DisplayName("상품 통합 테스트 : 상품 등록 -> 상품 수정 -> 상품 삭제")
  @Test
  void productIntegratedTest() {
    // 상품 추가
    long price = 100_000L;
    ProductDTO productDTO = new ProductDTO("J", Category.TOP, price);
    ResponseEntity<ResultDTO<ProductDTO>> resAddDTO = productController.add(productDTO);
    Assertions.assertThat(resAddDTO.getBody().status()).isEqualTo(ResultStatus.SUCCESS);
    ProductDTO addedProductDTO = resAddDTO.getBody().result().orElseThrow();
    Assertions.assertThat(productDTO.price()).isEqualTo(addedProductDTO.price());

    // 추가된 상품이 `카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API`에서 조회가 되는지 확인
    SearchDTO searchDTO = new SearchDTO(Category.TOP);
    ResponseEntity<ResultDTO<SearchResultDTO>> resSearchDTO = serviceController.lowestPriceByCategory(searchDTO);
    Assertions.assertThat(resSearchDTO.getBody().status()).isEqualTo(ResultStatus.SUCCESS);
    SearchResultDTO searchResultDTO = resSearchDTO.getBody().result().orElseThrow();
    Assertions.assertThat(price).isEqualTo(searchResultDTO.highestProduct().get(0).price());

    // 상품 수정
    productDTO = new ProductDTO("J", Category.TOP, 90_000L);
    ResponseEntity<ResultDTO<ProductDTO>> resUpdateDTO = productController.update(productDTO);
    Assertions.assertThat(resUpdateDTO.getBody().status()).isEqualTo(ResultStatus.SUCCESS);
    ProductDTO updatedProductDTO = resUpdateDTO.getBody().result().orElseThrow();
    Assertions.assertThat(productDTO.price()).isEqualTo(updatedProductDTO.price());

    // 상품 삭제
    ProductDeleteDTO deleteDTO = new ProductDeleteDTO("J", Category.TOP);
    ResponseEntity<ResultDTO<CommonDTO>> resDeleteDTO = productController.delete(deleteDTO);
    Assertions.assertThat(resDeleteDTO.getBody().status()).isEqualTo(ResultStatus.SUCCESS);
    CommonDTO deleteCommonDTO = resDeleteDTO.getBody().result().orElseThrow();
    Assertions.assertThat("ok").isEqualTo(deleteCommonDTO.message());
  }

  @DisplayName("서비스 통합 테스트")
  @Test
  void serviceIntegratedTest() {
    // 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회
    ResponseEntity<ResultDTO<CategoryLowestPriceDTO>> resCategoryLowestPriceDTO = serviceController.categoryLowestPrice();
    Assertions.assertThat(resCategoryLowestPriceDTO.getBody().status()).isEqualTo(ResultStatus.SUCCESS);
    CategoryLowestPriceDTO categoryLowestPriceDTO = resCategoryLowestPriceDTO.getBody().result().orElseThrow();
    Assertions.assertThat(34_100L).isEqualTo(categoryLowestPriceDTO.totalPrice());

    // 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회
    ResponseEntity<ResultDTO<BrandLowestPriceDTO>> resBrandLowestPriceDTO = serviceController.brandLowestPrice();
    Assertions.assertThat(resBrandLowestPriceDTO.getBody().status()).isEqualTo(ResultStatus.SUCCESS);
    BrandLowestPriceDTO brandLowestPriceDTO = resBrandLowestPriceDTO.getBody().result().orElseThrow();
    Assertions.assertThat(36_100).isEqualTo(brandLowestPriceDTO.totalPrice());

    // 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회
    SearchDTO searchDTO = new SearchDTO(Category.TOP);
    ResponseEntity<ResultDTO<SearchResultDTO>> resSearchDTO = serviceController.lowestPriceByCategory(searchDTO);
    Assertions.assertThat(resSearchDTO.getBody().status()).isEqualTo(ResultStatus.SUCCESS);
    SearchResultDTO searchResultDTO = resSearchDTO.getBody().result().orElseThrow();
    Assertions.assertThat(10_000).isEqualTo(searchResultDTO.lowestProduct().get(0).price());
    Assertions.assertThat(11_400).isEqualTo(searchResultDTO.highestProduct().get(0).price());
  }

}