package com.nanda.moon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nanda.moon.dto.ProductParamDTO;
import com.nanda.moon.dto.ResponseDTO;
import com.nanda.moon.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Operation(summary = "Add Product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Add Product", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }) })
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addProduct(@RequestBody ProductParamDTO productParamDTO) {
		return productService.add(productParamDTO);
	}

	@Operation(summary = "Get Product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Get Product", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }) })
	@RequestMapping(value = "/get/{productcode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProduct(@PathVariable("productcode") String productcode) {
		return productService.getProduct(productcode);
	}

	@Operation(summary = "Search Product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Search Product", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }) })
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> search(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer currentpage,
			@RequestParam(name = "size", required = false, defaultValue = "10") Integer pagesize) {
		return productService.search(keyword, currentpage, pagesize);
	}

}
