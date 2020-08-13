package com.nanda.moon.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nanda.moon.common.CommonUtils;
import com.nanda.moon.dto.ProductParamDTO;
import com.nanda.moon.dto.ResponseDTO;
import com.nanda.moon.entity.Product;
import com.nanda.moon.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ResponseEntity<String> add(ProductParamDTO productParamDTO) {
		if (!CommonUtils.isNotEmpty(productParamDTO.getProductcode()))
			return new ResponseEntity<String>(
					new ResponseDTO<ProductParamDTO>("404", "productcode is empty.").toString(), HttpStatus.OK);

		if (!CommonUtils.isNotEmpty(productParamDTO.getProductname()))
			return new ResponseEntity<String>(
					new ResponseDTO<ProductParamDTO>("404", "productname is empty.").toString(), HttpStatus.OK);

		if (productParamDTO.getPrice() == null)
			return new ResponseEntity<String>(new ResponseDTO<ProductParamDTO>("404", "price is empty.").toString(),
					HttpStatus.OK);

		Product product = productRepository.findByProductcode(productParamDTO.getProductcode());
		if (product != null)
			return new ResponseEntity<String>(
					new ResponseDTO<ProductParamDTO>("400", "productcode is used.").toString(), HttpStatus.OK);

		product = new Product();
		BeanUtils.copyProperties(productParamDTO, product);
		productRepository.save(product);

		ResponseDTO<ProductParamDTO> result = new ResponseDTO<ProductParamDTO>();
		result.addData(productParamDTO);
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}

	public ResponseEntity<String> getProduct(String productcode) {
		Product product = productRepository.findByProductcode(productcode);
		if (product == null)
			return new ResponseEntity<String>(
					new ResponseDTO<ProductParamDTO>("404", "Product is not found.").toString(), HttpStatus.OK);

		ProductParamDTO dto = new ProductParamDTO();
		BeanUtils.copyProperties(product, dto);

		ResponseDTO<ProductParamDTO> result = new ResponseDTO<ProductParamDTO>();
		result.addData(dto);
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}

	public ResponseEntity<String> search(String keyword, int currentpage, int pagesize) {
		keyword = CommonUtils.isNotEmpty(keyword) ? "%" + keyword + "%" : "%";

		int indexpage = currentpage > 0 ? currentpage - 1 : 0;
		Pageable pageable = PageRequest.of(indexpage, pagesize);

		ResponseDTO<ProductParamDTO> result = new ResponseDTO<ProductParamDTO>();
		Page<Product> page = productRepository.seach(keyword, pageable);
		if (page.hasContent()) {
			for (Product product : page) {
				ProductParamDTO dto = new ProductParamDTO();
				BeanUtils.copyProperties(product, dto);
				result.addData(dto);
			}
		}

		Integer totalCount = Long.valueOf(page.getTotalElements()).intValue();
		result.setPagedata(totalCount, currentpage, page.getTotalPages(), pagesize);

		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}

}
