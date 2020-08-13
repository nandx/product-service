package com.nanda.moon.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20, unique = true, nullable = false)
	private Long id;

	@Column(name = "productcode", length = 50, unique = true, nullable = false)
	private String productcode;

	@Column(name = "productname", length = 200, nullable = false)
	private String productname;

	@Column(name = "price", precision = 15, scale = 4)
	private BigDecimal price;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createddate")
	private Date createddate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateddate")
	private Date updateddate;

}
