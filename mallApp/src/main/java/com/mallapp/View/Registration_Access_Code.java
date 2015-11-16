package com.mallapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mallapp.Model.UserProfileModel;
import com.mallapp.utils.SendVerificationCode;
import com.mallapp.Model.UserProfile;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;

public class Registration_Access_Code extends Activity implements OnClickListener{


	private ImageButton back_screen ; Button continue_next;
	private TextView code_not_receive;
	private EditText code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_access_code);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		continue_next 	= (Button) findViewById(R.id.v_continue);
		code 			= (EditText) findViewById(R.id.verify_code);
		code_not_receive= (TextView) findViewById(R.id.code_not_receive_text);
		setCodeMessage();
		back_screen		= (ImageButton) findViewById(R.id.back_screen);
        back_screen.setOnClickListener(this);
		code_not_receive.setOnClickListener(this);
		continue_next.setOnClickListener(this);
		
		
		code.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.toString().length()==4){
					VerifyAccessCode(s.toString().trim());
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void setCodeMessage() {
		String code_text= code_not_receive.getText().toString();
		String htmlString=code_text+"<u> "+getResources().getString(R.string.access_code_country_5)+"</u>";
		code_not_receive.setText(Html.fromHtml(htmlString));
		//SpannableString content = new SpannableString(getResources().getString(R.string.access_code_country_5));
		//content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		//code_text= code_text+" "+content;
	}

	
	protected void startProfileActivity() {
		Intent intent = new Intent(Registration_Access_Code.this, RegistrationProfileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(intent);
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
	@Override
	public 	void onClick(View v) {
		
		if(v.getId() == code_not_receive.getId()){
			final UserProfileModel userProfile=	SharedPreferenceUserProfile.getUserCountry(getApplicationContext());
			new Thread() {
				@Override
				public void run() {
					SendVerificationCode.sendVerificationCode(getApplicationContext(), userProfile.getPhone(),
							userProfile.isPush_notification(), null,
							userProfile.getCountryCode());
				}
			}.start();

			
		}else if(v.getId() == continue_next.getId()){
			if (code.getText().toString().trim().length() > 0 ) {
				VerifyAccessCode(code.getText().toString().trim());
				
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.access_code_country_4), Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
				//if (Log.MODE_DEBUG)
				//	startProfileActivity();
			}
		}else if(v.getId() == back_screen.getId()){
			Intent intent= new Intent(Registration_Access_Code.this, PhoneRegistrationActivity.class);
			finish();
			startActivity(intent);
		}
	}

	
	private void VerifyAccessCode(String access_Code) {
		
		UserProfileModel userProfile=	SharedPreferenceUserProfile.getUserCountry(getApplicationContext());
		String phone_N = userProfile.getMobilePhone();
		
		boolean success= SendVerificationCode.verifyAccessCode(Registration_Access_Code.this, phone_N, access_Code);
		if(success)
			startProfileActivity();
		else
			;
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent= new Intent(Registration_Access_Code.this, PhoneRegistrationActivity.class);
		finish();
		startActivity(intent);
	}
	
	
}