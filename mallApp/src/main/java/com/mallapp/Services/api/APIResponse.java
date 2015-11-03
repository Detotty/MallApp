package com.mallapp.Services.api;

public class APIResponse {

	private int code; // http response code
	private String body; // response body

	public APIResponse(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return code + ":" + body;
	}
}
