package com.mallapp.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter.FilterListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mallapp.Controllers.ShopFiltration;
import com.mallapp.utils.Log;

public class Registration_Select_Country extends Activity implements  OnItemClickListener, OnClickListener{

	private EditText autoComplete;
	private ArrayAdapter<String> adapter;
	//FastSearchListView listView;
	List<String> countries = new ArrayList<String>();
	private LinearLayout 		side_index_layout;
	Map<String, Integer> mapIndex;
	String [] search_list;
	ListView list_view;
	ImageButton back_screen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_select_country);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();
       // actionBar.setDisplayHomeAsUpEnabled(true);
		getFileStorageDir();
		
        list_view			= (ListView) findViewById(R.id.search_list);
        side_index_layout	= (LinearLayout) 		findViewById(R.id.side_index);
        back_screen			= (ImageButton) findViewById(R.id.back_screen);
        back_screen.setOnClickListener(this);
        
        autoComplete		= (EditText) findViewById(R.id.auto_complete_search);
        autoComplete.setHint("Enter Country name");
        
        adapter 			= new ArrayAdapter<String>(this, R.layout.list_item, R.id.item_code_list, search_list);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);
        
        displayIndex();
        
//        listView = (FastSearchListView) findViewById(R.id.search_list);
//        adapter = new SelectCountryAdapter(countries, getApplicationContext());
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
        
        /**
		 * Enabling Search Filter
		 * */
        autoComplete.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				// MainActivity.this.adapter.getFilter().filter(cs);
				Registration_Select_Country.this.adapter.getFilter().filter(cs, new FilterListener() {
					public void onFilterComplete(int count) {
						Log.d("log", "result count:" + count);
					}
				});
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}


	public void getFileStorageDir() {
		InputStream inputStream = null;
		try{
			inputStream = getResources().openRawResource(R.raw.countries_code);
			byte[] reader = new byte[inputStream.available()];
			
			while (inputStream.read(reader) != -1) {
			}
			
			String countries_data=new String(reader);
			search_list= countries_data.split(",");
			
			for(int i= 0; i< search_list.length; i++){
				countries.add(search_list[i]);
			}
			Collections.sort(countries);
			
		} catch(IOException e) {
			Log.e("", e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.e("", e.getMessage());
				}
			}
		}
	}
	
	
	private void displayIndex() {
		mapIndex= ShopFiltration.getIndexList(getResources());
		TextView textView;
		List<String> indexList = new ArrayList<String>(mapIndex.keySet());
		for (String index : indexList) {
			textView = (TextView) getLayoutInflater().inflate( R.layout.list_item_side_index, side_index_layout, false);
			textView.setText(index);
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickIndex(v);
				}
			});
			side_index_layout.addView(textView);
		}
	}
	
	
	private void onClickIndex(View view) {
		TextView selectedIndex = (TextView) view;
		Log.e("", "selectedIndex.getText() = "+selectedIndex.getText());
		Log.e("", "mapIndex.get(selectedIndex.getText()= "+mapIndex.get(selectedIndex.getText()));
		list_view.setSelection(mapIndex.get(selectedIndex.getText()));
		Toast.makeText(getApplicationContext(), ""+selectedIndex.getText(), Toast.LENGTH_SHORT).show();
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent resultIntent = new Intent();
		resultIntent.putExtra("result", adapter.getItem(position));
		setResult(Registration_Country.REQUEST_CODE_FOR_COUNTRY, resultIntent);
		finish();
	}
	


	@Override
	public void onClick(View v) {
		if(v.getId() == back_screen.getId()){
			Intent intent= new Intent(Registration_Select_Country.this, Registration_Country.class);
			finish();
			startActivity(intent);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent= new Intent(Registration_Select_Country.this, Registration_Country.class);
		finish();
		startActivity(intent);
	}
}
