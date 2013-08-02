package br.com.vamodebus.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import br.com.vamodebus.R;

/**
 * Created by Eduardo Silva Rosa on 03/06/2013. mail to:
 * edus.silva.rosa@gmail.com
 */
public class AboutActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set window to be full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.about);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
			
			TextView name = (TextView) findViewById(R.id.about_value_name);
			name.setText(getResources().getString(R.string.app_name));
			
			TextView pack = (TextView) findViewById(R.id.about_value_package);
			pack.setText(getApplicationContext().getPackageName());
			
			TextView version = (TextView) findViewById(R.id.about_value_version);
			version.setText(info.versionName);
		} catch (NameNotFoundException e) {
			Log.e("VDB", "Failed to retireve App Information", e);
		}
		
		
	}

	public void callUrl(View v) {
		Uri uriUrl = Uri.parse(getResources().getString(
				R.string.about_label_company_url));
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}

	public void callEmail(View v) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/html");
		intent.putExtra(Intent.EXTRA_EMAIL,
				getResources().getString(R.string.about_label_company_email));
		intent.putExtra(Intent.EXTRA_SUBJECT,
				"[VamoDeBus!/Contato] Sobre nosso aplicativo ...");

		startActivity(Intent.createChooser(intent, "Send Email"));
	}

	public void callPbx(View v) {
		try {
			String phone = getResources().getString(R.string.about_label_company_pbx);
			phone = phone.replace("(", "");
			phone = phone.replace(")", "");
			phone = phone.replace("-", "");
			phone = phone.replace(" ", "");
			phone = "tel:012" + phone;

			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse(phone));

			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e("VDB", "Call failed", e);
		}
	}
}