package com.musinsa.backend.web.rest;

import com.musinsa.backend.config.MetadataComponent;
import com.musinsa.backend.domain.Metadata;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.service.ProductService;
import com.musinsa.backend.web.errors.ErrorConstants;
import com.musinsa.backend.web.rest.dto.CommonDTO;
import com.musinsa.backend.web.rest.dto.ProductDTO;
import com.musinsa.backend.web.rest.dto.ProductDeleteDTO;
import com.musinsa.backend.web.rest.dto.ResultDTO;
import com.musinsa.backend.web.rest.mapper.ProductMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final Logger logger = LoggerFactory.getLogger(ProductController.class);

  private final MetadataComponent metadataComponent;
  private final ProductMapper productMapper;
  private final ProductService productService;

  public ProductController(MetadataComponent metadataComponent, ProductService productService, ProductMapper productMapper) {
    this.metadataComponent = metadataComponent;
    this.productService = productService;
    this.productMapper = productMapper;
  }

  // 상품 추가
  @PostMapping("/add")
  public ResponseEntity add(@RequestBody ProductDTO dto) {
    Optional<Product> opt = productService.getById(dto.brand(), dto.category());
    if (opt.isPresent()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.AlreadyExist, "이미 존재하는 상품입니다."));
    }
    Product product = productService.save(productMapper.toEntity(dto));
    List<Product> productList = metadataComponent.get().productList();
    productList.add(product);
    metadataComponent.set(new Metadata(productList));
    return ResponseEntity.ok().body(ResultDTO.success(productMapper.toDto(product)));
  }

  // 상품 수정
  @PostMapping("/update")
  public ResponseEntity update(@RequestBody ProductDTO dto) {
    Optional<Product> opt = productService.getById(dto.brand(), dto.category());
    if (opt.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.NotFound, "해당 상품이 없습니다."));
    }
    Product product = productService.save(productMapper.toEntity(dto));
    List<Product> productList = metadataComponent.get().productList();
    productList.remove(product);
    productList.add(product);
    metadataComponent.set(new Metadata(productList));
    return ResponseEntity.ok().body(ResultDTO.success(productMapper.toDto(product)));
  }

  // 상품 삭제
  @PostMapping("/delete")
  public ResponseEntity delete(@RequestBody ProductDeleteDTO dto) {
    Optional<Product> opt = productService.getById(dto.brand(), dto.category());
    if (opt.isEmpty()) {
      return ResponseEntity.ok().body(ResultDTO.failure(ErrorConstants.NotFound, "해당 상품이 없습니다."));
    }
    productService.delete(dto.brand(), dto.category());
    List<Product> productList = metadataComponent.get().productList();
    productList.remove(opt.get());
    metadataComponent.set(new Metadata(productList));
    return ResponseEntity.ok().body(ResultDTO.success(new CommonDTO("ok")));
  }

}
