package br.com.vamodebus.adapters;

import java.io.UnsupportedEncodingException;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.vamodebus.R;

/**
 * Created by Eduardo Silva Rosa on 31/05/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class WhereIsTheBusAdapter extends BaseAdapter{
	
	Context ctx;
	List<Pair<String, Boolean>> listPair;
	
	public WhereIsTheBusAdapter(Context ctx, List<Pair<String, Boolean>> listPair){
		this.ctx = ctx;
		this.listPair = listPair;
	}

	@Override
	public int getCount() {
		return listPair.size();
	}

	@Override
	public Object getItem(int position) {
		return listPair.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.where_is_the_bus_row, null);
        
        ImageView imageView = (ImageView) v.findViewById(R.id.image_bus);
        TextView nameRoute = (TextView) v.findViewById(R.id.name_bus_stop);
        
        @SuppressWarnings("unchecked")
		Pair<String, Boolean> pair = (Pair<String, Boolean>)getItem(position);
		try {
			nameRoute.setText(new String(pair.first.getBytes(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        if(pair.second){
        	imageView.setVisibility(ImageView.VISIBLE);
        	v.setBackgroundColor(Color.rgb(220, 241, 255));
        }else{
        	imageView.setVisibility(ImageView.INVISIBLE);
        }
        
        
		return v;
	}

}
