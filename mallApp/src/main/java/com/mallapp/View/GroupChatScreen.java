package com.mallapp.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chatdbserver.utils.IMessageStatus;
import com.chatdbserver.xmpp.IMManager;
import com.chatdbserver.xmpp.listener.IGroupChatListener;
import com.chatdbserver.xmpp.model.PhoneBookContacts;
import com.chatdbserver.xmpp.model.SingleChat;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Constants.GlobelWebURLs;
import com.mallapp.Model.UserProfileModel;
import com.mallapp.SharedPreferences.DataHandler;
import com.mallapp.utils.StaticLiterls;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class GroupChatScreen extends Activity implements IGroupChatListener {

    String filePath;
    IMManager im;
    Vector<SingleChat> messageVector = new Vector<SingleChat>();
    EditText messageText;
    Button sendButton;
    public static ListView chatMessageList;
    public static Handler handle;
    LinearLayout sent;
    UserProfileModel userProfile;
    Bitmap yourSelectedImage;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

//        refresList();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    TextView location, time, name;
    ImageView announcment_image;
    String group = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);
        chatMessageList = (ListView) findViewById(R.id.chat_listview);
        TextView title = (TextView) findViewById(R.id.heading);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        im = IMManager.getIMManager(this);

        userProfile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);

        im.setGroupChatListener(GroupChatScreen.this);

        group = getIntent().getStringExtra("group_title").replace(" ", "_").toLowerCase();
        ArrayList<String> arrayList = getIntent().getStringArrayListExtra("group_members");
        boolean create = getIntent().getBooleanExtra("create", false);
        boolean announcement = getIntent().getBooleanExtra("announcement", false);

        try {
            if (create) {
                boolean success = im.createGroup(group);
                if (success) {
                    im.joinRoom(group, GlobelWebURLs.ce_user + userProfile.getUserId());
                    im.sendGroupMessage("Group Created");
                    for (int i = 0; i < arrayList.size(); i++) {
                        String string = arrayList.get(i);
                        im.inviteRoom(group, string);
                    }
                }
            } else {
                im.joinRoom(group, GlobelWebURLs.ce_user + userProfile.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageButton add_participant = (ImageButton) findViewById(R.id.right_button);
        add_participant.setVisibility(View.VISIBLE);
        add_participant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopup(v);
            }
        });
        Vector<SingleChat> db_msg = im.getAllChatsByJid(group);
        chatMessageList.setAdapter(new chatListBaseAdapter(getBaseContext(), db_msg));
        List<PhoneBookContacts> participants = new ArrayList<>();
        Map<String, PhoneBookContacts> map = new LinkedHashMap<>();
        for (SingleChat ays : db_msg) {
            map.put(ays.getPhoneBookContacts().getUserId(), ays.getPhoneBookContacts());
        }
        participants.clear();
        participants.addAll(map.values());
        StaticLiterls.particpantsList = participants;

        handle = new Handler();
        if (announcement) {
            LayoutInflater inflater = getLayoutInflater();
            ViewGroup header = (ViewGroup) inflater.inflate(R.layout.ann_chat_layout, chatMessageList, false);
            location = (TextView) header.findViewById(R.id.announcement_location);
            time = (TextView) header.findViewById(R.id.announcement_time);
            name = (TextView) header.findViewById(R.id.announcement_name);
            announcment_image = (ImageView) header.findViewById(R.id.announcement_img);
            location.setText(StaticLiterls.announcement_location);
            time.setText(StaticLiterls.changeDateFormat(StaticLiterls.announcement_time));
            name.setText(StaticLiterls.announcement_name);
            announcment_image.setImageBitmap(StaticLiterls.announcement_bitmap);
            chatMessageList.addHeaderView(header, null, false);
            title.setText(StaticLiterls.announcement_name);

        } else {
            title.setText(group.split("@")[0].replace("_", " "));

        }

        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);

            }
        });
