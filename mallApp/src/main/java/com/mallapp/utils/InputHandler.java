package com.mallapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputHandler {
	
	
	
	public static void hideSoftKeyboard(Activity activity_) {
	    if(activity_.getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) activity_.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(activity_.getCurrentFocus().getWindowToken(), 0);
	    }
	}
	
	public static void showSoftKeyboard(Activity activity_, View view) {
	    if(activity_.getCurrentFocus()!=null) {
	    	InputMethodManager inputMethodManager = (InputMethodManager) activity_.getSystemService(Context.INPUT_METHOD_SERVICE);
	    	inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	    }
	}
	
	
}
