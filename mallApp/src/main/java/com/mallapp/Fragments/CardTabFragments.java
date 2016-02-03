package com.mallapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mallapp.View.AddCardActivity;
import com.mallapp.View.R;

public class CardTabFragments extends Fragment{

    View viewHome;
    TextView heading;
    ImageButton btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        viewHome = inflater.inflate(R.layout.fragment_parent_tab_cards, container, false);

        init();

        return viewHome;
    }

    public void init(){
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

}
