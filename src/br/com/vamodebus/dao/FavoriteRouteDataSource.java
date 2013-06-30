package br.com.vamodebus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.vamodebus.db.SqlLiteHelper;
import br.com.vamodebus.model.FavoriteRoute;

/**
 * Created by Eduardo Silva Rosa on 30/06/2013.
 * mail to: edus.silva.rosa@gmail.com
 */

public class FavoriteRouteDataSource {
    private SQLiteDatabase database;
    private SqlLiteHelper dbHelper;
    private String[] allColumns = { SqlLiteHelper.COLUMN_ID,
            SqlLiteHelper.NAME};

    public FavoriteRouteDataSource(Context context){
        dbHelper = new SqlLiteHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void add(FavoriteRoute favoriteRoute){
        ContentValues value = new ContentValues();
        value.put(SqlLiteHelper.COLUMN_ID,favoriteRoute.getId());
        value.put(SqlLiteHelper.CODE,favoriteRoute.getCode());
        value.put(SqlLiteHelper.NAME,favoriteRoute.getName());
        database.insert(SqlLiteHelper.TABLE_FAVORITE_ROUTE,null,value);
    }

    public void Delete(FavoriteRoute favoriteRoute){
        database.delete(SqlLiteHelper.TABLE_FAVORITE_ROUTE, SqlLiteHelper.COLUMN_ID
                + " = " + favoriteRoute.getId(), null);
    }
}
