package br.com.vamodebus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.vamodebus.db.SqlLiteHelper;
import br.com.vamodebus.model.Config;
import br.com.vamodebus.model.FavoriteRoute;

/**
 * Created by edusr on 7/8/13.
 */
public class ConfigDataSource{

    private SQLiteDatabase database;
    private SqlLiteHelper dbHelper;
    private String[] allColumns = { SqlLiteHelper.COLUMN_ID,
        SqlLiteHelper.NAME,SqlLiteHelper.VALUE};

    public ConfigDataSource(Context context){
        dbHelper = new SqlLiteHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
        database.close();
    }

    public void add(Config favoriteRoute){
        ContentValues value = new ContentValues();
        value.put(SqlLiteHelper.COLUMN_ID,favoriteRoute.getId());
        value.put(SqlLiteHelper.VALUE,favoriteRoute.getValue());
        value.put(SqlLiteHelper.NAME,favoriteRoute.getName());
        database.insert(SqlLiteHelper.TABLE_CONFIG,null,value);
    }

    public void Delete(Config favoriteRoute){
        database.delete(SqlLiteHelper.TABLE_CONFIG, SqlLiteHelper.COLUMN_ID
                + " = " + favoriteRoute.getId(), null);
    }

    public List<Config> getAllConfigs() {
        List<Config> comments = new ArrayList<Config>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_CONFIG,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Config comment = cursorToFavoriteRoute(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return comments;
    }
    
    public void updateListRouteConfiguration(long id,String value){
    	ContentValues values = new ContentValues();
    	values.put("value", value);
    	database.update(SqlLiteHelper.TABLE_CONFIG, values, SqlLiteHelper.COLUMN_ID + "=" + id, null);
    }

    public Config getListRouteConfig(){
       if(database == null){
            return null;
        }
        String query = "select * from " + SqlLiteHelper.TABLE_CONFIG + " where name = 'listRoute'";
       Cursor cursor =  database.rawQuery(query,null);

        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            Config config = cursorToFavoriteRoute(cursor);
            cursor.close();
            return config;
        }else{
            return null;
        }


    }

    private Config cursorToFavoriteRoute(Cursor cursor){
        Config favoriteRoute = new Config();
        favoriteRoute.setValue(cursor.getString(cursor.getColumnIndex("value")));
        favoriteRoute.setName(cursor.getString(cursor.getColumnIndex("name")));
        favoriteRoute.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        return favoriteRoute;
    }
}

