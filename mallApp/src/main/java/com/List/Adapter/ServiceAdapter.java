package com.List.Adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mallapp.Constants.AppConstants;
import com.mallapp.Model.Services;
import com.mallapp.View.R;
import com.mallapp.imagecapture.ImageLoader;


public class ServiceAdapter extends ArrayAdapter<Services>{

	private ArrayList<Services> service_search;
	Context context;
	Activity activity;
	public ImageLoader imageLoader;
	
	public ServiceAdapter(Context context, Activity act, int textViewResourceId, 
								ArrayList<Services> objects) {
		super(context, textViewResourceId, objects);
		service_search= objects;
		this.context= context;
		this.activity= act;
		imageLoader	= new ImageLoader(context);
	}

	
	
	
	public ArrayList<Services> getService_search() {
		return service_search;
	}

	public void setService_search(ArrayList<Services> service_search) {
		this.service_search = service_search;
	}

	@Override
	public int getCount() {
		return service_search.size();
	}

	@Override
	public Services getItem(int position) {
		return this.service_search.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return (position);
	}

	@Override
	public int getPosition(Services item) {
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

	Services service_obj;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
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
			imageLoader.DisplayImage(AppConstants.PREF_URI_KEY, back_image);
			
			//ImageView img = (ImageView) v.findViewById(R.id.img);
			//img.setImageResource(c.imageId);
		}else{
			TextView address 	= (TextView) v.findViewById(R.id.address);
			TextView phone_no 	= (TextView) v.findViewById(R.id.phone_no);
			address.setText(service_obj.getAddress());
			phone_no.setText(service_obj.getPhone_no());
		}
		title.setText(service_obj.getTitle());
		decs.setText(service_obj.getDescription());
		floor_no.setText(service_obj.getFloor_no());
		return v;
	}
}