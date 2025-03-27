package com.musinsa.backend.domain;

import java.io.Serializable;
import java.util.Objects;

public class ProductPk implements Serializable {

  private String brand;
  private Category category;

  public ProductPk() {
  }

  public ProductPk(String brand, Category category) {
    this.brand = brand;
    this.category = category;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ProductPk productPk)) {
      return false;
    }
    return Objects.equals(brand, productPk.brand) && category == productPk.category;
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, category);
  }
}
