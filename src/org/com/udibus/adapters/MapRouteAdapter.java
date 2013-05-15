package org.com.udibus.adapters;

import java.util.HashMap;

import org.com.udibusleitorhtml.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapRouteAdapter extends BaseAdapter{
	HashMap<String, String> optionsMaped;
	private String[] mKeys;
	Context ctx;
	
	
	
	
	public MapRouteAdapter(HashMap<String, String> optionsMaped,Context
			 ctx ) {
		this.optionsMaped = optionsMaped;
		this.mKeys = this.optionsMaped.keySet().toArray(new String[optionsMaped.size()]);
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		return optionsMaped.size();
	}

	@Override
	public Object getItem(int position) {
		return optionsMaped.get(mKeys[position]);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String key = mKeys[position];
        String value = getItem(position).toString();
        View v;
        
        LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.row_route, null);
        
        TextView nameRoute = (TextView) v.findViewById(R.id.name_route);
        nameRoute.setText(value);
        v.setTag(key);
        
		return v;
	}
	

}
