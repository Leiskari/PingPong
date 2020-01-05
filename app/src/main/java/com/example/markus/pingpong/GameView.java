package com.example.markus.pingpong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread gameThread = null;
    volatile boolean playing = true;
    private Racket racketComp, racketPlayer;
    private Ball ball;
    private Paint paintBall;
    private Paint paintText;
    private SurfaceHolder surfaceHolder;
    private Bitmap bitmapBG, bitmapRP, bitmapRC;
    private static int screenX, screenY, lives, score;
    int highScore[] = new int[3];
    SharedPreferences sharedPreferences;
    static MediaPlayer ballHitSound;
    final MediaPlayer gameOversound;
    private int difficulty;

    public GameView(Context context, int screenX, int screenY, int difficulty) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
        this.difficulty = difficulty;
        racketComp = new Racket(context, difficulty, this);
        racketPlayer = new Racket(context, 1, this);
        ball = new Ball(context, this, difficulty);
        surfaceHolder = getHolder();
        paintBall = new Paint();
        paintText = new Paint();
        bitmapBG = BitmapFactory.decodeResource(context.getResources(), R.drawable.table);
        bitmapRC = racketComp.getBitmap();
        bitmapRP = racketPlayer.getBitmap();
        score = 0;
        lives = 3;
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        ballHitSound = MediaPlayer.create(context,R.raw.ball_hit);
        gameOversound = MediaPlayer.create(context,R.raw.scoring_point);
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
        if (outOfLives())
            gameOver();

    }


    private void update() {
        ball.update();
        racketComp.update();
        racketPlayer.updatePlayer();
    }

    private void draw() {

       if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();;
            paintText.setTextSize(40);
            paintBall.setColor(Color.rgb(255, 127, 39));
            Rect rectBG = new Rect(0, 0, screenX, screenY);
            Rect rectRC = new Rect(racketComp.getX(), racketComp.getY(), racketComp.getX()+racketComp.getWIDTH(), racketComp.getY()+racketComp.getHEIGHT());
            Rect rectRP = new Rect(racketPlayer.getX(), racketPlayer.getY(), racketPlayer.getX()+racketPlayer.getWIDTH(), racketPlayer.getY()+racketPlayer.getHEIGHT());
            canvas.drawBitmap(bitmapBG, null, rectBG, null);
            canvas.drawBitmap(bitmapRP, null, rectRP, null);
            canvas.drawBitmap(bitmapRC, null, rectRC, null);
            canvas.drawCircle(ball.getBallX(), ball.getBallY(), ball.getSize(), paintBall);
            canvas.drawText("Score: " + score,20,40, paintText);
            canvas.drawText("Lives: " + lives,260,40, paintText);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {

        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException ignored){ }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }



    public static int getScreenX() {
        return screenX;
    }

    public static int getScreenY() {
        return screenY;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                racketPlayer.setX1((int)motionEvent.getX());
                break;
        }
        return true;
    }

    public Racket getRacket(int id) {
        if (id == 1)
            return racketPlayer;
        else
            return racketComp;
    }

    public Ball getBall(){
        return ball;
    }

    public void updateScore(){
        score = score + 1*(difficulty-1);
    }

    public void updateScore10(){
        score = score + 10*(difficulty-1);
    }

    public void updateLives(){
        lives--;
        if (lives <= 0)
            playing = false;


    }

    public boolean outOfLives(){
        if (lives>0)
            return false;
        else
            return true;
    }

    public void gameOver() {

        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paintText.setTextSize(125);
            paintText.setTextAlign(Paint.Align.CENTER);
            paintText.setColor(Color.WHITE);
            canvas.drawText("Game over!", screenX/2, screenY/2 -75, paintText);
            canvas.drawText("Your score: " + score,screenX/2,screenY/2 +75, paintText);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

        gameOversound.start();

        int score1 = 0, score2 = 0;

        for(int i=0;i<3;i++){
            if(highScore[i]<score){
                score1 = highScore[i];

                final int finalI = i;
                highScore[i] = score;
                break;
            }
        }
        for(int i=1;i<3;i++){
            if(highScore[i]<score1){
                score2 = highScore[i];

                final int finalI = i;
                highScore[i] = score1;
                break;
            }
        }
        if(highScore[2]<score2)
            highScore[2] = score2;


        SharedPreferences.Editor e = sharedPreferences.edit();
        for(int i=0;i<3;i++){
            int j = i+1;
            e.putInt("score"+j,highScore[i]);
        }
        e.apply();
    }
}
