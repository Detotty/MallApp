package com.mallapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.chatdbserver.xmpp.listener.IPresenceListener;
import com.chatdbserver.xmpp.listener.IXMPPMessageTabListener;
import com.chatdbserver.xmpp.model.OpenChats;
import com.chatdbserver.xmpp.model.SingleChat;
import com.mallapp.View.CeRegContacts;
import com.mallapp.View.ChatScreen;
import com.mallapp.View.R;
import com.squareup.picasso.Picasso;

import org.jivesoftware.smack.packet.Presence;

import java.util.List;


public class MessagesTabFragments extends Fragment implements IXMPPMessageTabListener, IPresenceListener {

    private static final String TAG = MessagesTabFragments .class.getCanonicalName();
    private View viewHome;
    private Handler mHandler = null;
    private ListView inboxList = null;
    IMManager im;
    List<OpenChats> oc_list;
    TextView noRecordtextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewHome = inflater.inflate(R.layout.fragment_parent_tab_messages, container, false);
        im = im.getIMManager(getActivity().getApplicationContext());
        im.setMessageListener(this);
        noRecordtextView = (TextView) viewHome.findViewById(R.id.noRecordtextView);

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
        inboxList = (ListView) viewHome.findViewById(R.id.listView);

        initializeUI();

        return viewHome;
    }

    @Override
    public void onPause() {
        super.onPause();
        im.setPresenceListener(null);

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
        showRecentList();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    public void showRecentList() {
        oc_list = im.getOpenChat();
        if (oc_list != null && oc_list.size() == 0) {
            noRecordtextView.setVisibility(View.VISIBLE);
            inboxList.setVisibility(View.GONE);

        } else {
            noRecordtextView.setVisibility(View.GONE);
            inboxList.setVisibility(View.VISIBLE);

        }
        inboxList.setAdapter(new InboxListBaseAdapter(viewHome.getContext(), oc_list));
        im.setPresenceListener(this);
    }

    @Override
    public void newMessageArrived(SingleChat singleChat) {
        Log.e("Recv msg plz", "" + singleChat.getMessage());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showRecentList();
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
                showRecentList();
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
            ImageView thumbnail;
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
                holder.relativeLayout_list_cell = (RelativeLayout) convertView.findViewById(R.id.list_item_r);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (chatVector.get(position).getNumOfUnreadMsgs() > 0) {
                holder.count.setText("(" + chatVector.get(position).getNumOfUnreadMsgs() + ")");
                holder.relativeLayout_list_cell.setBackgroundResource(R.drawable.unread_bg);
            } else {
                holder.count.setText("");
                holder.relativeLayout_list_cell.setBackgroundResource(R.drawable.latest_list_bg_blue);
            }
            if (!chatVector.get(position).getJid().equals("")) {
                if (chatVector.get(position).getPhoneBookContacts().getStatus()) {
                    holder.status.setText(R.string.online);
                    holder.status.setTextColor(ContextCompat.getColor(con, R.color.purple));

                } else {
                    holder.status.setText(R.string.offline);
                    holder.status.setTextColor(ContextCompat.getColor(con, R.color.grey));

                }
                Picasso.with(getActivity()).load(chatVector.get(position).getPhoneBookContacts().getFileName()).placeholder(R.drawable.avatar).into(holder.thumbnail);
                String lastMsg = chatVector.get(position).getLastMessage().getMessage();
//                if (lastMsg.length() >= 5 && lastMsg.substring(0, 5).equals("long:")) {
//                    holder.number.setText("Location");
//                } else
                if (lastMsg.startsWith("(ce_template_photomsg)")) {
                    holder.number.setText("Photo");
                }
//                else if (lastMsg.length() >= 8 && lastMsg.substring(0, 8).equals("sticker_")) {
//                    holder.number.setText("Sticker");
//                }
                else {
                    holder.number.setText(chatVector.get(position).getLastMessage().getMessage());
                }


            } else {
                holder.number.setText("");
                //holder.name.setText(chatVector.get(position).getJid());
            }
            holder.name.setText(chatVector.get(position).getPhoneBookContacts().getFirstName());
            holder.lastMessageTime.setText(con.getString(R.string.last_activity) + chatVector.get(position).getLastMessage().getMsgtime());
            inboxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(view.getContext(), ChatScreen.class);
                    intent.putExtra("user_name", chatVector.get(position).getPhoneBookContacts().getFirstName());
                    intent.putExtra("user_id", chatVector.get(position).getJid());
                    intent.putExtra("user_pic", chatVector.get(position).getPhoneBookContacts().getFileName());
                    startActivity(intent);
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


    public void refreshList() {
        Log.e("Refresh", "Open messages");

        mHandler.post(new Runnable() {

            @Override
            public void run() {
                ((BaseAdapter) inboxList.getAdapter()).notifyDataSetChanged();
            }
        });
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
                        im.deleteOpenChat(openChats);
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
}
