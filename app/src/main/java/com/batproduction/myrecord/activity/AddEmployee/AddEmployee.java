package com.batproduction.myrecord.activity.AddEmployee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.adaptor.EmployeeAdaptor;
import com.batproduction.myrecord.databinding.ActivityAddEmployeeBinding;
import com.batproduction.myrecord.model.EmployeeModel.Employee;
import com.batproduction.myrecord.sqliteDB.DBHandler;
import com.batproduction.myrecord.utils.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEmployee extends AppCompatActivity implements View.OnClickListener {
    ActivityAddEmployeeBinding aebi;
    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    String TAG = this.getClass().getSimpleName();

    EmployeeAdaptor employeeAdaptor;
    List<Employee> employeeList;
    DBHandler dbH;
    ImageButton backbtn_toolbar;
    EditText emp_id, emp_name,emp_contact,emp_address,emp_aadharcard,bank_name, bank_ifsc,bank_account;
    String id = "", name = "",contact="",address="",aadharCard="",bankName="",bankIfsc="",backAccount="";
    String cost = "";
    AlertDialog.Builder builder;
    AlertDialog dialog;

    String arrr;
    int ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aebi = DataBindingUtil.setContentView(this, R.layout.activity_add_employee);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) aebi.toolbar);
        title_toolbar.setText("Add Product");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        aebi.fabBtn.setOnClickListener(this);
        dbH = new DBHandler(this);
        initRecyclerView();

    }

    private void initRecyclerView() {
        employeeList = dbH.getEmployeeData();
        employeeAdaptor = new EmployeeAdaptor(this, employeeList);
        aebi.addEmployeeRV.setLayoutManager(new LinearLayoutManager(this));
        aebi.addEmployeeRV.setAdapter(employeeAdaptor);

        aebi.addEmployeeRV.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), aebi.addEmployeeRV, new RecyclerItemClickListener
                .OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                arrr = employeeList.get(position).getEmployee_id();
                Intent intent = new Intent(getApplicationContext(),EmployeeDetails.class);
                intent.putExtra("id",employeeList.get(position).getEmployee_id());

                Log.e("ID",employeeList.get(position).getEmployee_id());
//                startActivity(intent);
                startActivityForResult(intent, 0);
            }



            @Override
            public boolean onLongClick(View view, int position) {
                arrr = employeeList.get(position).getEmployee_id();
//                ar = Integer.parseInt(arrr);

                Log.e("ID", arrr);
                final AlertDialog alertDialog = new AlertDialog.Builder(AddEmployee.this).create();
                alertDialog.setTitle("Are you want to delete this");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("By deleting this, item will permanently be deleted. Are you still want to delete this?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();
//                        finish();
//                        startActivity(getIntent());

                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHandler databaseHelper = new DBHandler(AddEmployee.this);
                        boolean trm = databaseHelper.deleteitem(TAG, arrr);
                        if (trm) {

                            alertDialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
                alertDialog.show();
                return true;
            }
        }));


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
        final View addLayout = layoutInflater.inflate(R.layout.add_new_employee_layout, null);
        emp_id = addLayout.findViewById(R.id.input_employee_id);
        if(employeeList.size()>9){
            emp_id.setText("V0"+(employeeList.size()+1));
        }else if(employeeList.size()>99){
            emp_id.setText("V"+(employeeList.size()+1));
        }else{
            emp_id.setText("V00"+(employeeList.size()+1));
        }
        emp_name = addLayout.findViewById(R.id.input_employee_name);
        emp_contact = addLayout.findViewById(R.id.input_employee_contact);
        emp_address = addLayout.findViewById(R.id.input_employee_address);
        emp_aadharcard = addLayout.findViewById(R.id.input_employee_adharcard);
        bank_name = addLayout.findViewById(R.id.input_bankname);
        bank_ifsc = addLayout.findViewById(R.id.input_employee_ifsc);
        bank_account = addLayout.findViewById(R.id.input_bank_account);
        builder = new AlertDialog.Builder(addLayout.getContext());
        builder.setView(addLayout);
        builder.setCancelable(false);


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {

                try {
                    id = emp_id.getText().toString().trim();
                    name = emp_name.getText().toString().trim();
                    contact = emp_contact.getText().toString().trim();
                    address = emp_address.getText().toString().trim();
                    aadharCard = emp_aadharcard.getText().toString().trim();
                    bankName = bank_name.getText().toString().trim();
                    bankIfsc = bank_ifsc.getText().toString().trim();
                    backAccount = bank_account.getText().toString().trim();

                    DBHandler dbHandler = new DBHandler(AddEmployee.this);
                    if (dbHandler.addEmployee(id, name, contact,address,aadharCard,bankName,bankIfsc,backAccount)) {
                        Toast.makeText(AddEmployee.this, "Product add, successfully!", Toast.LENGTH_SHORT).show();
                        initRecyclerView();

                    } else {
                        Toast.makeText(AddEmployee.this, "Product not add, unsuccessful!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initRecyclerView();
    }
}
