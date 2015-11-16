package com.mallapp.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class StringUtils {

	public static String convertStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
		} catch (Exception e) {
			// Toast.makeText(this, "Stream Exception",
			// Toast.LENGTH_SHORT).show();
		}
		return total.toString();
	}

	public static boolean isValidMobile(String phone)
	{
		return android.util.Patterns.PHONE.matcher(phone).matches();
	}
}
