package com.musinsa.backend.web.rest.dto;

import java.util.List;

public record BrandLowestPriceDTO(String brand, List<ProductCategoryDTO> productList, long totalPrice) {

}
