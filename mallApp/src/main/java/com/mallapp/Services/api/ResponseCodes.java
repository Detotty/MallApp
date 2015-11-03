package com.mallapp.Services.api;

public enum ResponseCodes {

	OK(200), BAD_CREDENTIALS(400);

	private final int code;

	ResponseCodes(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
