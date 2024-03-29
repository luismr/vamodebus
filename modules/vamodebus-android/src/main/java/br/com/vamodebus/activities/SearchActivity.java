package br.com.vamodebus.activities;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import br.com.vamodebus.R;
import br.com.vamodebus.adapters.FavoriteRouteAdapter;
import br.com.vamodebus.adapters.HistoryAdapter;
import br.com.vamodebus.crawler.ParserHtml;
import br.com.vamodebus.dao.ConfigDataSource;
import br.com.vamodebus.dao.FavoriteRouteDataSource;
import br.com.vamodebus.dao.HistoryDataSource;
import br.com.vamodebus.model.Config;
import br.com.vamodebus.model.FavoriteRoute;
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

        final EditText textView = (EditText) findViewById(R.id.edit_text_code_of_route);
        
        final ImageButton buttonFindRoute = (ImageButton) findViewById(R.id.button_submit_code_of_route);
        buttonFindRoute.setOnClickListener(new OnClickListener(){

			@Override  
			public void onClick(View v) {
				
		        final ProgressDialog myDialog = ProgressDialog.show( SearchActivity.this, "Aguarde..." , " Buscando Rotas. Por Favor Aguarde ... ", true);
		        
				new Thread(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(getApplicationContext(),ListRoutesActivity.class);
						
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
    	
    	ConfigDataSource cds = new ConfigDataSource(this);
    	cds.open();

    	Config config = cds.getListRouteConfig();
    	cds.close();

    	int listRouteHelperList = -1;

    	if(config == null || config.getValue().equals("history")) {
    		listRouteHelperList = R.string.search_list_lastused;
    		
    		HistoryDataSource historyDataSource = new HistoryDataSource(getApplicationContext());
    		historyDataSource.open();
	        
	        List<History> l = historyDataSource.getAllHistory();
	        HistoryAdapter dataAdapter = new HistoryAdapter(this,l);
	   		setListAdapter(dataAdapter);
	   		
	        historyDataSource.close();
    	} else {
    		listRouteHelperList = R.string.search_list_mostused;
    		
	        FavoriteRouteDataSource favoriteRouteDataSource = new FavoriteRouteDataSource(this);
	        favoriteRouteDataSource.open();
	        
	        List<FavoriteRoute> lfr = favoriteRouteDataSource.getAllFavoriteRoutes();
	        FavoriteRouteAdapter fra = new FavoriteRouteAdapter(this, lfr);
	        setListAdapter(fra);
	        
	        favoriteRouteDataSource.close();
    	}
    	
		TextView t = (TextView) findViewById(R.id.seacrhList);
		t.setText(listRouteHelperList);
		
		int listRouteIsVisible = View.INVISIBLE;
		
		if (getListAdapter().getCount() > 0) {
			listRouteIsVisible = View.VISIBLE;
		}
		
		ListView view = (ListView) findViewById(android.R.id.list);
		view.setVisibility(listRouteIsVisible);
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

		//FIXME arrumar o parser urgente!
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
                TextView historyDescription = (TextView) v.findViewById(R.id.routeName);
                String routes = ParserHtml.mapTableToString(findRoutes(historyDescription.getText().toString().substring(0,4),v.getTag().toString()));
                
                Intent intent = new Intent(getApplicationContext(),WhereIsTheBusActivity.class);
                intent.putExtra("QUERY", historyDescription.getText().toString());
                intent.putExtra("ROUTES", routes);

                findRoutes(historyDescription.getText().toString().substring(0,4),v.getTag().toString());
                startActivity(intent);
                
                myDialog.dismiss();
            }
        }).start();
    }
}
