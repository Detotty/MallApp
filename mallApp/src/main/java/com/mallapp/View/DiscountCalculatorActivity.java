package com.mallapp.View;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class DiscountCalculatorActivity extends Activity implements OnClickListener {
	
	private EditText s_discount;
	private TextView discount_value, discounted_price;
	private TextView heading;
	private ImageButton navigation_button;
	private NumberPicker picker;
	String[] discount_percent = new String[] { "--", "5%", "10%", "15%", "20%", 
												"25%", "30%", "35%","40%", "45%",
												"50%","55%","60%","65%","65%","70%",
												"75%","80%","85%","90%","95%","100%"};
	double discount_value_ =0.00;
	double actual_price=0.00;
	double discount_amount=0.00;
	double discount_price=0.00;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discount_calculator_activity);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		init();
	}

	private void init() {
	
		s_discount		=  (EditText) findViewById(R.id.start_value);
		discount_value	=  (TextView) findViewById(R.id.discount_value);
		discounted_price=  (TextView) findViewById(R.id.discount_price);
		heading			= (TextView) findViewById(R.id.heading);
		heading.setText(getResources().getString(R.string.calculaor));
		navigation_button	= (ImageButton) findViewById(R.id.navigation);
		navigation_button.setOnClickListener(this);

		
		picker = (NumberPicker) findViewById(R.id.discount_picker);
		picker.setMinValue(0);
		picker.setMaxValue(discount_percent.length-1);
		picker.setDisplayedValues(discount_percent);
		picker.setWrapSelectorWheel(false);
		
		picker.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			
				String value= discount_percent[newVal];
				if(!value.equals("--") && value.length()>0){
					value= value.substring(0, value.length()-1);
					setDiscountValue(value);
				}else{
					value="0";
					setDiscountValue(value);
				}
				calculateDiscount(discount_value_, actual_price);
			}
		});
		
		
		
		s_discount.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String price= s.toString().trim();
				if(price !=null && price.length()>0 && !price.equals("")){
					setActualPrice(price);
				}else{
					price="0";
					setActualPrice(price);
				}
				calculateDiscount(discount_value_, actual_price);
				
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		
	}



	protected void setActualPrice(String price) {
		actual_price=Double.parseDouble(price);
	}

	protected void setDiscountValue(String value) {
		double discount_p= Double.parseDouble(value);
		discount_value_= discount_p;
	}

	private void calculateDiscount(double discount_p, double price_d) {
		
		discount_p= discount_p/100;
		discount_p= discount_p * price_d;
		double discount_amount= price_d- discount_p;
		
		if(discount_p>0 || discount_amount>0){
			discount_value.setText(String.format( "%.2f", discount_p ));
			discounted_price.setText(String.format( "%.2f", discount_amount ));
		}else{
			discount_value.setText("");
			discounted_price.setText("");
		}
	}

	@Override
	public void onClick(View v) {
		if(navigation_button.getId() == v.getId()){
			finish();
		}
	}
}
