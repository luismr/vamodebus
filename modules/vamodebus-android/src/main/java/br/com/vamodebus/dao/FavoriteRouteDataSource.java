package br.com.vamodebus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            SqlLiteHelper.NAME,
            SqlLiteHelper.NUMBER_ACCESS};

    public FavoriteRouteDataSource(Context context){
        dbHelper = new SqlLiteHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
        database.close();
    }

    public void add(FavoriteRoute favoriteRoute){
        ContentValues value = new ContentValues();
        value.put(SqlLiteHelper.COLUMN_ID,favoriteRoute.getId());
        value.put(SqlLiteHelper.CODE,favoriteRoute.getCode());
        value.put(SqlLiteHelper.NAME,favoriteRoute.getName());
        value.put(SqlLiteHelper.NUMBER_ACCESS,favoriteRoute.getAccesNumber());
        database.insert(SqlLiteHelper.TABLE_FAVORITE_ROUTE,null,value);
    }

    public void Delete(FavoriteRoute favoriteRoute){
        database.delete(SqlLiteHelper.TABLE_FAVORITE_ROUTE, SqlLiteHelper.COLUMN_ID
                + " = " + favoriteRoute.getId(), null);
    }

    public List<FavoriteRoute> getAllFavoriteRoutes() {
        List<FavoriteRoute> favoriteRoutes = new ArrayList<FavoriteRoute>();

        Cursor cursor = database.query(SqlLiteHelper.TABLE_FAVORITE_ROUTE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FavoriteRoute favoriteRoute = cursorToFavoriteRoute(cursor);
            favoriteRoutes.add(favoriteRoute);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return favoriteRoutes;
    }
    
    public void incrementAccesNumber(String id,int value){
    	ContentValues values = new ContentValues();
    	values.put(SqlLiteHelper.NUMBER_ACCESS, value);
    	database.update(SqlLiteHelper.TABLE_FAVORITE_ROUTE, values, SqlLiteHelper.COLUMN_ID + "=" + id, null);
    }
    
    public FavoriteRoute getFavoriteRouteById(String id){
        if(database == null){
        	Log.d("VDB","database é NULL"); 
        	return null;
             
         }
         String query = "select * from " + SqlLiteHelper.TABLE_FAVORITE_ROUTE + " where _id = " + id;
        Cursor cursor =  database.rawQuery(query,null);

        Log.d("VDB","QUERY: " + query);
        
         if(cursor.getCount() !=0){
        	 Log.d("VDB","CURSOR NAO É ZERO");
             cursor.moveToFirst();
             FavoriteRoute favoriteRoute = cursorToFavoriteRoute(cursor);
             cursor.close();
             return favoriteRoute;
         }else{
        	 Log.d("VDB","CURSOR ÉÉÉÉÉÉ ZERO");
        	 cursor.close();
             return null;
         }


     }

    private FavoriteRoute cursorToFavoriteRoute(Cursor cursor){
        FavoriteRoute favoriteRoute = new FavoriteRoute();
        favoriteRoute.setId(cursor.getString(0));
        favoriteRoute.setCode(cursor.getString(1));
        favoriteRoute.setName(cursor.getString(2));
        favoriteRoute.setAccesNumber(cursor.getInt(3));
        return favoriteRoute;
    }
}
