package br.com.vamodebus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.vamodebus.R;
import br.com.vamodebus.model.History;

/**
 * Created by edusr on 7/6/13.
 */
public class HistoryAdapter extends BaseAdapter {

    Context ctx;
    List<History> historyList;

    public HistoryAdapter(Context ctx, List<History> historyList){
        this.ctx = ctx;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.hitory_row, null);

        TextView historyDescription = (TextView) v.findViewById(R.id.textHistory);
        History history = (History)getItem(position);
        historyDescription.setText(history.getName());
        v.setTag(history.getId());

        return v;
    }

}
