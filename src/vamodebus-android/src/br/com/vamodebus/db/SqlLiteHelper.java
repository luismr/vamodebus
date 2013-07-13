package br.com.vamodebus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Eduardo Silva Rosa on 30/06/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class SqlLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FAVORITE_ROUTE = "FAVORITE_ROUTE";
    public static final String TABLE_HISTORY = "HISTORY";
    public static final String TABLE_CONFIG = "CONFIG";
    public static final String COLUMN_ID = "_id";
    public static final String CODE = " code";
    public static final String NAME = " name";
    public static final String NUMBER_ACCESS = "number_access";
    public static final String VALUE = "value";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 4;

    // Database creation sql statement
    private static final String DATABASE_CREATE_FAVORITE_ROUTE_TABLE = "create table if not exists "
            + TABLE_FAVORITE_ROUTE + "(" + COLUMN_ID
            + " text primary key , " + CODE
            + " text not null ," + NAME
            + " text not null);";

    private static final String DATABASE_CREATE_HISTORY_TABLE = "create table if not exists "
            + TABLE_HISTORY + "(" + COLUMN_ID
            + " text primary key , " + CODE
            + " text not null ," + NAME
            + " text not null," + NUMBER_ACCESS
            + " text not null);";

    private static final String DATABASE_CREATE_CONFIG_TABLE = "create table if not exists "
            + TABLE_CONFIG + "(" + COLUMN_ID
            + " integer primary key autoincrement , " + NAME
            + " text not null ," + VALUE
            + " text not null);";

    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_FAVORITE_ROUTE_TABLE);
        database.execSQL(DATABASE_CREATE_HISTORY_TABLE);
        database.execSQL(DATABASE_CREATE_CONFIG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqlLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_ROUTE);
        onCreate(db);
    }

}