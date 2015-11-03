package com.mallapp.Fragments;

import java.util.ArrayList;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.List.Adapter.RestaurantMenuAdapter;
import com.mallapp.Controllers.RestaurantFiltration;
import com.mallapp.Controllers.RestaurantList;
import com.mallapp.Model.RestaurantMenu;
import com.mallapp.View.R;
import com.mallapp.globel.GlobelRestaurants;
import com.mallapp.utils.Log;

public class RestaurantPagerFragment extends Fragment {

	private static Context context;
	private static final String ARG_POSITION = "position";
	private ArrayList<String> TITLES= new ArrayList<String>();
	private int position;
	private ListView list;
	static ArrayList<RestaurantMenu> array_list, filtered_list;
	RestaurantMenuAdapter adapter;

	public static RestaurantPagerFragment newInstance(int position, Context c) {
		context= c;
		RestaurantPagerFragment f 	= new RestaurantPagerFragment();
		Bundle b 					= 	new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	public RestaurantPagerFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context	= getActivity().getApplicationContext();
		position= getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		filterData();
		View rootView = inflater.inflate(R.layout.endorsement_view_pager_list, container, false);
		list = (ListView) rootView.findViewById(R.id.mallapp_listview);
		adapter= new RestaurantMenuAdapter(context, getActivity(), filtered_list);
		list.setAdapter(adapter);
		return rootView;
	}
	
	private  void filterData() {
		getRestaurantMenu();
		if(array_list!=null)
			resetData();
	}
	
	private void getRestaurantMenu() {
		if( GlobelRestaurants.menu_array != null && GlobelRestaurants.menu_array.size()>0){
			array_list= GlobelRestaurants.menu_array ;
		}else{
			saveMenuList();
		}
		Log.e("", "size of titles..."+TITLES.size());
		Log.e("", "size of array_list..."+array_list.size());
	}

	private void saveMenuList() {
		array_list= RestaurantList.readMenuList();
		GlobelRestaurants.menu_array  = array_list;
	}

	private void resetData() {
		String menu_type= null;
		if(TITLES.size()== 0 || TITLES == null)
			TITLES= GlobelRestaurants.TITLES;
		menu_type= TITLES.get(position).trim();
		filtered_list = RestaurantFiltration.filterRestaurantMenu(menu_type, array_list);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}