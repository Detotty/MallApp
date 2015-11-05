package com.mallapp.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import net.appkraft.parallax.ParallaxScrollView;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelProfile;
import com.mallapp.Constants.SocialSharingConstants;
import com.mallapp.Model.UserLocationModel;
import com.mallapp.Model.UserProfile;
import com.mallapp.SharedPreferences.SharedPreferenceManager;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.imagecapture.ImageImportHelper;
import com.mallapp.imagecapture.Image_Scaling;
import com.mallapp.imagecapture.ScalingUtilities;
import com.mallapp.imagecapture.ScalingUtilities.ScalingLogic;
import com.mallapp.layouts.ParentDialog;
import com.mallapp.socialsharing.Facebook_Login;
import com.mallapp.socialsharing.SessionStore;
import com.mallapp.socialsharing.Util;
import com.mallapp.utils.SharedInstance;
import com.mallapp.utils.Utils;

public class RegistrationProfileActivity extends Activity implements OnClickListener{

	//private static final String TAG = CreateProfileActivity.class.getSimpleName();
	private ImageImportHelper mImageImportHelper;
	private ImageView profileImageView;
	private EditText nameEditText, emailEditText;
	private TextView DOBEditText, locationTextView;
	private Date dateOfBirthday;
	//private String profile_image_array;
	private Button continueButton, syncFbButton;

	private RadioGroup gender_group;
	private RadioButton genderRadioButton, male, female;
	String nameString, DOBString, locationString, emailString, genderRadioButtonText;

	private static String FB_APP_ID = SocialSharingConstants.FB_APP_ID;

	Facebook facebook = new Facebook(FB_APP_ID);

	Bitmap profile_image_bitmap = null;

	String profile_image_path= null;

	private String requestType;

	//TextView text_;
	private String profile_image_arr;
	private Button continue_next, sync_fb;
	private ParallaxScrollView parallax;
	
	String user_name, date_birth, locat, edu, gender_s;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_profile);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//		ActionBar actionBar = getActionBar();
//		actionBar.hide();
		SessionStore.restore(facebook, getApplicationContext());
		
