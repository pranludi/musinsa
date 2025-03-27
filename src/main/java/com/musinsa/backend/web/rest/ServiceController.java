package com.musinsa.backend.web.rest;

import com.musinsa.backend.config.MetadataComponent;
import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Metadata;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.service.ProductService;
import com.musinsa.backend.web.errors.ErrorConstants;
import com.musinsa.backend.web.rest.dto.BrandLowestPriceDTO;
import com.musinsa.backend.web.rest.dto.CategoryLowestPriceDTO;
import com.musinsa.backend.web.rest.dto.ProductCategoryDTO;
import com.musinsa.backend.web.rest.dto.ResultDTO;
import com.musinsa.backend.web.rest.dto.SearchDTO;
import com.musinsa.backend.web.rest.dto.SearchResultDTO;
import com.musinsa.backend.web.rest.mapper.ProductCategoryMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ServiceController {

  private final MetadataComponent metadataComponent;
  private final ProductCategoryMapper productCategoryMapper;
  private final ProductService productService;

  public ServiceController(MetadataComponent metadataComponent, ProductService productService, ProductCategoryMapper productCategoryMapper) {
    this.metadataComponent = metadataComponent;
    this.productService = productService;
    this.productCategoryMapper = productCategoryMapper;
  }

  // 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
  @GetMapping("/category/lowest-price")
  public ResponseEntity categoryLowestPrice() {
    Metadata metadata = metadataComponent.get();
    Map<Category, List<Product>> collectByCategory = metadata.collectByCategory();
    if (collectByCategory.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.NotFound, "데이터가 없습니다."));
    }

    long totalPrice = 0;
    List<Product> selectedProductList = new ArrayList<>();
    for (Category category : Category.values()) {
      List<Product> products = collectByCategory.get(category);
      products.sort(Comparator.comparing(Product::getPrice));
      selectedProductList.add(products.get(0));
      totalPrice += products.get(0).getPrice();
    }

    return ResponseEntity.ok().body(ResultDTO.success(new CategoryLowestPriceDTO(selectedProductList, totalPrice)));
  }

  // 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
  @GetMapping("/brand/lowest-price")
  public ResponseEntity brandLowestPrice() {
    Metadata metadata = metadataComponent.get();
    Map<String, List<Product>> collectByBrand = metadata.productList().stream().collect(Collectors.groupingBy(Product::getBrand));
    if (collectByBrand.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.NotFound, "데이터가 없습니다."));
    }

    String lowestPriceBrand = "";
    long brandSum = Long.MAX_VALUE;
    for (String key : collectByBrand.keySet()) {
      long sum = collectByBrand.get(key).stream().mapToLong(Product::getPrice).sum();
      if (sum < brandSum) {
        brandSum = sum;
        lowestPriceBrand = key;
      }
    }
    List<ProductCategoryDTO> productCategoryDTOList = productCategoryMapper.toDto(collectByBrand.get(lowestPriceBrand));

    return ResponseEntity.ok().body(ResultDTO.success(new BrandLowestPriceDTO(lowestPriceBrand, productCategoryDTOList, brandSum)));
  }

  // 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
  @PostMapping("/search/lowest-price")
  public ResponseEntity lowestPriceByCategory(@RequestBody SearchDTO dto) {
    Metadata metadata = metadataComponent.get();
    Map<Category, List<Product>> collectByCategory = metadata.collectByCategory();
    if (collectByCategory.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.NotFound, "데이터가 없습니다."));
    }

    List<Product> selectedProductList = collectByCategory.get(dto.category());
    Optional<Product> lowestProduct = selectedProductList.stream().min(Comparator.comparing(Product::getPrice));
    Optional<Product> highestProduct = selectedProductList.stream().max(Comparator.comparing(Product::getPrice));

    if (lowestProduct.isEmpty() || highestProduct.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.SortError, "정렬 데이터에 오류가 있습니다."));
    }

    return ResponseEntity.ok()
      .body(
        ResultDTO.success(
          new SearchResultDTO(
            dto.category()
            , List.of(productCategoryMapper.toDto(lowestProduct.get()))
            , List.of(productCategoryMapper.toDto(highestProduct.get()))
          )
        )
      );
  }

}
