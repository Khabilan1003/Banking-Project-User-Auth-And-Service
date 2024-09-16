package com.banking.oracle.controller.model.response;

public class TokenResponseModel {
	String token;
	Integer userId;

	public TokenResponseModel() {
	}

	public TokenResponseModel(String token, Integer userId) {
		super();
		this.token = token;
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
