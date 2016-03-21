package com.mallapp.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Fragments.CardTabFragments;
import com.mallapp.Model.LoyaltyCardModel;
import com.mallapp.ServicesApi.MySqlConnection;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.imagecapture.Image_Scaling;
import com.mallapp.listeners.UniversalDataListener;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.BitmapLoadUtils;
import com.mallapp.utils.StaticLiterls;
import com.mallapp.utils.Utils;
import com.mallapp.utils.VolleyNetworkUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Sharjeel on 2/2/2016.
 */
public class AddCardActivity extends Activity implements View.OnClickListener, UniversalDataListener {

    TextView heading, bc_type, tvIssueDate, tvExpiryDate;

    Button btnDone, btnDelCard;

    ImageButton btnBack;

    ImageView btn_front_cam, btn_back_cam, barcode, btnDel1, btnDel2, frontCard, backCard;

    EditText etCardName, etCardNum, etProviderName, etBarcodeNum, etDescription;

    RelativeLayout layout_BarcodeType;
    LinearLayout layout_addCard;

    private Date dateOfBirthday, exp;
    private int day;
    private int month;
    private int year;

    public static Bitmap CropedImage;
    Bitmap bitmapFront, bitmapBack;

    static boolean add_photo, front_image;
    boolean isCardLoaded = false;
    boolean isDel = false;
    boolean isAdd = false;

    VolleyNetworkUtil volleyNetworkUtil;

    LoyaltyCardModel loyaltyCardModel;

