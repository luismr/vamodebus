package br.com.vamodebus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import br.com.vamodebus.R;

/**
 * Created by Eduardo Silva Rosa on 03/06/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class InfoActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info);
    }
}