package br.com.vamodebus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.vamodebus.db.SqlLiteHelper;
import br.com.vamodebus.model.History;


/**
 * Created by edusr on 7/1/13.
 */
public class HistoryDataSource{

    private SQLiteDatabase database;
    private SqlLiteHelper dbHelper;
    private String[] allColumns = { SqlLiteHelper.COLUMN_ID,
        SqlLiteHelper.CODE,
        SqlLiteHelper.NAME,
        SqlLiteHelper.NUMBER_ACCESS};

    public HistoryDataSource(Context context){
        dbHelper = new SqlLiteHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
        database.close();
    }

    public void add(History favoriteRoute){
        ContentValues value = new ContentValues();
        value.put(SqlLiteHelper.COLUMN_ID,favoriteRoute.getId());
        value.put(SqlLiteHelper.CODE,favoriteRoute.getCode());
        value.put(SqlLiteHelper.NAME,favoriteRoute.getName());
        value.put(SqlLiteHelper.NUMBER_ACCESS,favoriteRoute.getAccesNumber());
        database.insert(SqlLiteHelper.TABLE_HISTORY,null,value);
    }

    public void Delete(History favoriteRoute){
        database.delete(SqlLiteHelper.TABLE_HISTORY, SqlLiteHelper.COLUMN_ID
                + " = " + favoriteRoute.getId(), null);
    }

    public List<History> getAllHistory() {
        List<History> comments = new ArrayList<History>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_HISTORY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            History comment = cursorToFavoriteRoute(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return comments;
    }

    private History cursorToFavoriteRoute(Cursor cursor){
        History favoriteRoute = new History();
        favoriteRoute.setId(cursor.getString(0));
        favoriteRoute.setCode(cursor.getString(1));
        favoriteRoute.setName(cursor.getString(2));
        return favoriteRoute;
    }
}
