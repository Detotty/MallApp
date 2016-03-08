package com.mallapp.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatdbserver.utils.IMessageStatus;
import com.chatdbserver.xmpp.IMManager;
import com.chatdbserver.xmpp.listener.IXMPPChatListener;
import com.chatdbserver.xmpp.model.SingleChat;
import com.mallapp.Constants.AppConstants;
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
import java.util.Vector;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class ChatScreen extends Activity implements IXMPPChatListener {

    String filePath;
    String contactNumber = "", contactName = "";
    IMManager im;
    static Vector<SingleChat> messageVector = new Vector<SingleChat>();
    EditText messageText;
    ImageView sel_img;
    Button sendButton;
    public static ListView chatMessageList;
    public static Handler handle;
    boolean isSliderShown = false;
    Animation animShow, animHide;
    LinearLayout sent;
    private GridView gridview, stkr_grid;
    String stringLongitude;
    public static String longi = null;
    public static String lat = null;
    String participent = "";
    String participent_pic = "";
    String participent_name = "";
    String my_pic = "";
    UserProfileModel userProfile;
    private ArrayList<String> messages = new ArrayList<String>();
    Bitmap yourSelectedImage;
    private Handler mHandler = null;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        im.setChatListener(this);
        refresList();
        super.onResume();
    }

    @Override
     protected void onPause() {
        super.onPause();
        im.setChatListener(null);
    }

    String stringLatitude;

    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);
        mHandler = new Handler();
        im = IMManager.getIMManager(this);
        userProfile = (UserProfileModel) DataHandler.getObjectPreferences(AppConstants.PROFILE_DATA, UserProfileModel.class);
        my_pic = userProfile.getImageBase64String();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(pol);
        }
        if (getIntent().getExtras() != null) {
            participent = getIntent().getExtras().getString("user_id");
            participent_pic = getIntent().getExtras().getString("user_pic");
            participent_name = getIntent().getExtras().getString("user_name");
        }
        handle = new Handler();
        TextView title = (TextView) findViewById(R.id.heading);
        title.setText(participent_name);
        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);

            }
        });
        showChatList();
        loadUi();
    }

    public void showChatList() {
        Vector<SingleChat> db_msg = im.getAllChatsByJid(participent);
        chatMessageList = (ListView) findViewById(R.id.chat_listview);
        chatMessageList.setSelected(false);
        chatMessageList.setAdapter(new chatListBaseAdapter(getBaseContext(), db_msg));
    }

    private void loadUi() {
        // TODO Auto-generated method stub
        messageText = (EditText) findViewById(R.id.edt_messages);
        messageText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Button take_pic = (Button) findViewById(R.id.take_pic);
        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfileImage(getString(R.string.add_photo));
            }
        });
        sendButton = (Button) findViewById(R.id.send_msg);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String text = messageText.getText().toString().trim();
                if (text == null || text.length() < 1) {
                    Toast.makeText(v.getContext(), "Cannot send empty Message!", Toast.LENGTH_SHORT).show();

                } else if (IMManager.getIMManager(null).isXmmpConnected()) {
                    SingleChat obj = im.sendIMMessage(participent, text);
                    messageText.setText("");

                    newMessage(obj);
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
        messageText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


            }
        });


    }

    private void selectProfileImage(String dialogTitle) {
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.choose_gallery), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatScreen.this);
        builder.setTitle(dialogTitle);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getString(R.string.take_photo))) {
                    final int CAMERA_PIC_REQUEST = 2500;
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                } else if (options[item].equals(getString(R.string.choose_gallery))) {
                    Intent i = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    final int ACTIVITY_SELECT_IMAGE = 1234;
                    startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
                } else if (options[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void newMessage(SingleChat message) {
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
                    ((BaseAdapter) chatMessageList.getAdapter()).notifyDataSetChanged();
                    chatMessageList.setSelection(chatMessageList.getAdapter().getCount() - 1);
                }


            }
        });
    }

    static class ViewHolder {
        TextView urMessage, urDate, otherDate, otherMessage, urMsgStatus;
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
//                holder.urMsgStatus.setVisibility(View.VISIBLE);

                Picasso.with(con).load(my_pic).placeholder(R.drawable.avatar).into(holder.urImage);
                try {
                    byte[] imageByte = Base64.decode(my_pic, Base64.DEFAULT);
                    Bitmap bm = StaticLiterls.getResizedBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
                    holder.urImage.setImageBitmap(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
                        holder.img_gallery.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Intent i = new Intent(ChatScreen.this, FullScreenImage.class);
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
                Picasso.with(con).load(participent_pic).placeholder(R.drawable.avatar).into(holder.otherImage);
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
                        if( messageVector.get(position).getLocalUrl()!=null) {
                            final String file = messageVector.get(position).getLocalUrl();
                            holder.img_rcv.setVisibility(View.VISIBLE);
                            Picasso.with(con).load(file).placeholder(R.drawable.avatar).into(holder.img_rcv);

                            holder.img_rcv.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    Intent i = new Intent(ChatScreen.this, FullScreenImage.class);
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

//				String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//				obj.setTime(mydate);
//				obj.setNumber(contactNumber);
//				obj.setReceived(true);
//				obj.setName(contactName);
                    //obj.saveToDb(v.getContext());

                    //  sel_img.setImageBitmap(image);
                }
                break;

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
                StaticLiterls.showProgressDialog(ChatScreen.this);
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
                SingleChat obj = im.sendIMMessage(participent, "(ce_template_photomsg)" + imag);
                obj.setLocalUrl(imag);
                im.updateMsgToDB(participent, obj);
                newMessage(obj);
            }

        }
        PostImage postedimage = new PostImage();
        postedimage.execute(img);
    }


    @Override
    public void newMessageArrived(final SingleChat chat) {
        // TODO Auto-generated method stub
        Log.e("Recv msg", "" + chat.getMessage());

        //messages.add(fromName + ":");
        messages.add(chat.getMessage());
        // add the incoming message to the list view
        handle.post(new Runnable() {
            public void run() {
                //  setListAdapter();
                String simple = messages.toString()
                        .replace(",", "")
                        .replace("[", "")
                        .replace("]", "")
                        .trim();
                if (messages.contains("long:")) {

//                    ArrayList<Dimensions> list = getDimentions(messages);
//                    for (Dimensions dm : list) {
//                        longi = dm.getLatitude();
//                        lat = dm.getLongitude();
//                        Log.e("long", "" + longi);
//                        Log.e("lat", "" + lat);
//                    }
//                    String map = "http://maps.google.com/?q=" + lat + "," + longi;
//                    Log.e("Map:", "" + map);
////          		 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
////          		 startActivity(browserIntent);
//                    Bitmap Img_location = BitmapFactory.decodeResource(getResources(), R.drawable.map);
//
////          		 MessagesObject obj=new MessagesObject();
////   				obj.setMessage(Img_location);
////   				String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
////					obj.setTime(mydate);
////					obj.setNumber(contactuser);
////   				obj.setReceived(false);
////   				obj.setName(contactName);
//                    //obj.saveToDb(v.getContext());
//                    //chat.setBitmap(Img_location);
//                    chat.setLat(longi);
//                    chat.setLongg(lat);
//                    im.updateMsgToDB(contactuser, chat);
//                    messageText.setText("");
//                    newMessage(chat);
//                    messages.clear();
//   				Intent i = new Intent(ChatScreen.this,Recieved_location.class);
//   				startActivity(i);


                } else if (chat.getMessage().startsWith("(ce_template_photomsg)")) {
                    String cleanurl = chat.getMessage()
                            .replace("(ce_template_photomsg)", "")
                            .trim();
                    Log.e("Url", "" + cleanurl);
                    chat.setLocalUrl(cleanurl.trim());
                    chat.setMsgStatus(IMessageStatus.MESSAGEREAD);
                    im.updateMsgToDB(participent, chat);
                    messageText.setText("");
                    newMessage(chat);
                    messages.clear();
                } else {
                    messageText.setText("");
                    chat.setMsgStatus(IMessageStatus.MESSAGEREAD);
                    im.updateMsgToDB(participent, chat);
                    newMessage(chat);
                    messages.clear();
                }
            }
        });

    }

    @Override
    public void messageDelivered(SingleChat singleChat) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showChatList();
            }
        });

    }


    @SuppressWarnings("deprecation")
    public void enableSubmitIfReady() {

        boolean isReady = messageText.getText().toString().length() > 0;
        sendButton.setEnabled(isReady);
        sendButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_send_sel));

    }
}
