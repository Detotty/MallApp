package com.mallapp.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chatdbserver.xmpp.IMManager;
import com.chatdbserver.xmpp.listener.IPresenceListener;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelWebURLs;
import com.mallapp.Model.ContactBean;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.utils.CE_Controller;
import com.mallapp.utils.CrowdEyesGeneral;
import com.squareup.picasso.Picasso;

import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class CeRegContacts extends Activity implements IPresenceListener {
    private ListView contactListView = null;

    private List<ContactBean> searchContactList = new ArrayList<ContactBean>();
    private ArrayList<PhoneBookContacts> searchCeContactList = new ArrayList<>();
    UserProfileModel user_profile;
    EditText searchContactEditText;
    List<PhoneBookContacts> contactListCE = new ArrayList<>();
    CeContactListBaseAdapter CEadapter;
    TextView done;
    EditText grp_title;
    IMManager imManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_contacts);
        imManager = IMManager.getIMManager(this);


        user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);
        contactListView = (ListView) findViewById(R.id.listView_invite);
        searchContactEditText = (EditText) findViewById(R.id.search_latest);
        grp_title = (EditText) findViewById(R.id.group_edt);
        done = (TextView) findViewById(R.id.textViewDone);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();

            }
        });


    }

    public void ShowContacts() {
        imManager.setPresenceListener(this);

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

    @Override
    protected void onResume() {
        super.onResume();
        ShowContacts();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void CrowdEyesContactList() {
        contactListCE = imManager.getAppContacts();
//        if (contactListCE != null) {
        Collections.sort(contactListCE, new Comparator<PhoneBookContacts>() {
            @Override
            public int compare(PhoneBookContacts lhs, PhoneBookContacts rhs) {
                return lhs.getFirstName().compareTo(rhs.getFirstName());
            }
        });
        CEadapter = new CeContactListBaseAdapter(getApplicationContext(), contactListCE);
        contactListView.setAdapter(CEadapter);
//        } else {
//            if (contactListCE != null) {
//                contactListCE = CrowdEyesGeneral.getCEContactList();
//                CEadapter = new CeContactListBaseAdapter(getApplicationContext(), contactListCE);
//                contactListView.setAdapter(CEadapter);
//            }
//        }
    }

    @Override
    public void processPresence(Presence presence) {
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        ShowContacts();
                    }
                }
        );
    }


    class ViewHolder {
        TextView name, status;
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
                convertView = mInflater.inflate(R.layout.ce_contact_list_cell, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.txt_name);
                holder.status = (TextView) convertView.findViewById(R.id.status_txt);
                holder.user_img = (ImageView) convertView.findViewById(R.id.img_blocked);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (!contactModelArrayList.get(position).getFirstName().equals("")) {
                holder.name.setText(contactModelArrayList.get(position).getFirstName());
                if (contactModelArrayList.get(position).getFileName() == "null") {
                    Bitmap announcer = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                    holder.user_img.setImageBitmap(announcer);
                } else {
                    Picasso.with(CeRegContacts.this).load(contactModelArrayList.get(position).getFileName()).placeholder(R.drawable.avatar).into(holder.user_img);
                }

                if (contactModelArrayList.get(position).getStatus()) {
                    holder.status.setText(R.string.online);
                    holder.status.setTextColor(ContextCompat.getColor(con, R.color.purple));
                } else {
                    holder.status.setText(R.string.offline);
                    holder.status.setTextColor(ContextCompat.getColor(con, R.color.grey));


                }
            }
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent;
                    String pro_id = contactModelArrayList.get(position).getUserId();
                    String user_pic = contactModelArrayList.get(position).getFileName();


                    intent = new Intent(CeRegContacts.this, ChatScreen.class);
                    intent.putExtra("user_id", GlobelWebURLs.ce_user + pro_id);
                    intent.putExtra("user_name", contactModelArrayList.get(position).getFirstName());
                    intent.putExtra("user_pic", user_pic);
                    startActivity(intent);
                    finish();


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

    private void showCeSearchContactView() {

        CEadapter = new CeContactListBaseAdapter(getApplicationContext(), searchCeContactList);
        contactListView.setAdapter(CEadapter);
        contactListView.setFastScrollAlwaysVisible(true);
        CEadapter.notifyDataSetChanged();
    }

}

