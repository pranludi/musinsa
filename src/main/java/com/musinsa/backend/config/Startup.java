package com.musinsa.backend.config;

import com.musinsa.backend.domain.Category;
import com.musinsa.backend.domain.Metadata;
import com.musinsa.backend.domain.Product;
import com.musinsa.backend.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Startup implements InitializingBean {

  final MetadataComponent metadataComponent;
  final ProductService productService;

  public Startup(MetadataComponent metadataComponent, ProductService productService) {
    this.metadataComponent = metadataComponent;
    this.productService = productService;
  }

  @Override
  public void afterPropertiesSet() {

    List<Product> productList = new ArrayList<>();
    productList.add(new Product("A", Category.TOP, 11_200));
    productList.add(new Product("A", Category.OUTER, 5_500));
    productList.add(new Product("A", Category.PANTS, 4_200));
    productList.add(new Product("A", Category.SHOES, 9_000));
    productList.add(new Product("A", Category.BAG, 2_000));
    productList.add(new Product("A", Category.HAT, 1_700));
    productList.add(new Product("A", Category.SOCKS, 1_800));
    productList.add(new Product("A", Category.ACCESSORY, 2_300));

    productList.add(new Product("B", Category.TOP, 10_500));
    productList.add(new Product("B", Category.OUTER, 5_900));
    productList.add(new Product("B", Category.PANTS, 3_800));
    productList.add(new Product("B", Category.SHOES, 9_100));
    productList.add(new Product("B", Category.BAG, 2_100));
    productList.add(new Product("B", Category.HAT, 2_000));
    productList.add(new Product("B", Category.SOCKS, 2_000));
    productList.add(new Product("B", Category.ACCESSORY, 2_200));

    productList.add(new Product("C", Category.TOP, 10_000));
    productList.add(new Product("C", Category.OUTER, 6_200));
    productList.add(new Product("C", Category.PANTS, 3_300));
    productList.add(new Product("C", Category.SHOES, 9_200));
    productList.add(new Product("C", Category.BAG, 2_200));
    productList.add(new Product("C", Category.HAT, 1_900));
    productList.add(new Product("C", Category.SOCKS, 2_200));
    productList.add(new Product("C", Category.ACCESSORY, 2_100));

    productList.add(new Product("D", Category.TOP, 10_100));
    productList.add(new Product("D", Category.OUTER, 5_100));
    productList.add(new Product("D", Category.PANTS, 3_000));
    productList.add(new Product("D", Category.SHOES, 9_500));
    productList.add(new Product("D", Category.BAG, 2_500));
    productList.add(new Product("D", Category.HAT, 1_500));
    productList.add(new Product("D", Category.SOCKS, 2_400));
    productList.add(new Product("D", Category.ACCESSORY, 2_000));

    productList.add(new Product("E", Category.TOP, 10_700));
    productList.add(new Product("E", Category.OUTER, 5_000));
    productList.add(new Product("E", Category.PANTS, 3_800));
    productList.add(new Product("E", Category.SHOES, 9_900));
    productList.add(new Product("E", Category.BAG, 2_300));
    productList.add(new Product("E", Category.HAT, 1_800));
    productList.add(new Product("E", Category.SOCKS, 2_100));
    productList.add(new Product("E", Category.ACCESSORY, 2_100));

    productList.add(new Product("F", Category.TOP, 11_200));
    productList.add(new Product("F", Category.OUTER, 7_200));
    productList.add(new Product("F", Category.PANTS, 4_000));
    productList.add(new Product("F", Category.SHOES, 9_300));
    productList.add(new Product("F", Category.BAG, 2_100));
    productList.add(new Product("F", Category.HAT, 1_600));
    productList.add(new Product("F", Category.SOCKS, 2_300));
    productList.add(new Product("F", Category.ACCESSORY, 1_900));

    productList.add(new Product("G", Category.TOP, 10_500));
    productList.add(new Product("G", Category.OUTER, 5_800));
    productList.add(new Product("G", Category.PANTS, 3_900));
    productList.add(new Product("G", Category.SHOES, 9_000));
    productList.add(new Product("G", Category.BAG, 2_200));
    productList.add(new Product("G", Category.HAT, 1_700));
    productList.add(new Product("G", Category.SOCKS, 2_100));
    productList.add(new Product("G", Category.ACCESSORY, 2_000));

    productList.add(new Product("H", Category.TOP, 10_800));
    productList.add(new Product("H", Category.OUTER, 6_300));
    productList.add(new Product("H", Category.PANTS, 3_100));
    productList.add(new Product("H", Category.SHOES, 9_700));
    productList.add(new Product("H", Category.BAG, 2_100));
    productList.add(new Product("H", Category.HAT, 1_600));
    productList.add(new Product("H", Category.SOCKS, 2_000));
    productList.add(new Product("H", Category.ACCESSORY, 2_000));

    productList.add(new Product("I", Category.TOP, 11_400));
    productList.add(new Product("I", Category.OUTER, 6_700));
    productList.add(new Product("I", Category.PANTS, 3_200));
    productList.add(new Product("I", Category.SHOES, 9_500));
    productList.add(new Product("I", Category.BAG, 2_400));
    productList.add(new Product("I", Category.HAT, 1_700));
    productList.add(new Product("I", Category.SOCKS, 1_700));
    productList.add(new Product("I", Category.ACCESSORY, 2_400));

    productService.productSetup(productList);

    Map<Category, List<Product>> collectByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
    metadataComponent.set(new Metadata(productList, collectByCategory));
  }

}
