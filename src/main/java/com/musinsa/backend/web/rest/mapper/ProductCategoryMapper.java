package com.musinsa.backend.web.rest.mapper;

import com.musinsa.backend.domain.Product;
import com.musinsa.backend.web.rest.dto.ProductCategoryDTO;
import com.musinsa.backend.web.rest.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, Product> {

}
