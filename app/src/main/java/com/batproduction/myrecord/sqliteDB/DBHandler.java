package com.batproduction.myrecord.sqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.batproduction.myrecord.activity.AddEmployee.AddEmployee;
import com.batproduction.myrecord.activity.AddProduct.AddProduct;
import com.batproduction.myrecord.model.EmployeeModel.Employee;
import com.batproduction.myrecord.model.ProductModel.Product;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recordDB";
    private static final String PRODUCT_TABLE_NAME= "product_table";
    private static final String PRODUCT_COLUMN_ID = "product_id";
    private static final String PRODUCT_COLUMN_NAME = "product_name";
    private static final String PRODUCT_COLUMN_COST = "product_cost";


    private static final String EMPLOYEE_TABLE_NAME= "employee_table";
    private static final String EMPLOYEE_COLUMN_ID = "employee_id";
    private static final String EMPLOYEE_COLUMN_NAME = "employee_name";
    private static final String EMPLOYEE_CONTACT = "employee_contact";
    private static final String EMPLOYEE_ADDRESS = "employee_address";
    private static final String EMPLOYEE_AADHARCARD = "employee_aadharcard";
    private static final String EMPLOYEE_BANKNAME = "employee_bankname";
    private static final String EMPLOYEE_BANKIFSC = "employee_bankifsc";
    private static final String EMPLOYEE_BANKACCOUNT = "employee_bankaccount";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " +
                PRODUCT_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_COLUMN_ID + " TEXT," +
                PRODUCT_COLUMN_NAME + " TEXT," +
                PRODUCT_COLUMN_COST+" REAL )";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        addProduct("0123T","0123 Thin", 700,sqLiteDatabase);
        addProduct("0123W","0123 Wide", 800,sqLiteDatabase);
        addProduct("456T","456 Thin", 1100,sqLiteDatabase);
        addProduct("456T","456 Wide", 1300,sqLiteDatabase);
        addProduct("Full","Full", 1500,sqLiteDatabase);

        //ADD Employee----------------------------------------------------------------------------
        String CREATE_ADD_EMP_TABLE = "CREATE TABLE " +
                EMPLOYEE_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMPLOYEE_COLUMN_ID + " TEXT," +
                EMPLOYEE_COLUMN_NAME + " TEXT," +
                EMPLOYEE_CONTACT + " TEXT,"+
                EMPLOYEE_ADDRESS +" TEXT," +
                EMPLOYEE_AADHARCARD+ " TEXT," +
                EMPLOYEE_BANKNAME + " TEXT,"+
                EMPLOYEE_BANKIFSC +" TEXT," +
                EMPLOYEE_BANKACCOUNT+ " TEXT )";
        sqLiteDatabase.execSQL(CREATE_ADD_EMP_TABLE);

        addEmployee("V001","Sachin", "9988776655", "Meerut","DFDRG2563G","ICICI BANK", "ICIC000064","654321478956",sqLiteDatabase);
        addEmployee("V002","Anuj", "9988776655", "Aligarh","DFDRG2563G","ICICI BANK", "ICIC000064","654321478956",sqLiteDatabase);
        addEmployee("V003","Vipin", "9988776655", "Meerut","DFDRG2563G","ICICI BANK", "ICIC000064","654321478956",sqLiteDatabase);
        addEmployee("V004","Abhishek", "9988776655", "Meerut","DFDRG2563G","ICICI BANK", "ICIC000064","654321478956",sqLiteDatabase);
        addEmployee("V005","Pradeep", "9988776655", "Meerut","DFDRG2563G","ICICI BANK", "ICIC000064","654321478956",sqLiteDatabase);
        addEmployee("V006","Amit", "9988776655", "Meerut","DFDRG2563G","ICICI BANK", "ICIC000064","654321478956",sqLiteDatabase);



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

    public List<Product> getProductData(){
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

        return data;
    }

    //----------------------------------------------------------------------------------
    public void addEmployee(String employee_id, String employee_name, String employee_contact,
            String employee_address,String employee_aadharcard,String employee_bankname,
                            String employee_bankifsc,String employee_bankaccount, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put("employee_id", employee_id);
        values.put("employee_name", employee_name);
        values.put("employee_contact", employee_contact);
        values.put("employee_address", employee_address);
        values.put("employee_aadharcard", employee_aadharcard);
        values.put("employee_bankname", employee_bankname);
        values.put("employee_bankifsc", employee_bankifsc);
        values.put("employee_bankaccount", employee_bankaccount);
        database.insert(EMPLOYEE_TABLE_NAME, null, values);

    }

    public boolean addEmployee(String employee_id, String employee_name, String employee_contact,
                               String employee_address,String employee_aadharcard,String employee_bankname,
                               String employee_bankifsc,String employee_bankaccount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employee_id", employee_id);
        values.put("employee_name", employee_name);
        values.put("employee_contact", employee_contact);
        values.put("employee_address", employee_address);
        values.put("employee_aadharcard", employee_aadharcard);
        values.put("employee_bankname", employee_bankname);
        values.put("employee_bankifsc", employee_bankifsc);
        values.put("employee_bankaccount", employee_bankaccount);
        db.insert(EMPLOYEE_TABLE_NAME, null, values);
        db.close();
        return true;

    }

    public List<Employee> getEmployeeData(){
        // Product dataModel = new Product();
        List<Employee> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+EMPLOYEE_TABLE_NAME +" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        Employee dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new Employee();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("employee_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("employee_name"));
            String contact = cursor.getString(cursor.getColumnIndexOrThrow("employee_contact"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("employee_address"));
            String aadharcard = cursor.getString(cursor.getColumnIndexOrThrow("employee_aadharcard"));
            String bankname = cursor.getString(cursor.getColumnIndexOrThrow("employee_bankname"));
            String ifsc = cursor.getString(cursor.getColumnIndexOrThrow("employee_bankifsc"));
            String account = cursor.getString(cursor.getColumnIndexOrThrow("employee_bankaccount"));

            dataModel.setEmployee_id(id);
            dataModel.setEmployee_name(name);
            dataModel.setEmployee_contact(contact);
            dataModel.setEmployee_address(address);
            dataModel.setEmployee_aadharcard(aadharcard);
            dataModel.setEmployee_bankname(bankname);
            dataModel.setEmployee_bankifsc(ifsc);
            dataModel.setEmployee_bankaccount(account);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        return data;
    }



    public boolean deleteitem(Context context, String id){

        SQLiteDatabase db = this.getWritableDatabase();
        if(context instanceof AddProduct){
            db.delete(PRODUCT_TABLE_NAME,PRODUCT_COLUMN_ID+"=?", new String[]{id});
        }
        else if(context instanceof AddEmployee)
//        db.execSQL("delete from "+PRODUCT_TABLE_NAME+" where "+PRODUCT_COLUMN_ID+" ="+id);
            db.delete(EMPLOYEE_TABLE_NAME,EMPLOYEE_COLUMN_ID+"=?", new String[]{id});


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
