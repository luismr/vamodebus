package br.com.vamodebus.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import br.com.vamodebus.R;
import br.com.vamodebus.adapters.WhereIsTheBusAdapter;
import br.com.vamodebus.crawler.ParserHtml;

import com.google.analytics.tracking.android.EasyTracker;

/**
 * Created by Eduardo Silva Rosa on 31/05/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class WhereIsTheBusActivity extends BaseActivity {


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // set window to be full screen 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.where_is_the_bus);
        
		final Bundle extras = getIntent().getExtras();
        String routes = extras.getString("ROUTES");

        TextView view = (TextView) findViewById(R.id.seacrhList);
        view.setText(extras.getString("QUERY"));
        
        loadWhereIsTheBusInfo(routes);
	}

	public void loadWhereIsTheBusInfo(final String routes) {
		List<Pair<String, Boolean>> pairs = parseRoutesToPairs(routes);
		WhereIsTheBusAdapter whereTheBusIsAdapter = new WhereIsTheBusAdapter(WhereIsTheBusActivity.this, pairs);

		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(whereTheBusIsAdapter);
	}

    private List<Pair<String, Boolean>> parseRoutesToPairs(final String routes) {
    	List<Pair<String, Boolean>> pairs = new ArrayList<Pair<String,Boolean>>();
    	
    	StringTokenizer lines = new StringTokenizer(routes, "\n");

    	while (lines.hasMoreTokens()) {
    		String line = lines.nextToken();
    		String [] fields = line.split(":");
    		
    		Pair<String, Boolean> pair = new Pair<String, Boolean>(fields[0], Boolean.valueOf(fields[1]));
    		pairs.add(pair);
    	}
    	
		return pairs;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       return super.onCreateOptionsMenu(menu);
    }
}
