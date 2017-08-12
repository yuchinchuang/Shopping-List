package com.example.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ShoppingDataSource {

    private static final String TAG = "ShoppingItemDataSource";

    private SQLiteDatabase database;
    private ShoppingSQLiteOpenHelper dbHelper;

    private static final String[] TABLE_SHOPPINGLIST = {
            ShoppingSQLiteOpenHelper.COLUMN_ID,
            ShoppingSQLiteOpenHelper.COLUMN_NAME,
            ShoppingSQLiteOpenHelper.COLUMN_QUANTITY,
            ShoppingSQLiteOpenHelper.COLUMN_ISPURCHASED
    };

    public ShoppingDataSource(Context context)
    {
        dbHelper = new ShoppingSQLiteOpenHelper(context);
    }

    public void open(){
        try{
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLException sqle)
        {
            Log.d(TAG, "Could not open the database! - " + sqle.getMessage());
        }
    }

    public void close(){
        database.close();
    }

    public void insert(String name, int quantity)
    {
        ContentValues values = new ContentValues();
        values.put(ShoppingSQLiteOpenHelper.COLUMN_NAME, name);
        values.put(ShoppingSQLiteOpenHelper.COLUMN_QUANTITY, quantity);
        values.put(ShoppingSQLiteOpenHelper.COLUMN_ISPURCHASED, 0);

        long id = database.insert(
                ShoppingSQLiteOpenHelper.TABLE_SHOPPINGLIST,
                null,
                values
        );

        Log.d(TAG, "Insert shopItem " + id + " into the database!");
    }

    public void delete(ShoppingItem shoppingItem)
    {
        long id = shoppingItem.getId();
        database.delete(
                ShoppingSQLiteOpenHelper.TABLE_SHOPPINGLIST,
                ShoppingSQLiteOpenHelper.COLUMN_ID + " = " + id,
                null
        );
        Log.d(TAG, "ShopItem " + id + " deleted!");
    }

    public void deleteAll(){
        database.delete(ShoppingSQLiteOpenHelper.TABLE_SHOPPINGLIST, null, null);
    }

    public ArrayList<ShoppingItem> getAll(){
        ArrayList<ShoppingItem> shoppingList = new ArrayList<>();

        Cursor cursor = database.query(
                ShoppingSQLiteOpenHelper.TABLE_SHOPPINGLIST,
                TABLE_SHOPPINGLIST,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            ShoppingItem shoppingItem = cursorToShoppingItem(cursor);
            shoppingList.add(shoppingItem);

            cursor.moveToNext();
        }

        cursor.close();
        return shoppingList;
    }

    private ShoppingItem cursorToShoppingItem(Cursor cursor) {

        ShoppingItem temp = new ShoppingItem(cursor.getString(1), cursor.getInt(2));
        temp.setId(cursor.getLong(0));
        temp.setIsPurchased(cursor.getInt(3));
        return temp;
    }

    public void update(ShoppingItem item){

        ContentValues values = new ContentValues();
        if(item.getIsPurchased() == 0)
        {
            values.put(ShoppingSQLiteOpenHelper.COLUMN_ISPURCHASED, 1);
        }
        else
        {
            values.put(ShoppingSQLiteOpenHelper.COLUMN_ISPURCHASED, 0);
        }

        database.update(
                ShoppingSQLiteOpenHelper.TABLE_SHOPPINGLIST,
                values,
                ShoppingSQLiteOpenHelper.COLUMN_ID + " = " + item.getId(),
                null
        );
    }
}
