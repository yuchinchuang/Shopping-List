package com.example.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ShoppingSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "ShoppingSQLiteOpenHelper";

    private static final String DATABASE_NAME = "shoppingList.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_SHOPPINGLIST = "shoppingList";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_ISPURCHASED = "isPurchased";

    private static final String CREATE_TABLE = "create table " + TABLE_SHOPPINGLIST + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_QUANTITY + " integer not null, "
            + COLUMN_ISPURCHASED + " integer not null);";

    public ShoppingSQLiteOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPINGLIST);
        onCreate(sqLiteDatabase);
        Log.d(TAG, "Database upgraded!");
    }
}
