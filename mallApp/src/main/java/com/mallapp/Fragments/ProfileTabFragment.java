package com.mallapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.mallapp.Model.UserProfile;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.R;
import com.mallapp.View.RegistrationProfileActivity;
import com.mallapp.View.Select_Favourite_Center;
import com.mallapp.View.Select_Interest;

import java.io.ByteArrayOutputStream;


public class ProfileTabFragment extends Fragment{


    View viewHome;
    UserProfile user_profile;
    ImageView user_ImageView;
    ImageButton back,fav;
//    String link = StaticLiterls.link;
    String[] list_items, settings_items ;

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
        profileListview();
        TextView header = (TextView) viewHome.findViewById(R.id.offer_title);
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
       /* facebook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticLiterls.postToFacebook(getActivity(), getResources().getString(R.string.ce_share_msg) + "\n" + link);
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticLiterls.sendSms(getActivity(), getResources().getString(R.string.ce_share_msg) + "\n" + link);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticLiterls.sendEmail(getActivity(), getResources().getString(R.string.ce_share_msg) + "\n" + link);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticLiterls.postToTwitter(getActivity(), getResources().getString(R.string.ce_share_msg_tw) + "\n" + link);
            }
        });*/
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
        user_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                *//*Intent i = new Intent(getActivity(), FullScreenImage.class);
                i.putExtra("img", encoded);
                startActivity(i);*//*
            }
        });
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
                    Intent intent = new Intent(getActivity(), RegistrationProfileActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);
                } else if (textView_prfile.getText().toString().equals(getString(R.string.edit_centers))) {
                    Intent intent = new Intent(getActivity(), Select_Favourite_Center.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

                } else if (textView_prfile.getText().toString().equals(getString(R.string.edit_interests))) {
                    Intent intent = new Intent(getActivity(), Select_Interest.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slidout_left,
                            R.anim.slidein_left);

                } else if (textView_prfile.getText().toString().equals(getString(R.string.help))) {
                    loadWebFragments();
                } else if (textView_prfile.getText().toString().equals(getString(R.string.privacy_policy))) {
                    loadWebFragments();
                } else if (textView_prfile.getText().toString().equals(getString(R.string.about_us))) {
                    loadWebFragments();
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

    /*private void selectProfileImage(String dialogTitle) {
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
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image");
                    startActivityForResult(intent, 1);
                } else if (options[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                user_ImageView.setImageBitmap(photo);
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
//                        selectProfileImage("Re-Select Picture");
                        return;
                    }
                    user_ImageView.setImageBitmap(bitmapSelectedImage);


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

    void loadWebFragments(){
        WebFragment nextFrag= new WebFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.scrollView, nextFrag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
