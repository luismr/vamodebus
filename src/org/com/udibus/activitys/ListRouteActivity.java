package org.com.udibus.activitys;

import java.util.HashMap;

import org.com.udibus.adapters.MapRouteAdapter;
import org.com.udibus.leitorhtml.ParserHtml;
import org.com.udibusleitorhtml.R;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListRouteActivity extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_routes);
        
        final Bundle extras = getIntent().getExtras();  
        
        HashMap<String, String> optionsMaped = (HashMap<String, String>) extras.getSerializable("ROUTES");
        
        MapRouteAdapter mapRouteAdapter = new MapRouteAdapter(optionsMaped, this);
        
        ListView listRoutes = (ListView) findViewById(android.R.id.list);
        
        listRoutes.setAdapter(mapRouteAdapter);
        
        listRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
				Intent intent = new Intent(getApplicationContext(),WhereTheBusIsActivity.class);
				intent.putExtra("ROUTE", extras.getString("ROUTE"));
				intent.putExtra("IDROUTE", v.getTag().toString());
				startActivity(intent);
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

}
