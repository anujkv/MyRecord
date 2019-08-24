package com.batproduction.myrecord.activity.AddEmployee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.batproduction.myrecord.DataBinderMapperImpl;
import com.batproduction.myrecord.R;
import com.batproduction.myrecord.databinding.ActivityEmployeeDetailsBinding;
import com.batproduction.myrecord.sqliteDB.DBHandler;
import com.batproduction.myrecord.utils.CONSTANT;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeDetails extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    private Bundle bundle;
    private String id;
    private SQLiteDatabase sqLiteDatabase;
    ActivityEmployeeDetailsBinding aedb;
    Cursor cursor;
    ImageButton backBtn_toolbar;
    boolean FLAG = false;
    DBHandler dbHandler = new DBHandler(this);
    ArrayList<String> arrayList;
    public static AtomicInteger activitiesLaunched = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (activitiesLaunched.incrementAndGet() > 1) { finish(); }
        super.onCreate(savedInstanceState);
        aedb = DataBindingUtil.setContentView(this, R.layout.activity_employee_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        title_toolbar.setText("Employee Details");
        backBtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backBtn_toolbar.setOnClickListener(this);
        aedb.okBtn.setOnClickListener(this);
        aedb.editBtn.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();



        if (bundle != null) {
            id = bundle.getString("id");
            if (id != null) {
                arrayList = dbHandler.fetchEmpDetails(id);
                Log.e("TAG", new Gson().toJson(arrayList));
                        aedb.inputEmployeeId.setText(arrayList.get(1));
                        aedb.inputEmployeeName.setText(arrayList.get(2));
                        aedb.inputEmployeeContact.setText(arrayList.get(3));
                        aedb.inputEmployeeAddress.setText(arrayList.get(4));
                        aedb.inputEmployeeAdharcard.setText(arrayList.get(5));
                        aedb.inputBankname.setText(arrayList.get(6));
                        aedb.inputEmployeeIfsc.setText(arrayList.get(7));
                        aedb.inputBankAccount.setText(arrayList.get(8));
            }

        } else {
            Toast.makeText(getApplicationContext(), CONSTANT.dataNotFound, Toast.LENGTH_SHORT).show();
            finish();

        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.okBtn:
                finish();
                break;
            case R.id.editBtn:
                if(FLAG){
                    saveData();
                    FLAG= false;

                }else{
                    editData();
                    FLAG = true;
                }
                break;
        }
    }

    private void saveData() {
        aedb.editBtn.setText(R.string.edit);
        aedb.inputEmployeeName.setEnabled(false);
        aedb.inputEmployeeContact.setEnabled(false);
        aedb.inputEmployeeAddress.setEnabled(false);
        aedb.inputEmployeeAdharcard.setEnabled(false);
        aedb.inputBankname.setEnabled(false);
        aedb.inputEmployeeIfsc.setEnabled(false);
        aedb.inputBankAccount.setEnabled(false);

        dbHandler.updateEmployeeDetail(arrayList.get(1),
                aedb.inputEmployeeName.getText().toString().trim(),
                aedb.inputEmployeeContact.getText().toString().trim(),
                aedb.inputEmployeeAddress.getText().toString().trim(),
                aedb.inputEmployeeAdharcard.getText().toString().trim(),
                aedb.inputBankname.getText().toString().trim(),
                aedb.inputEmployeeIfsc.getText().toString().trim(),
                aedb.inputBankAccount.getText().toString().trim());
    }

    private void editData() {
        aedb.editBtn.setText(R.string.save);
        aedb.inputEmployeeName.setEnabled(true);
        aedb.inputEmployeeContact.setEnabled(true);
        aedb.inputEmployeeAddress.setEnabled(true);
        aedb.inputEmployeeAdharcard.setEnabled(true);
        aedb.inputBankname.setEnabled(true);
        aedb.inputEmployeeIfsc.setEnabled(true);
        aedb.inputBankAccount.setEnabled(true);
    }

    @Override
    protected void onDestroy() {

        //remove this activity from the counter
        activitiesLaunched.getAndDecrement();

        super.onDestroy();

    }
}
