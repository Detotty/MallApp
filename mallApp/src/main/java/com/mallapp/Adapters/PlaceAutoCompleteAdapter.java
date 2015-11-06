package com.mallapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mallapp.Constants.AppConstants;
import com.mallapp.GPS.AutoCompleteApi;
import com.mallapp.Model.PlaceAutoCompleteModel;
import com.mallapp.Model.UserLocationModel;
import com.mallapp.View.R;
import com.mallapp.utils.SharedInstance;
import com.mallapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Sharjeel on 11/6/2015.
 */
public class PlaceAutoCompleteAdapter extends ArrayAdapter<PlaceAutoCompleteModel>
        implements Filterable {

    //private ArrayList<HashMap<String, String>>  resultList;
    private ArrayList<String> resultList;
    private ArrayList<PlaceAutoCompleteModel> mResultList;
    private UserLocationModel userLocationModel;

    private ItemFilter filter = new ItemFilter();

    private Context context;

    // private LayoutInflater mInflater;
    public PlaceAutoCompleteAdapter(Context context, int textViewResourceId, UserLocationModel userLocationModel) {
        super(context, textViewResourceId);
        //mInflater = LayoutInflater.from(context);

        this.context = context;
        //this.userLocationModel = userLocationModel;
        mResultList = new ArrayList<PlaceAutoCompleteModel>();

    }




    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public PlaceAutoCompleteModel getItem(int index) {
        //HashMap<String, String> map= resultList.get(index);
        return mResultList.get(index);
    }

    static class ViewHolder {
        TextView placeNameTextView;
        TextView placeDescriptionTextView;
        PlaceAutoCompleteModel PlaceAutoCompleteModel;

    }


    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        final PlaceAutoCompleteModel item = getItem(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nearbyplace_list_view_layout, parent, false);

            viewHolder.placeNameTextView  = (TextView) convertView.findViewById(R.id.place_name_text_view);
            viewHolder.placeDescriptionTextView = (TextView) convertView.findViewById(R.id.place_description_text_view);

            viewHolder.PlaceAutoCompleteModel = item;
            //convertView.setTag(item);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(item.getName()!=null)
            viewHolder.placeNameTextView.setText(item.getName());
        else
            viewHolder.placeNameTextView.setText(item.getDescription());
        viewHolder.placeDescriptionTextView.setText(item.getVicinity());


//		convertView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.e("", "click using adapter view of text auto complete"+ item.getName()+"......place_id....."+ item.getPlace_id());
//
//                ViewHolder viewHolder = (ViewHolder) v.getTag();
//                PlaceAutoCompleteModel item = viewHolder.PlaceAutoCompleteModel;
//
//                String placeId = item.getPlace_id();
//                String endorsement_name	 = item.getName();
//                String placeDescription = item.getVicinity();
//
//                Log.d("", "Selected plce : " + placeId);
//                Log.d("", "Selected: plac id:" + endorsement_name);
//                Log.d("", "Selected: plac description:" + placeDescription);
//
//
//
//                Intent intent= new Intent(context, CreateEndorsementActivity.class);
//                intent.putExtra(AppConstants.Intent_Endorsement_Place, "" + endorsement_name);//adapter.getItem(position));
//                intent.putExtra(AppConstants.Intent_Endorsement_Place_Id, "" + placeId);
//                intent.putExtra(AppConstants.Intent_Endorsement_Place_Description, "" + placeDescription);
//
//                Bundle data = ((SelectEndorsmentPlaceActivity)context).getIntent().getExtras();
//                Log.d("TAG",""+data.get( AppConstants.Intent_Category_Id) );
//                intent.putExtra( AppConstants.Intent_Category_Id, "" +data.getString( AppConstants.Intent_Category_Id ) );
//
//                //finish();
//                context.startActivity(intent);
//
//			}
//		});


        return convertView;
    }

    private class ItemFilter extends Filter {
        // Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null) {
                // Retrieve the autocomplete results.
                //resultList = AutocompleteAPI.autocomplete(constraint.toString());

                if(userLocationModel == null) {
                    Utils.getDefaultLocation(context);
                    userLocationModel = (UserLocationModel) SharedInstance.getInstance().getSharedHashMap().get(AppConstants.USER_LOCATION);
                } else {
                    mResultList = AutoCompleteApi.autocomplete(constraint.toString(), userLocationModel);
                }

                //Log.d("place api auto complete:",mResultList.toString());
                // Assign the data to the FilterResults
                if (mResultList != null) {
                    filterResults.values = mResultList;
                    filterResults.count = mResultList.size();
                }
                else{
                    mResultList = new ArrayList<PlaceAutoCompleteModel>();
                    filterResults.values = mResultList;
                    filterResults.count = mResultList.size();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
        //  };

    }
    public Filter getFilter() {
        return filter;
    }

//
//
//    @Override
//    public void onClick(View v) {
//        PlaceAutoCompleteModel item = adapter1.getItem(position);
//        final String placeId = String.valueOf(item.placeId);
//        endorsement_name	 = String.valueOf(item.description);
//
//        Log.i("", "Selected plce : " + item.description);
//        Log.i("", "Selected: plac id" + item.placeId);
//
//
//
//        Intent intent= new Intent(SelectEndorsmentPlaceActivity.this, CreateEndorsementActivity.class);
//        intent.putExtra(AppConstants.Intent_Endorsement_Place, ""+endorsement_name);//adapter.getItem(position));
//
//        Bundle data = getIntent().getExtras();
//        Log.d("TAG",""+data.get( AppConstants.Intent_Category_Id) );
//        intent.putExtra( AppConstants.Intent_Category_Id, "" +data.getString( AppConstants.Intent_Category_Id ) );
//
//        //finish();
//        startActivity(intent);
//    }
}


