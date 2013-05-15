package org.com.udibus.activitys;

import java.util.HashMap;
import java.util.Map;

import org.com.udibus.leitorhtml.ParserHtml;
import org.com.udibusleitorhtml.R;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        Button buttonFindRoute = (Button) findViewById(R.id.button_submit_code_of_route);
        
        final EditText textView = (EditText) findViewById(R.id.edit_text_code_of_route);
        buttonFindRoute.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				
		        final ProgressDialog myDialog = ProgressDialog.show( MainActivity.this, "Aguarde..." , " Buscando Rotas. Por Favor Aguarde ... ", true);
		        
				new Thread(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(getApplicationContext(),ListRouteActivity.class);
						
						intent.putExtra("ROUTE", textView.getText().toString());
						intent.putExtra("ROUTES", findRoutes(textView.getText().toString()));
						findRoutes(textView.getText().toString());
						startActivity(intent);
						myDialog.dismiss();
					}
				}).start();
				
				
				
			}
        });
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	EasyTracker.getInstance().activityStart(this);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	EasyTracker.getInstance().activityStop(this);
    }
    
	public HashMap<String, String> findRoutes(String str) {
		ParserHtml parserHtml = new ParserHtml(
				"http://200.170.170.86/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
						+ str + "&btcode=Buscar&route=0");
		Log.i("URL_CONEXAO", "http://200.170.170.86/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
						+ str + "&btcode=Buscar&route=0");

		HashMap<String, String> optionsMaped = parserHtml.mapOptions();
		return optionsMaped;
	}

    

    
}
