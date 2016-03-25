package com.mallapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatdbserver.xmpp.IMManager;
import com.chatdbserver.xmpp.listener.IGroupMsgTabListener;
import com.chatdbserver.xmpp.listener.IPresenceListener;
import com.chatdbserver.xmpp.listener.IXMPPMessageTabListener;
import com.chatdbserver.xmpp.model.OpenChats;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.chatdbserver.xmpp.model.SingleChat;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelWebURLs;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.View.CeRegContacts;
import com.mallapp.View.ChatScreen;
import com.mallapp.View.GroupChatScreen;
import com.mallapp.View.R;
import com.mallapp.utils.ConnectionDetector;
import com.mallapp.utils.StaticLiterls;
import com.squareup.picasso.Picasso;

import org.jivesoftware.smack.packet.Presence;

import java.util.List;


public class MessagesTabFragments extends Fragment implements IXMPPMessageTabListener, IPresenceListener, IGroupMsgTabListener {

    private static final String TAG = MessagesTabFragments.class.getCanonicalName();
    private View viewHome;
    private ListView inboxList = null;
    IMManager imManager;
    TextView noRecordtextView;
    UserProfileModel user_profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_profile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewHome = inflater.inflate(R.layout.fragment_parent_tab_messages, container, false);
        imManager = imManager.getIMManager(getActivity().getApplicationContext());

        noRecordtextView = (TextView) viewHome.findViewById(R.id.noRecordtextView);
        inboxList = (ListView) viewHome.findViewById(R.id.listView);

