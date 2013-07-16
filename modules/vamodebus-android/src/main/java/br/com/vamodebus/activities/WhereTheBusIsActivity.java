package br.com.vamodebus.activities;

import java.util.List;

import br.com.vamodebus.R;

import br.com.vamodebus.adapters.WhereTheBusIsAdapter;
import br.com.vamodebus.crawler.ParserHtml;

//import com.actionbarsherlock.app.ActionBar;
//import com.actionbarsherlock.app.SherlockActivity;
//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuInflater;
import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;
import android.widget.ListView;

/**
 * Created by Eduardo Silva Rosa on 31/05/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class WhereTheBusIsActivity extends BaseActivity {


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.where_the_bus_is);
		final Bundle extras = getIntent().getExtras();
        String html = extras.getString("ROUTES");
        carregaTela(ParserHtml.mapTable(html));
/*        if(extras != null){
            AsyncTask asyncTask =  new BusList().execute(extras.getString("ROUTE"), extras.getString("IDROUTE"));
            try {
                List<Pair<String, Boolean>> l = (List<Pair<String, Boolean>>) asyncTask.get();
                carregaTela(l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this,"Rota não identificada",Toast.LENGTH_LONG).show();
        }*/
	}

	public void carregaTela(final List<Pair<String, Boolean>> listPair) {
		WhereTheBusIsAdapter whereTheBusIsAdapter = new WhereTheBusIsAdapter(
				WhereTheBusIsActivity.this, listPair);
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(whereTheBusIsAdapter);
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
/*        MenuInflater inflater;
        inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity_main,menu);
        return  true;*/
    }

    // private ProgressDialog dialog ;
    /*private class BusList extends AsyncTask<String, Void, List>{





        @Override
        protected void onPreExecute() {
            dialog=new ProgressDialog(WhereTheBusIsActivity.this);
            super.onPreExecute();
            dialog.setTitle("Calculating...");
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.show();
        }

        @Override
        protected List doInBackground(String... strings) {
*//*            List<Pair<String, Boolean>> listPair = new ParserHtml(
                    "http://200.170.170.86/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
                            + strings[0] + "&route=" + strings[1]).mapTable();
            return new ArrayList<Pair<String, Boolean>>(listPair);*//*
            return new ArrayList<Pair<String, Boolean>>();
        }

        @Override
        protected void onPostExecute(List list) {
            if(dialog.isShowing()){
                dialog.dismiss();
            }

        }
    }*/

}
