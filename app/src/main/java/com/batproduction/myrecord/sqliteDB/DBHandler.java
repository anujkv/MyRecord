package com.batproduction.myrecord.sqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.batproduction.myrecord.model.Product;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB";
    private static final String TABLE_NAME= "product";
    private static final String COLUMN_ID = "product_id";
    private static final String COLUMN_NAME = "product_name";
    private static final String COLUMN_COST = "product_cost";

    public DBHandler(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ID +
                "TEXT," + COLUMN_NAME + "TEXT," +COLUMN_COST+"REAL )";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    public String loadHandler() {}
    public void addProduct(String product_id, String product_name, double product_cost, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put("product_id",product_id);
        values.put("product_name",product_name);
        values.put("product_cost",product_cost);
        database.insert(TABLE_NAME, null,values);

    }
//    public Product findHandler(String product_name) {}
    public boolean deleteHandler(int ID) { return true;}
//    public boolean updateHandler(int ID, String name) {}
}
