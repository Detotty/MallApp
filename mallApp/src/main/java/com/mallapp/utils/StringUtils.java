package com.mallapp.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
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

	public static String changeDateFormat(String encode_date) {
		if (encode_date!=null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			SimpleDateFormat sdf_1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

			Date date = null;
			try {
				date = sdf.parse(encode_date);
				encode_date = sdf_1.format(date);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			encode_date = "";
		}
		return encode_date;
	}
}
