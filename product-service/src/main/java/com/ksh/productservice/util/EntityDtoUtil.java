package com.ksh.productservice.util;

import org.springframework.beans.BeanUtils;

import com.ksh.productservice.dto.ProductDto;
import com.ksh.productservice.entity.Product;

public class EntityDtoUtil {

	public static ProductDto toDto(Product product) {
		ProductDto dto = new ProductDto();
		
//		dto.setDescription(product.getDescription());
		BeanUtils.copyProperties(product, dto);
		return dto;
		
	}
	
	public static Product toEntity(ProductDto productDto) {
		Product product = new Product();
		
		BeanUtils.copyProperties(productDto, product);
		return product;
		
	}
}
