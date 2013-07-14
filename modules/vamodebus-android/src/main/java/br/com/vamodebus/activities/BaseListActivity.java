package br.com.vamodebus.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.vamodebus.R;

/**
 * Created by Eduardo Silva Rosa on 31/05/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class BaseListActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onMenuItemSelected(int featureId,MenuItem item) {
        if(item.getItemId() == R.id.menu_settings){
            Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.configuration){
            Intent intent = new Intent(getApplicationContext(),ConfigActivity.class);
            startActivity(intent);
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}