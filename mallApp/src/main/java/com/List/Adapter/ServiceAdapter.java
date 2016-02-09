package com.List.Adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.mallapp.Model.ServicesModel;
import com.mallapp.View.FullScreenImage;
import com.mallapp.View.R;
import com.mallapp.globel.GlobelMainMenu;
import com.mallapp.imagecapture.ImageLoader;
import com.mallapp.utils.AppUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class ServiceAdapter extends ArrayAdapter<ServicesModel>{

	private ArrayList<ServicesModel> service_search;
	Context context;
	Activity activity;
	public ImageLoader imageLoader;
	
	public ServiceAdapter(Context context, Activity act, int textViewResourceId, 
								ArrayList<ServicesModel> objects) {
		super(context, textViewResourceId, objects);
		service_search= objects;
		this.context= context;
		this.activity= act;
		imageLoader	= new ImageLoader(context);
	}

	
	
	
	public ArrayList<ServicesModel> getService_search() {
		return service_search;
	}

	public void setService_search(ArrayList<ServicesModel> service_search) {
		this.service_search = service_search;
	}

	@Override
	public int getCount() {
		return service_search.size();
	}

	@Override
	public ServicesModel getItem(int position) {
		return this.service_search.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(ServicesModel item) {
		return super.getPosition(item);
	}
	
	
	
	@Override
	public int getItemViewType(int position) {
		boolean isOpen	= getItem(position).isOpened();
		if(isOpen){
			return 1;
		}else
			return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
		// TODO Auto-generated method stub
		//return super.getViewTypeCount();
	}

	ServicesModel service_obj;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	
		View v = convertView;
		int type = getItemViewType(position);
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (type == 0) {
				v = inflater.inflate(R.layout.list_item_service, parent, false);
			}
			else {
				v = inflater.inflate(R.layout.list_item_services_detail, parent, false);
			}
		}
		
		service_obj	= getItem(position);
		TextView title 		= (TextView) v.findViewById(R.id.title);
		TextView decs 		= (TextView) v.findViewById(R.id.detail);
		TextView floor_no 	= (TextView) v.findViewById(R.id.floor_no);

		if (type == 0) {
			ImageView back_image= (ImageView) v.findViewById(R.id.service_image);
			Picasso.with(context).load(service_obj.getFacilityImageURL()).into(back_image);

			//ImageView img = (ImageView) v.findViewById(R.id.img);
			//img.setImageResource(c.imageId);
		}else{
			ImageView back_image= (ImageView) v.findViewById(R.id.service_image);
			ImageView location= (ImageView) v.findViewById(R.id.imageView1);
			TextView address 	= (TextView) v.findViewById(R.id.address);
			final TextView phone_no 	= (TextView) v.findViewById(R.id.phone_no);
			address.setText(service_obj.getAddress());
			phone_no.setText(service_obj.getPhone());
			Transformation transformation = new RoundedTransformationBuilder()
					.borderColor(Color.GRAY)
					.borderWidthDp(1)
					.cornerRadiusDp(7)
					.oval(false)
					.build();
			Picasso.with(context).load(service_obj.getFacilityImageURL()).transform(transformation).placeholder(R.drawable.mallapp_placeholder).fit().into(back_image);

			phone_no.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AppUtils.displayCallDialog(v.getRootView().getContext(), service_obj.getPhone());
				}
			});
			location.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String encoded = service_obj.getMapImageURL();
					Intent i = new Intent(activity, FullScreenImage.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("img", encoded);
					if (encoded!=null)
						activity.getApplication().startActivity(i);
				}
			});
		}
		title.setText(service_obj.getFacilityType());
		decs.setText(service_obj.getBriefText());
		floor_no.setText(service_obj.getFloor());

		/*v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ServicesModel s1 = 	getItem(position);
				s1.setOpened(false);


				ServicesModel s = getItem(position);
				if (s.isOpened()) {
					s.setOpened(false);
				} else {
					s.setOpened(true);
				}
				GlobelMainMenu.selected_index_services = position;
			}
		});*/
		return v;
	}
}