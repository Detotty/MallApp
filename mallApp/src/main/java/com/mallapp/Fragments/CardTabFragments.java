package com.mallapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.List.Adapter.CardsGridAdapter;
import com.List.Adapter.CardsGridSearchAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.Constants.MainMenuConstants;
import com.mallapp.Model.LoyaltyCardModel;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.View.AddCardActivity;
import com.mallapp.View.CardDetailsActivity;
import com.mallapp.View.R;
import com.mallapp.listeners.UniversalDataListener;
import com.mallapp.utils.Log;
import com.mallapp.utils.VolleyNetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CardTabFragments extends Fragment implements UniversalDataListener {

    View viewHome;

    TextView heading;

    EditText searchField;

    ImageButton btnAdd;

    private Button cancel_search;

    VolleyNetworkUtil volleyNetworkUtil;

    GridView gridView, searchGridView;

    CardsGridAdapter adapter;
    CardsGridSearchAdapter searchAdapter;

    ArrayList<LoyaltyCardModel> loyaltyCardModels;
    ArrayList<LoyaltyCardModel> loyaltyCardSearchModels;
    ArrayList<LoyaltyCardModel> loyaltyCardSearchResultsModels;

    String TAG = getClass().getCanonicalName();

    public static boolean isUpdate = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        volleyNetworkUtil = new VolleyNetworkUtil(getActivity());
        loyaltyCardModels = new ArrayList<>();
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

    public void init() {
        gridView = (GridView) viewHome.findViewById(R.id.grid);
        searchGridView = (GridView) viewHome.findViewById(R.id.search_grid);
        heading = (TextView) viewHome.findViewById(R.id.heading);
        searchField = (EditText) viewHome.findViewById(R.id.search_feild);
        btnAdd = (ImageButton) viewHome.findViewById(R.id.btnAdd);
        cancel_search = (Button) viewHome.findViewById(R.id.cancel_search);

        heading.setText(getResources().getString(R.string.loyalty_card_header));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCardActivity.class);
                startActivity(intent);
            }
        });
        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchGridView.setVisibility(View.GONE);

                if (searchField.getText().toString().length() > 0) {

                    cancel_search.setTextColor(getResources().getColor(R.color.grey));
                    searchField.setText("");
                    gridView.setVisibility(View.VISIBLE);

                } else {

                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isUpdate) {
            isUpdate = false;
            loyaltyCardModels.clear();
            loyaltyCardSearchResultsModels.clear();
            loyaltyCardSearchModels.clear();
            volleyNetworkUtil.GetBarcodeTypes(ApiConstants.GET_USER_LOYALTY_CARD + SharedPreferenceUserProfile.getUserId(getActivity()), this);
        }
    }

    @Override
    public void onDataReceived(JSONObject jsonObject, JSONArray jsonArray) {

        Gson gson = new Gson();
        String data = jsonArray.toString();
        Type listType = new TypeToken<List<LoyaltyCardModel>>() {
        }.getType();
        loyaltyCardModels = gson.fromJson(data, listType);
        if (loyaltyCardModels.size() == 0) {
            loyaltyCardModels = new ArrayList<>();
            loyaltyCardSearchResultsModels= new ArrayList<>();
            loyaltyCardSearchModels= new ArrayList<>();
            if (adapter!=null)
            adapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), getString(R.string.toast_no_card), Toast.LENGTH_LONG).show();
        } else {
            loyaltyCardSearchModels = gson.fromJson(data, listType);
            loyaltyCardSearchResultsModels = gson.fromJson(data, listType);

            searchAdapter = new CardsGridSearchAdapter(getActivity().getApplicationContext(), getActivity(), R.layout.grid_cards_layout, loyaltyCardModels);
            searchGridView.setAdapter(searchAdapter);
            searchGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    LoyaltyCardModel cardModel = loyaltyCardSearchResultsModels.get(position);
                    Intent intent = new Intent(getActivity(), CardDetailsActivity.class);
                    intent.putExtra(MainMenuConstants.LOYALTY_CARD_OBJECT, cardModel);
                    startActivity(intent);
                }
            });

            adapter = new CardsGridAdapter(getActivity().getApplicationContext(), getActivity(), R.layout.grid_cards_layout, loyaltyCardModels);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    LoyaltyCardModel cardModel = loyaltyCardModels.get(position);
                    Intent intent = new Intent(getActivity(), CardDetailsActivity.class);
                    intent.putExtra(MainMenuConstants.LOYALTY_CARD_OBJECT, cardModel);
                    startActivity(intent);
                }
            });

            searchField.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence cs, int start, int before, int count) {

                    String searchString = cs.toString().trim();
                    Log.e(TAG, "" + searchString);
                    int textLength = searchString.length();

                    if (textLength > 0) {

                        cancel_search.setTextColor(getResources().getColor(R.color.purple));
                        gridView.setVisibility(View.GONE);

                        if (loyaltyCardSearchModels == null || loyaltyCardSearchModels.size() == 0) {

                        }
                        loyaltyCardSearchResultsModels = new ArrayList<LoyaltyCardModel>();

                        for (int i = 0; i < loyaltyCardSearchModels.size(); i++) {
                            //Log.e(TAG, "shop_name = .....get");
                            String cardTitle = loyaltyCardSearchModels.get(i).getCardTitle().toString();
                            //Log.e(TAG, "shop_name = ...."+ shop_name);
                            if (textLength <= cardTitle.length()) {

                                if (searchString.equalsIgnoreCase(cardTitle.substring(0, textLength)))
                                    loyaltyCardSearchResultsModels.add(loyaltyCardSearchModels.get(i));
                            }
                        }
                        Log.e(TAG, "CardSearch_array = " + loyaltyCardSearchModels.size());
                        Log.e(TAG, "search results = " + loyaltyCardSearchResultsModels.size());

                        if (loyaltyCardSearchResultsModels != null && loyaltyCardSearchResultsModels.size() > 0) {
                            searchAdapter.setCard_search(loyaltyCardSearchResultsModels);
                            searchGridView.setVisibility(View.VISIBLE);
                            searchAdapter.notifyDataSetChanged();

                        } else {
                            gridView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        cancel_search.setTextColor(getResources().getColor(R.color.grey));
                        Log.e(TAG, "view existing lists");
                        gridView.setVisibility(View.VISIBLE);
                        searchGridView.setVisibility(View.GONE);

                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    @Override
    public void OnError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
