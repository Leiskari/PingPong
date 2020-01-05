package com.example.markus.pingpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Racket {
    private final int HEIGHT = 16;
    private final int WIDTH = 200;
    private int x, x1;
    private int y;
    private Bitmap bitmap;
    private GameView gameView;
    private int compXa, compXb;;

    public Racket (Context context, int id, GameView gameView) {
        x = GameView.getScreenX()/2 - WIDTH/2;
        x1 = x;
        this.gameView = gameView;
        if (id == 1){
            y = GameView.getScreenY() - HEIGHT - 80;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle_player);
        }
        if (id == 2){
            y = 150;
            compXa = 10;
            compXb = 2;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle_cpu);
        }
        if (id == 3){
            y = 150;
            compXa = 17;
            compXb = 3;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle_cpu);
        }
        if (id == 4){
            y = 150;
            compXa = 25;
            compXb = 5;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle_cpu);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setX1(int x1) {
        this.x1 = x1-WIDTH/2;

    }

    public void update() {
        if (x > compXa && x < GameView.getScreenX() - WIDTH - compXa){
            if (gameView.getBall().getBallYA() > 0){
                if(gameView.getBall().getBallX() > x+(WIDTH/2))
                    x += compXb;
                if(gameView.getBall().getBallX() < x+(WIDTH/2))
                    x -= compXb;
            }
            if (gameView.getBall().getBallYA() < 0){
                if(gameView.getBall().getBallX() > x+(WIDTH/2))
                    x += compXa;
                if(gameView.getBall().getBallX() < x+(WIDTH/2))
                    x -= compXa;
            }
        }
        if (x <= compXa+compXb)
            x += 10;
        if (x >= GameView.getScreenX() - WIDTH - compXa - compXb)
            x -= 10;
    }

    public void updatePlayer() {
        if (x<x1){
            x += 25;
        }
        if (x>x1){
            x -= 25;
        }
    }

    public Rect getBounds() {
        return new Rect(x, y, x+WIDTH, y+HEIGHT);
    }

}
