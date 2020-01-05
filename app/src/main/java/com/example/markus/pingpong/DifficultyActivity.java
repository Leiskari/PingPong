package com.example.markus.pingpong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DifficultyActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton buttonEasy;
    private ImageButton buttonMedium;
    private ImageButton buttonHard;

    private static int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        buttonEasy = findViewById(R.id.buttonEasy);
        buttonEasy.setOnClickListener(this);

        buttonMedium = findViewById(R.id.buttonMedium);
        buttonMedium.setOnClickListener(this);

        buttonHard = findViewById(R.id.buttonHard);
        buttonHard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.buttonEasy:
                difficulty = 2;
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.buttonMedium:
                difficulty = 3;
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.buttonHard:
                difficulty = 4;
                startActivity(new Intent(this, GameActivity.class));
                break;

        }

    }

    public static int getDifficulty() {
        return difficulty;
    }

}
