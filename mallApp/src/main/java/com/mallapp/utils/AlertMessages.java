package com.mallapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertMessages {
	
	
	
	public static void show_alert(Context context,  String title, String message, String button_message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message)
		       .setCancelable(false)
		       .setPositiveButton(button_message, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		
	}
	
	

}
