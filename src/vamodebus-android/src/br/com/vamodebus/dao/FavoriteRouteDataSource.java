package br.com.vamodebus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<FavoriteRoute> getAllComments() {
        List<FavoriteRoute> comments = new ArrayList<FavoriteRoute>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_FAVORITE_ROUTE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavoriteRoute comment = cursorToFavoriteRoute(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return comments;
    }

    private FavoriteRoute cursorToFavoriteRoute(Cursor cursor){
        FavoriteRoute favoriteRoute = new FavoriteRoute();
        favoriteRoute.setId(cursor.getString(0));
        favoriteRoute.setCode(cursor.getString(1));
        favoriteRoute.setName(cursor.getString(2));
        return favoriteRoute;
    }
}
