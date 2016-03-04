package com.mallapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mallapp.Model.ContactBean;
import com.mallapp.View.R;
import com.mallapp.listeners.ContactListener;
import com.mallapp.utils.ImageLoader;
import com.mallapp.utils.LocalDataStructure;

import java.util.List;

/**
 * Created by appster on 10/29/2015.
 */
public class ContactAdapter extends BaseAdapter {

    private List<ContactBean> contactList;
    private Context context;
    @SuppressWarnings("unused")
    private Handler handler=null;
    private ImageLoader mImageLoader;
    private ContactListener contactListener;
    private LocalDataStructure localDataStructure=null;

    public ContactAdapter(final Context pContext,
                          final List<ContactBean> pContactList,
                          final ImageLoader pImageLoader,final ContactListener pContactListener) {

        context = pContext;
        contactList = pContactList;
        mImageLoader=pImageLoader;
        contactListener = pContactListener;
        localDataStructure = LocalDataStructure.getInstance();

    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        final ContactBean contactBean=contactList.get(position);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.row_contact, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.contactimageView);
            holder.displayNameTxtView = (TextView) convertView
                    .findViewById(R.id.dsiplayname);
            holder.relativeLayout=(RelativeLayout)convertView
                    .findViewById(R.id.callrowhs);
            holder.header = (LinearLayout)
                    convertView.findViewById(R.id.section);
            holder.isWaveIcon = (ImageView)convertView.findViewById(R.id.arrowimageButton);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        try {

            String name =contactBean.getDisplayName();
            holder.displayNameTxtView.setText(name);
            String imageUrl = contactBean.getContactImage();
            mImageLoader.loadImage(imageUrl, holder.image);

            if(localDataStructure!=null && localDataStructure.getContactRegisterMap()
                    .containsKey(contactBean.getContactId())){

                holder.isWaveIcon.setVisibility(View.VISIBLE);



            }else{

                holder.isWaveIcon.setVisibility(View.GONE);

            }


            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(contactListener!=null)
                        contactListener.onContactClickEvent(contactBean);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }



    private class ViewHolder {

        ImageView image;
        TextView displayNameTxtView;
        RelativeLayout relativeLayout;
        @SuppressWarnings("unused")
        LinearLayout header ;
        ImageView isWaveIcon;
    }



}

