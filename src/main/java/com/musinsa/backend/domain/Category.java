package com.musinsa.backend.domain;

public enum Category {
  TOP("상의"),
  OUTER("아우터"),
  PANTS("바지"),
  SHOES("스니커즈"),
  BAG("가방"),
  HAT("모자"),
  SOCKS("양말"),
  ACCESSORY("액세서리");

  private final String name;

  Category(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
