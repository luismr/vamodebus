package br.com.vamodebus.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import br.com.vamodebus.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash);

        new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                SplashActivity.this.startApplication();
            }
        }.start();

    }

    public void startApplication() {
        Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(mainIntent);
        SplashActivity.this.finish();
    }
}
