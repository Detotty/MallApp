package com.mallapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.List.Adapter.CardsGridAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Model.BarcodeTypeModel;
import com.mallapp.Model.LoyaltyCardModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.AddCardActivity;
import com.mallapp.View.R;
import com.mallapp.listeners.UniversalDataListener;
import com.mallapp.utils.VolleyNetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CardTabFragments extends Fragment implements UniversalDataListener{

    View viewHome;

    TextView heading;

    ImageButton btnAdd;

    VolleyNetworkUtil volleyNetworkUtil;

    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        volleyNetworkUtil = new VolleyNetworkUtil(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        viewHome = inflater.inflate(R.layout.fragment_parent_tab_cards, container, false);

        init();
        volleyNetworkUtil.GetBarcodeTypes(ApiConstants.GET_USER_LOYALTY_CARD + SharedPreferenceUserProfile.getUserId(getActivity()), this);

        return viewHome;
    }

    public void init(){
        gridView = (GridView) viewHome.findViewById(R.id.grid);
        heading = (TextView) viewHome.findViewById(R.id.heading);
        btnAdd = (ImageButton) viewHome.findViewById(R.id.btnAdd);
        heading.setText(getResources().getString(R.string.loyalty_card_header));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDataReceived(JSONObject jsonObject, JSONArray jsonArray) {

        Gson gson = new Gson();
        String data = jsonArray.toString();
        Type listType = new TypeToken<List<LoyaltyCardModel>>() {
        }.getType();
        final ArrayList<LoyaltyCardModel> loyaltyCardModels = gson.fromJson(data, listType);

        CardsGridAdapter adapter = new CardsGridAdapter(getActivity().getApplicationContext(),getActivity(),R.layout.grid_cards_layout,loyaltyCardModels);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                LoyaltyCardModel cardModel = loyaltyCardModels.get(position);
            }
        });
    }

    @Override
    public void OnError() {

    }
}
