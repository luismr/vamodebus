package br.com.vamodebus.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import br.com.vamodebus.R;

/**
 * Created by edusr on 7/6/13.
 * Updated by luismr on 7/12/13
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set window to be full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.splash);

        // set timer to only 2 secs 
        new CountDownTimer(2 * 1000, 1000) {
        	
            @Override
            public void onTick(long millisUntilFinished) {
            	// nothing
            }

            @Override
            public void onFinish() {
                if (isOnline()) {
                    startApplication();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
                    
                    dialog.setTitle("Não há conexão com a internet");
                    dialog.setPositiveButton("Sair",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    });
                    
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        }.start();

    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        
        // simplified return condition
        return (info != null && info.isConnectedOrConnecting());
    }

    public void startApplication() {
        Intent mainIntent = new Intent(SplashActivity.this,SocialActivity.class);
        startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
       // nothing
    }
}
