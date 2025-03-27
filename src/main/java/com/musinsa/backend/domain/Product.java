package com.musinsa.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product")
@IdClass(ProductPk.class)
public class Product implements Serializable {

  @Id
  @Column(nullable = false)
  private String brand;

  @Id
  @Column(nullable = false)
  private Category category;

  @Column(nullable = false)
  private long price;

  public Product() {
  }

  public Product(String brand, Category category, long price) {
    this.brand = brand;
    this.category = category;
    this.price = price;
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

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Product product)) {
      return false;
    }
    return Objects.equals(brand, product.brand) && category == product.category;
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, category);
  }

  @Override
  public String toString() {
    return "Product{" +
      "brand='" + brand + '\'' +
      ", category=" + category +
      ", price=" + price +
      '}';
  }
}
