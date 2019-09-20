package com.batproduction.myrecord.sqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.batproduction.myrecord.model.CashDModule.CashDistributionResponse;
import com.batproduction.myrecord.model.DailyProductionEntryModel.DailyProductModel;
import com.batproduction.myrecord.model.EmployeeModel.Employee;
import com.batproduction.myrecord.model.ProductModel.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recordDB";
    private static final String PRODUCT_TABLE_NAME = "product_table";
    private static final String PRODUCT_COLUMN_ID = "product_id";
    private static final String PRODUCT_COLUMN_NAME = "product_name";
    private static final String PRODUCT_COLUMN_COST = "product_cost";


    private static final String EMPLOYEE_TABLE_NAME = "employee_table";
    private static final String EMPLOYEE_COLUMN_ID = "employee_id";
    private static final String EMPLOYEE_COLUMN_NAME = "employee_name";
    private static final String EMPLOYEE_CONTACT = "employee_contact";
    private static final String EMPLOYEE_ADDRESS = "employee_address";
    private static final String EMPLOYEE_AADHARCARD = "employee_aadharcard";
    private static final String EMPLOYEE_BANKNAME = "employee_bankname";
    private static final String EMPLOYEE_BANKIFSC = "employee_bankifsc";
    private static final String EMPLOYEE_BANKACCOUNT = "employee_bankaccount";

    private static final String DP_TABLE_NAME = "db_table";
    private static final String DP_COLUMN_ID = "dp_id";
    private static final String DP_EMP_ID = "employee_id";
    private static final String DP_PRD_ID = "product_id";
    private static final String DP_PRD_PRS = "price";
    private static final String DP_QTY = "dp_qty";
    private static final String DP_TOTAL = "dp_total";
    private static final String DP_DATE = "dp_date";

    private static final String CASH_TABLE_NAME = "cash_table";
    private static final String CASH_ID = "cash_id";
    private static final String CASH_EMP_ID = "employee_id";
    private static final String CASH_TRANS_DATE = "cash_trans_date";
    private static final String CASH_DISTRIBUTE_AMOUNT = "cash_amount";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " +
                PRODUCT_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT not null, " +
                PRODUCT_COLUMN_ID + " TEXT not null," +
                PRODUCT_COLUMN_NAME + " TEXT not null," +
                PRODUCT_COLUMN_COST + " REAL not null)";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        addProduct("0123T", "0123 Thin", 700, sqLiteDatabase);
        addProduct("0123W", "0123 Wide", 800, sqLiteDatabase);
        addProduct("456T", "456 Thin", 1100, sqLiteDatabase);
        addProduct("456T", "456 Wide", 1300, sqLiteDatabase);
        addProduct("Full", "Full", 1500, sqLiteDatabase);

        //ADD Employee----------------------------------------------------------------------------
        String CREATE_ADD_EMP_TABLE = "CREATE TABLE " +
                EMPLOYEE_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMPLOYEE_COLUMN_ID + " TEXT not null," +
                EMPLOYEE_COLUMN_NAME + " TEXT not null," +
                EMPLOYEE_CONTACT + " TEXT," +
                EMPLOYEE_ADDRESS + " TEXT," +
                EMPLOYEE_AADHARCARD + " TEXT," +
                EMPLOYEE_BANKNAME + " TEXT," +
                EMPLOYEE_BANKIFSC + " TEXT," +
                EMPLOYEE_BANKACCOUNT + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_ADD_EMP_TABLE);

        addEmployee("V001", "Sachin", "9988776655", "Meerut", "DFDRG2563G", "ICICI BANK", "ICIC000064", "654321478956", sqLiteDatabase);
        addEmployee("V002", "Anuj", "9988776655", "Aligarh", "DFDRG2563G", "ICICI BANK", "ICIC000064", "654321478956", sqLiteDatabase);
        addEmployee("V003", "Vipin", "9988776655", "Meerut", "DFDRG2563G", "ICICI BANK", "ICIC000064", "654321478956", sqLiteDatabase);
        addEmployee("V004", "Abhishek", "9988776655", "Meerut", "DFDRG2563G", "ICICI BANK", "ICIC000064", "654321478956", sqLiteDatabase);
        addEmployee("V005", "Pradeep", "9988776655", "Meerut", "DFDRG2563G", "ICICI BANK", "ICIC000064", "654321478956", sqLiteDatabase);
        addEmployee("V006", "Amit", "9988776655", "Meerut", "DFDRG2563G", "ICICI BANK", "ICIC000064", "654321478956", sqLiteDatabase);

