package com.musinsa.backend.web.rest.dto;

import com.musinsa.backend.domain.Product;
import java.util.List;

public record CategoryLowestPriceDTO(List<Product> productList, long totalPrice) {

}