//        showChatList();
        loadUi();
    }

    public void showChatList() {


        chatMessageList.setSelected(false);

    }

    private void loadUi() {
        // TODO Auto-generated method stub
        messageText = (EditText) findViewById(R.id.edt_messages);
        messageText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Button take_pic = (Button) findViewById(R.id.take_pic);
        take_pic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfileImage(getString(R.string.add_photo));
            }
        });
        sendButton = (Button) findViewById(R.id.send_msg);
        sendButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String text = messageText.getText().toString().trim();
                if (text == null || text.length() < 1) {
                    Toast.makeText(v.getContext(), "Cannot send empty Message!", Toast.LENGTH_SHORT).show();

                } else if (IMManager.getIMManager(null).isXmmpConnected()) {
                    im.sendGroupMessage(text);
                    messageText.setText("");
                } else {
                    Toast.makeText(v.getContext(), "Error server connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
                if (messageText.getText().toString().trim().equals("")) {
                    sendButton.setBackgroundResource(R.drawable.ic_send_holo_light);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        messageText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


            }
        });


    }

    private void selectProfileImage(String dialogTitle) {
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.choose_gallery), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupChatScreen.this);
        builder.setTitle(dialogTitle);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getString(R.string.take_photo))) {
                    final int CAMERA_PIC_REQUEST = 2500;
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                } else if (options[item].equals(getString(R.string.choose_gallery))) {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    final int ACTIVITY_SELECT_IMAGE = 1234;
                    startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                } else if (options[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void newMessage(SingleChat message) {
        if (messageVector != null)
            messageVector.add(message);
        Log.e(" Size : ", "" + messageVector.size());
//		db.addListItem(messageVector);
//		  cursor = db.getListItem();
//	        Log.e("count", "" + cursor.getCount());
//	        if (cursor != null && cursor.moveToFirst()) {
//	          
//
//	            do {
//
//	                Log.e("value==", "" + cursor.getString(1));
//	                Log.e("value==", "" + cursor.getString(2));
//	                Log.e("value==", "" + cursor.getString(3));
//
//	            } while (cursor.moveToNext());
//
//	        }

        refresList();


    }

    //	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		
//		Presence presence = new Presence(Presence.Type.unavailable);
//        Log.e("Sign Out: ", ""+presence.toString());
//        con.sendPacket(presence);
//	}
    public static void refresList() {
        handle.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (chatMessageList != null) {
                    chatMessageList.setSelection(chatMessageList.getAdapter().getCount() - 1);
                }


            }
        });
    }


    static class ViewHolder {
        TextView urMessage, urDate, otherDate, otherMessage, urMsgStatus, participant_name;
        ImageView img_gallery, img_rcv;
        View urVIew, OtherView;
        ImageView urImage, otherImage;
        LinearLayout send_layout;

    }

    public class chatListBaseAdapter extends BaseAdapter {
        //private static Vector<DealObject> searchArrayList;
        Context con;
        private LayoutInflater mInflater;

        public chatListBaseAdapter(Context pCon, Vector<SingleChat> messageVecto) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(pCon);
            messageVector = messageVecto;
            con = pCon;
        }

        public int getCount() {
//			System.out.println("GetCount " + messageVector.size());

            return messageVector.size();
        }

        public Object getItem(int position) {
            return messageVector.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.buble_chat_list_row, null);
                holder = new ViewHolder();
                sent = (LinearLayout) findViewById(R.id.send);

                holder.send_layout = (LinearLayout) convertView.findViewById(R.id.send);
                holder.urVIew = convertView.findViewById(R.id.ur_layout);
                holder.urImage = (ImageView) convertView.findViewById(R.id.ur_image);
                holder.urDate = (TextView) convertView.findViewById(R.id.urdate);
                holder.urMessage = (TextView) convertView.findViewById(R.id.ur_message);
                holder.urVIew = (View) convertView.findViewById(R.id.ur_layout);
                holder.otherImage = (ImageView) convertView.findViewById(R.id.other_image);

                holder.OtherView = (View) convertView.findViewById(R.id.other_layout);
                holder.otherDate = (TextView) convertView.findViewById(R.id.otherdate);
                holder.otherMessage = (TextView) convertView.findViewById(R.id.other_message);
                holder.urMsgStatus = (TextView) convertView.findViewById(R.id.urstatus);
                holder.participant_name = (TextView) convertView.findViewById(R.id.participent_name);
                holder.img_gallery = (ImageView) convertView.findViewById(R.id.img_gall);
                holder.img_rcv = (ImageView) convertView.findViewById(R.id.img_rcv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (messageVector.get(position).getMessageType() == IMessageStatus.MESSAGEIN) {
                holder.otherMessage.setVisibility(View.GONE);
                holder.OtherView.setVisibility(View.GONE);
                holder.otherDate.setVisibility(View.GONE);
                holder.otherImage.setVisibility(View.GONE);
                holder.urVIew.setVisibility(View.VISIBLE);
                holder.urMessage.setVisibility(View.VISIBLE);
                holder.urDate.setVisibility(View.VISIBLE);
                holder.urImage.setVisibility(View.VISIBLE);
                holder.img_gallery.setVisibility(View.GONE);
                holder.img_rcv.setVisibility(View.GONE);
                holder.participant_name.setVisibility(View.GONE);
//                holder.urMsgStatus.setVisibility(View.VISIBLE);

                Picasso.with(con).load(messageVector.get(position).getPhoneBookContacts().getFileName()).placeholder(R.drawable.avatar).into(holder.urImage);


                holder.urDate.setText(messageVector.get(position).getMsgtime());

//                if (messageVector.get(position).getMsgStatus() == IMessageStatus.MESSAGESENT) {
//                    holder.urMsgStatus.setText("sent");
//                } else if (messageVector.get(position).getMsgStatus() == IMessageStatus.MESSAGEDELIVERED) {
//                    holder.urMsgStatus.setText("delivered");
//                } else if (messageVector.get(position).getMsgStatus() == IMessageStatus.MESSAGESEEN) {
//                    holder.urMsgStatus.setText("seen");
//                }
                if (!messageVector.get(position).getMessage().equals("")) {
                    if (messageVector.get(position).getMessage().length() >= 5 && messageVector.get(position).getMessage().substring(0, 5).equals("long:")) {
                        holder.urMessage.setText("");
//                        Bitmap Img_location = BitmapFactory.decodeResource(getResources(), R.drawable.map);
//                        holder.img_gallery.setImageBitmap(Img_location);
//                        holder.img_gallery.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                // TODO Auto-generated method stub
//                                Intent i = new Intent(ChatScreen.this, ViewLocation.class);
//                                startActivity(i);
//                            }
//                        });
                    } else if (messageVector.get(position).getMessage().startsWith("(ce_template_photomsg)")) {
                        holder.urMessage.setText("");
//						Bitmap Img_location = BitmapFactory.decodeResource(getResources(), R.drawable.map);
//						holder.img_gallery.setImageBitmap(messageVector.get(position).getBitmap());
//						holder.img_rcv.setImageBitmap(messageVector.get(position).getBitmap());
                        final String file = messageVector.get(position).getLocalUrl().trim();
                        holder.img_gallery.setVisibility(View.VISIBLE);
                        Picasso.with(con).load(file).placeholder(R.drawable.avatar).into(holder.img_gallery);
                        holder.img_gallery.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Intent i = new Intent(GroupChatScreen.this, FullScreenImage.class);
                                i.putExtra("url", file);
                                startActivity(i);
                            }
                        });
                    } else {
                        holder.urMessage.setText(messageVector.get(position).getMessage());
                        holder.img_gallery.setImageBitmap(messageVector.get(position).getBitmap());
                    }

                }
