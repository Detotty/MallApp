package com.List.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mallapp.Model.LoyaltyCardModel;
import com.mallapp.View.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 2/11/2016.
 */
public class CardsGridSearchAdapter extends ArrayAdapter<LoyaltyCardModel> {

    Context mContext;

    Activity activity;

    private ArrayList<LoyaltyCardModel> loyaltyCardModelArrayList;


    public CardsGridSearchAdapter(Context c, Activity activity, int resource, ArrayList<LoyaltyCardModel> loyaltyCardModelArrayList) {
        super(c,resource);
        mContext = c;
        this.activity = activity;
        this.loyaltyCardModelArrayList = loyaltyCardModelArrayList;
    }

    public void setCard_search(ArrayList<LoyaltyCardModel> loyaltyCardModelArrayList) {
        this.loyaltyCardModelArrayList = loyaltyCardModelArrayList;
        //Log.e("", shop_search.size()+" setShop_search = shop_search");
    }

    static class ViewHolder {
        TextView title;
        ImageView card_image;
    }

    @Override
    public int getCount() {
        return loyaltyCardModelArrayList.size();
    }

    @Override
    public LoyaltyCardModel getItem(int position) {
        return this.loyaltyCardModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(LoyaltyCardModel item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        View view = convertView;

        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.grid_cards_layout, null);
            holder = new ViewHolder();
            holder.title = (TextView) 	view.findViewById(R.id.grid_text);
            holder.card_image = (ImageView) 	view.findViewById(R.id.grid_image);
            view.setTag(holder);

        }else
            holder = (ViewHolder) view.getTag();

        final LoyaltyCardModel fav_obj= getItem(position);
        holder.title.setText(fav_obj.getCardTitle());
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(5)
                .oval(false)
                .build();

        Picasso.with(mContext).load(fav_obj.getFrontImageUrl()).transform(transformation).placeholder(R.drawable.mallapp_placeholder).fit().into(holder.card_image);


        return view;

    }
}