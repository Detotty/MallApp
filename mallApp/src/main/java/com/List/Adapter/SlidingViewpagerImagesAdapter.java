package com.List.Adapter;

/**
 * Created by Sharjeel on 2/10/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mallapp.Model.LoyaltyCardModel;
import com.mallapp.View.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;


public class SlidingViewpagerImagesAdapter extends PagerAdapter {


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    LoyaltyCardModel loyaltyCardModel;


    public SlidingViewpagerImagesAdapter(Context context, ArrayList<Integer> IMAGES, LoyaltyCardModel loyaltyCardModel) {
        this.context = context;
        this.IMAGES = IMAGES;
        this.loyaltyCardModel = loyaltyCardModel;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.list_floor_view, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.iv_floor);
        final TextView textView = (TextView) imageLayout
                .findViewById(R.id.tv_floor_name);
        final View viewLine = (View) imageLayout
                .findViewById(R.id.lineView);
        viewLine.setVisibility(View.GONE);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(5)
                .oval(false)
                .build();
        if (position==0){
            Picasso.with(context).load(loyaltyCardModel.getFrontImageUrl()).placeholder(R.drawable.mallapp_placeholder).resize(800,450).transform(transformation).into(imageView);
            textView.setTextColor(Color.parseColor("#663399"));
            textView.setText(R.string.front);
        }
        else{
            Picasso.with(context).load(loyaltyCardModel.getBackImageUrl()).placeholder(R.drawable.mallapp_placeholder).resize(800, 450).transform(transformation).into(imageView);
            textView.setTextColor(Color.parseColor("#663399"));
            textView.setText(R.string.back);
        }



        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
