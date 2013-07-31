/**
 * 
 */
package br.com.vamodebus.activities;

import br.com.vamodebus.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * @author luismr
 *
 */
public class HelpActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about);
    }

}
