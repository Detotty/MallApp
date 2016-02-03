package com.List.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.Model.BarcodeTypeModel;
import com.mallapp.View.BarcodePreviewActivity;
import com.mallapp.View.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 2/2/2016.
 */
public class BarcodePreviewAdapter extends ArrayAdapter<BarcodeTypeModel> {

    private ArrayList<BarcodeTypeModel> barcode_list;
    Context context;
    Activity activity;

    public BarcodePreviewAdapter(Context context, Activity activity, int resource, ArrayList<BarcodeTypeModel> barcode_list) {
        super(context, resource);
        this.barcode_list = barcode_list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return barcode_list.size();
    }

    @Override
    public BarcodeTypeModel getItem(int position) {
        return this.barcode_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getPosition(BarcodeTypeModel item) {
        return super.getPosition(item);
    }

    static class ViewHolder {
        TextView barcode_type;
        ImageView barcode_image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View view = convertView;

        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.list_floor_view, null);
            holder = new ViewHolder();
            holder.barcode_type = (TextView) 	view.findViewById(R.id.tv_floor_name);
            holder.barcode_image = (ImageView) 	view.findViewById(R.id.iv_floor);
            view.setTag(holder);

        }else
            holder = (ViewHolder) view.getTag();

        final BarcodeTypeModel fav_obj= getItem(position);
        holder.barcode_type.setText(fav_obj.getBarcodeType1());
        Picasso.with(context).load(fav_obj.getBarcodeImageURL()).into(holder.barcode_image);

        holder.barcode_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarcodePreviewActivity.Barcodetype = fav_obj.getBarcodeType1();
                activity.finish();
            }
        });
        return view;
    }

}
