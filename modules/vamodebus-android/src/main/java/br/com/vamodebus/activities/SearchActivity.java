package br.com.vamodebus.activities;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import br.com.vamodebus.R;
import br.com.vamodebus.adapters.HistoryAdapter;
import br.com.vamodebus.dao.HistoryDataSource;
import br.com.vamodebus.leitorhtml.ParserHtml;
import br.com.vamodebus.model.History;

import com.google.analytics.tracking.android.EasyTracker;



/**
 * Created by Eduardo Silva Rosa <edus.silva.rosa@gmail.com> on 31/05/2013.
 * Updated by Luis Machado Reis <luismr@singularideas.com.br> on 13/07/2013
 */
public class SearchActivity extends BaseListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set window to be full screen 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.search); 

        ImageButton buttonFindRoute = (ImageButton) findViewById(R.id.button_submit_code_of_route);

        final EditText textView = (EditText) findViewById(R.id.edit_text_code_of_route);
        buttonFindRoute.setOnClickListener(new OnClickListener(){

			@Override  
			public void onClick(View v) {
				
		        final ProgressDialog myDialog = ProgressDialog.show( SearchActivity.this, "Aguarde..." , " Buscando Rotas. Por Favor Aguarde ... ", true);
		        
				new Thread(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(getApplicationContext(),ListRouteActivity.class);
						
						intent.putExtra("ROUTE", textView.getText().toString());
						intent.putExtra("ROUTES", findRoutes(textView.getText().toString()));
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

        HistoryDataSource historyDataSource = new HistoryDataSource(getApplicationContext());
        historyDataSource.open();

        List<History> l = historyDataSource.getAllHistory();
        HistoryAdapter dataAdapter = new HistoryAdapter(this,l);

        setListAdapter(dataAdapter);
        historyDataSource.close();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	EasyTracker.getInstance().activityStop(this);
    }
    
	public HashMap<String, String> findRoutes(String str) {
		ParserHtml parserHtml = new ParserHtml(
				"http://200.170.170.87/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
						+ str + "&btcode=Buscar&route=0");
		Log.i("URL_CONEXAO", "http://200.170.170.87/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
						+ str + "&btcode=Buscar&route=0");

		return parserHtml.mapOptions();
	}

    public String findRoutes(String route,String edCode) {
        ParserHtml parserHtml = new ParserHtml(
                "http://200.170.170.87/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
                        + route + "&route="+ edCode );
        return parserHtml.getHtml();
    }


    @Override
    protected void onListItemClick(ListView l,final View v, int position, long id) {
        final ProgressDialog myDialog = ProgressDialog.show( SearchActivity.this, "Aguarde..." , " Buscando Rotas. Por Favor Aguarde ... ", true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),WhereTheBusIsActivity.class);

                TextView historyDescription = (TextView) v.findViewById(R.id.textHistory);

                intent.putExtra("ROUTES", findRoutes(historyDescription.getText().toString().substring(0,4),v.getTag().toString()));

                findRoutes(historyDescription.getText().toString().substring(0,4),v.getTag().toString());
                startActivity(intent);
                myDialog.dismiss();
            }
        }).start();
    }
}
