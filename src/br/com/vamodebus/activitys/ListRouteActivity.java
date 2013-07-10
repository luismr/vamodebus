package br.com.vamodebus.activitys;

import java.util.HashMap;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.com.vamodebus.R;
import br.com.vamodebus.adapters.MapRouteAdapter;
import br.com.vamodebus.dao.HistoryDataSource;
import br.com.vamodebus.leitorhtml.ParserHtml;
import br.com.vamodebus.model.History;

import com.google.analytics.tracking.android.EasyTracker;

/**
 * Created by Eduardo Silva Rosa on 31/05/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class ListRouteActivity extends BaseListActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_routes);
        
        final Bundle extras = getIntent().getExtras();  
        
        HashMap<String, String> optionsMapped = (HashMap<String, String>) extras.getSerializable("ROUTES");
        
        MapRouteAdapter mapRouteAdapter = new MapRouteAdapter(optionsMapped, this);
        
        ListView listRoutes = (ListView) findViewById(android.R.id.list);
        
        listRoutes.setAdapter(mapRouteAdapter);
        
        listRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View v, int position,long id) {
/*				Intent intent = new Intent(getApplicationContext(),WhereTheBusIsActivity.class);
				intent.putExtra("ROUTE", extras.getString("ROUTE"));
				intent.putExtra("IDROUTE", v.getTag().toString());
				startActivity(intent);*/

                final ProgressDialog myDialog = ProgressDialog.show( ListRouteActivity.this, "Aguarde..." , " Buscando Rotas. Por Favor Aguarde ... ", true);

                History history = new History();
                history.setId(v.getTag().toString());
                TextView nameRoute = (TextView) v.findViewById(R.id.name_route);
                history.setName(nameRoute.getText().toString());
                history.setAccesNumber("1");
                history.setCode("0");
                HistoryDataSource hDS = new HistoryDataSource(getApplicationContext());
                hDS.open();
                hDS.add(history);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),WhereTheBusIsActivity.class);


                        intent.putExtra("ROUTES", findRoutes(extras.getString("ROUTE"),v.getTag().toString()));
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
