package com.mallapp.View;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class NotificationActivity extends Activity {

    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ImageButton button_back = (ImageButton) findViewById(R.id.navigation);
        heading = (TextView) findViewById(R.id.heading);
        heading.setText(getResources().getString(R.string.notifications));
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*overridePendingTransition(R.anim.right_out,
                        R.anim.right_in);*/

            }
        });
    }

}
