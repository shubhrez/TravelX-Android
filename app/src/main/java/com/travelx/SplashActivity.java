package com.travelx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;


public class SplashActivity extends Activity {

    Intent intent = null;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);

        intent = new Intent(this,MainHomeActivity.class);
//        intent = new Intent(this,PlaceDetails.class);
        countDownTimer = new CountDownTimer(1500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
                SplashActivity.this.overridePendingTransition(R.anim.animation, R.anim.animation_out);
            }
        };
        countDownTimer.start();
    }

}