//ADD Daily Product Table----------------------------------------------------------------------------
        String CREATE_DAILY_PRODUCTION_TABLE = "CREATE TABLE " +
                DP_TABLE_NAME + "( "+
                DP_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DP_EMP_ID + " TEXT not null," +
                DP_PRD_ID + " TEXT not null," +
                DP_PRD_PRS + " DOUBLE not null," +
                DP_QTY + " INTEGER not null," +
                DP_TOTAL + " DOUBLE not null," +
                DP_DATE + " DATE not null,"+
                " FOREIGN KEY ("+DP_PRD_ID+") REFERENCES "+ PRODUCT_TABLE_NAME+ " ("+DP_PRD_ID+"), "+
                " FOREIGN KEY ("+DP_EMP_ID+") REFERENCES "+ EMPLOYEE_TABLE_NAME+ " ("+DP_EMP_ID+"))";
        sqLiteDatabase.execSQL(CREATE_DAILY_PRODUCTION_TABLE);

        addDailyProductionEntry("V001", "31-Aug,2019 17:52:41","0123T",700,10,7000,sqLiteDatabase);
        addDailyProductionEntry("V002", "31-Aug,2019 17:52:41","456T",1100,10,11000,sqLiteDatabase);
        addDailyProductionEntry("V003", "31-Aug,2019 17:52:41","0123T",700,10,7000,sqLiteDatabase);
        addDailyProductionEntry("V004", "31-Aug,2019 17:52:41","0123T",700,10,7000,sqLiteDatabase);
        addDailyProductionEntry("V005", "31-Aug,2019 17:52:41","full",1300,10,13000,sqLiteDatabase);

