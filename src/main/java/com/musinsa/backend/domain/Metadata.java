package com.musinsa.backend.domain;

import java.util.List;
import java.util.Map;

public record Metadata(
  List<Product> productList,
  Map<Category, List<Product>> collectByCategory
) {

}
