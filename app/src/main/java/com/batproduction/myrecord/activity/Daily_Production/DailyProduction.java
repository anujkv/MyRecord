package com.batproduction.myrecord.activity.Daily_Production;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.batproduction.myrecord.activity.AddEmployee.AddEmployee;
import com.batproduction.myrecord.databinding.ActivityDailyProductionBinding;
import com.batproduction.myrecord.model.EmployeeModel.Employee;
import com.batproduction.myrecord.model.EmployeeModel.EmployeeNameList;
import com.batproduction.myrecord.model.ProductModel.Product;
import com.batproduction.myrecord.sqliteDB.DBHandler;
import com.batproduction.myrecord.utils.DateTime;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyProduction extends AppCompatActivity implements View.OnClickListener{
    ActivityDailyProductionBinding adpb;
    String TAG = this.getClass().getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    ImageButton backBtn_toolbar;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    EditText emp_id,input_created_date,input_product_name,input_price,input_qty,input_total;
    Spinner employeeSpinner,productSpinner;
    List<EmployeeNameList> employeeNameLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adpb = DataBindingUtil.setContentView(this, R.layout.activity_daily_production);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        title_toolbar.setText("Daily Production Entry");
        backBtn_toolbar =findViewById(R.id.backbtn_toolbar);
        backBtn_toolbar.setOnClickListener(this);
        adpb.fabBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
        List<String> arrayNamaList =  dbHandler.fetchEmployeeList();
        arrayNamaList.add(0,"Select");
        List<Product> productList;
        productList = dbHandler.getProductData();
        List<Employee> employeeList;
        employeeList = dbHandler.getEmployeeData();

        List<String> arrayProductNameList = dbHandler.fetchProductList();
        arrayProductNameList.add(0,"Select");

        Log.e(TAG + " Product",new Gson().toJson(productList));
        Log.e(TAG + " employeeList",new Gson().toJson(employeeList));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,arrayNamaList);
        employeeSpinner.setAdapter(spinnerArrayAdapter);

        final ArrayAdapter<String> spinnerProductArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,arrayProductNameList);
        productSpinner.setAdapter(spinnerProductArrayAdapter);

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Clicked on: " + arrayProductNameList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Clicked on: " + arrayNamaList.get(position), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        input_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.e("beforeTextChanged",""+start+ ", count: "+ count + ", before:" + before);

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                Log.e("onTextChanged",""+start+ ", count: "+ count + ", after:" + after);
                if(start==0){
                    input_total.setText(start+"");
                }

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterTextChanged",""+editable);
                try {
                    if (Integer.parseInt(editable.toString()) > 0) {
                        Log.e("Value ", String.valueOf(10 * Integer.parseInt(editable.toString())));
                        int x = Integer.parseInt(editable.toString());
                        input_total.setText((10*x) + "");
                    }
                }catch(NumberFormatException e){
                    input_total.setText("0");
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

                input_created_date.setText(dateTime.currentDateTime());

//                try {
//                    id = emp_id.getText().toString().trim();
//                    name = emp_name.getText().toString().trim();
//                    contact = emp_contact.getText().toString().trim();
//                    address = emp_address.getText().toString().trim();
//                    aadharCard = emp_aadharcard.getText().toString().trim();
//                    bankName = bank_name.getText().toString().trim();
//                    bankIfsc = bank_ifsc.getText().toString().trim();
//                    backAccount = bank_account.getText().toString().trim();
//
//                    DBHandler dbHandler = new DBHandler(AddEmployee.this);
//                    if (dbHandler.addEmployee(id, name, contact,address,aadharCard,bankName,bankIfsc,backAccount)) {
//                        Toast.makeText(AddEmployee.this, "Product add, successfully!", Toast.LENGTH_SHORT).show();
//
//                        initRecyclerView();
//
//                    } else {
//                        Toast.makeText(AddEmployee.this, "Product not add, unsuccessful!", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
            }
        });
        dialog = builder.create();
        dialog.show();
    }

}
