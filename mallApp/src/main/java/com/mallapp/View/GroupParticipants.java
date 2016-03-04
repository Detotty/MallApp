package com.mallapp.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.mallapp.utils.StaticLiterls;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by appster on 2/25/2016.
 */
public class GroupParticipants extends Activity {
    ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grp_participants);
        TextView textVie = (TextView) findViewById(R.id.heading);
        textVie.setText("Participants");
        ImageButton imageButton = (ImageButton) findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        contactListView = (ListView) findViewById(R.id.listView_invite);
        contactListView.setAdapter(new CeContactListBaseAdapter(this, StaticLiterls.particpantsList));
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

            if (!contactModelArrayList.get(position).getFirstName().equals("")) {
                holder.name.setText(contactModelArrayList.get(position).getFirstName());
                if (contactModelArrayList.get(position).getFileName() == "null") {
                    Bitmap announcer = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                    holder.user_img.setImageBitmap(announcer);
                } else {
                    Picasso.with(con).load(contactModelArrayList.get(position).getFileName()).placeholder(R.drawable.avatar).into(holder.user_img);
                }
            }
            return convertView;
        }
    }
}
