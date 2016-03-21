package com.List.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.Model.FloorOverViewModel;
import com.mallapp.View.FullScreenImage;
import com.mallapp.View.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Sharjeel on 1/25/2016.
 */
public class FloorOverviewAdapter extends ArrayAdapter<FloorOverViewModel> {

    private ArrayList<FloorOverViewModel> floor_list;
    Context context;
    Activity activity;

    public FloorOverviewAdapter(Context context, Activity activity, int resource, ArrayList<FloorOverViewModel> floor_list ) {
        super(context, resource);
        this.floor_list = floor_list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return floor_list.size();
    }

    @Override
    public FloorOverViewModel getItem(int position) {
        return this.floor_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getPosition(FloorOverViewModel item) {
        return super.getPosition(item);
    }

    static class ViewHolder {
        TextView floor_title;
        ImageView floor_image;
    }


    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View view = convertView;

        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.list_floor_view, null);
            holder = new ViewHolder();
            holder.floor_title 	= (TextView) 	view.findViewById(R.id.tv_floor_name);
            holder.floor_image		= (ImageView) 	view.findViewById(R.id.iv_floor);
            view.setTag(holder);

        }else
            holder = (ViewHolder) view.getTag();

        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
        lastPosition = position;

        final FloorOverViewModel fav_obj= getItem(position);
        holder.floor_title.setText(fav_obj.getName());
        Picasso.with(context).load(fav_obj.getFloorImageURL()).fit().placeholder(R.drawable.mallapp_placeholder).into(holder.floor_image);

        holder.floor_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encoded = fav_obj.getFloorImageURL();
                Intent i = new Intent(activity, FullScreenImage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("img", encoded);
                if (encoded!=null)
                activity.getApplication().startActivity(i);
            }
        });
        return view;
    }
}