//		 try {
//	            PackageInfo info = getPackageManager().getPackageInfo("com.mallapp.View", 
//	                    PackageManager.GET_SIGNATURES);
//	            for (Signature signature : info.signatures) {
//	                MessageDigest md = MessageDigest.getInstance("SHA");
//	                md.update(signature.toByteArray());
//	                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//	                }
//	        } catch (NameNotFoundException e) {
//
//	        } catch (NoSuchAlgorithmException e) {
//
//	        }
		//05-12 17:06:11.586: D/KeyHash:(15701): k/6DY49SZoyBXA7o/zbYuetdL78=

		
		
		init();
		mImageImportHelper 	= ImageImportHelper.getInstance(RegistrationProfileActivity.this);

	}

	private void init() {

		nameEditText = (EditText) findViewById(R.id.user_name);
		emailEditText = (EditText) findViewById(R.id.user_email);
		locationTextView = (TextView) findViewById(R.id.user_location);
		DOBEditText = (TextView) findViewById(R.id.user_dob);

		continueButton = (Button) findViewById(R.id.continue_save);
		syncFbButton = (Button) findViewById(R.id.synce_facebook);

		profileImageView= (ImageView) findViewById(R.id.profile_image_edit);

		male 			= (RadioButton) findViewById(R.id.male);
		female 			= (RadioButton) findViewById(R.id.female);
		gender_group 	= (RadioGroup) findViewById(R.id.radioGroup1);


//		Bitmap profile_bitmap 	= BitmapFactory.decodeResource(getResources(), R.drawable.parallax_avatar);
//		Image_Scaling.setRoundedImgeToImageView(getApplicationContext(), profileImageView, profile_bitmap);
//		profile_image_bitmap = profile_bitmap;

		DOBEditText.setOnClickListener(dobEditTextListener);
//		locationTextView.setOnClickListener(locationTextViewListener);
		profileImageView.setOnClickListener(profileImageListener);
//		syncFbButton.setOnClickListener(loginButtonListener);
		continueButton.setOnClickListener(continueButtonListener);
	}

	
	private void selectProfileImage(String dialogTitle) {
		final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
		AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationProfileActivity.this);
		builder.setTitle(dialogTitle);
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					dispatchTakePictureIntent(ImageImportHelper.ACTION_TAKE_PHOTO_IMAGEVIEW);

				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(intent, ImageImportHelper.ACTION_TAKE_PHOTO_FROM_GALLERY);
					
				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	
	// New function for captureImageIntent
	private void dispatchTakePictureIntent(int actionCode) {
		mImageImportHelper.dispatchTakeAndSavePictureIntent(this, actionCode);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.e("request code", ""+ requestCode);
		Log.e("result code", ""+ resultCode);
		
		if (requestCode == ImageImportHelper.ACTION_TAKE_PHOTO_IMAGEVIEW) {
			if (resultCode == RESULT_OK) {
				mImageImportHelper.handleCameraPhoto(profileImageView, false);
				profile_image_arr = SharedPreferenceManager.getString(getApplicationContext(), GlobelProfile.profile_image);
			}
		} else if (requestCode == ImageImportHelper.ACTION_TAKE_PHOTO_FROM_GALLERY) {
			if (resultCode == RESULT_OK) {
				try {
					Uri selectedImageUri = data.getData();
					String picturePath = getPath(selectedImageUri);
					profile_image_arr = picturePath;
					SharedPreferenceManager.saveString(RegistrationProfileActivity.this, GlobelProfile.profile_image, profile_image_arr);
					
					Bitmap bitmapSelectedImage = BitmapFactory.decodeFile(picturePath);
					if (bitmapSelectedImage == null) {
						selectProfileImage("Re-Select Picture");
						return;
					}
					
	                bitmapSelectedImage =Image_Scaling.getImageOrintation(bitmapSelectedImage, profile_image_arr); 
//	                		Bitmap.createBitmap(bitmapSelectedImage, 0, 0, bitmapSelectedImage.getWidth(), bitmapSelectedImage.getHeight(), matrix, true);
	                
//					Matrix matrix = new Matrix();
//					matrix.postRotate(90);
//					rotatedBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
//					
					setFlatImgeToImageView(profileImageView, bitmapSelectedImage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			facebook.authorizeCallback(requestCode, resultCode, data);
		}
	}

	
	private void setFlatImgeToImageView(ImageView profileImageView, Bitmap profile_bitmap) {
		int mDstWidth = getResources().getDimensionPixelSize(R.dimen.destination_width);
		int mDstHeight = getResources().getDimensionPixelSize(R.dimen.destination_height);
		profile_bitmap = ScalingUtilities.createScaledBitmap(profile_bitmap, mDstWidth, mDstHeight, ScalingLogic.CROP);
		Drawable d = new BitmapDrawable(getResources(), profile_bitmap);
		profileImageView.setImageDrawable(d);
	}

	
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private int day;
	private int month;
	private int year;

	
	void show_date() {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// Launch Date Picker Dialog
		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@SuppressLint({ "DefaultLocale", "SimpleDateFormat" })
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd , yyyy");

				Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
				System.out.println("Date : " + sdf.format(calendar.getTime()));
				System.out.println("Date : " + year + monthOfYear + dayOfMonth);

				// bady.setText(""+ sdf.format(calendar.getTime()));
				monthOfYear = monthOfYear + 1;

				// String dateS = String.format("%02d", monthOfYear);
				// String date_d = String.format("%02d", dayOfMonth);

			}
		}, year, month, day);
		dpd.show();
	}

	 
	protected void set_user_profile() {
		user_name= nameEditText.getText().toString();
		Log.e("", "user name fr peofile "+ user_name);
		UserProfile userProfile= new UserProfile(user_name, date_birth,edu, locat,gender_s );
		SharedPreferenceUserProfile.SaveUserProfile(userProfile, getApplicationContext());
	}
	
	
	/*private OnClickListener loginButtonListener = new OnClickListener() {
		public void onClick( View v ) {
			if( !facebook.isSessionValid() ) {
    			Toast.makeText(RegistrationProfileActivity.this, "Authorizing", Toast.LENGTH_SHORT).show();
    			facebook.authorize(RegistrationProfileActivity.this, new String[] { "" }, new LoginDialogListener());
    			}
			else {
				Toast.makeText(RegistrationProfileActivity.this, "Authorizing", Toast.LENGTH_SHORT).show();
    			//Toast.makeText( RegistrationProfileActivity.this, "Has valid session", Toast.LENGTH_SHORT).show();
    			try {
    				if (android.os.Build.VERSION.SDK_INT > 9) {
    					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    					StrictMode.setThreadPolicy(policy);
    				}
    				JSONObject json = Util.parseJson(facebook.request("me"));
	    			//String facebookID = json.getString("id");
    				*//*
    				 * {"id":"988220997868656","first_name":"Ashoo","birthday":"02\/29\/1988","timezone":5,
    				 * "location":{"id":"108104849224069","name":"Lahore, Pakistan"},"verified":true,
    				 * "name":"Ashoo Tariq","locale":"en_US",
    				 * "link":"https:\/\/www.facebook.com\/app_scoped_user_id\/988220997868656\/",
    				 * "last_name":"Tariq","gender":"female",
    				 * "education":[{"type":"High School","school":{"id":"174224049341609",
    				 * "name":"University of the Punjab"}},{"type":"High School",
    				 * "year":{"id":"142963519060927","name":"2010"},
    				 * "with":[{"id":"953654231333448","name":"Madiha Gohar"}],
    				 * "school":{"id":"125986397393","name":"Queen Mary School\/College Lahore"}},
    				 * {"type":"High School","year":{"id":"143018465715205","name":"2000"},
    				 * "school":{"id":"108090135891387","name":"govt girls high school"}},
    				 * {"type":"High School","year":{"id":"143018465715205","name":"2000"},
    				 * "school":{"id":"115538921793445","name":"Garner High"}},
    				 * {"type":"College","school":{"id":"112063712139822","name":"University of the Punjab"}},
    				 * {"type":"College","year":{"id":"142963519060927","name":"2010"},
    				 * "school":{"id":"163338127139321","name":"Punjab University College of Information Technology"}},
    				 * {"concentration":[{"id":"104076956295773","name":"Computer Science"},
    				 * {"id":"186739741356361","name":"Computer Information Technology"},
    				 * {"id":"106168626080858","name":"Software Engineering"}],
    				 * "year":{"id":"142963519060927","name":"2010"},
    				 * "with":[{"id":"10203931101540617","name":"Reen Sami"}],
    				 * "school":{"id":"107853569236131","name":"Punjab University College of Information Technology"},
    				 * "type":"Graduate School"}],"updated_time":"2015-06-15T05:28:18+0000"}

    				 *//*
    				Log.e("", "JSON: ....."+ json.toString());
	    			String firstName = json.getString("first_name");
	    			String lastName = json.getString("last_name");

					nameEditText.setText(""+firstName + " " + lastName );
	    			//Toast.makeText(RegistrationProfileActivity.this, "You already have a valid session, " + firstName + " " + lastName + ". No need to re-authorize.", Toast.LENGTH_SHORT).show();
	    			}
    			catch( Exception error ) {
    				error.printStackTrace();
    				Toast.makeText( RegistrationProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
    			}
    			catch( FacebookError error ) {
    				error.printStackTrace();
    				Toast.makeText( RegistrationProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
    			}
			}
		}
	};*/
    
    
	/*public final class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			try {
				//The user has logged in, so now you can query and use their Facebook info
				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				JSONObject json = Util.parseJson(facebook.request("me"));
				Log.e("", "JSON: ....."+ json.toString());
				//String facebookID = json.getString("id");
				String firstName = json.getString("first_name");
				String lastName = json.getString("last_name");
				
				name.setText(""+firstName + " " + lastName );
				
				//Toast.makeText( RegistrationProfileActivity.this, "Thank you for Logging In, " +facebookID+"     ....   "+ firstName + " " + lastName + "!", Toast.LENGTH_SHORT).show();
				SessionStore.save(facebook, RegistrationProfileActivity.this);
			}
			catch( Exception error ) {
				error.printStackTrace();
				Toast.makeText( RegistrationProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
			}
			catch( FacebookError error ) {
				error.printStackTrace();
				Toast.makeText( RegistrationProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
			}
		}
		
		public void onFacebookError(FacebookError error) {
			error.printStackTrace();
			Toast.makeText( RegistrationProfileActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		}
		
		public void onError(DialogError error) {
			error.printStackTrace();
			Toast.makeText( RegistrationProfileActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		}
		
		public void onCancel() {
			Toast.makeText( RegistrationProfileActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		}
	}*/

	private OnClickListener profileImageListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			selectProfileImage("Add Photo!");
		}
	};

	private OnClickListener continueButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (nameEditText.getText().toString().trim().length() <= 0) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_error_name_message), Toast.LENGTH_LONG).show();
				return;
			}else if (emailEditText.getText().toString() == null || emailEditText.getText().length() <= 0) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_error_email_message), Toast.LENGTH_LONG).show();
				return;
			}else if(!Utils.isValidEmailAddress(emailEditText.getText().toString())){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_error_email_validity_message), Toast.LENGTH_LONG).show();
				return;
			}
			/**
			 * save user nameEditText in shared prefernece
			 */
			saveUserProfile();
		}
	};

	/*private OnClickListener locationTextViewListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			select_location();
		}
	};*/

	private OnClickListener dobEditTextListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			show_date();
		}
	};
    
	
	@Override
	public void onClick(View v) {
		
		if(profileImageView.getId()== v.getId()){
			selectProfileImage("Add Photo!");
//		}else if(sync_fb.getId()== v.getId()){
//			Facebook_Login fb_profile= new Facebook_Login(getApplicationContext(), this, name, text_, false);
//			fb_profile.loginToFacebook();
//			
		}else if(continue_next.getId()== v.getId()){
			if (nameEditText.getText().toString().trim().length() <= 0) {
				Toast.makeText(getApplicationContext(), "Please enter name.", Toast.LENGTH_LONG).show();
				return;
			}
//			else if (profile_image_arr == null || profile_image_arr.length() <= 0) {
//				Toast.makeText(getApplicationContext(), "Please Select Profile Image.", Toast.LENGTH_LONG).show();
//				return;
//			}
			
			/**
			 * save user name in shared prefernece
			 */
			user_name= nameEditText.getText().toString();
			set_user_profile();
			
			/**
			 *  Start Endorsment dashboard activity
			 */
			Toast.makeText(getApplicationContext(), "Registration Successfully!", Toast.LENGTH_LONG).show();
			//Intent intent = new Intent(CreateProfileActivity.this, Endorsment.class);
			Intent intent 	= new Intent(RegistrationProfileActivity.this, Select_Favourite_Center.class);
			//Intent tabIntent 	= new Intent(CreateProfileActivity.this, Endorsment.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}

	protected void saveUserProfile() {

		nameString = nameEditText.getText().toString();
		emailString = emailEditText.getText().toString();
		locationString = locationTextView.getText().toString();

		DOBString = DOBEditText.getText().toString();
		int selectedId = gender_group.getCheckedRadioButtonId();

		genderRadioButton = (RadioButton) findViewById(selectedId);
		genderRadioButtonText = genderRadioButton.getText().toString();

		UserProfile userProfile = null;

		if(SharedInstance.getInstance().getSharedHashMap().containsKey(AppConstants.PROFILE_DATA)) {
			userProfile = (UserProfile) SharedInstance.getInstance().getSharedHashMap().get(AppConstants.PROFILE_DATA);
		}

		if(userProfile == null)
			userProfile = new UserProfile();

		userProfile.setName(nameString);
		userProfile.setEmail(emailString);
		userProfile.setDefaultLocationName(locationString);
		userProfile.setDeviceType("android");

		if(genderRadioButtonText.trim().startsWith("M")|| genderRadioButtonText.trim().startsWith("m"))
			userProfile.setGender("Male");
		else
			userProfile.setGender("Female");

		if(DOBString != null && DOBString.length()>0){
			long unixDOB = Utils.convertToUnixDate(DOBString, "MMM dd, yyyy");
			userProfile.setUnixDob(unixDOB);
//				Date date 			= null;
//				//date = GetCurrentDate.StringToDate("MMM dd , yyyy", DOBString);
//
//				SimpleDateFormat d_format = new SimpleDateFormat("MMM dd , yyyy", Locale.ENGLISH);
//				//DateFormat d_format = new SimpleDateFormat(format,  Locale.ENGLISH);
//
//				try {
//					date = d_format.parse(DOBString);
//					//d_format.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}

//				if(date!= null){
//
//					String date_as_encoded = GetCurrentDate.DateToString("yyyy-MM-dd KK:mm:ss a Z", date);
//
//					if(date_as_encoded !=null){
//
//						date = GetCurrentDate.StringToDate("yyyy-MM-dd KK:mm:ss a Z", date_as_encoded);
//						date_as_encoded= GetCurrentDate.unixDateConversion(date);
//						userProfile.setDob(DOBString);
//
//						Log.e("", "UTC date conversion "+ date_as_encoded);
//						Log.e("", "new Date() "+ new Date());
//						Log.e("", "DOBString "+ DOBString);
//						Log.e("", "DOBString "+ date);
//					}
//				}
		}

		if(profile_image_bitmap != null){
			//String user_profile_image = encodeTobase64(profile_image_bitmap);
			userProfile.setImageBitmap(profile_image_bitmap);
		}
		/*requestType = RequestType.UPDATE_USER_PROFILE;

		controller.updateUserProfile(userProfile,this);*/

	}

//	protected void select_location() {
//
//		location_dialog = new ParentDialog(RegistrationProfileActivity.this);
//		location_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		location_dialog.setContentView(R.layout.add_location);
//		location_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//
//		Button confirm = (Button) location_dialog.findViewById(R.id.cancel);
//		Button done = (Button) location_dialog.findViewById(R.id.done);
//
//		UserLocationModel userLocationModel = null;
//
//		if(SharedInstance.getInstance().getSharedHashMap().containsKey(AppConstants.USER_LOCATION)) {
//			userLocationModel = (UserLocationModel) SharedInstance.getInstance().getSharedHashMap().get(AppConstants.USER_LOCATION);
//		}
//
//		final PlacesAutoCompleteAdapter adapter1	= new PlacesAutoCompleteAdapter(this,R.id.item_code_list,null,userLocationModel);
//		AutoCompleteTextView autoCompView	= (AutoCompleteTextView) location_dialog.findViewById(R.id.autoComplete);
//		//autoCompView.setAdapter(adapter1);
//
//		//autoCompView.setOnItemClickListener(locationItemClickListener);
//
//		listView	= (ListView) location_dialog.findViewById(R.id.search_list);
//
//		autoCompView.addTextChangedListener(new MyTextWatcher(adapter1));
//
//		listView.setAdapter(adapter1);
//
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				PlaceAutocompleteModel item = (PlaceAutocompleteModel) parent.getItemAtPosition(position);
//				locationTextView.setText("" + item.getDescription());
//				location_dialog.dismiss();
//			}
//		});
//
//
//
//		confirm.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				location_dialog.dismiss();
//			}
//		});
//
//
//		done.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				location_dialog.dismiss();
//				//set_tag_view();
//				//add_selected_tags.removeAllViews();
//				//add_selected_tags.addView(linear);
//			}
//		});
//
//		location_dialog.show();
//	}


	
}
