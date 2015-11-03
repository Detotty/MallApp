package com.mallapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.List.Adapter.RestaurantMenuAdapter;
import com.mallapp.View.R;

public class OpeningHourPagerFragment extends Fragment {

	//private static Context context;
	private static final String ARG_POSITION = "position";
	//private ArrayList<String> TITLES= new ArrayList<String>();
	private int position;
	private TextView opening_hours1, opening_hour2;
	String text_open1, text_open2;
	
	//private ListView list;
	//static ArrayList<RestaurantMenu> array_list, filtered_list;
	RestaurantMenuAdapter adapter;

	public static OpeningHourPagerFragment newInstance(int position, Context c) {
		//context= c;
		OpeningHourPagerFragment f 	= new OpeningHourPagerFragment();
		Bundle b 					= 	new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	public OpeningHourPagerFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//context	= getActivity().getApplicationContext();
		position= getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		filterData();
		View rootView 	= 	inflater.inflate(R.layout.opening_hours_information_activity, container, false);
		opening_hours1	=	(TextView) rootView.findViewById(R.id.opening_hour1);
		opening_hour2	= 	(TextView) rootView.findViewById(R.id.opening_hour2);
		
		opening_hours1.setText(text_open1);
		opening_hour2.setText(text_open2);
		
		return rootView;
	}
	
	private  void filterData() {
		if(position == 0){
			text_open1	=	"monday - friday : 10am - 10pm";
			text_open2	=	"saturday - sunday : 10am - 6pm";
		
		}else if(position == 1){
			text_open1	=	"monday - friday : 8am - 8pm";
			text_open2	=	"saturday - sunday : 8am - 3pm";
		
		}else if(position == 2){
			text_open1	=	"monday - friday : 9am - 6pm";
			text_open2	=	"saturday - sunday : 9am - 3pm";
		
		}else if(position == 3){
			text_open1	=	"monday - friday : 10am - 10pm";
			text_open2	=	"saturday - sunday : 11am - 3pm";
		
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
