package com.mallapp.View;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Location.Gps.Lattitude_Logitude;
import com.mallapp.Controllers.SendVerificationCode;
import com.mallapp.utils.AlertMessages;

public class PhoneRegistrationActivity extends Activity implements OnClickListener{

	Lattitude_Logitude gps;
	double latitude;
    double longitude;
	public static final int REQUEST_CODE_FOR_COUNTRY = 1;
	private static final String TAG = PhoneRegistrationActivity.class.getSimpleName();
    String countryName, CountryID="", CountryZipCode;
    EditText  phone_no;
    TextView country;
    Button continue_next;
    Resources res;
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_phone_activity);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		res = getResources();
		init();
//        getDefaultLocation();
	}
	
	private void init() {
		country	= (TextView) findViewById(R.id.country_name);
		phone_no= (EditText) findViewById(R.id.phone_no);
		country.setText("");
		phone_no.setText("");
		continue_next=  (Button) findViewById(R.id.r_continue);
		continue_next.setOnClickListener(this);
		country.setOnClickListener(this);
	}

	/**
	 * verify phone no before send access code
	 */
	
	protected void phoneNoVerification(final String phone_no) {
	
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(res.getString(R.string.register_country_7));
		alertDialogBuilder.setMessage(res.getString(R.string.register_country_8)+phone_no+"\n"+res.getString(R.string.register_country_9));
		
		alertDialogBuilder.setPositiveButton(res.getString(R.string.yes_), 	new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				sendSMSVerificationCode(phone_no);
			}
		});
		alertDialogBuilder.setNegativeButton(res.getString(R.string.edit_), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Intent negativeActivity = new Intent(getApplicationContext(),com.example.alertdialog.NegativeActivity.class);
//      	    startActivity(negativeActivity);
			}
		});
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	
	protected void sendSMSVerificationCode(final String phone_N) {
	
		try {
			boolean push_notify= false;
			final CheckBox checkBox = (CheckBox) findViewById(R.id.push_notification);
			if (checkBox.isChecked()) {
				push_notify= true;
			}
			final boolean finalPush_notify = push_notify;
			new Thread() {
				@Override
				public void run() {
					SendVerificationCode.sendVerificationCode(PhoneRegistrationActivity.this, phone_N, finalPush_notify, countryName, CountryZipCode);
				}
			}.start();
			Intent intent= new Intent(PhoneRegistrationActivity.this, Registration_Access_Code.class);
			finish();
     		startActivity(intent);
     		
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),res.getString(R.string.register_country_10),Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	
	private void pushNotificationVerification() {
		boolean push_notify= false;
		final CheckBox checkBox = (CheckBox) findViewById(R.id.push_notification);
		if (checkBox.isChecked())
			push_notify= true;
		
		if(push_notify){
			String phone_N= CountryZipCode + phone_no.getText().toString().trim();
			phoneNoVerification(phone_N);
		}else{
			AlertMessages.show_alert(PhoneRegistrationActivity.this, ""+R.string.app_name1, "Please accept, recieving push notifications/SMS", "OK");
		}
	}
	
	private void open_setting(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PhoneRegistrationActivity.this);
		alertDialogBuilder.setTitle("Location services disabled");
		// set dialog message
		alertDialogBuilder.setMessage("Mall App needs access to your location. Please turn on location access.")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
				//Register.this.finish();
			}
		})
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});
		
		alertDialogBuilder.show();
	}
	
	
	@SuppressWarnings("unused")
	private void wifi_setting(){
	
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PhoneRegistrationActivity.this);
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			alertDialogBuilder = new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Panel);
        else
        	alertDialogBuilder = new AlertDialog.Builder(this);
		
		// set title
		alertDialogBuilder.setTitle("Location services disabled");
		// set dialog message
		alertDialogBuilder
			.setMessage("Endorsement needs access to your location. Please turn on location access.")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
					//Register.this.finish();
				}
			})
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});

			// create alert dialog
			//AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialogBuilder.show();

			// show it
			//alertDialog.show();
			
	}
	
	
	private void getDefaultLocation() {
		
		gps= new Lattitude_Logitude(getApplicationContext());
		// check if GPS enabled      
		Log.e(" condition..", " location .."+gps.canGetLocation());
		
		if(gps.canGetLocation()){
			
        	Log.e(" condition..", " location active .."+gps.canGetLocation());
        	
        	latitude 	= gps.getLatitude();
        	longitude 	= gps.getLongitude();
        	countryName	= gps.getCountryName();
        	CountryID	= gps.getCountryCode();
        	
        	Log.e(" condition..", " latitude .."+latitude);
        	Log.e(" condition..", "country_code ..."+CountryID);
        	
        	try {
        		if(CountryID!= null && CountryID.length()>0 &&  !CountryID.equals("null")){
        			String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        			for(int i=0;i<rl.length;i++){
        				String[] g=rl[i].split(",");
        				if(g[1].trim().equals(CountryID.trim())){
        					CountryZipCode=g[0];
        					break;  
        				}
        			}
        			country.setText(countryName+"(+"+CountryZipCode+")");
            		phone_no.requestFocus();
            		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        		}else{
        			
        		}
        		
        		
        		//InputMethodManager inputMethodManager =  (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        		//inputMethodManager.toggleSoftInputFromWindow(phone_no.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
//                Handler mHandler= new Handler();
//                
//                mHandler.post(new Runnable() {
//                	public void run() {
//                		
//                		phone_no.requestFocus();
//                	}
//                });
                
                
                
        	}catch (Exception e) {
        	//	Log.e(" condition..", "country_code ..."+CountryID);
        		e.printStackTrace();
        		country.setText("");
        		//getDefaultLocation();
        		//Toast.makeText(getApplicationContext(), "Check Network Connection ", Toast.LENGTH_LONG).show();
        	}
            
        }else{
        	//Log.e(" else condition..", "call pop up for location .."+ gps.canGetLocation());
        	open_setting();
        }
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Log.e(TAG, ""+requestCode+"............"+ resultCode);
		if(resultCode == requestCode && requestCode== REQUEST_CODE_FOR_COUNTRY){
	    	//Log.e(TAG, "back: REQUEST_CODE_FOR_ENDORSEMENT");
	    	String resultString = data.getExtras().getString("result", "#");
	    	country.setText(""+resultString);
	    	int index= resultString.indexOf("(");
	    	countryName= resultString.substring(0, index);
	    	CountryZipCode= resultString.substring(index+2, resultString.length()-1);
	    	Log.e(TAG, ""+resultString);
	    	phone_no.requestFocus();
	    	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	    }
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	@Override
	protected void onResume() {
		
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getSimState() != TelephonyManager.SIM_STATE_ABSENT){
		  // The phone has SIM card
			GetCountryZipCode();
			//Log.e(TAG, "on resume call from mobile network ...."+ country.getText().toString());
		} else {
		  // No SIM card on the phone
//			getDefaultLocation();
		//	Log.e(TAG, "on resume call from web locaton...."+ country.getText().toString());
		}
		super.onResume();
	}

	
	
	
	@SuppressWarnings("unused")
	private void getMobileNetworkLocation() {
		TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String networkOperator = tel.getNetworkOperator();
		int mcc = 0, mnc = 0;
		if (networkOperator != null) {
			mcc = Integer.parseInt(networkOperator.substring(0, 3));
			mnc = Integer.parseInt(networkOperator.substring(3));
		}
		Log.w("MCC : ",  "MCC = "+ mcc+"\n"+ "MNC : "+mnc);
	}
	
	
	public String GetCountryZipCode() {
	    
		String CountryID="";
	    String CountryZipCode="";
	    
	    TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
	    CountryID= manager.getSimCountryIso().toUpperCase(Locale.ENGLISH);
	    
	    if(CountryID!=null && CountryID.length()>0)
	    {
	    	setCountryID(CountryID);
	    	String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
		    
	    	for(int i=0;i<rl.length;i++)
	    	{
		    	String[] g=rl[i].split(",");
		    	if(g[1].trim().equals(CountryID.trim()))
		    	{
		    		CountryZipCode=g[0];
		    		break;  
		    	}
		    }
	    	
	    	if(CountryZipCode!=null && CountryZipCode.length()>0){
	    		setCountryZipCode(CountryZipCode);
	    		getCountryName_();
	    	}
		}
	    
	    Log.d("MCC : ",  "CountryID = "+ CountryID);
	    Log.d("MCC : ",  "CountryZipCode = "+ CountryZipCode);
	    return CountryZipCode;
	}
	


	private void getCountryName_() {
		Locale obj = new Locale("", CountryID);
		String country_name	=	"";
		country_name		= 	obj.getDisplayCountry();
		if(country_name!=null && country_name.length()>0){
			setCountryName(country_name);
			setCountry();
		}
		System.out.println("Country Code = " + obj.getCountry() 
			+ ", Country Name = " + obj.getDisplayCountry());
	}
	
	
	
	

	private void setCountry() {
		country.setText(getCountryName()+"(+"+getCountryZipCode()+")");
		phone_no.requestFocus();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	@Override
	protected void onPause() {
		//Log.e(TAG, "on pause call  afetre return from setting");
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()== country.getId()){
			Intent intent= new Intent(PhoneRegistrationActivity.this, Registration_Select_Country.class);
			startActivityForResult(intent , REQUEST_CODE_FOR_COUNTRY);

		}else if(v.getId() == continue_next.getId()){
			if(country.getText().toString().length()>0 ){
    			if(phone_no.getText().toString().length()>0 ){
    				pushNotificationVerification();
    			}else{
    				Toast.makeText(getApplicationContext(), "Enter Phone number ", Toast.LENGTH_LONG).show();
    			}
			}else{
    			Toast.makeText(getApplicationContext(), "Select country first", Toast.LENGTH_LONG).show();
    		}
		}
	}

	
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	
	public String getCountryID() {
		return CountryID;
	}

	public void setCountryID(String countryID) {
		CountryID = countryID;
	}

	
	public String getCountryZipCode() {
		return CountryZipCode;
	}

	public void setCountryZipCode(String countryZipCode) {
		CountryZipCode = countryZipCode;
	}

}