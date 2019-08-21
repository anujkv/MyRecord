package com.batproduction.myrecord.sqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.batproduction.myrecord.model.Product;
import com.batproduction.myrecord.utils.CONSTANT;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB";
    private static final String PRODUCT_TABLE_NAME= "product_table";
    private static final String PRODUCT_COLUMN_ID = "product_id";
    private static final String PRODUCT_COLUMN_NAME = "product_name";
    private static final String PRODUCT_COLUMN_COST = "product_cost";

    public DBHandler(Context context) {
        super(context, PRODUCT_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + PRODUCT_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUCT_COLUMN_ID +
                " TEXT," + PRODUCT_COLUMN_NAME + " TEXT," + PRODUCT_COLUMN_COST+" REAL )";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        addProduct("0123T","0123 Thin", 700,sqLiteDatabase);
        addProduct("0123W","0123 Wide", 800,sqLiteDatabase);
        addProduct("456T","456 Thin", 1100,sqLiteDatabase);
        addProduct("456T","456 Wide", 1300,sqLiteDatabase);
        addProduct("Full","Full", 1500,sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE_NAME+" ;");
    }

//    public String loadHandler() {}
    public void addProduct(String product_id, String product_name, double product_cost, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put("product_id",product_id);
        values.put("product_name",product_name);
        values.put("product_cost",product_cost);
        database.insert(PRODUCT_TABLE_NAME, null,values);

    }

    public boolean addProduct(String product_id, String product_name, double product_cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id",product_id);
        values.put("product_name",product_name);
        values.put("product_cost",product_cost);
        db.insert(PRODUCT_TABLE_NAME, null,values);
        db.close();
        return true;

    }
//    public Product findHandler(String product_name) {}

    public List<Product> getdata(){
        // Product dataModel = new Product();
        List<Product> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+PRODUCT_TABLE_NAME+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        Product dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new Product();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("product_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
            String cost = cursor.getString(cursor.getColumnIndexOrThrow("product_cost"));
            dataModel.setProduct_id(id);
            dataModel.setProduct_name(name);
            dataModel.setProduct_cost(cost);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (Product mo:data ) {

            Log.e("Hellomo",""+mo.getProduct_name());
        }
        //
        return data;
    }

    public boolean deleteitem(String id){

        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from "+PRODUCT_TABLE_NAME+" where "+PRODUCT_COLUMN_ID+" ="+id);
        db.delete(PRODUCT_TABLE_NAME,PRODUCT_COLUMN_ID+"=?", new String[]{id});

        return  true;

    }


    public boolean updateProductHandler(String ID, double cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COLUMN_COST,cost);
        db.update(PRODUCT_TABLE_NAME,values,PRODUCT_COLUMN_ID+"=?", new String[]{ID});
        return true;
    }
}
