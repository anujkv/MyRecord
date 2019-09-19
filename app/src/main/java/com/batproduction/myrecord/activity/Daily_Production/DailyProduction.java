package com.batproduction.myrecord.activity.Daily_Production;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.databinding.ActivityDailyProductionBinding;
import com.batproduction.myrecord.model.EmployeeModel.Employee;
import com.batproduction.myrecord.model.EmployeeModel.EmployeeNameList;
import com.batproduction.myrecord.model.ProductModel.Product;
import com.batproduction.myrecord.sqliteDB.DBHandler;
import com.batproduction.myrecord.utils.DateTime;
import com.google.gson.Gson;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyProduction extends AppCompatActivity implements View.OnClickListener {
    ActivityDailyProductionBinding adpb;
    String TAG = this.getClass().getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    ImageButton backBtn_toolbar;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText emp_id, input_created_date, input_product_name, input_price, input_qty, input_total;
    String id, date, product_id,product_name,name;
    double price, total;
    int qty;
    SQLiteDatabase s;
    Spinner employeeSpinner, productSpinner;
    List<EmployeeNameList> employeeNameLists;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adpb = DataBindingUtil.setContentView(this, R.layout.activity_daily_production);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        title_toolbar.setText("Daily Production Entry");
        backBtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backBtn_toolbar.setOnClickListener(this);
        adpb.fabBtn.setOnClickListener(this);

        Cursor data;
        DBHandler dbHandler = new DBHandler(DailyProduction.this);
//        data = dbHandler.showProductionTable();
//        if (data != null && data.moveToFirst()) {
//            do {
//                View tableRow = LayoutInflater.from(this).inflate(R.layout.entry_row_item_layout, null, false);
//                TextView uuid = (TextView) tableRow.findViewById(R.id.uuid);
//                TextView emp_id = (TextView) tableRow.findViewById(R.id.emp_id);
//                TextView date = (TextView) tableRow.findViewById(R.id.date);
//                TextView product_name = (TextView) tableRow.findViewById(R.id.product_name);
//                TextView price = (TextView) tableRow.findViewById(R.id.price);
//                TextView qty = (TextView) tableRow.findViewById(R.id.qty);
//                TextView total = (TextView) tableRow.findViewById(R.id.total);
//
//
//                uuid.setText(data.getString(1));
//                emp_id.setText(data.getString(2));
//                date.setText(data.getString(3));
//                product_name.setText(data.getString(4));
//                price.setText(data.getString(5));
//                qty.setText(data.getString(6));
//                total.setText(data.getString(7));
//                adpb.tableLayout.addView(tableRow);
//
//            } while (data.moveToNext());
////            data.close();
//        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.fabBtn:
                openAddProductDialog();
                break;
        }
    }

    private void openAddProductDialog() {
        LayoutInflater layoutInflater = getLayoutInflater();
        final View addLayout = layoutInflater.inflate(R.layout.add_entry_layout, null);
        emp_id = addLayout.findViewById(R.id.input_employee_id);

        input_created_date = addLayout.findViewById(R.id.input_created_date);
        input_product_name = addLayout.findViewById(R.id.input_product_name);
        employeeSpinner = addLayout.findViewById(R.id.employeeSpinner);
        productSpinner = addLayout.findViewById(R.id.productSpinner);
        input_price = addLayout.findViewById(R.id.input_price);
        input_qty = addLayout.findViewById(R.id.input_qty);
        input_total = addLayout.findViewById(R.id.input_total);
        builder = new AlertDialog.Builder(addLayout.getContext());
        builder.setView(addLayout);
        builder.setCancelable(false);

        DateTime dateTime = new DateTime();
        input_created_date.setText(dateTime.currentDateTime());

        DBHandler dbHandler = new DBHandler(this);
        List<String> arrayNamaList = dbHandler.fetchEmployeeList();
        arrayNamaList.add(0, "Select");
        List<Product> productList;
        productList = dbHandler.getProductData();
        List<Employee> employeeList;
        employeeList = dbHandler.getEmployeeData();

        List<String> arrayProductNameList = dbHandler.fetchProductList();
        arrayProductNameList.add(0, "Select");

        Log.e(TAG + " Product", new Gson().toJson(productList));
        Log.e(TAG + " employeeList", new Gson().toJson(employeeList));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, arrayNamaList);
        employeeSpinner.setAdapter(spinnerArrayAdapter);

        final ArrayAdapter<String> spinnerProductArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, arrayProductNameList);
        productSpinner.setAdapter(spinnerProductArrayAdapter);

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Clicked on: " + arrayProductNameList.get(position), Toast.LENGTH_SHORT).show();
                product_name = arrayProductNameList.get(position);
                product_id = dbHandler.fetchProductId(product_name);
                price = dbHandler.fetchPrice(product_name);
                input_price.setText(String.valueOf(price));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Clicked on: " + arrayNamaList.get(position), Toast.LENGTH_SHORT).show();
                name = arrayNamaList.get(position);
                id = dbHandler.fetchEmployee(name);
                Log.e("id",name);
//                input_price.setText(String.valueOf(id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        input_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.e("beforeTextChanged", "" + start + ", count: " + count + ", before:" + before);

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                Log.e("onTextChanged", "" + start + ", count: " + count + ", after:" + after);
                if (start == 0) {
                    input_total.setText(start + "");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterTextChanged", "" + editable);
                try {
                    if (Integer.parseInt(editable.toString()) > 0) {
                        Log.e("Value ", String.valueOf(10 * Integer.parseInt(editable.toString())));
                        int x = Integer.parseInt(editable.toString());
                        input_total.setText((Double.parseDouble(input_price.getText().toString().trim()) * x) + "");
                        total = Double.parseDouble(input_price.getText().toString().trim()) * x;
                        qty = x;
                    }
                } catch (NumberFormatException e) {
                    input_total.setText("0");
                    total = 0;
                }
            }
        });

//
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });
//
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                date = dateTime.currentDateTime();
                input_created_date.setText(date);

                try {

                    DBHandler dbHandler = new DBHandler(DailyProduction.this);

                    if (dbHandler.addDailyProductionEntry(id, date, product_id, price, qty, total)) {

                        Toast.makeText(DailyProduction.this, "Product add, successfully!", Toast.LENGTH_SHORT).show();

//                        initRecyclerView();

                    } else {
                        Toast.makeText(DailyProduction.this, "Data not add, unsuccessful!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialog = builder.create();
        dialog.show();
    }

}
