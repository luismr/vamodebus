package br.com.vamodebus.activitys;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import br.com.vamodebus.R;

import br.com.vamodebus.adapters.WhereTheBusIsAdapter;
import br.com.vamodebus.leitorhtml.ParserHtml;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class WhereTheBusIsActivity extends ListActivity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.where_the_bus_is);
		final Bundle extras = getIntent().getExtras();
        if(extras != null){
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
        }
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

    private class BusList extends AsyncTask<String, Void, List>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(WhereTheBusIsActivity.this);
            dialog.setTitle("Calculating...");
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.show();
        }

        @Override
        protected List doInBackground(String... strings) {
            List<Pair<String, Boolean>> listPair = new ParserHtml(
                    "http://200.170.170.86/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
                            + strings[0] + "&route=" + strings[1]).mapTable();
            return new ArrayList<Pair<String, Boolean>>(listPair);
        }

        @Override
        protected void onPostExecute(List list) {
            dialog.dismiss();
        }
    }

}
