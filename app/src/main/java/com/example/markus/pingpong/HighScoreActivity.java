package com.example.markus.pingpong;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

        TextView textView,textView2,textView3,textView4;

        SharedPreferences sharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_high_score);

            //initializing the textViews
            textView = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);
            textView3 = findViewById(R.id.textView3);
            textView4 = findViewById(R.id.textView4);

            sharedPreferences  = getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);

            //setting the values to the textViews
            textView2.setText("1."+sharedPreferences.getInt("score1",0));
            textView3.setText("2."+sharedPreferences.getInt("score2",0));
            textView4.setText("3."+sharedPreferences.getInt("score3",0));
    }
}
