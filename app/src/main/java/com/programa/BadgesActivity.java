package com.programa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BadgesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);
        ImageView img;
        for (String x: DisplayMessageActivity.badges
             ) {
            switch (x){
                case "1" : img = findViewById(R.id.imageViewLogica);
                img.setVisibility(View.VISIBLE);
                break;
                case "2" : img = findViewById(R.id.imageViewexplorador);
                img.setVisibility(View.VISIBLE);
                break;
                default: break;
            }
        }
    }
}
