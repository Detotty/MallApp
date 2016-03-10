package com.mallapp.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.List.Adapter.BarcodePreviewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Model.BarcodeTypeModel;
import com.mallapp.listeners.UniversalDataListener;
import com.mallapp.utils.VolleyNetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharjeel on 2/2/2016.
 */
public class BarcodePreviewActivity  extends Activity implements UniversalDataListener{

    ImageButton btnBack;
    TextView heading;
    EditText et_cardName,et_CardNum,et_CardProvider,et_BarcodeNum;
    VolleyNetworkUtil volleyNetworkUtil;
    BarcodePreviewAdapter barcodePreviewAdapter;
    ListView BarcodetypeList;
    public static String Barcodetype="UCP-A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_preview);
        init();
        volleyNetworkUtil = new VolleyNetworkUtil(this);
        volleyNetworkUtil.GetBarcodeTypes(ApiConstants.GET_BARCODE_TYPES,this);
    }

    void init(){
        btnBack = (ImageButton) findViewById(R.id.back);
        heading = (TextView) findViewById(R.id.heading);
        et_cardName = (EditText) findViewById(R.id.et_CardName);
        et_CardNum = (EditText) findViewById(R.id.et_addNum);
        et_CardProvider = (EditText) findViewById(R.id.et_providerName);
        et_BarcodeNum = (EditText) findViewById(R.id.et_barcodeName);
        BarcodetypeList = (ListView) findViewById(R.id.list_barcode);
        heading.setText(getResources().getString(R.string.preview_barcode_header));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onDataReceived(JSONObject jsonObject, JSONArray jsonArray) {
        Gson gson = new Gson();
        String data = jsonArray.toString();
        Type listType = new TypeToken<List<BarcodeTypeModel>>() {
        }.getType();

        ArrayList<BarcodeTypeModel> model = gson.fromJson(data, listType);
        barcodePreviewAdapter = new BarcodePreviewAdapter(getApplicationContext(),this, R.layout.list_floor_view, model);
        BarcodetypeList.setAdapter(barcodePreviewAdapter);
        for (BarcodeTypeModel bar: model
             ) {
            if (bar.getBarcodeType1().replace("_", "-").equals(Barcodetype)){
                BarcodetypeList.setSelection(model.indexOf(bar));
            }
        }

    }

    @Override
    public void OnError(String message) {

    }

}
