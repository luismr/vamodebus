package br.com.vamodebus.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.vamodebus.R;
import br.com.vamodebus.model.FavoriteRoute;
import br.com.vamodebus.model.History;

/**
 * Created by edusr on 7/6/13.
 */
public class FavoriteRouteAdapter  extends BaseAdapter {

    Context ctx;
    List<FavoriteRoute> favoriteRoutes;

    public FavoriteRouteAdapter(Context ctx, List<FavoriteRoute> favoriteRoutes){
        this.ctx = ctx;
        this.favoriteRoutes = favoriteRoutes;
    }

    @Override
    public int getCount() {
        return favoriteRoutes.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteRoutes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.search_row_favorite, null);

        TextView favoriteRouteDescription = (TextView) v.findViewById(R.id.routeName);
        TextView favoriteRouteAccessNumber = (TextView) v.findViewById(R.id.numberAccess);
        FavoriteRoute favoriteRoute = (FavoriteRoute)getItem(position);
        favoriteRouteDescription.setText(favoriteRoute.getName());
        favoriteRouteAccessNumber.setText("( " + favoriteRoute.getAccesNumber() + " +)");
        v.setTag(favoriteRoute.getId());

        return v;
    }

}