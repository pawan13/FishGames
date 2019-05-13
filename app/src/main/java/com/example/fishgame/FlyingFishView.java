package com.example.fishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class FlyingFishView extends View
{    // variable declared
    private Bitmap fish[] = new Bitmap[2];
    private  int fishX ;
    private int fishY;
    private int fishSpeed;

    private int RedX, RedY, RedSpeed = 16;
    private Paint redPaint = new Paint();

    private int BlueX, BlueY , BlueSpeed = 20;
    private Paint bluePaint = new Paint();

    private int GrayX, GrayY , GraySpeed = 18;
    private Paint GrayPaint = new Paint();

    private int SnakeX, SnakeY, snakeSpeed = 17;
    private Bitmap Snake[] = new Bitmap[1];

    private int canvasWidth, canvasHeight;


    private int score, HealthCount = 3;
    private boolean touch = false;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap Health[] = new Bitmap[2];

    private static long COUNTDOWN_IN_MILLIS = 300000;
    private Paint txtCountDown = new Paint();

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    public long minutesLeft;



    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(false);

        GrayPaint.setColor(Color.GRAY);
        GrayPaint.setAntiAlias(false);

        Snake[0] = BitmapFactory.decodeResource(getResources(),R.drawable.snake);

        txtCountDown.setColor(Color.WHITE);
        txtCountDown.setTextSize(40);
        txtCountDown.setTypeface(Typeface.DEFAULT_BOLD);
        txtCountDown.setAntiAlias(true);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);



        Health[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        Health[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 600;
        fishX = 0;
        score = 0;

    }

    public void startCountDown(){

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                minutesLeft = millisUntilFinished/60000;
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                gameFinish();
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;

        fishY = fishY + fishSpeed;
        if (fishY < minFishY) {
            fishY = minFishY;
        }
        if (fishY > maxFishY){
            fishY = maxFishY;
        }


        fishSpeed = fishSpeed + 2;
        if (touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishX,fishY, null);
        }

        RedX = RedX - RedSpeed;
        if (hitBallChecked(RedX, RedY)){
            score = score + 1;
            RedX = - 100;
        }
        if (RedX < 0){
            RedX = canvasWidth + 25;
            RedY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(RedX, RedY, 30, redPaint);

        BlueX = BlueX - BlueSpeed;
        if (hitBallChecked(BlueX, BlueY)){
            score = score + 10;
            BlueX = - 100;
        }
        if (BlueX < 0){
            BlueX = canvasWidth + 30;
            BlueY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(BlueX, BlueY, 30, bluePaint);

        GrayX = GrayX - GraySpeed;
        if (hitBallChecked(GrayX, GrayY)){
            score = score - 5;
            GrayX = - 100;
        }
        if (GrayX < 0){
            GrayX = canvasWidth + 30;
            GrayY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawCircle(GrayX, GrayY, 30,GrayPaint);

        SnakeX = SnakeX - snakeSpeed;
        if (hitBallChecked(SnakeX, SnakeY)){
            SnakeX = - 100;
            HealthCount--;

            if (HealthCount == 0)
            {
               gameFinish();
            }
        }
        if (SnakeX < 0){
            SnakeX = canvasWidth + 30;
            SnakeY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);
        }
        canvas.drawBitmap(Snake[0],SnakeX, SnakeY,null);

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();

        canvas.drawText("Score: " + score,20, 60, scorePaint);
        canvas.drawText("MinutesLeft:" + minutesLeft, 800, 200, txtCountDown);

        for (int i =0; i <3; i++) {
            int x = (int) (580 + Health[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < HealthCount) {
                canvas.drawBitmap(Health[0], x, y, null);
            } else {
                canvas.drawBitmap(Health[1], x, y, null);
            }
        }
    }
    public boolean hitBallChecked(int x, int y){
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }
        public void gameFinish(){
        Toast.makeText(getContext(),"Game Over", Toast.LENGTH_LONG ).show();
        Intent GameFinish = new Intent(getContext(), GameFinish.class);
        GameFinish.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        GameFinish.putExtra("Score", score);
        getContext().startActivity(GameFinish);
            countDownTimer.cancel();
            countDownTimer = null;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_MOVE){
            touch = true;
            if (score <= 25) {
                fishSpeed = -22;
            }
            if (score > 25 && score <= 50)
            {
                fishSpeed = -20;
            }
            if (score > 50 )
            {
                fishSpeed = -18;
            }
        }
        return true;
    }
}