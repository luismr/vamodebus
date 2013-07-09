package br.com.vamodebus.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;

import br.com.vamodebus.R;

/**
 * Created by edusr on 7/6/13.
 */
public class SocialActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social);

        new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                SocialActivity.this.startApplication();

            }
        }.start();

    }

    public void startApplication() {
        Intent mainIntent = new Intent(SocialActivity.this,MainActivity.class);
        startActivity(mainIntent);
        SocialActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}
