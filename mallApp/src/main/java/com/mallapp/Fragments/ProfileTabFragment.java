package com.mallapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.UserProfile;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.FullScreenImage;
import com.mallapp.View.FullscreenActivity;
import com.mallapp.View.LanguageChangeActivity;
import com.mallapp.View.NotificationActivity;
import com.mallapp.View.R;
import com.mallapp.View.RegistrationProfileActivity;
import com.mallapp.View.Select_Favourite_Center;
import com.mallapp.View.Select_Interest;
import com.mallapp.View.SplashScreen;
import com.mallapp.imagecapture.Image_Scaling;
import com.mallapp.utils.BuildConfig;
import com.mallapp.utils.RegistrationController;
import com.mallapp.utils.SocialUtils;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import me.drakeet.materialdialog.MaterialDialog;


public class ProfileTabFragment extends Fragment {


    View viewHome;
    UserProfileModel user_profile;
    ImageView user_ImageView;
    ImageButton back, fav;
    TextView heading, tv_Build;
    //    String link = StaticLiterls.link;
    String[] list_items, settings_items;
    public static boolean isUpdate = false;
    MaterialDialog mMaterialDialog;
    VolleyNetworkUtil volleyNetworkUtil;
    RegistrationController registrationController;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        viewHome = inflater.inflate(R.layout.fragment_parent_tab_profile, container, false);
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        /*if (link.equals("")) {
            SignUpWebservice.getPlayStoreLink();
            link = StaticLiterls.link;
        }*/
        initUI();
        return viewHome;
    }

    @Override
    public void onResume() {
        super.onResume();
//        user_profile = SharedPreferenceUserProfile.getUserProfile(getActivity().getApplicationContext());
    }

    public void initUI() {
        volleyNetworkUtil = new VolleyNetworkUtil(getActivity());
        registrationController = new RegistrationController(getActivity());
        profileListview();
        heading = (TextView) viewHome.findViewById(R.id.offer_title);
        tv_Build = (TextView) viewHome.findViewById(R.id.textView10);
        user_ImageView = (ImageView) viewHome.findViewById(R.id.user_image);
        back = (ImageButton) viewHome.findViewById(R.id.back);
        fav = (ImageButton) viewHome.findViewById(R.id.fav_offer);
        ImageButton imageButton_back = (ImageButton) viewHome.findViewById(R.id.back);
        Button camera = (Button) viewHome.findViewById(R.id.button_camera);
        ImageView facebook_btn = (ImageView) viewHome.findViewById(R.id.facebook);
        ImageView twitter = (ImageView) viewHome.findViewById(R.id.twitter);
        ImageView sms = (ImageView) viewHome.findViewById(R.id.sms);
        ImageView email = (ImageView) viewHome.findViewById(R.id.email);
        back.setVisibility(View.GONE);
        fav.setVisibility(View.GONE);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        tv_Build.setText(getResources().getString(R.string.build_verion)+" "+versionName+" "+versionCode);
        user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);

        if (user_profile.getImageBase64String() != null) {
            byte[] decodedString = Base64.decode(user_profile.getImageBase64String(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            user_ImageView.setImageBitmap(decodedByte);
        }
        heading.setText(user_profile.getFullName());


        facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("The Mall App")
                        .setContentDescription(
                                getResources().getString(R.string.mall_app_invite_msg))
                        .setContentUrl(Uri.parse("http://mallappbackend.zap-itsolutions.com/"))
                        .build();

                shareDialog.show(linkContent);
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtils.sendSms(getActivity(), getResources().getString(R.string.mall_app_invite_msg) + "\n");
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtils.sendEmail(getActivity(), getResources().getString(R.string.mall_app_invite_msg) + "\n");
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtils.postToTwitter(getActivity(), getResources().getString(R.string.mall_app_invite_msg) + "\n");
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfileImage("Add Photo!");
            }
        });
        user_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FullscreenActivity.class);
                startActivity(i);
            }
        });
       /* facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticLiterls.postToFacebook(getActivity(), getResources().getString(R.string.ce_share_msg) + "\n" + link);
            }
        });

        */
        /*imageButton_back.setVisibility(View.GONE);
        String user_image = user_profile.getPicture_path();
        String user_name = user_profile.getName();
        header.setText(user_name);
        try {
            byte[] imageByte = Base64.decode(user_image, Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            user_ImageView.setImageBitmap(bm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Bitmap bmp = ((BitmapDrawable) user_ImageView.getDrawable()).getBitmap();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectProfileImage(getString(R.string.add_photo));
            }
        });
*/
    }

    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }

    private void profileListview() {
        list_items = getResources().getStringArray(R.array.profile_items);
        settings_items = getResources().getStringArray(R.array.settings_items);
        LinearLayout profile_view = (LinearLayout) viewHome.findViewById(R.id.listView_profile_activities);
        LinearLayout seetings_view = (LinearLayout) viewHome.findViewById(R.id.listView_profile_settings);
        profile_view.removeAllViews();
        for (String s : list_items) {
            View view = add_profile(s);
            profile_view.addView(view);
        }
        for (String s : settings_items) {
            View view = add_profile(s);
            seetings_view.addView(view);
        }
    }

    public View add_profile(final String s) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.profile_list_item, null);
        final TextView textView_prfile = (TextView) addView.findViewById(R.id.textView_item_pro);
        textView_prfile.setText(s);
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView_prfile.getText().toString().equals(getString(R.string.edit_profile))) {
                    isUpdate = true;
                    Intent intent = new Intent(getActivity(), RegistrationProfileActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);
                } else if (textView_prfile.getText().toString().equals(getString(R.string.edit_centers))) {
                    isUpdate = true;
                    Intent intent = new Intent(getActivity(), Select_Favourite_Center.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

                } else if (textView_prfile.getText().toString().equals(getString(R.string.edit_interests))) {
                    isUpdate = true;
                    Intent intent = new Intent(getActivity(), Select_Interest.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

                } else if (textView_prfile.getText().toString().equals(getString(R.string.help))) {
                    Intent intent = new Intent(getActivity(), WebFragment.class);
                    intent.putExtra("heading", getString(R.string.help));
                    intent.putExtra("url", getString(R.string.help_url));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);
                } else if (textView_prfile.getText().toString().equals(getString(R.string.privacy_policy))) {
                    Intent intent = new Intent(getActivity(), WebFragment.class);
                    intent.putExtra("heading", getString(R.string.privacy_policy));
                    intent.putExtra("url", getString(R.string.privacy_url));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);
                } else if (textView_prfile.getText().toString().equals(getString(R.string.about_us))) {
                    Intent intent = new Intent(getActivity(), WebFragment.class);
                    intent.putExtra("heading", getString(R.string.about_us));
                    intent.putExtra("url", getString(R.string.about_url));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);
                } else if (textView_prfile.getText().toString().equals(getString(R.string.notification))) {
                    Intent intent = new Intent(getActivity(), NotificationActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

                } else if (textView_prfile.getText().toString().equals(getString(R.string.change_language))) {
                    Intent intent = new Intent(getActivity(), LanguageChangeActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

                } else if (textView_prfile.getText().toString().equals(getString(R.string.logout))) {
                    mMaterialDialog = new MaterialDialog(getActivity())
                            .setTitle(getResources().getString(R.string.logout_dialog_title))
                            .setMessage(getResources().getString(R.string.logout_dialog_message))
                            .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    volleyNetworkUtil.GetUserSignOut(ApiConstants.MALL_USER_SIGN_OUT + SharedPreferenceUserProfile.getUserId(getActivity()));
                                    SharedPreferenceUserProfile.DeleteUserProfile(getActivity());
                                    Intent intent = new Intent(getActivity(), SplashScreen.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.cancel_button_title), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog.dismiss();

                                }
                            });

                    mMaterialDialog.show();

                } /*else if (textView_prfile.getText().toString().equals(getString(R.string.privacy_policy))) {
                    Intent i = new Intent(getActivity(), WebPages.class);
                    i.setData(Uri.parse(getResources().getString(R.string.privacy_url)));
                    StaticLiterls.web_title = textView_prfile.getText().toString();
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

                } else if (textView_prfile.getText().toString().equals(getString(R.string.terms))) {
                    Intent i = new Intent(getActivity(), WebPages.class);
                    i.setData(Uri.parse(getResources().getString(R.string.term_of_use_url)));
                    StaticLiterls.web_title = textView_prfile.getText().toString();
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

//                } else if (textView_prfile.getText().toString().equals(getString(R.string.help))) {
//                    Intent i = new Intent(getActivity(), WebPages.class);
//                    i.setData(Uri.parse(getResources().getString(R.string.help_url)));
//                    StaticLiterls.web_title = textView_prfile.getText().toString();
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.slidout_left,
//                            R.anim.slidein_left);

                }*/ else {

                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }

            }
        });
        return addView;
    }

    private void selectProfileImage(String dialogTitle) {
        final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.choose_gallery), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(dialogTitle);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getString(R.string.take_photo))) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, 0);
                } else if (options[item].equals(getString(R.string.choose_gallery))) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);
                } else if (options[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                user_ImageView.setImageBitmap(photo);
                user_profile.setImageBase64String(Image_Scaling.encodeTobase64(photo));
                registrationController.updateUserProfile(user_profile, null);
                DataHandler.updatePreferences(AppConstants.PROFILE_DATA, user_profile);
            }
        } else if (requestCode == 1) {

            Log.e("", "image from gallery ");
            if (resultCode == Activity.RESULT_OK) {

                try {
                    Uri selectedImageUri = data.getData();
                    String picturePath = getPath(selectedImageUri);
                    ;
                    Bitmap bitmapSelectedImage = BitmapFactory.decodeFile(picturePath);

                    if (bitmapSelectedImage == null) {
                        selectProfileImage("Re-Select Picture");
                        return;
                    }
                    user_ImageView.setImageBitmap(bitmapSelectedImage);
                    user_profile.setImageBase64String(Image_Scaling.encodeTobase64(bitmapSelectedImage));
                    registrationController.updateUserProfile(user_profile, null);
                    DataHandler.updatePreferences(AppConstants.PROFILE_DATA, user_profile);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    void loadWebFragments() {
        /*WebFragment nextFrag= new WebFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.scrollView, nextFrag);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }

}
