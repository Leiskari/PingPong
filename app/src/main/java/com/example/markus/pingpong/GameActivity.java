package com.example.markus.pingpong;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        if(DifficultyActivity.getDifficulty() == 2) {
            gameView = new GameView(this, size.x, size.y, 2);
        }

        if(DifficultyActivity.getDifficulty() == 3) {
            gameView = new GameView(this, size.x, size.y, 3);
        }

        if(DifficultyActivity.getDifficulty() == 4) {
            gameView = new GameView(this, size.x, size.y, 4);
        }


        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
