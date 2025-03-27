package com.musinsa.backend.web.rest.mapper;

import com.musinsa.backend.domain.Product;
import com.musinsa.backend.web.rest.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

}
