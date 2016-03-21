package com.mallapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.List.Adapter.SlidingViewpagerImagesAdapter;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.LoyaltyCardModel;
import com.mallapp.utils.AppUtils;
import com.mallapp.utils.Utils;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sharjeel on 2/10/2016.
 */
public class CardDetailsActivity extends Activity implements View.OnClickListener{

    TextView heading, tvProviderName, tvCardNum, tvBarcodeNum, tvDescription, tvDescTitle;

    ImageView iv_Barcode,btnDone;

    ImageButton btnBack;

    LoyaltyCardModel loyaltyCardModel;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.front_card,R.drawable.back_card};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_card_details);
        loyaltyCardModel = (LoyaltyCardModel) getIntent().getSerializableExtra(MainMenuConstants.LOYALTY_CARD_OBJECT);

        init();
    }

    private void init() {

        heading = (TextView) findViewById(R.id.heading);
        tvProviderName = (TextView) findViewById(R.id.tv_provider);
        tvCardNum = (TextView) findViewById(R.id.tv_cardNum);
        tvBarcodeNum = (TextView) findViewById(R.id.tv_barcodeNum);
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvDescTitle = (TextView) findViewById(R.id.tv_descTitle);

        btnBack = (ImageButton) findViewById(R.id.back);

        btnDone = (ImageView) findViewById(R.id.btnDone);

        iv_Barcode = (ImageView) findViewById(R.id.iv_barcode);

        heading.setText(loyaltyCardModel.getCardTitle());

        btnDone.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingViewpagerImagesAdapter(CardDetailsActivity.this,ImagesArray,loyaltyCardModel));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        /*// Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);*/

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

        setDetails();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnBack.getId()) {
            finish();
        }else if (v.getId() == btnDone.getId()) {
            if (Utils.isInternetAvailable(this)) {
                Intent intent = new Intent(this, AddCardActivity.class);
                intent.putExtra("add", false);
                intent.putExtra(MainMenuConstants.LOYALTY_CARD_OBJECT,loyaltyCardModel);
                startActivity(intent);
                finish();
            } else
                AppUtils.matDialog(this, getResources().getString(R.string.no_internet), getResources().getString(R.string.network_error));

        }
    }

    void setDetails(){

        tvProviderName.setText(loyaltyCardModel.getProviderName());
        tvCardNum.setText(String.valueOf(loyaltyCardModel.getCardNumber()));
        tvBarcodeNum.setText(loyaltyCardModel.getBarcode());
        Picasso.with(this).load(loyaltyCardModel.getBarcodeImageURL()).placeholder(R.drawable.mallapp_placeholder).fit().into(iv_Barcode);
        if (loyaltyCardModel.getUserNotes()==null || loyaltyCardModel.getUserNotes().isEmpty()){
            tvDescription.setVisibility(View.GONE);
            tvDescTitle.setVisibility(View.GONE);
        }
        else
            tvDescription.setText(loyaltyCardModel.getUserNotes());


    }
}