    MaterialDialog mMaterialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loyalty_card);
        BarcodePreviewActivity.Barcodetype = "";
        volleyNetworkUtil = new VolleyNetworkUtil(this);
        loyaltyCardModel = new LoyaltyCardModel();
        isAdd = getIntent().getBooleanExtra("add", true);
        init();
        try {
            loyaltyCardModel = (LoyaltyCardModel) getIntent().getSerializableExtra(MainMenuConstants.LOYALTY_CARD_OBJECT);
            setDetails();
            btnDelCard.setVisibility(View.VISIBLE);
            isCardLoaded = true;
        } catch (Exception e) {
            loyaltyCardModel = new LoyaltyCardModel();
            e.printStackTrace();
        }
    }

    void init() {

        heading = (TextView) findViewById(R.id.heading);
        bc_type = (TextView) findViewById(R.id.bc_type);
        tvExpiryDate = (TextView) findViewById(R.id.et_expiryDate);
        tvIssueDate = (TextView) findViewById(R.id.et_issueDate);

        btnDone = (Button) findViewById(R.id.btnDone);
        btnDelCard = (Button) findViewById(R.id.btnDelCard);

        btnBack = (ImageButton) findViewById(R.id.back);

        btnDel1 = (ImageView) findViewById(R.id.btnDel);
        btnDel2 = (ImageView) findViewById(R.id.btnDel1);
        btn_front_cam = (ImageView) findViewById(R.id.btnCam);
        btn_back_cam = (ImageView) findViewById(R.id.btnCam2);
        frontCard = (ImageView) findViewById(R.id.front_card);
        backCard = (ImageView) findViewById(R.id.back_card);
        barcode = (ImageView) findViewById(R.id.btnBarcode);

        etCardName = (EditText) findViewById(R.id.et_CardName);
        etCardNum = (EditText) findViewById(R.id.et_addNum);
        etProviderName = (EditText) findViewById(R.id.et_providerName);
        etBarcodeNum = (EditText) findViewById(R.id.et_barcodeName);
        etDescription = (EditText) findViewById(R.id.et_description);

        layout_BarcodeType = (RelativeLayout) findViewById(R.id.barcode_type);
        layout_addCard = (LinearLayout) findViewById(R.id.layout_addcard);

        if (isAdd)
            heading.setText(getResources().getString(R.string.create));
        else
            heading.setText(getResources().getString(R.string.update));
        btnDone.setOnClickListener(this);
        btnDelCard.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btn_front_cam.setOnClickListener(this);
        btn_back_cam.setOnClickListener(this);
        barcode.setOnClickListener(this);
        btnDel1.setOnClickListener(this);
        btnDel2.setOnClickListener(this);
        tvIssueDate.setOnClickListener(this);
        tvExpiryDate.setOnClickListener(this);
        layout_BarcodeType.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(5)
                .oval(false)
                .build();
        bc_type.setText(BarcodePreviewActivity.Barcodetype);
        if (CropedImage != null) {
            if (front_image) {
                Picasso.with(this).load(getImageUri(this, CropedImage)).fit().transform(transformation).into(frontCard);
                bitmapFront = BitmapLoadUtils.compress(Bitmap.createScaledBitmap(CropedImage, 800, 450, true), 10);
                CropedImage = null;
                btnDel1.setVisibility(View.VISIBLE);
            } else {
                Picasso.with(this).load(getImageUri(this, CropedImage)).fit().transform(transformation).into(backCard);
                bitmapBack = BitmapLoadUtils.compress(Bitmap.createScaledBitmap(CropedImage, 800, 450, true), 10);
                CropedImage = null;
                btnDel2.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == btnBack.getId()) {
            finish();
        } else if (v.getId() == tvIssueDate.getId()) {
            show_date(true);
        } else if (v.getId() == tvExpiryDate.getId()) {
            show_date(false);
        } else if (v.getId() == btnDone.getId()) {
            if (Utils.isInternetAvailable(this)) {
                if (isCardLoaded) {
                    bitmapFront = ((BitmapDrawable) frontCard.getDrawable()).getBitmap();
                    bitmapBack = ((BitmapDrawable) backCard.getDrawable()).getBitmap();
                }
                if (validation()) {
                    PostCardData();
                }
            } else
                AppUtils.matDialog(this, getResources().getString(R.string.no_internet), getResources().getString(R.string.network_error));

        } else if (v.getId() == btn_front_cam.getId()) {
            add_photo = true;
            front_image = true;
            selectCardImage("Add Photo!");
        } else if (v.getId() == btn_back_cam.getId()) {
            add_photo = true;
            front_image = false;
            selectCardImage("Add Photo!");
        } else if (v.getId() == barcode.getId()) {
            add_photo = false;
            new IntentIntegrator(this).initiateScan();
        } else if (v.getId() == layout_BarcodeType.getId()) {
            Intent intent = new Intent(this, BarcodePreviewActivity.class);
            startActivity(intent);
        } else if (v.getId() == btnDel1.getId()) {
            frontCard.setImageDrawable(getDrawable(R.drawable.front_card));
            btnDel1.setVisibility(View.INVISIBLE);
            bitmapFront = null;
        } else if (v.getId() == btnDel2.getId()) {
            backCard.setImageDrawable(getDrawable(R.drawable.back_card));
            btnDel2.setVisibility(View.INVISIBLE);
            bitmapBack = null;
        } else if (v.getId() == btnDelCard.getId()) {
            mMaterialDialog = new MaterialDialog(AddCardActivity.this)
                    .setTitle(getResources().getString(R.string.logout_dialog_title))
                    .setMessage(getResources().getString(R.string.del_card_message))
                    .setPositiveButton(getResources().getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                            isDel = true;
                            volleyNetworkUtil.PostDelCard(ApiConstants.DEL_LOYALTY_CARD + loyaltyCardModel.getId(), AddCardActivity.this);
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel_button_title), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    });

            mMaterialDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (add_photo) {
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                Uri selectedImageUri = data.getData();
                Intent intent = new Intent(AddCardActivity.this, CropActivity.class);
                intent.setData(selectedImageUri);
                startActivity(intent);
            }
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Log.d("AddCardActivity", "Cancelled scan");
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("AddCardActivity", "Scanned");
                    Toast.makeText(this, "Scanned: " + result.getFormatName(), Toast.LENGTH_LONG).show();
                    etBarcodeNum.setText(result.getContents());
                    String type = result.getFormatName();
                    BarcodePreviewActivity.Barcodetype = AppUtils.getBarcodeType(type);
                }
            } else {
                // This is important, otherwise the result will not be passed to the fragment
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }


    private void show_date(final boolean issue) {
        if (issue) {
            if (dateOfBirthday == null) {
                final Calendar c = Calendar.getInstance();
                Log.d("", "date default:" + c.getTime());

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                dateOfBirthday = c.getTime();
            } else {
                final Calendar c = Calendar.getInstance();
                c.setTime(dateOfBirthday);
                Log.d("", "user date:" + c.getTime());

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }
        } else {
            if (exp == null) {
                final Calendar c = Calendar.getInstance();
                Log.d("", "date default:" + c.getTime());

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                exp = c.getTime();
            } else {
                final Calendar c = Calendar.getInstance();
                c.setTime(exp);
                Log.d("", "user date:" + c.getTime());

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }
        }

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                if (view.isShown()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);

                    System.out.println("Date : " + sdf.format(calendar.getTime()));
                    System.out.println("Date : " + year + monthOfYear + dayOfMonth);

                    Calendar c = Calendar.getInstance();
                    c.set(year, monthOfYear, dayOfMonth);


                    if (issue) {
                        try {
                            dateOfBirthday = c.getTime();
                            if (!tvExpiryDate.getText().toString().isEmpty()) {
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                                cal.setTime(sdfs.parse(tvExpiryDate.getText().toString()));
                                if (calendar.before(cal)) {
                                    tvIssueDate.setText("" + sdf.format(calendar.getTime()));
                                } else {
                                    showMessage(getResources().getString(R.string.iss_card_message));
                                }
                            } else {
                                tvIssueDate.setText("" + sdf.format(calendar.getTime()));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            exp = c.getTime();
                            if (!tvIssueDate.getText().toString().isEmpty()) {
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                                cal.setTime(sdfs.parse(tvIssueDate.getText().toString()));
                                if (calendar.after(cal)) {
                                    tvExpiryDate.setText("" + sdf.format(calendar.getTime()));
                                } else {
                                    showMessage(getResources().getString(R.string.exp_card_message));
                                }
                            } else {
                                showMessage(getResources().getString(R.string.iss_emp_message));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    monthOfYear = monthOfYear + 1;
                    // locationTextView.requestFocus();
                }
                // String dateS = String.format("%02d", monthOfYear);
                // String date_d = String.format("%02d", dayOfMonth);
            }
        }, year, month, day);

        if (!issue) {
            Calendar maxDate = Calendar.getInstance();
            maxDate.set(Calendar.YEAR, 2016);
            dpd.getDatePicker().setMinDate(maxDate.getTimeInMillis() - 1000);
        } else {
            Calendar maxDate = Calendar.getInstance();
            maxDate.set(Calendar.YEAR, 2016);
            dpd.getDatePicker().setMaxDate(maxDate.getTimeInMillis() - 1000);
        }

        // dpd.getDatePicker().


        dpd.show();
    }

    //region Form Validations
    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidCardName() {
        if (TextUtils.isEmpty(etCardName.getText().toString())) {
            showMessage(getResources().getString(R.string.enter_card_name));
        }
        return !TextUtils.isEmpty(etCardName.getText().toString());
    }

    private boolean isValidCardNum() {
        if (TextUtils.isEmpty(etCardNum.getText().toString())) {
            showMessage(getResources().getString(R.string.enter_card_num));
        }
        return !TextUtils.isEmpty(etCardNum.getText().toString());
    }

    private boolean isValidIssueDate() {
        if (TextUtils.isEmpty(tvIssueDate.getText().toString())) {
            showMessage(getResources().getString(R.string.enter_card_issue));
        }
        return !TextUtils.isEmpty(tvIssueDate.getText().toString());
    }

    private boolean isValidExpiryDate() {
        if (TextUtils.isEmpty(tvExpiryDate.getText().toString())) {
            showMessage(getResources().getString(R.string.enter_card_expiry));
        }
        return !TextUtils.isEmpty(tvExpiryDate.getText().toString());
    }

    private boolean isValidBarcodeNum() {
        if (TextUtils.isEmpty(etBarcodeNum.getText().toString())) {
            showMessage(getResources().getString(R.string.enter_bar_num));
        }
        return !TextUtils.isEmpty(etBarcodeNum.getText().toString());
    }

    private boolean isValidBcType() {
        if (TextUtils.isEmpty(bc_type.getText().toString())) {
            showMessage(getResources().getString(R.string.select_bar_type));
        }
        return !TextUtils.isEmpty(bc_type.getText().toString());
    }

    private boolean isCardImagesAvailable() {
        if (bitmapFront == null || bitmapBack == null) {
            showMessage(getResources().getString(R.string.select_card_image));
            return false;
        }
        return true;
    }

    private boolean validation() {
        return isCardImagesAvailable() && isValidCardName() && isValidCardNum() && isValidIssueDate() && isValidExpiryDate() && isValidBarcodeNum() && isValidBcType();
    }
    //endregion


    //region Card Images Selection
    private void selectCardImage(String dialogTitle) {
        final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.choose_gallery), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //endregion


    //region Post Card Data
    void PostCardData() {
        loyaltyCardModel.setBarcodeType(bc_type.getText().toString());
        loyaltyCardModel.setBarcode(etBarcodeNum.getText().toString());
        loyaltyCardModel.setCardTitle(etCardName.getText().toString());
        loyaltyCardModel.setIssueDate(tvIssueDate.getText().toString());
        loyaltyCardModel.setExpiryDate(tvExpiryDate.getText().toString());
        loyaltyCardModel.setProviderName(etProviderName.getText().toString());
        loyaltyCardModel.setUserId(SharedPreferenceUserProfile.getUserId(this));
        loyaltyCardModel.setUserNotes(etDescription.getText().toString());
        loyaltyCardModel.setCardNumber(Long.parseLong(etCardNum.getText().toString()));
        loyaltyCardModel.setFrontBase64ImageString(Image_Scaling.encodeTobase64(bitmapFront));
        loyaltyCardModel.setBacksideBase64ImageString(Image_Scaling.encodeTobase64(bitmapBack));

        Gson gson = new Gson();
        String jsonString = gson.toJson(loyaltyCardModel);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
//            volleyNetworkUtil.PostLoyaltyCard(ApiConstants.SAVE_LOYALTY_CARD, jsonObject, this);
            AddCard(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override
    public void onDataReceived(JSONObject jsonObject, JSONArray jsonArray) {
        boolean success = false;
        try {
            success = jsonObject.getBoolean("Success");
            if (success) {
                CardTabFragments.isUpdate = true;
                if (isDel)
                    AppUtils.matDialogFinish(this, getResources().getString(R.string.app_name1), getResources().getString(R.string.card_del), this);
                else if (isAdd) {
                    AppUtils.matDialogFinish(this, getResources().getString(R.string.app_name1), getResources().getString(R.string.card_added), this);
                } else
                    AppUtils.matDialogFinish(this, getResources().getString(R.string.app_name1), getResources().getString(R.string.card_up), this);

            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnError(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, getResources().getString(R.string.card_type_mismatch), Toast.LENGTH_SHORT).show();
    }


    void setDetails() {
        etCardName.setText(loyaltyCardModel.getCardTitle().trim());
        etProviderName.setText(loyaltyCardModel.getProviderName().trim());
        etCardNum.setText(String.valueOf(loyaltyCardModel.getCardNumber()).trim());
        etBarcodeNum.setText(loyaltyCardModel.getBarcode().trim());
        bc_type.setText(loyaltyCardModel.getBarcodeType().trim());
        etDescription.setText(loyaltyCardModel.getUserNotes().trim());
        tvIssueDate.setText(loyaltyCardModel.getIssueDate().substring(0, loyaltyCardModel.getIssueDate().indexOf("T")).trim());
        tvExpiryDate.setText(loyaltyCardModel.getExpiryDate().substring(0, loyaltyCardModel.getExpiryDate().indexOf("T")).trim());
        heading.setText(getResources().getString(R.string.edit));

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(5)
                .oval(false)
                .build();
        Picasso.with(this).load(loyaltyCardModel.getFrontImageUrl()).fit().placeholder(R.drawable.front_card).transform(transformation).into(frontCard);
        Picasso.with(this).load(loyaltyCardModel.getBackImageUrl()).fit().placeholder(R.drawable.back_card).transform(transformation).into(backCard);

        btnDel1.setVisibility(View.VISIBLE);
        btnDel2.setVisibility(View.VISIBLE);

        BarcodePreviewActivity.Barcodetype = loyaltyCardModel.getBarcodeType().trim();
    }


    void AddCard(final JSONObject jsonObject) {
        new AsyncTask<Void, Void, String>() {
            protected void onPreExecute() {
                StaticLiterls.showProgressDialog(AddCardActivity.this);
            }

            @Override
            protected String doInBackground(Void... params) {
                String success = null;
                try {
                    success = MySqlConnection.sendPost(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
                return success;
            }

            @Override
            protected void onPostExecute(String success) {
                StaticLiterls.DismissesDialog();
                try {
                    if (!success.isEmpty()) {
                        JSONObject json = new JSONObject(success);
                        onDataReceived(json, null);
                    } else
                        OnError(success);

                } catch (JSONException e) {
                    e.printStackTrace();
                    OnError(success);
                }
            }
        }.execute(null, null, null);
    }


}
