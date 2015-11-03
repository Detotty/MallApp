package com.mallapp.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import net.appkraft.parallax.ParallaxScrollView;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.ActionBar;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.mallapp.Constants.GlobelProfile;
import com.mallapp.Constants.SocialSharingConstants;
import com.mallapp.Model.UserProfile;
import com.mallapp.SharedPreferences.SharedPreferenceManager;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.imagecapture.ImageImportHelper;
import com.mallapp.imagecapture.Image_Scaling;
import com.mallapp.imagecapture.ScalingUtilities;
import com.mallapp.imagecapture.ScalingUtilities.ScalingLogic;
import com.mallapp.socialsharing.SessionStore;
import com.mallapp.socialsharing.Util;

public class Registration_Profile extends Activity implements OnClickListener{

	//private static final String TAG = CreateProfileActivity.class.getSimpleName();
	private ImageImportHelper mImageImportHelper;
	private ImageView profileImageView;
	private EditText name;
	//TextView text_;
	private String profile_image_arr;
	private Button continue_next, sync_fb;
	private ParallaxScrollView parallax;
	
	String user_name, date_birth, locat, edu, gender_s;

	private static String FB_APP_ID= SocialSharingConstants.FB_APP_ID;
	
	Facebook facebook = new Facebook(FB_APP_ID);
	
	
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

		
		
		
		mImageImportHelper 	= ImageImportHelper.getInstance(Registration_Profile.this);
		name 				= (EditText) findViewById(R.id.name);
		continue_next 		= (Button) findViewById(R.id.continue_save);
		sync_fb 			= (Button) findViewById(R.id.synce_facebook);
		parallax 			= (ParallaxScrollView) findViewById(R.id.scrollView1);
		profileImageView 	= (ImageView) findViewById(R.id.set_profile_image);
		
		parallax.setImageViewToParallax(profileImageView);
		profileImageView.setOnClickListener(this);
		sync_fb.setOnClickListener(loginButtonListener);
		continue_next.setOnClickListener(this);
	}

	
	private void selectProfileImage(String dialogTitle) {
		final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
		AlertDialog.Builder builder = new AlertDialog.Builder(Registration_Profile.this);
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
					SharedPreferenceManager.saveString(Registration_Profile.this, GlobelProfile.profile_image, profile_image_arr);
					
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
		user_name= name.getText().toString();
		Log.e("", "user name fr peofile "+ user_name);
		UserProfile userProfile= new UserProfile(user_name, date_birth,edu, locat,gender_s );
		SharedPreferenceUserProfile.SaveUserProfile(userProfile, getApplicationContext());
	}
	
	
	private OnClickListener loginButtonListener = new OnClickListener() {
		public void onClick( View v ) {
			if( !facebook.isSessionValid() ) {
    			Toast.makeText(Registration_Profile.this, "Authorizing", Toast.LENGTH_SHORT).show();
    			facebook.authorize(Registration_Profile.this, new String[] { "" }, new LoginDialogListener());
    			}
			else {
				Toast.makeText(Registration_Profile.this, "Authorizing", Toast.LENGTH_SHORT).show();
    			//Toast.makeText( Registration_Profile.this, "Has valid session", Toast.LENGTH_SHORT).show();
    			try {
    				if (android.os.Build.VERSION.SDK_INT > 9) {
    					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    					StrictMode.setThreadPolicy(policy);
    				}
    				JSONObject json = Util.parseJson(facebook.request("me"));
	    			//String facebookID = json.getString("id");
    				/*
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

    				 */
    				Log.e("", "JSON: ....."+ json.toString());
	    			String firstName = json.getString("first_name");
	    			String lastName = json.getString("last_name");
	    			
	    			name.setText(""+firstName + " " + lastName );
	    			//Toast.makeText(Registration_Profile.this, "You already have a valid session, " + firstName + " " + lastName + ". No need to re-authorize.", Toast.LENGTH_SHORT).show();
	    			}
    			catch( Exception error ) {
    				error.printStackTrace();
    				Toast.makeText( Registration_Profile.this, error.toString(), Toast.LENGTH_SHORT).show();
    			}
    			catch( FacebookError error ) {
    				error.printStackTrace();
    				Toast.makeText( Registration_Profile.this, error.toString(), Toast.LENGTH_SHORT).show();
    			}
			}
		}
	};
    
    
	public final class LoginDialogListener implements DialogListener {
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
				
				//Toast.makeText( Registration_Profile.this, "Thank you for Logging In, " +facebookID+"     ....   "+ firstName + " " + lastName + "!", Toast.LENGTH_SHORT).show();
				SessionStore.save(facebook, Registration_Profile.this);
			}
			catch( Exception error ) {
				error.printStackTrace();
				Toast.makeText( Registration_Profile.this, error.toString(), Toast.LENGTH_SHORT).show();
			}
			catch( FacebookError error ) {
				error.printStackTrace();
				Toast.makeText( Registration_Profile.this, error.toString(), Toast.LENGTH_SHORT).show();
			}
		}
		
		public void onFacebookError(FacebookError error) {
			error.printStackTrace();
			Toast.makeText( Registration_Profile.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		}
		
		public void onError(DialogError error) {
			error.printStackTrace();
			Toast.makeText( Registration_Profile.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		}
		
		public void onCancel() {
			Toast.makeText( Registration_Profile.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		}
	}
    
	
	@Override
	public void onClick(View v) {
		
		if(profileImageView.getId()== v.getId()){
			selectProfileImage("Add Photo!");
//		}else if(sync_fb.getId()== v.getId()){
//			Facebook_Login fb_profile= new Facebook_Login(getApplicationContext(), this, name, text_, false);
//			fb_profile.loginToFacebook();
//			
		}else if(continue_next.getId()== v.getId()){
			if (name.getText().toString().trim().length() <= 0) {
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
			user_name= name.getText().toString();
			set_user_profile();
			
			/**
			 *  Start Endorsment dashboard activity
			 */
			Toast.makeText(getApplicationContext(), "Registration Successfully!", Toast.LENGTH_LONG).show();
			//Intent intent = new Intent(CreateProfileActivity.this, Endorsment.class);
			Intent intent 	= new Intent(Registration_Profile.this, Select_Favourite_Center.class);
			//Intent tabIntent 	= new Intent(CreateProfileActivity.this, Endorsment.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}


	
}
