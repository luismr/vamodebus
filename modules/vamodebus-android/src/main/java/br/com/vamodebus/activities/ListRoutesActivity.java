package br.com.vamodebus.activities;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.vamodebus.R;
import br.com.vamodebus.adapters.MapRouteAdapter;
import br.com.vamodebus.crawler.ParserHtml;
import br.com.vamodebus.dao.FavoriteRouteDataSource;
import br.com.vamodebus.dao.HistoryDataSource;
import br.com.vamodebus.model.FavoriteRoute;
import br.com.vamodebus.model.History;

import com.google.analytics.tracking.android.EasyTracker;

/**
 * Created by Eduardo Silva Rosa on 31/05/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class ListRoutesActivity extends BaseListActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // set window to be full screen 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.list_routes);
        
        final Bundle extras = getIntent().getExtras();  
        
        @SuppressWarnings("unchecked")
		HashMap<String, String> optionsMapped = (HashMap<String, String>) extras.getSerializable("ROUTES");
        
        MapRouteAdapter mapRouteAdapter = new MapRouteAdapter(optionsMapped, this);
        
        if (mapRouteAdapter.getCount() == 1) {
        	//TODO Forward Automático
        }
        
        ListView listRoutes = (ListView) findViewById(android.R.id.list);

        listRoutes.setAdapter(mapRouteAdapter);
        listRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        	@Override
			public void onItemClick(AdapterView<?> parent, final View v, int position,long id) {

                final ProgressDialog myDialog = ProgressDialog.show( ListRoutesActivity.this, "Aguarde..." , " Buscando Rotas. Por Favor Aguarde ... ", true);

                final TextView nameRoute = (TextView) v.findViewById(R.id.name_route);

                History history = new History();
                history.setId(v.getTag().toString());
                history.setName(nameRoute.getText().toString());
                history.setCode("0");
                
                HistoryDataSource hDS = new HistoryDataSource(getApplicationContext());
                hDS.open();
                hDS.add(history);
                hDS.close();
                
                FavoriteRouteDataSource fds = new FavoriteRouteDataSource(getApplicationContext());
                fds.open();
                
                FavoriteRoute fr = fds.getFavoriteRouteById(v.getTag().toString());
                
                if (fr == null) {
                	fr = new FavoriteRoute();
                	fr.setId(v.getTag().toString());
                	fr.setName(nameRoute.getText().toString());
                	fr.setCode("0");
                	fr.setAccesNumber(1);
                	fds.add(fr);
                } else {
                	fds.incrementAccesNumber(fr.getId(), fr.getAccesNumber()+1);
                }
                
                fds.close();

                new Thread(new Runnable() {
                	
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),WhereIsTheBusActivity.class);
                        
                        String routes = ParserHtml.mapTableToString(findRoutes(extras.getString("ROUTE"),v.getTag().toString()));
                        
                        intent.putExtra("QUERY", nameRoute.getText().toString());
                        intent.putExtra("ROUTES", routes);

                        startActivity(intent);
                        myDialog.dismiss();
                    }
                }).start();
			}
		});
	}

    public String findRoutes(String route,String edCode) {
        ParserHtml parserHtml = new ParserHtml(
                "http://200.170.170.87/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
                        + route + "&route="+ edCode );
        return parserHtml.getHtml();
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

}
