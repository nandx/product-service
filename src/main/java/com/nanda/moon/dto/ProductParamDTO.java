package com.nanda.moon.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductParamDTO {

	private String productcode;
	private String productname;
	private BigDecimal price;

}
