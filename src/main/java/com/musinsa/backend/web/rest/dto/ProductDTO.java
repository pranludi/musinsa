package com.musinsa.backend.web.rest.dto;

import com.musinsa.backend.domain.Category;

public record ProductDTO(String brand, Category category, long price) {

}
