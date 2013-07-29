package br.com.vamodebus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;
import br.com.vamodebus.R;
import br.com.vamodebus.dao.ConfigDataSource;
import br.com.vamodebus.model.Config;

/**
 * Created by edusr on 7/8/13.
 */
public class ConfigActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set window to be full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.config);

		Config config = new Config();

		config.setName("listRoute");
		config.setValue("history");

		ConfigDataSource configDataSource = new ConfigDataSource(getApplicationContext());
		configDataSource.open();

		if (configDataSource.getListRouteConfig() == null) {
			configDataSource.add(config);
		}

		configDataSource.close();
	}

	@Override
	protected void onStart() {
		super.onStart();

		Config config = getConfigFromDB();

		RadioButton historyRadio = (RadioButton) findViewById(R.id.radioHistory);
		RadioButton favoriteRadio = (RadioButton) findViewById(R.id.radioPrefer);

		if (config.getValue().equals("history")) {
			historyRadio.setChecked(true);
		} else {
			favoriteRadio.setChecked(true);
		}
	}

	public Config getConfigFromDB() {
		ConfigDataSource configDataSource = new ConfigDataSource(getApplicationContext());
		configDataSource.open();
		Config config = configDataSource.getListRouteConfig();
		configDataSource.close();

		return config;
	}

	public void updateConfigInDB(String value) {
		ConfigDataSource configDataSource = new ConfigDataSource(getApplicationContext());
		configDataSource.open();
		Config config = configDataSource.getListRouteConfig();
		configDataSource.updateListRouteConfiguration(config.getId(), value);
		configDataSource.close();
	}

	public void onRadioButtonClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		
		switch (view.getId()) {
		case R.id.radioHistory:
			if (checked) {
				updateConfigInDB("history");
				Toast.makeText(this,getResources().getString(R.string.config_msg_history),Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.radioPrefer:
			if (checked) {
				updateConfigInDB("prefer");
				Toast.makeText(this,getResources().getString(R.string.config_msg_favorites),Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	public void onClickCleanDataButton(View v) {
		ConfigDataSource configDataSource = new ConfigDataSource(getApplicationContext());
		configDataSource.open();
		configDataSource.deleteListDB();
		configDataSource.close();
		
		Toast.makeText(this, getResources().getString(R.string.config_msg_cleandata), Toast.LENGTH_SHORT).show();
	}

}
