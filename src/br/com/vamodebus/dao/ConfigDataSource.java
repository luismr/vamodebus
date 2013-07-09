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
        SqlLiteHelper.NAME};

    public ConfigDataSource(Context context){
        dbHelper = new SqlLiteHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void add(Config favoriteRoute){
        ContentValues value = new ContentValues();
        value.put(SqlLiteHelper.COLUMN_ID,favoriteRoute.getId());
        value.put(SqlLiteHelper.CODE,favoriteRoute.getValue());
        value.put(SqlLiteHelper.NAME,favoriteRoute.getName());
        database.insert(SqlLiteHelper.TABLE_FAVORITE_ROUTE,null,value);
    }

    public void Delete(Config favoriteRoute){
        database.delete(SqlLiteHelper.TABLE_FAVORITE_ROUTE, SqlLiteHelper.COLUMN_ID
                + " = " + favoriteRoute.getId(), null);
    }

    public List<Config> getAllComments() {
        List<Config> comments = new ArrayList<Config>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_FAVORITE_ROUTE,
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

    private Config cursorToFavoriteRoute(Cursor cursor){
        Config favoriteRoute = new Config();
        favoriteRoute.setId(cursor.getLong(0));
        favoriteRoute.setValue(cursor.getString(1));
        favoriteRoute.setName(cursor.getString(2));
        return favoriteRoute;
    }
}