//				

            } else {
                holder.otherMessage.setVisibility(View.VISIBLE);
                holder.OtherView.setVisibility(View.VISIBLE);
                holder.otherDate.setVisibility(View.VISIBLE);
                holder.otherImage.setVisibility(View.VISIBLE);
                holder.urVIew.setVisibility(View.GONE);
                holder.urMessage.setVisibility(View.GONE);
                holder.urDate.setVisibility(View.GONE);
                holder.urImage.setVisibility(View.GONE);
                holder.img_gallery.setVisibility(View.GONE);
                holder.img_rcv.setVisibility(View.GONE);
                holder.urMsgStatus.setVisibility(View.GONE);
                holder.participant_name.setVisibility(View.VISIBLE);
                Picasso.with(con).load(messageVector.get(position).getPhoneBookContacts().getFileName()).placeholder(R.drawable.avatar).into(holder.otherImage);
                holder.participant_name.setText(messageVector.get(position).getPhoneBookContacts().getFirstName());
                holder.otherDate.setText(messageVector.get(position).getMsgtime());
                if (!messageVector.get(position).getMessage().equals("")) {
                    if (messageVector.get(position).getMessage().length() >= 5 && messageVector.get(position).getMessage().substring(0, 5).equals("long:")) {
                        holder.otherMessage.setText("");
//                        Bitmap Img_location = BitmapFactory.decodeResource(getResources(), R.drawable.map);
//                        holder.img_rcv.setImageBitmap(Img_location);
//
//                        holder.img_rcv.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                // TODO Auto-generated method stub
//                                Intent i = new Intent(ChatScreen.this, ViewLocation.class);
//                                startActivity(i);
//                            }
//                        });
                    } else if (messageVector.get(position).getMessage().startsWith("(ce_template_photomsg)")) {
                        holder.otherMessage.setText("");
//						final String file=messageVector.get(position).getLocalUrl();
                        if (messageVector.get(position).getLocalUrl() != null) {
                            final String file = messageVector.get(position).getLocalUrl();
                            holder.img_rcv.setVisibility(View.VISIBLE);
                            Picasso.with(con).load(file).placeholder(R.drawable.avatar).into(holder.img_rcv);

                            holder.img_rcv.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(GroupChatScreen.this, FullScreenImage.class);
                                    i.putExtra("url", file);
                                    startActivity(i);
                                }
                            });
                        }
                    } else {
                        holder.otherMessage.setText(messageVector.get(position).getMessage());
                        holder.img_rcv.setImageBitmap(messageVector.get(position).getBitmap());

                    }

                }
