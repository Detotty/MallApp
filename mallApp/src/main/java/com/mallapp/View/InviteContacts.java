package com.mallapp.View;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chatdbserver.xmpp.IMManager;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.mallapp.Adapters.ContactAdapter;
import com.mallapp.Adapters.SeparatedListAdapter;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelWebURLs;
import com.mallapp.Model.ContactBean;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.listeners.ContactListener;
import com.mallapp.utils.CE_Controller;
import com.mallapp.utils.CrowdEyesGeneral;
import com.mallapp.utils.ImageLoader;
import com.mallapp.utils.StaticLiterls;
import com.mallapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InviteContacts extends Activity implements ContactListener {
    private ListView contactListView = null;
    private Handler mHandler = null;
    private List<ContactBean> contactList = null;
    private List<String> headerList;
    private Map<String, List<ContactBean>> contactMap;
    private SeparatedListAdapter adapter;
    private ContactAdapter contactAdapter;
    private ContactListener contactListener;
    private ImageLoader mImageLoader;
    private List<ContactBean> searchContactList = new ArrayList<ContactBean>();
    private ArrayList<PhoneBookContacts> searchCeContactList = new ArrayList<>();
    UserProfileModel user_profile;
    EditText searchContactEditText;
    RadioButton one, two;
    List<PhoneBookContacts> contactListCE = new ArrayList<>();
    CeContactListBaseAdapter CEadapter;
    boolean chat = false;
    boolean participant = false;
    boolean announcement = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContactListener(this);
        setContentView(R.layout.activity_invite_contacts);
        user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);
        contactListView = (ListView) findViewById(R.id.listView_invite);
        searchContactEditText = (EditText) findViewById(R.id.search_latest);
        one = (RadioButton) findViewById(R.id.button_one);
        two = (RadioButton) findViewById(R.id.button_two);
        if (getIntent().getExtras() != null) {
            chat = getIntent().getExtras().getBoolean("chat");
            participant = getIntent().getExtras().getBoolean("participant");
            announcement = getIntent().getExtras().getBoolean("announcement");
            if (chat || participant) {
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
            }
        }
        mImageLoader = new ImageLoader(this,
                getListPreferredItemHeight()) {
            @Override
            protected Bitmap processBitmap(Object data) {
                return loadContactPhotoThumbnail((String) data, getImageSize());
            }
        };
        mImageLoader.setLoadingImage(R.drawable.ic_contact_picture_holo_light);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
                setResult(0);

            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrowdEyesContactList();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneContacts();
            }
        });

        if (CrowdEyesGeneral.getCEContactList() == null) {
            CE_Controller.getInstance().getAllUserContactAndUpdateOnServer();
            CrowdEyesContactList();
            contactSearchView();
        } else {
            CrowdEyesContactList();
            contactSearchView();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
    }

    private void phoneContacts() {
        loadContactList();


        if (contactList != null) {

            Collections.sort(headerList);
            adapter = new SeparatedListAdapter(
                    InviteContacts.this, headerList.size());
            for (int index = 0; index < headerList.size(); index++) {
                final String mapKey = headerList.get(index);
                final List<ContactBean> tempArrayList = contactMap
                        .get(mapKey);
                contactAdapter = new ContactAdapter(
                        InviteContacts.this, tempArrayList,
                        mImageLoader, getContactListener());
                adapter.addSection(mapKey, contactAdapter,
                        index);
                contactListView.setAdapter(adapter);
                contactListView
                        .setFastScrollAlwaysVisible(true);
                contactAdapter.notifyDataSetChanged();
            }

        }


    }

    @Override
    public void onContactClickEvent(ContactBean pContactBean) {
        String name = "";
        String phoneNumber = "";
        String image_uri = "";
        Bitmap bitmap;
        ByteArrayOutputStream _bs = new ByteArrayOutputStream();

        ContentResolver cr = getContentResolver();
        Cursor phones = getContentResolver().query(

                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts._ID + "=" + pContactBean.getContactId(),
                null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        while (phones.moveToNext()) {

            name = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            image_uri = phones
                    .getString(phones
                            .getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

        }
        phones.close();

        List<String> contactNumberList = new ArrayList<String>();


        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{pContactBean.getContactId()}, null);

        while (pCur.moveToNext()) {
            int phNumber = pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = pCur.getString(phNumber);
            contactNumberList.add(number);

        }
        if (pCur != null)
            pCur.close();

        for (String string : contactNumberList) {
            phoneNumber = string;
        }
        if (image_uri != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), Uri.parse(image_uri));
                bitmap = getclip(bitmap);

                bitmap.compress(Bitmap.CompressFormat.PNG, 50, _bs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String c_id = pContactBean.getContactId();
        Intent intent = new Intent(InviteContacts.this, ProfileContactScreen.class);
        intent.putExtra("name", name);
        intent.putExtra("number", phoneNumber);
        intent.putExtra("image", _bs.toByteArray());
        startActivity(intent);
        overridePendingTransition(R.anim.slidout_left,
                R.anim.slidein_left);

    }

    public ContactListener getContactListener() {
        return contactListener;
    }

    /**
     * @param contactListener the contactListener to set
     */
    public void setContactListener(ContactListener contactListener) {
        this.contactListener = contactListener;
    }

    private int getListPreferredItemHeight() {
        final TypedValue typedValue = new TypedValue();
        // Resolve list item preferred height theme attribute into typedValue
        getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);
        // Create a new DisplayMetrics object
        final DisplayMetrics metrics = new android.util.DisplayMetrics();
        // Populate the DisplayMetrics
        getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);

        // Return theme value based on DisplayMetrics
        return (int) typedValue.getDimension(metrics);
    }


    /**
     * Decodes and scales a contact's image from a file pointed to by a Uri in
     * the contact's data, and returns the result as a Bitmap. The column that
     * contains the Uri varies according to the platform version.
     *
     * @param photoData For platforms prior to Android 3.0, provide the Contact._ID
     *                  column value. For Android 3.0 and later, provide the
     *                  Contact.PHOTO_THUMBNAIL_URI value.
     * @param imageSize The desired target width and height of the output image in
     *                  pixels.
     * @return A Bitmap containing the contact's image, resized to fit the
     * provided image size. If no thumbnail exists, returns null.
     */
    private Bitmap loadContactPhotoThumbnail(String photoData, int imageSize) {


        AssetFileDescriptor afd = null;

        // This "try" block catches an Exception if the file descriptor returned
        // from the Contacts
        // Provider doesn't point to an existing file.
        try {
            Uri thumbUri;
            // If Android 3.0 or later, converts the Uri passed as a string to a
            // Uri object.
            if (Utils.hasHoneycomb()) {
                thumbUri = Uri.parse(photoData);
            } else {
                // For versions prior to Android 3.0, appends the string
                // argument to the content
                // Uri for the Contacts table.
                final Uri contactUri = Uri.withAppendedPath(
                        ContactsContract.Contacts.CONTENT_URI, photoData);

                // Appends the content Uri for the Contacts.Photo table to the
                // previously
                // constructed contact Uri to yield a content URI for the
                // thumbnail image
                thumbUri = Uri.withAppendedPath(contactUri,
                        ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }
            // Retrieves a file descriptor from the Contacts Provider. To learn
            // more about this
            // feature, read the reference documentation for
            // ContentResolver#openAssetFileDescriptor.
            afd = getContentResolver().openAssetFileDescriptor(
                    thumbUri, "r");

            // Gets a FileDescriptor from the AssetFileDescriptor. A
            // BitmapFactory object can
            // decode the contents of a file pointed to by a FileDescriptor into
            // a Bitmap.
            FileDescriptor fileDescriptor = afd.getFileDescriptor();

            if (fileDescriptor != null) {
                // Decodes a Bitmap from the image pointed to by the
                // FileDescriptor, and scales it
                // to the specified width and height
                return ImageLoader.decodeSampledBitmapFromDescriptor(
                        fileDescriptor, imageSize, imageSize);
            }
        } catch (FileNotFoundException e) {
            // If the file pointed to by the thumbnail URI doesn't exist, or the
            // file can't be
            // opened in "read" mode, ContentResolver.openAssetFileDescriptor
            // throws a
            // FileNotFoundException.
            if (BuildConfig.DEBUG) {
                Log.d("", "Contact photo thumbnail not found for contact "
                        + photoData + ": " + e.toString());
            }
        } finally {
            // If an AssetFileDescriptor was returned, try to close it
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    // Closing a file descriptor might cause an IOException if
                    // the file is
                    // already closed. Nothing extra is needed to handle this.
                }
            }
        }

        // If the decoding failed, returns null
        return null;
    }


    private void CrowdEyesContactList() {
        contactListCE = CrowdEyesGeneral.getCEContactList();
//        if (contactListCE != null) {
//            CEadapter = new CeContactListBaseAdapter(getApplicationContext(), contactListCE);
//            contactListView.setAdapter(CEadapter);
//        } else {
        IMManager imManager = IMManager.getIMManager(this);
        contactListCE = imManager.getAppContacts();
        Collections.sort(contactListCE, new Comparator<PhoneBookContacts>() {
            @Override
            public int compare(PhoneBookContacts lhs, PhoneBookContacts rhs) {
                return lhs.getFirstName().compareTo(rhs.getFirstName());
            }
        });
        for (int i = 0; i < contactListCE.size(); i++) {
            PhoneBookContacts phoneBookContacts = contactListCE.get(i);
            if (phoneBookContacts.getUserId().equals(user_profile.getUserId()) || phoneBookContacts.getUserId().startsWith(GlobelWebURLs.ce_claim)) {
                contactListCE.remove(phoneBookContacts);
            }
        }
        for (int i = 0; i < contactListCE.size(); i++) {
            PhoneBookContacts phoneBookContacts = contactListCE.get(i);
            if (phoneBookContacts.getUserId().equals(user_profile.getUserId()) || phoneBookContacts.getUserId().startsWith(GlobelWebURLs.ce_claim)) {
                contactListCE.remove(phoneBookContacts);
            }
        }
        CEadapter = new CeContactListBaseAdapter(getApplicationContext(), contactListCE);
        contactListView.setAdapter(CEadapter);
//        }
    }

    private void loadContactList() {

        try {
            contactList = new ArrayList<ContactBean>();
            contactMap = new HashMap<String, List<ContactBean>>();
            headerList = new ArrayList<String>();


            String letter = "+0123456789";
            contactMap.clear();
            headerList.clear();

            Cursor phones = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI, null,
                    null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

            while (phones.moveToNext()) {
                String image_uri = null;

                String id = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.Contacts._ID));
                String name = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                image_uri = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                if (!(Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)) {
                    continue;
                }

                ContactBean contactBean = new ContactBean();
                if (name != null) {
                    contactBean.setDisplayName(name);
                } else {
                    contactBean.setDisplayName("#");
                }


                contactBean.setContactId(id);
                contactBean.setHomeNumber("");
                contactBean.setWorkNumber("");
                //contactBean.setPhoneNumber(phoneNumber);
                contactBean.setContactImage(image_uri);


                contactList.add(contactBean);

                char firtsLetter = contactBean.getDisplayName().charAt(0);
                String key;
                if (letter.contains(String.valueOf(firtsLetter))) {
                    key = "#";
                } else {
                    key = String.valueOf(firtsLetter).toUpperCase();
                }

                if (contactMap.containsKey(key)) {
                    List<ContactBean> tempList = contactMap.get(key);
                    contactMap.remove(key);
                    tempList.add(contactBean);
                    contactMap.put(key, tempList);

                } else {
                    headerList.add(key);
                    List<ContactBean> fistItem = new ArrayList<ContactBean>();
                    fistItem.add(contactBean);
                    contactMap.put(key, fistItem);
                }
            }
            phones.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getclip(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    class ViewHolder {
        TextView name;
        ImageView user_img;

    }

    class CeContactListBaseAdapter extends BaseAdapter {
        Context con;
        private LayoutInflater mInflater;
        List<PhoneBookContacts> contactModelArrayList;

        public CeContactListBaseAdapter(Context mainMenu, List<PhoneBookContacts> list) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(mainMenu);
            con = mainMenu;
            this.contactModelArrayList = list;
        }

        public int getCount() {
            return contactModelArrayList.size();
        }

        public Object getItem(int position) {
            return contactModelArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.blocked_list_cell, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.txt_name);
                holder.user_img = (ImageView) convertView.findViewById(R.id.img_blocked);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (contactModelArrayList.get(position).getUserId().equals(user_profile.getUserId())) {
                convertView.setVisibility(View.GONE);
            } else {
                convertView.setVisibility(View.VISIBLE);
            }
            if (!contactModelArrayList.get(position).getFirstName().equals("")) {
                holder.name.setText(contactModelArrayList.get(position).getFirstName());
                if (contactModelArrayList.get(position).getFileName() == "null") {
                    Bitmap announcer = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                    holder.user_img.setImageBitmap(announcer);
                } else {
                    Picasso.with(InviteContacts.this).load(contactModelArrayList.get(position).getFileName()).placeholder(R.drawable.avatar).into(holder.user_img);
//                    holder.imageLoader.get(contactModelArrayList.get(position).getProfile_pic_url(), new com.android.volley.toolbox.ImageLoader.ImageListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            android.util.Log.e("", "Image Load Error: " + error.getMessage());
//                        }
//
//                        @Override
//                        public void onResponse(com.android.volley.toolbox.ImageLoader.ImageContainer response, boolean arg1) {
//                            if (response.getBitmap() != null) {
//                                // load image into imageview
//                                holder.user_img.setImageBitmap(response.getBitmap());
//
//                            }
//                        }
//                    });
                }
            }
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ImageView blockedImg = (ImageView) view.findViewById(R.id.img_blocked);
                    Intent intent;
                    String pro_id = contactModelArrayList.get(position).getUserId();
                    String user_pic = contactModelArrayList.get(position).getFileName();

                    if (participant) {
                        if (!contactModelArrayList.get(position).getUserId().equals(user_profile.getUserId())) {
                            intent = getIntent();
                            intent.putExtra("pro_id", GlobelWebURLs.ce_user + pro_id);
                            intent.putExtra("pro_name", contactModelArrayList.get(position).getFirstName());
                            setResult(1, intent);
                            finish();
                        } else {
                            Toast.makeText(InviteContacts.this, R.string.cant_chat, Toast.LENGTH_LONG).show();
                        }

                    } else {
                        if (chat) {
                            intent = new Intent(InviteContacts.this, ChatScreen.class);
                            intent.putExtra("user_id", GlobelWebURLs.ce_user + pro_id);
                            intent.putExtra("user_pic", user_pic);
                            startActivity(intent);
                            finish();
//                            if (!contactModelArrayList.get(position).getUserId().equals(user_profile.getProfile_ID())) {
//                                if (participant) {
//                                    intent = new Intent(view.getContext(), FullChatScreen.class);
//                                    intent.putExtra("profile_id", pro_id);
//                                    intent.putExtra("chat", true);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slidout_left,
//                                            R.anim.slidein_left);
//                                    finish();
//                                } else {
//                                    intent = new Intent(view.getContext(), FullChatScreen.class);
//                                    intent.putExtra("profile_id", pro_id);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slidout_left,
//                                            R.anim.slidein_left);
//                                    finish();
//                                }
//                            } else {
//                                Toast.makeText(InviteContacts.this, R.string.cant_chat, Toast.LENGTH_LONG).show();
//                            }
                        } else {
                            Bitmap bmp = null;
                            if (blockedImg.getDrawable() != null) {
                                bmp = ((BitmapDrawable) blockedImg.getDrawable()).getBitmap();
                            } else {
                                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                            }
                            /*intent = new Intent(view.getContext(), UserProfileActivity.class);
                            intent.putExtra("name", contactModelArrayList.get(position).getFirstName());
                            intent.putExtra("profile_id", contactModelArrayList.get(position).getUserId());
                            StaticLiterls.announceR_IMG = bmp;
                            startActivity(intent);
                            overridePendingTransition(R.anim.slidout_left,
                                    R.anim.slidein_left);*/
                        }
                    }

                }
            });
            return convertView;
        }
    }

    private void contactSearchView() {

        searchContactEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!searchContactEditText.hasFocus())
                    return;

                String str = s.toString();

                findContactList(str);


            }
        });
    }

    private void findContactList(final String contactNameStr) {

        searchContactList.clear();
        searchCeContactList.clear();

        if (two.isChecked()) {
            for (ContactBean contactBean : contactList) {

                if (contactBean.getDisplayName().toLowerCase()
                        .contains(contactNameStr.toLowerCase())) {
                    searchContactList.add(contactBean);
                }
            }
            showSearchContactView();
        }
        if (one.isChecked()) {
            if (contactListCE != null) {
                for (PhoneBookContacts contact : contactListCE) {

                    if (contact.getFirstName().toLowerCase()
                            .contains(contactNameStr.toLowerCase())) {
                        searchCeContactList.add(contact);
                    }
                }
                showCeSearchContactView();
            }
        }


    }

    private void showSearchContactView() {

        contactAdapter = new ContactAdapter(
                InviteContacts.this, searchContactList,
                mImageLoader, getContactListener());
        contactListView.setAdapter(contactAdapter);
        contactListView
                .setFastScrollAlwaysVisible(true);
        contactAdapter.notifyDataSetChanged();

    }

    private void showCeSearchContactView() {

        CEadapter = new CeContactListBaseAdapter(getApplicationContext(), searchCeContactList);
        contactListView.setAdapter(CEadapter);
        contactListView.setFastScrollAlwaysVisible(true);
        CEadapter.notifyDataSetChanged();
    }

}