        ImageButton imageButton = (ImageButton) viewHome.findViewById(R.id.chat_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CeRegContacts.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slidout_left,
                        R.anim.slidein_left);
            }
        });


        return viewHome;
    }

    @Override
    public void onPause() {
        super.onPause();
        imManager.setPresenceListener(null);
        imManager.setGroupTabChatListener(null);

    }


    private void initializeUI() {

        try {

            showRecentList();

        } catch (Exception e) {
            Log.e(TAG,
                    " Error while initialize UI compoments and error message is = "
                            + e.getMessage());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        imManager.setMessageListener(this);
        imManager.setGroupTabChatListener(this);
        if (ConnectionDetector.isInternetAvailable(getActivity())) {
            getMyConv();
        } else {
            ConnectionDetector.showAlertDialog(getActivity(), getResources().getString(R.string.no_internet),
                    getResources().getString(R.string.network_error), false);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    public void showRecentList() {
        imManager.setPresenceListener(this);
        List<OpenChats> oc_list = imManager.getOpenChat();
        if (oc_list != null && oc_list.size() == 0) {
            noRecordtextView.setVisibility(View.VISIBLE);
            inboxList.setVisibility(View.GONE);

        } else {
            noRecordtextView.setVisibility(View.GONE);
            inboxList.setVisibility(View.VISIBLE);

        }
        InboxListBaseAdapter inboxListBaseAdapter = new InboxListBaseAdapter(viewHome.getContext(), oc_list);
        int index = inboxList.getFirstVisiblePosition();
        View v = inboxList.getChildAt(0);
        int top = (v == null) ? -1 : v.getTop();
        inboxList.setAdapter(inboxListBaseAdapter);
        inboxList.setSelectionFromTop(index, top);

    }

    @Override
    public void newMessageArrived(SingleChat singleChat) {
        Log.e("Recv msg plz", "" + singleChat.getMessage());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getMyConv();
            }
        });


    }

    @Override
    public void messageDelivered(SingleChat singleChat) {

    }

    @Override
    public void processPresence(Presence presence) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getMyConv();
            }
        });

    }

    @Override
    public void groupJoined(String str) {

    }

    @Override
    public void leavegroup(String str) {

    }

    @Override
    public void newGroupMessageArrived(SingleChat singleChat, String jid) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getMyConv();
            }
        });
    }


    class InboxListBaseAdapter extends BaseAdapter {
        //private static Vector<DealObject> searchArrayList;
        Context con;
        private LayoutInflater mInflater;
        List<OpenChats> chatVector;

        public InboxListBaseAdapter(Context mainMenu, List<OpenChats> list) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(mainMenu);
            con = mainMenu;
            this.chatVector = list;
        }

        public int getCount() {
            return chatVector.size();
        }

        public Object getItem(int position) {
            return chatVector.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView name, count, lastMessageTime, number, status;
            ImageView thumbnail, thumb_ann;
            RelativeLayout relativeLayout_list_cell;

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.my_conv_list_cell, null);
                holder = new ViewHolder();
                holder.count = (TextView) convertView.findViewById(R.id.count);
                holder.lastMessageTime = (TextView) convertView.findViewById(R.id.time);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.number = (TextView) convertView.findViewById(R.id.number);
                holder.status = (TextView) convertView.findViewById(R.id.status);
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
                holder.thumb_ann = (ImageView) convertView.findViewById(R.id.announcement_image);
                holder.relativeLayout_list_cell = (RelativeLayout) convertView.findViewById(R.id.list_item_r);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PhoneBookContacts phoneBookContacts = chatVector.get(position).getPhoneBookContacts();
            if (chatVector.get(position).getNumOfUnreadMsgs() > 0) {
                holder.count.setText("(" + chatVector.get(position).getNumOfUnreadMsgs() + ")");
                holder.relativeLayout_list_cell.setBackgroundResource(R.drawable.unread_bg);
            } else {
                holder.count.setText("");
                holder.relativeLayout_list_cell.setBackgroundResource(R.drawable.latest_list_bg_blue);
            }
            if (!chatVector.get(position).getJid().equals("")) {


                if (!chatVector.get(position).getJid().contains(GlobelWebURLs.ce_group)) {
                    if (phoneBookContacts != null) {
                        if (phoneBookContacts.getStatus()) {
                            holder.status.setText(R.string.online);
                            holder.status.setTextColor(Color.parseColor("#FF048DB6"));

                        } else {
                            holder.status.setText(R.string.offline);
                            holder.status.setTextColor(Color.parseColor("#ACACAC"));

                        }
                        holder.status.setCompoundDrawables(null, null, null, null);
                        holder.status.setTextSize(15);
                        holder.thumbnail.setVisibility(View.VISIBLE);

                        holder.thumb_ann.setVisibility(View.INVISIBLE);
                        Picasso.with(getActivity()).load(phoneBookContacts.getFileName()).placeholder(R.drawable.avatar).into(holder.thumbnail);
                        holder.name.setText(phoneBookContacts.getFirstName());
                    }
                } else if (chatVector.get(position).getJid().contains(GlobelWebURLs.ce_group) && chatVector.get(position).getJid().startsWith(GlobelWebURLs.ce_claim)) {
                    holder.thumbnail.setVisibility(View.INVISIBLE);
                    holder.thumb_ann.setVisibility(View.VISIBLE);

                    if (chatVector.get(position).getPhoneBookContacts() != null) {
                        Drawable img;
                        String ann_status = chatVector.get(position).getPhoneBookContacts().getUsername();
                        holder.name.setText(chatVector.get(position).getPhoneBookContacts().getFirstName());
                        if (ann_status.equals("4")) {
                            img = con.getResources().getDrawable(R.drawable.green);
                            img.setBounds(0, 0, 40, 40);
                            holder.status.setCompoundDrawables(img, null, null, null);
                            holder.status.setText(R.string.delivered);
                            holder.status.setTextColor(Color.parseColor("#37ba29"));
                            holder.status.setTextSize(8);
                        } else if (ann_status.equals("6")) {
                            img = con.getResources().getDrawable(R.drawable.red);
                            img.setBounds(0, 0, 40, 40);
                            holder.status.setCompoundDrawables(img, null, null, null);
                            holder.status.setText(R.string.canceled);
                            holder.status.setTextColor(Color.parseColor("#37ba29"));
                            holder.status.setTextSize(8);
                        } else if (ann_status.equals("5")) {
                            img = con.getResources().getDrawable(R.drawable.ico_handedover);
                            img.setBounds(0, 0, 40, 40);
                            holder.status.setCompoundDrawables(img, null, null, null);
                            holder.status.setText(getString(R.string.announcement_handed_over));
                            holder.status.setTextColor(Color.parseColor("#37ba29"));
                            holder.status.setTextSize(10);
                        } else {
                            holder.status.setText(chatVector.get(position).getPhoneBookContacts().getEndorsementsCount());
                            holder.status.setTextColor(Color.parseColor("#FF048DB6"));
                            holder.status.setTextSize(13);
                        }

                        if (chatVector.get(position).getPhoneBookContacts().getFileName() != null && !chatVector.get(position).getPhoneBookContacts().getFileName().equals("null")) {
                            Picasso.with(getActivity()).load(chatVector.get(position).getPhoneBookContacts().getFileName()).placeholder(R.drawable.default_object).into(holder.thumb_ann);
                        }
                    }
                } else {
                    holder.status.setText("");
                    holder.status.setCompoundDrawables(null, null, null, null);
                    holder.status.setTextSize(15);
                    holder.thumb_ann.setVisibility(View.INVISIBLE);
                    holder.thumbnail.setVisibility(View.VISIBLE);

                    holder.name.setText(chatVector.get(position).getJid().split("@")[0].replace("_", " "));
                    holder.thumbnail.setImageResource(R.drawable.group_chat_icon);
                }
                String lastMsg = chatVector.get(position).getLastMessage().getMessage();
                if (lastMsg.startsWith("(ce_template_photomsg)")) {
                    holder.number.setText("Photo");
                } else {
                    holder.number.setText(chatVector.get(position).getLastMessage().getMessage());
                }


            } else {
                holder.number.setText("");
            }

            holder.lastMessageTime.setText(con.getString(R.string.last_activity) + chatVector.get(position).getLastMessage().getMsgtime());
            inboxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    Intent intent = null;
                    if (!chatVector.get(position).getJid().contains(GlobelWebURLs.ce_group)) {
                        intent = new Intent(view.getContext(), ChatScreen.class);
                        intent.putExtra("user_name", chatVector.get(position).getPhoneBookContacts().getFirstName());
                        intent.putExtra("user_id", chatVector.get(position).getJid());
                        intent.putExtra("user_pic", chatVector.get(position).getPhoneBookContacts().getFileName());
                        startActivity(intent);
                    } else if (chatVector.get(position).getJid().contains(GlobelWebURLs.ce_group) && chatVector.get(position).getJid().startsWith(GlobelWebURLs.ce_claim)) {
                        ImageView AnnouncerImg = (ImageView) view.findViewById(R.id.announcement_image);
                        Bitmap bitmap = ((BitmapDrawable) AnnouncerImg.getDrawable()).getBitmap();
                        intent = new Intent(view.getContext(), GroupChatScreen.class);
                        intent.putExtra("group_title", chatVector.get(position).getJid());
                        intent.putExtra("create", false);
                        intent.putExtra("announcement", true);
                        StaticLiterls.announcement_location = chatVector.get(position).getPhoneBookContacts().getLastName();
                        StaticLiterls.announcement_time = chatVector.get(position).getPhoneBookContacts().getMobilePhone();
                        StaticLiterls.announcement_name = chatVector.get(position).getPhoneBookContacts().getFirstName();
                        StaticLiterls.announcement_bitmap = bitmap;
                        startActivity(intent);
                    } else {
                        intent = new Intent(view.getContext(), GroupChatScreen.class);
                        intent.putExtra("group_title", chatVector.get(position).getJid());
                        intent.putExtra("create", false);
                        startActivity(intent);
                    }
                }
            });

            inboxList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    // TODO Auto-generated method stub

                    alert_box(chatVector.get(pos));

                    return true;
                }
            });
            return convertView;
        }
    }


    public void alert_box(final OpenChats openChats) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle(getString(R.string.app_name));
        builder1.setMessage(R.string.delete_conversation);
        builder1.setCancelable(true);
        builder1.setPositiveButton(R.string.yes_,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        imManager.deleteOpenChat(openChats);
                        showRecentList();
                    }
                });
        builder1.setNegativeButton(R.string.no_,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    void getMyConv() {
        new AsyncTask<Void, Void, String>() {
            protected void onPreExecute() {

                StaticLiterls.showProgressDialog(getActivity());
            }

            @Override
            protected String doInBackground(Void... params) {
//                String success = Messaging_WebServices.GetMyConv(user_profile);
                String success ="";
                return success;
            }

            @Override
            protected void onPostExecute(String success) {
                StaticLiterls.DismissesDialog();
                showRecentList();

            }
        }.execute(null, null, null);
    }
}
