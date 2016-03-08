package com.mallapp.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Sharjeel on 07/03/2016.
 */
public class LanguageChangeActivity extends Activity implements View.OnClickListener{

    TextView heading;
    ImageView SelectDef, SelectEng, SelectDan;
    LinearLayout Def,Eng,Dan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        heading = (TextView) findViewById(R.id.heading);
        heading.setText(getResources().getString(R.string.change_language));
        Def = (LinearLayout) findViewById(R.id.lang_def);
        Eng = (LinearLayout) findViewById(R.id.lang_eng);
        Dan = (LinearLayout) findViewById(R.id.lang_dan);

        SelectDef = (ImageView) findViewById(R.id.imageView1);
        SelectEng = (ImageView) findViewById(R.id.imageView2);
        SelectDan = (ImageView) findViewById(R.id.imageView3);


        Def.setOnClickListener(this);
        Eng.setOnClickListener(this);
        Dan.setOnClickListener(this);

        ImageButton button_back = (ImageButton) findViewById(R.id.back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*overridePendingTransition(R.anim.right_out,
                        R.anim.right_in);*/

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lang_def){
            SelectDef.setVisibility(View.VISIBLE);
            SelectEng.setVisibility(View.GONE);
            SelectDan.setVisibility(View.GONE);

        }else if (v.getId() == R.id.lang_eng){

            SelectDef.setVisibility(View.GONE);
            SelectEng.setVisibility(View.VISIBLE);
            SelectDan.setVisibility(View.GONE);

        }else if (v.getId() == R.id.lang_dan){

            SelectDef.setVisibility(View.GONE);
            SelectEng.setVisibility(View.GONE);
            SelectDan.setVisibility(View.VISIBLE);

        }
    }
}
