package com.example.markus.pingpong;

import android.content.Context;
import android.graphics.Rect;

public class Ball {


    private final int size = 24;
    private int  ballX;
    private int ballY;
    private int ballXA = 16, ballYA = 16;
    private int turn = 0;
    GameView gameView;

    public Ball(Context context, GameView gameView, int difficulty) {
        this.gameView = gameView;
        ballX = GameView.getScreenX()/2;
        ballY = GameView.getScreenY()/2;
        if (difficulty == 2) {
            ballXA = 16;
            ballYA = 16;
        }
        if (difficulty == 3) {
            ballXA = 24;
            ballYA = 24;
        }
        if (difficulty == 4) {
            ballXA = 30;
            ballYA = 30;
        }
    }

    public int getSize() {
        return size;
    }

    public int getBallX() {
        return ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public int getBallXA() {
        return ballXA;
    }

    public int getBallYA() {
        return ballYA;
    }


    public void setBallXA(int ballXA) {
        this.ballXA = ballXA;
    }

    public void setBallYA(int ballYA) {
        this.ballYA = ballYA;
    }

    public void update() {
        ballX += ballXA;
        ballY += ballYA;
        if (ballY < 100) {
            gameView.updateScore10();
            turn = 0;
            ballY = GameView.getScreenY() / 2;
            ballYA = -ballYA;
        }
        else if (ballY > GameView.getScreenY() - size/2) {
            gameView.updateLives();
            turn = 0;
            ballY = GameView.getScreenY() / 2;
            ballYA = -ballYA;
        }
        else if (ballX < size ){
            ballXA = -ballXA;
            ballX = size + 1;
        }
        else if (ballX > GameView.getScreenX() - size){
            ballXA = -ballXA;
            ballX = GameView.getScreenX() - size + 1;
        }

        checkCollision();
    }

    private void checkCollision() {
        if (gameView.getRacket(1).getBounds().intersect(this.getBounds())){
            //if the ball is coming from above and hits the rackets upper third, the ball is sent back to same direction it came from
            if (turn != 1){
                if (ballX+size >= gameView.getRacket(1).getY() &&
                        ballX+size < gameView.getRacket(1).getX() + (gameView.getRacket(1).getWIDTH()/3)){
                    ballYA = -ballYA;
                    gameView.updateScore();
                    GameView.ballHitSound.start();
                    if (ballXA>=0)
                        ballXA = -ballXA;
                }
                else if (ballX+size >= gameView.getRacket(1).getX() + (gameView.getRacket(1).getWIDTH()/3)
                        && ballX <= gameView.getRacket(1).getX() + (gameView.getRacket(1).getWIDTH()*2/3)){
                    ballYA = -ballYA;
                    gameView.updateScore();
                    GameView.ballHitSound.start();
                }
                else if (ballX > gameView.getRacket(1).getX() + (gameView.getRacket(1).getWIDTH()*2/3)
                        && ballX <= gameView.getRacket(1).getX() + (gameView.getRacket(1).getWIDTH())){
                    ballYA = -ballYA;
                    gameView.updateScore();
                    GameView.ballHitSound.start();
                    if (ballXA<=0)
                        ballXA = -ballXA;
                }
                turn = 1;

            }
        }
        if (gameView.getRacket(2).getBounds().intersect(this.getBounds())){
            if(turn != 2) {
                if (ballX+size >= gameView.getRacket(2).getY() &&
                        ballX+size < gameView.getRacket(2).getX() + (gameView.getRacket(2).getWIDTH()/3)){
                    ballYA = -ballYA;
                    GameView.ballHitSound.start();
                    if (ballXA>=0)
                        ballXA = -ballXA;
                }
                else if (ballX+size >= gameView.getRacket(2).getX() + (gameView.getRacket(2).getWIDTH()/3)
                        && ballX <= gameView.getRacket(2).getX() + (gameView.getRacket(2).getWIDTH()*2/3)){
                    ballYA = -ballYA;
                    GameView.ballHitSound.start();
                }
                else if (ballX > gameView.getRacket(2).getX() + (gameView.getRacket(2).getWIDTH()*2/3)
                        && ballX <= gameView.getRacket(2).getX() + (gameView.getRacket(2).getWIDTH())){
                    ballYA = -ballYA;
                    GameView.ballHitSound.start();
                    if (ballXA<=0)
                        ballXA = -ballXA;
                }
                turn = 2;

            }
        }
    }


    public Rect getBounds() {
        return new Rect(ballX-size, ballY-size, ballX+size, ballY+size);
    }
}