//Cash Distribution-------------------------------------------------------------------------------
        String CREATE_CASH_DISTRIBUTION_TABLE = "CREATE TABLE " +
                CASH_TABLE_NAME + "( "+
                CASH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CASH_EMP_ID + " TEXT not null, " +
                CASH_TRANS_DATE + " TEXT not null, " +
                CASH_DISTRIBUTE_AMOUNT + " INTEGER not null, " +
                " FOREIGN KEY ("+ CASH_EMP_ID + ") REFERENCES " + EMPLOYEE_TABLE_NAME+ " ("+CASH_EMP_ID+"))";
        sqLiteDatabase.execSQL(CREATE_CASH_DISTRIBUTION_TABLE);

        addCashDistribution("V001", "31-Aug,2019 17:52:41",700,sqLiteDatabase);
        addCashDistribution("V001", "31-Aug,2019 17:52:41",300,sqLiteDatabase);
        addCashDistribution("V002", "31-Aug,2019 17:52:41",500,sqLiteDatabase);
    }

    public boolean addCashDistribution(String emp_id, String date, int cash_amount) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employee_id",emp_id);
        values.put("cash_trans_date",date);
        values.put("cash_amount",cash_amount);
        sqLiteDatabase.insert(CASH_TABLE_NAME, null, values);
        return true;
    }

    public boolean addCashDistribution(String emp_id, String date, int cash_amount, SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();
        values.put("employee_id",emp_id);
        values.put("cash_trans_date",date);
        values.put("cash_amount",cash_amount);
        sqLiteDatabase.insert(CASH_TABLE_NAME, null, values);
        return true;
    }

    public List<CashDistributionResponse> getCashData(){
        List<CashDistributionResponse> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from "+ CASH_TABLE_NAME + " ;",null);
        Cursor cursor = db.rawQuery("SELECT * FROM employee_table A, " +
                CASH_TABLE_NAME+ " B " +
                "WHERE A.employee_id=B.employee_id ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        CashDistributionResponse response = null;
        while (cursor.moveToNext()){
            response = new CashDistributionResponse();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(CASH_ID));
            String emp_id = cursor.getString(cursor.getColumnIndexOrThrow("A.employee_name"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(CASH_TRANS_DATE));
            String cash = cursor.getString(cursor.getColumnIndexOrThrow(CASH_DISTRIBUTE_AMOUNT));
            response.setCashId(id);
            response.setEmployeeId(emp_id);
            response.setDpDate(date);
            response.setCash(cash);
            stringBuffer.append(response);
            data.add(response);

        }
        return data;
    }


    public boolean addDailyProductionEntry(String emp_id, String date, String product_id,
                                           double price, int qty, double total, SQLiteDatabase s)
    {
        ContentValues values = new ContentValues();
//        values.put("dp_id", );
        values.put("employee_id", emp_id);
        values.put("dp_date", date);
        values.put("product_id", product_id);
        values.put("price", price);
        values.put("dp_qty", qty);
        values.put("dp_total", total);
        s.insert(DP_TABLE_NAME, null, values);
        return true;
    }

    public boolean addDailyProductionEntry(String emp_id, String date, String product_id,
                                           double price, int qty, double total) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
//        values.put("dp_id", 1);
        values.put("employee_id", emp_id);
        values.put("dp_date", date);
        values.put("product_id", product_id);
        values.put("price", price);
        values.put("dp_qty", qty);
        values.put("dp_total", total);
        db.insert(DP_TABLE_NAME, null, values);
        return true;
    }
    public Cursor showProductionTable(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor  cursor = sqLiteDatabase.rawQuery("select * from " + DP_TABLE_NAME + " ;", null);
        if(cursor != null && !cursor.isClosed()){
            cursor.close();

        }
        return cursor;
    }


    public List<DailyProductModel> getDailyProductionData() {
        // Product dataModel = new Product();
        List<DailyProductModel> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from " + DP_TABLE_NAME + " ;", null);
        Cursor cursor = db.rawQuery("SELECT * FROM product_table A,employee_table B,db_table C " +
                "WHERE A.product_id=C.product_id AND B.employee_id=C.employee_id ;", null);

        StringBuffer stringBuffer = new StringBuffer();
        DailyProductModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new DailyProductModel();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("dp_id"));
            String emplid = cursor.getString(cursor.getColumnIndexOrThrow("B.employee_name"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("dp_date"));
            String product_id = cursor.getString(cursor.getColumnIndexOrThrow("A.product_name"));
            String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
            String qty = cursor.getString(cursor.getColumnIndexOrThrow("dp_qty"));
            String total = cursor.getString(cursor.getColumnIndexOrThrow("dp_total"));
            dataModel.setDpId(id);
            dataModel.setEmployeeId(emplid);
            dataModel.setDpDate(date);
            dataModel.setProductId(product_id);
            dataModel.setPrice(price);
            dataModel.setDpQty(qty);
            dataModel.setDpTotal(total);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        return data;
    }


    //    public String loadHandler() {}
    public void addProduct(String product_id, String product_name, double product_cost, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put("product_id", product_id);
        values.put("product_name", product_name);
        values.put("product_cost", product_cost);
        database.insert(PRODUCT_TABLE_NAME, null, values);

    }

    public boolean addProduct(String product_id, String product_name, double product_cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", product_id);
        values.put("product_name", product_name);
        values.put("product_cost", product_cost);
        db.insert(PRODUCT_TABLE_NAME, null, values);
        db.close();
        return true;

    }

    public List<Product> getProductData() {
        // Product dataModel = new Product();
        List<Product> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCT_TABLE_NAME + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        Product dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new Product();
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
    public String fetchProductId(String id){
        String idS = "";
        Log.e("idS",new Gson().toJson(id));
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ PRODUCT_COLUMN_ID +" FROM "+PRODUCT_TABLE_NAME+" Where "+
                PRODUCT_COLUMN_NAME+"=?", new String[]{id});
        Log.e("Curser",new Gson().toJson(cursor));
        List<Double> price = new ArrayList<>();
        while (cursor.moveToNext()) {
            idS = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_ID));

            Log.e("idS",new Gson().toJson(idS));
        }
        cursor.close();
        db.close();
        return idS;
    }
    public double fetchPrice(String id){
        double name = 0;
        Log.e("id",new Gson().toJson(id));
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ PRODUCT_COLUMN_COST +" FROM "+PRODUCT_TABLE_NAME+" Where "+
                PRODUCT_COLUMN_NAME+"=?", new String[]{id});
        Log.e("Curser",new Gson().toJson(cursor));
        List<Double> price = new ArrayList<>();
        while (cursor.moveToNext()) {
            name = cursor.getDouble(cursor.getColumnIndexOrThrow("product_cost"));

            Log.e("price",new Gson().toJson(name));
        }
        cursor.close();
        db.close();
        return name;
    }

    public List<String> fetchProductList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCT_TABLE_NAME + " ;", null);
        List<String> strings = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
            strings.add(name);
        }
        return strings;
    }

    public boolean updateProductHandler(String ID, double cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COLUMN_COST, cost);
        db.update(PRODUCT_TABLE_NAME, values, PRODUCT_COLUMN_ID + "=?", new String[]{ID});
        return true;
    }

    //fetch last latest id--------------------------------------------------------------
    public ArrayList fetchEmpDetails(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<String> arrayList=new ArrayList<String>();
        Cursor cursorEmployees = db.rawQuery("SELECT * FROM "+EMPLOYEE_TABLE_NAME+" Where "+EMPLOYEE_COLUMN_ID+"=?", new String[]{id});
        Log.e(TAG+ " cursorEmployee", new Gson().toJson(cursorEmployees));
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                arrayList.add(cursorEmployees.getString(0));
                arrayList.add(cursorEmployees.getString(1));
                arrayList.add(cursorEmployees.getString(2));
                arrayList.add(cursorEmployees.getString(3));
                arrayList.add(cursorEmployees.getString(4));
                arrayList.add(cursorEmployees.getString(5));
                arrayList.add(cursorEmployees.getString(6));
                arrayList.add(cursorEmployees.getString(7));
                arrayList.add(cursorEmployees.getString(8));

            } while (cursorEmployees.moveToNext());
        }
