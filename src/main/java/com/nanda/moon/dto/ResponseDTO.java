package com.nanda.moon.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ResponseDTO<T> {

	private boolean success = false;
	private String message;
	private List<T> data;
	private Error error;
	private Pagedata pagedata;

	public ResponseDTO() {
		this.success = true;
		this.data = new ArrayList<T>();
	}

	public ResponseDTO(String errorCode, String errorMessage) {
		this.success = false;
		this.error = new Error(errorCode, errorMessage);
	}

	@Data
	public static class Error {
		private String code;
		private String message;

		public Error(String code, String message) {
			this.code = code;
			this.message = message;
		}
	}

	@Data
	public static class Pagedata {

		private int totalCount = 0;
		private int currentPage = 1;
		private int pageCount = 1;
		private int pageSize = 1;

		public Pagedata(int totalCount, int currentPage, int pageCount, int pageSize) {
			this.totalCount = totalCount;
			this.currentPage = currentPage;
			this.pageCount = pageCount;
			this.pageSize = pageSize;
		}

	}

	public void setPagedata(int totalCount, int currentPage, int pageCount, int pageSize) {
		this.pagedata = new Pagedata(totalCount, currentPage, pageCount, pageSize);
	}

	public void addData(T data) {
		this.data.add(data);
	}

	public String toString() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String str = objectMapper.writeValueAsString(this);
			return str;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		JSONObject jsonError = new JSONObject();
		jsonError.put("code", "INTERNAL_SERVER_ERROR");
		jsonError.put("message", "Internal Server Error");

		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("error", jsonError);
		return json.toString();
	}

}
