package br.com.vamodebus.activitys;

import java.util.ArrayList;
import java.util.List;

import br.com.vamodebus.R;

import br.com.vamodebus.adapters.WhereTheBusIsAdapter;
import br.com.vamodebus.leitorhtml.ParserHtml;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.Window;
import android.widget.ListView;

public class WhereTheBusIsActivity extends ListActivity {

	private static final int MSG_INICIAR = 1;
	private static final int MSG_DOWNLOAD = 2;
	private static final int MSG_FINALIZAR = 3;

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Message aux_msg;
			// ProgressBar pb;

			switch (msg.what) {
			case MSG_INICIAR:
				aux_msg = new Message();
				aux_msg.what = MSG_DOWNLOAD;
				WhereTheBusIsActivity.this.handler.sendMessage(aux_msg);
				break;
			case MSG_DOWNLOAD:

				final Bundle extras = getIntent().getExtras();

				List<Pair<String, Boolean>> listPair = findBusStop(
						extras.getString("ROUTE"), extras.getString("IDROUTE"));
				carregaTela(listPair);
				aux_msg = new Message();
				aux_msg.what = MSG_FINALIZAR;
				WhereTheBusIsActivity.this.handler.sendMessage(aux_msg);
				break;
			case MSG_FINALIZAR:
				break;
			}
		}
	}

	MyHandler handler = new MyHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.where_the_bus_is);
		final ProgressDialog pb = new ProgressDialog(WhereTheBusIsActivity.this);
		pb.setTitle("Aguarde...");
		pb.setMessage("Buscando Informações");
		pb.show();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = MSG_INICIAR;
				handler.sendMessage(msg);
				pb.dismiss();
			};
		}).start();
		
	}

	public void carregaTela(final List<Pair<String, Boolean>> listPair) {
		WhereTheBusIsAdapter whereTheBusIsAdapter = new WhereTheBusIsAdapter(
				WhereTheBusIsActivity.this, listPair);
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(whereTheBusIsAdapter);
	}

	public List<Pair<String, Boolean>> findBusStop(String route, String idRoute) {
		List<Pair<String, Boolean>> listPair = new ParserHtml(
				"http://200.170.170.86/webclient/webclient/arenawebclientiis.dll/synoptic?edcode="
						+ route + "&route=" + idRoute).mapTable();
		return new ArrayList<Pair<String, Boolean>>(listPair);
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
