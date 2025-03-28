package com.musinsa.backend.web.rest;

import com.musinsa.backend.config.MetadataComponent;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.service.MetadataService;
import com.musinsa.backend.service.ProductService;
import com.musinsa.backend.web.errors.ErrorConstants;
import com.musinsa.backend.web.rest.dto.CommonDTO;
import com.musinsa.backend.web.rest.dto.ProductDTO;
import com.musinsa.backend.web.rest.dto.ProductDeleteDTO;
import com.musinsa.backend.web.rest.dto.ResultDTO;
import com.musinsa.backend.web.rest.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 관리")
@RestController
@RequestMapping("/product")
public class ProductController {

  private final Logger logger = LoggerFactory.getLogger(ProductController.class);

  private final MetadataService metadataService;
  private final ProductMapper productMapper;
  private final ProductService productService;

  public ProductController(MetadataService metadataService, ProductService productService, ProductMapper productMapper) {
    this.metadataService = metadataService;
    this.productService = productService;
    this.productMapper = productMapper;
  }

  @Operation(summary = "상품 추가 API")
  @Parameters(value = {
    @Parameter(name = "brand", description = "브랜드 pk"),
    @Parameter(name = "category", description = "카테고리 pk"),
    @Parameter(name = "price", description = "가격")
  })
  @PostMapping("/add")
  public ResponseEntity add(@RequestBody ProductDTO dto) {
    Optional<Product> opt = productService.getById(dto.brand(), dto.category());
    if (opt.isPresent()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.AlreadyExist, "이미 존재하는 상품입니다."));
    }
    Product product = productService.save(productMapper.toEntity(dto));
    metadataService.add(product);
    return ResponseEntity.ok().body(ResultDTO.success(productMapper.toDto(product)));
  }

  @Operation(summary = "상품 수정 API")
  @Parameters(value = {
    @Parameter(name = "brand", description = "브랜드 pk"),
    @Parameter(name = "category", description = "카테고리 pk"),
    @Parameter(name = "price", description = "가격")
  })
  @PostMapping("/update")
  public ResponseEntity update(@RequestBody ProductDTO dto) {
    Optional<Product> opt = productService.getById(dto.brand(), dto.category());
    if (opt.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.NotFound, "해당 상품이 없습니다."));
    }
    Product product = productService.save(productMapper.toEntity(dto));
    metadataService.update(product);
    return ResponseEntity.ok().body(ResultDTO.success(productMapper.toDto(product)));
  }

  @Operation(summary = "상품 삭제 API")
  @Parameters(value = {
    @Parameter(name = "brand", description = "브랜드 pk"),
    @Parameter(name = "category", description = "카테고리 pk")
  })
  @PostMapping("/delete")
  public ResponseEntity delete(@RequestBody ProductDeleteDTO dto) {
    Optional<Product> opt = productService.getById(dto.brand(), dto.category());
    if (opt.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.NotFound, "해당 상품이 없습니다."));
    }
    productService.delete(opt.get());
    metadataService.delete(opt.get());
    return ResponseEntity.ok().body(ResultDTO.success(new CommonDTO("ok")));
  }

}
