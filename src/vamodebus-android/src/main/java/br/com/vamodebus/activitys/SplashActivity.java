package br.com.vamodebus.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
                if(isOnline()){
                    SplashActivity.this.startApplication();
                }else{
                    AlertDialog.Builder d = new AlertDialog.Builder(SplashActivity.this);
                    d.setTitle("Não há conexão com a internet");
                    d.setPositiveButton("Sair",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    });
                    d.setCancelable(false);
                    d.show();
                }
            }
        }.start();

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void startApplication() {
        Intent mainIntent = new Intent(SplashActivity.this,SocialActivity.class);
        startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}
