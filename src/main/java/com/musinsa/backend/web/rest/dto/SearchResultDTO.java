package com.musinsa.backend.web.rest.dto;

import com.musinsa.backend.domain.Category;
import java.util.List;

public record SearchResultDTO(Category category, List<ProductCategoryDTO> lowestProduct, List<ProductCategoryDTO> highestProduct) {

}
