package com.mallapp.Services.api;

public enum HttpMethods {

	GET("GET"), POST("POST"), DELETE("DELETE"), PUT("PUT");

	private final String name;

	HttpMethods(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
