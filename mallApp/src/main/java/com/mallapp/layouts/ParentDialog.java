/**
 * 
 */
package com.mallapp.layouts;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.Window;

/**
 * @Date Apr 2, 2014
 */
public class ParentDialog extends Dialog {

	private Context localContext;
	//private OnDialogTouchListener mOnDialogTouchListener;

	public ParentDialog(Context context) {
		super(context);
		localContext = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		super.dispatchTouchEvent(ev);
		return false;
	}

}
