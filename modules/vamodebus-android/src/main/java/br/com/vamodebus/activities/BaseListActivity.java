package br.com.vamodebus.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.vamodebus.R;

/**
 * Created by Eduardo Silva Rosa on 31/05/2013. mail to:
 * edus.silva.rosa@gmail.com
 */
public class BaseListActivity extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		
		if (item.getItemId() == R.id.menu_about) {
			intent = new Intent(getApplicationContext(), AboutActivity.class);
		} else if (item.getItemId() == R.id.menu_settings) {
			intent = new Intent(getApplicationContext(), SettingsActivity.class);
		} else if (item.getItemId() == R.id.menu_help) {
			intent = new Intent(getApplicationContext(), HelpActivity.class);
		}
		
		startActivity(intent);
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.context_menu, menu);
		return true;
	}

	public void callFaceBook(View v) {
		Uri uriUrl = Uri.parse("http://www.facebook.com/VamoDeBus");
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}

	public void callTwitter(View v) {
		Uri uriUrl = Uri.parse("http://www.twitter.com/VamoDeBus");
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}

	public void callSite(View v) {
		Uri uriUrl = Uri.parse("http://www.vamodebus.com.br");
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}
}