package com.mallapp.View;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.SocialSharingConstants;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.utils.RegistrationController;
import com.mallapp.Model.FacebookProfileModel;
import com.mallapp.utils.SharedInstance;
import com.mallapp.utils.Utils;

import org.json.JSONObject;

import java.util.Arrays;

public class RegistrationActivity extends Activity {

    public static final int REQUEST_CODE_FOR_COUNTRY = 1;
    private static final String TAG = RegistrationActivity.class.getSimpleName();


    private Button registerPhoneButton;
    private Button registerFbButton;
    private LoginButton loginButton;
    CallbackManager callbackManager;
    private Resources res;
    private static String FB_APP_ID = SocialSharingConstants.FB_APP_ID;

//    private Facebook facebook = new Facebook(FB_APP_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /*ActionBar actionBar = getActionBar();

        actionBar.hide();*/

        //SharedPreferences prefs = getSharedPreferences("PROFILE", Context.MODE_PRIVATE);



        res = getResources();
        init();
        //getDefaultLocation();

//        String token = SharedPreferenceUserProfile.getUserToken(this);

//        if( token == null )

            Utils.getDefaultLocation(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
// App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        setFbData(object);
                                        Log.v("LoginActivity", response.toString());
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(RegistrationActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(RegistrationActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        registerFbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(RegistrationActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

    }

    private void init() {



        registerPhoneButton			= (Button) findViewById(R.id.btn_register_phone);
        registerFbButton		= (Button) findViewById(R.id.btn_register_fb);

        registerPhoneButton.setOnClickListener(registerPhoneButtonListener);
//        registerFbButton.setOnClickListener(registerFbButtonListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.RegistrationActivity, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK &&  requestCode == AppConstants.OPEN_LOCATION_SETTING_RESULT_CODE){
            //ProgressDialog dialog = ProgressDialog.show(this,"","Please wait while we get your location");

            Log.d("result", "on result received");
            Utils.getDefaultLocation(this);
            //dialog.dismiss();

        }
        else if(data != null ){

//            facebook.authorizeCallback(requestCode, resultCode, data);

        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }





    protected void onDestroy() {
        super.onDestroy();
    }

    public View.OnClickListener registerPhoneButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(RegistrationActivity.this, PhoneRegistrationActivity.class);
            startActivity(intent);

        }
    };

    /*public View.OnClickListener registerFbButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!facebook.isSessionValid()) {
                Toast.makeText(RegistrationActivity.this, "Logining with facebook", Toast.LENGTH_SHORT).show();
                facebook.authorize(RegistrationActivity.this, new String[]{"public_profile", "email", "user_birthday"}, new LoginDialogListener());
            } else {
                Toast.makeText(RegistrationActivity.this, "Logining with facebook", Toast.LENGTH_SHORT).show();
                //Toast.makeText( RegistrationProfileActivity.this, "Has valid session", Toast.LENGTH_SHORT).show();
                try {
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }

                    JSONObject json = Util.parseJson(facebook.request("me"));
                    Log.e("", "JSON: ....." + json.toString());

                    setFbData(json);

                    //Toast.makeText(RegistrationProfileActivity.this, "You already have a valid session, " + firstName + " " + lastName + ". No need to re-authorize.", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    error.printStackTrace();
                    Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                } catch (FacebookError error) {
                    error.printStackTrace();
                    Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    public final class LoginDialogListener implements Facebook.DialogListener {
        public void onComplete(Bundle values) {
            try {
                //The user has logged in, so now you can query and use their Facebook info
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                JSONObject json = Util.parseJson(facebook.request("me"));
                Log.e("", "JSON: ....." + json.toString());

                setFbData(json);

                SessionStore.save(facebook, RegistrationActivity.this);


            }
            catch( Exception error ) {
                error.printStackTrace();
                Toast.makeText( RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
            catch( FacebookError error ) {
                error.printStackTrace();
                Toast.makeText( RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        public void onFacebookError(FacebookError error) {
            error.printStackTrace();
            Toast.makeText( RegistrationActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
        }

        public void onError(DialogError error) {
            error.printStackTrace();
            Toast.makeText( RegistrationActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
        }

        public void onCancel() {
            Toast.makeText( RegistrationActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
        }
    }*/

    private void setFbData(JSONObject jsonObject){

        try {

            FacebookProfileModel facebookProfileModel = null;

            Gson gson = new Gson();

            facebookProfileModel = gson.fromJson(jsonObject.toString(), FacebookProfileModel.class);

//            String id = "" + jsonObject.getString("id");
//            String gender = jsonObject.getString("gender");
//            String name = jsonObject.getString("name");
//
//            String email = jsonObject.getString("email");
//            String dob = null;
//
//            if(jsonObject.has("birthday"))
//               dob = jsonObject.getString("birthday");


//            userProfile.setFacebookId(id);
//            userProfile.setName(name);
//            userProfile.setGender(gender);
//            userProfile.setFacebookProfile(true);
//
//            if(dob!= null)
//                userProfile.setDate_birth(dob);
//
//            userProfile.setEmail(email);
//
//            SharedPreferenceUserProfile.SaveUserProfile(userProfile,this);

            SharedInstance.getInstance().getSharedHashMap().put(AppConstants.FACEBOOK_DATA,facebookProfileModel);
            DataHandler.updatePreferences(AppConstants.FACEBOOK_DATA, facebookProfileModel);
            RegistrationController controller = new RegistrationController(this);

            controller.registerWithFacebook(facebookProfileModel);


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void showDashboard(UserProfileModel userProfile){

        SharedInstance.getInstance().getSharedHashMap().put(AppConstants.PROFILE_DATA, userProfile);
        DataHandler.updatePreferences(AppConstants.PROFILE_DATA, userProfile);
        //SharedPreferenceUserProfile.SaveUserProfile(userProfile, this);
        //Toast.makeText(getApplicationContext(), "Registration Successfully!", Toast.LENGTH_LONG).show();

        Intent intent 	= new Intent(RegistrationActivity.this, RegistrationProfileActivity.class);
        finish();
        startActivity(intent);

    }

}