//				


            }


            return convertView;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                String profile_id = data.getStringExtra("pro_id");
                String profile_name = data.getStringExtra("pro_name");
                im.inviteRoom(group, profile_id);
                im.sendGroupMessage(userProfile.getFullName() + getString(R.string.grp_msg) + profile_name + getString(R.string.grp_msg1));
            }
        }
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Post(filePath);
                    yourSelectedImage = BitmapFactory.decodeFile(filePath);
                }

                break;
            case 2500:
                if (resultCode == RESULT_OK) {
                    yourSelectedImage = (Bitmap) data.getExtras().get("data");
                    Uri tempUri = getImageUri(getApplicationContext(), yourSelectedImage);
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    filePath = finalFile.toString();
                    Post(filePath);


                }


        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public void Post(final String img) {


        class PostImage extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                StaticLiterls.showProgressDialog(GroupChatScreen.this);
            }

            protected String doInBackground(String... image) {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(StaticLiterls.ImImageUrl);
                MultipartEntity mpEntity = new MultipartEntity();
                String pic = img;
                String Response = "";
                File file = new File(img);
                final ContentBody cbFile = new FileBody(file, "image/jpeg");
                try {

                    mpEntity.addPart("file", cbFile);
                    mpEntity.addPart("filename", new StringBody(pic));

                    post.setEntity(mpEntity);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                try {
                    HttpResponse response1 = client.execute(post);
                    HttpEntity resEntity = response1.getEntity();
                    Response = EntityUtils.toString(resEntity);
                    Log.e("Response:", Response);

                    //Link:http://www.webspeaks.in/2012/08/upload-files-from-android-to-server.html
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return Response;
            }

            protected void onPostExecute(String imag) {
                StaticLiterls.DismissesDialog();
                im.sendGroupMessage("(ce_template_photomsg)" + imag);
            }

        }
        PostImage postedimage = new PostImage();
        postedimage.execute(img);
    }


    @Override
    public void groupJoined(String str) {

    }

    @Override
    public void leavegroup(String str) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        im.setGroupChatListener(null);
    }

    @Override
    public void newGroupMessageArrived(final SingleChat singleChat, final String jid) {


        Log.e("From", "" + singleChat.getId());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                singleChat.setMsgStatus(IMessageStatus.MESSAGEREAD);
                im.updateMsgToDB(jid, singleChat);
                newMessage(singleChat);
            }
        });


    }

    @SuppressWarnings("deprecation")
    public void enableSubmitIfReady() {

        boolean isReady = messageText.getText().toString().length() > 0;
        sendButton.setEnabled(isReady);
        sendButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_send_sel));

    }

    public void openPopup(View v) {


        LayoutInflater layoutInflater = (LayoutInflater) v.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(
                R.layout.pop_up_window, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.AnimationPopup);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub

            }
        });
        Button btn2 = (Button) popupView.findViewById(R.id.btn1);
        btn2.setVisibility(View.GONE);

        Button btn1 = (Button) popupView.findViewById(R.id.btn2);
        btn1.setText(R.string.add_participants);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(GroupChatScreen.this, InviteContacts.class);
                intent.putExtra("participant", true);
                startActivityForResult(intent, 1);
                popupWindow.dismiss();

            }
        });

        Button cancel = (Button) popupView.findViewById(R.id.cancel);
        cancel.setVisibility(View.VISIBLE);
        cancel.setText(R.string.show_participants);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupChatScreen.this, GroupParticipants.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        Button dismis = (Button) popupView.findViewById(R.id.cancelone);
        dismis.setVisibility(View.VISIBLE);
        dismis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

            }
        });

    }
}