//        Log.e(TAG, new Gson().toJson(cursorEmployees) );
//      cursorEmployees.close();
        return arrayList;
    }

    //Employee----------------------------------------------------------------------------------
    public void addEmployee(String employee_id, String employee_name, String employee_contact,
                            String employee_address, String employee_aadharcard, String employee_bankname,
                            String employee_bankifsc, String employee_bankaccount, SQLiteDatabase database) {
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
                               String employee_address, String employee_aadharcard, String employee_bankname,
                               String employee_bankifsc, String employee_bankaccount) {
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
    public String fetchEmployee(String name){
        String id = null;

        Log.e("name",new Gson().toJson(name));
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ EMPLOYEE_COLUMN_ID +" FROM "+EMPLOYEE_TABLE_NAME+" Where "+
                EMPLOYEE_COLUMN_NAME+"=?", new String[]{name});
        Log.e("Curser",new Gson().toJson(cursor));
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow(EMPLOYEE_COLUMN_ID));

            Log.e("price",new Gson().toJson(id));
        }
        cursor.close();
        db.close();
        return id;
    }

    public boolean updateEmployeeDetail(String employee_id, String employee_name, String employee_contact,
                               String employee_address, String employee_aadharcard, String employee_bankname,
                               String employee_bankifsc, String employee_bankaccount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("employee_id", employee_id);
        values.put("employee_name", employee_name);
        values.put("employee_contact", employee_contact);
        values.put("employee_address", employee_address);
        values.put("employee_aadharcard", employee_aadharcard);
        values.put("employee_bankname", employee_bankname);
        values.put("employee_bankifsc", employee_bankifsc);
        values.put("employee_bankaccount", employee_bankaccount);
        db.update(EMPLOYEE_TABLE_NAME,  values,EMPLOYEE_COLUMN_ID+ " =?", new String[]{employee_id});
        db.close();
        return true;

    }

    public List<Employee> getEmployeeData() {
        // Product dataModel = new Product();
        List<Employee> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + EMPLOYEE_TABLE_NAME + " ;", null);
        StringBuffer stringBuffer = new StringBuffer();
        Employee dataModel = null;
        while (cursor.moveToNext()) {
            dataModel = new Employee();
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


    public List<String> fetchEmployeeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + EMPLOYEE_TABLE_NAME + " ;", null);
        List<String> strings = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("employee_name"));
            strings.add(name);
        }
        return strings;
    }
//-----------------------------------------------------------------------------------------

    public boolean deleteitem(String context, String id) {
        Log.e(TAG, context);
        SQLiteDatabase db = this.getWritableDatabase();
        if (context.equals("AddProduct")) {
            db.delete(PRODUCT_TABLE_NAME, PRODUCT_COLUMN_ID + "=?", new String[]{id});
        }
        else if (context.equals("AddEmployee")) {
//        db.execSQL("delete from "+PRODUCT_TABLE_NAME+" where "+PRODUCT_COLUMN_ID+" ="+id);
            db.delete(EMPLOYEE_TABLE_NAME, EMPLOYEE_COLUMN_ID + "=?", new String[]{id});
        }
        else if(context.equals("DailyProduction")) {
            db.delete(DP_TABLE_NAME, DP_COLUMN_ID + "=?", new String[]{id});
        }
        else if(context.equals("CashDistribution")){
            db.delete(CASH_TABLE_NAME,CASH_ID + "=?", new String[]{id});
        }
        return true;
    }

// UPGRADE---------------------------------------------------------------------------------

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PRODUCT_TABLE_NAME+" ;");
    }
}
