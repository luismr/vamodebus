package br.com.vamodebus.activitys;

import br.com.vamodebus.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * Created by edusr on 7/6/13.
 * Updated by luismr on 7/12/13
 */
public class SocialActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set window to be full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.social);

        new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            	int completed = Math.round((millisUntilFinished / (5 * 1000)) * 100);
            	
            	ProgressBar bar = (ProgressBar) findViewById(R.id.socialLoadingProgressBar);
            	bar.setProgress(completed);
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
