package com.mallapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.List.Adapter.BarcodePreviewAdapter;

/**
 * Created by Sharjeel on 2/2/2016.
 */
public class AddCardActivity extends Activity implements View.OnClickListener{

    TextView heading, bc_type;
    Button btnDone;
    ImageButton btnBack;
    ImageView front_cam,back_cam,barcode,btnDel1,btnDel2;
    RelativeLayout layout_BarcodeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loyalty_card);

        init();

    }

    void init() {
        heading = (TextView) findViewById(R.id.heading);
        bc_type = (TextView) findViewById(R.id.bc_type);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnBack = (ImageButton) findViewById(R.id.back);
        btnDel1 = (ImageView) findViewById(R.id.btnDel);
        btnDel2 = (ImageView) findViewById(R.id.btnDel1);
        front_cam = (ImageView) findViewById(R.id.btnCam);
        back_cam = (ImageView) findViewById(R.id.btnCam2);
        barcode = (ImageView) findViewById(R.id.btnBarcode);
        layout_BarcodeType = (RelativeLayout) findViewById(R.id.barcode_type);
        heading.setText(getResources().getString(R.string.create));

        btnDone.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        front_cam.setOnClickListener(this);
        back_cam.setOnClickListener(this);
        barcode.setOnClickListener(this);
        btnDel1.setOnClickListener(this);
        btnDel2.setOnClickListener(this);
        layout_BarcodeType.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bc_type.setText(BarcodePreviewActivity.Barcodetype);
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==btnBack.getId()){
            finish();
        }
        else if (v.getId()==btnDone.getId()){

        }
        else if (v.getId()==front_cam.getId()){

        }
        else if (v.getId()==back_cam.getId()){

        }
        else if (v.getId()==barcode.getId()){

        }
        else if (v.getId()==layout_BarcodeType.getId()){
            Intent intent=  new Intent(this,BarcodePreviewActivity.class);
            startActivity(intent);
        }
    }
}
