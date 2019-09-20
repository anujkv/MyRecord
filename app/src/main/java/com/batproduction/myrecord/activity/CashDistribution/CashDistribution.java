package com.batproduction.myrecord.activity.CashDistribution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.batproduction.myrecord.adaptor.CashDistributionAdaptor;
import com.batproduction.myrecord.databinding.ActivityCashDistributionBinding;
import com.batproduction.myrecord.model.CashDModule.CashDistributionResponse;
import com.batproduction.myrecord.sqliteDB.DBHandler;
import com.batproduction.myrecord.utils.DateTime;
import com.batproduction.myrecord.utils.RecyclerItemClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CashDistribution extends AppCompatActivity implements View.OnClickListener{
    ActivityCashDistributionBinding acdb;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    ImageButton backBtn_toolbar;
    CashDistributionAdaptor cashDistributionAdaptor;
    List<CashDistributionResponse> responseList;
    private String TAG = this.getClass().getSimpleName();
    DBHandler dbH;
    Spinner employee_name_spinner;
    EditText dateET, cashET;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String name = "", date = "",id = "";

    String arrr;
    int cost = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acdb = DataBindingUtil.setContentView(this, R.layout.activity_cash_distribution);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        title_toolbar.setText("Cash Distribution");
        backBtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backBtn_toolbar.setOnClickListener(this);
        acdb.fabBtn.setOnClickListener(this);
        dbH = new DBHandler(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        responseList = dbH.getCashData();

        if(responseList!=null){

            cashDistributionAdaptor = new CashDistributionAdaptor(this, responseList);
            acdb.cashRV.setLayoutManager(new LinearLayoutManager(this));
            acdb.cashRV.setAdapter(cashDistributionAdaptor);

            acdb.cashRV.addOnItemTouchListener(new RecyclerItemClickListener(
                    getApplicationContext(),acdb.cashRV,new RecyclerItemClickListener
                    .OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    arrr = responseList.get(position).getCashId();
                    Toast.makeText(CashDistribution.this, ""+arrr, Toast.LENGTH_SHORT).show();

                }

                @Override
                public boolean onLongClick(View view, int position) {
                    arrr= responseList.get(position).getCashId();
//                ar = Integer.parseInt(arrr);

                    Log.e("ID",arrr);
                    final AlertDialog alertDialog =new AlertDialog.Builder(CashDistribution.this).create();
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
                            DBHandler databaseHelper = new DBHandler(CashDistribution.this);
                            boolean trm=databaseHelper.deleteitem(TAG, arrr);
                            if (trm){

                                alertDialog.dismiss();
//                                finish();
//                                startActivity(getIntent());
                                initRecyclerView();
                            } }
                    });
                    alertDialog.show();

                    return true;
                }
            }));
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backbtn_toolbar:
                finish();
                break;
            case R.id.fabBtn:
                openCashDialog();
                break;
        }
    }

    private void openCashDialog() {
        DBHandler dbHandler = new DBHandler(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View addLayout = layoutInflater.inflate(R.layout.cash_itemview, null);
        employee_name_spinner = addLayout.findViewById(R.id.employee_name_spinner);
        dateET = addLayout.findViewById(R.id.dateET);
        cashET = addLayout.findViewById(R.id.cashET);
        builder = new AlertDialog.Builder(addLayout.getContext());
        builder.setView(addLayout);
        builder.setCancelable(false);
        builder.setTitle("Cash Distribution Entry");
        DateTime dateTime = new DateTime();
        dateET.setText(dateTime.currentDateTime());
        int amount = 0;

//        dbH.fetchEmployeeList();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(dbH.fetchEmployeeList());
        arrayList.add(0,"Select");
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, arrayList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employee_name_spinner.setAdapter(spinnerArrayAdapter);

        employee_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Clicked on: " + arrayList.get(position), Toast.LENGTH_SHORT).show();
                if(arrayList.contains("Select")){
                    arrayList.remove(0);
                }
                name = arrayList.get(position);
                id = dbHandler.fetchEmployee(name);
                Log.e("id",name);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {

                try {
                    if(isValid()){

                        date = dateET.getText().toString().trim();
                        cost = Integer.parseInt(cashET.getText().toString().trim());

                        DBHandler dbHandler = new DBHandler(CashDistribution.this);
                        if(dbHandler.addCashDistribution(id, date, cost)){
                            Toast.makeText(CashDistribution.this,"Product add, successfully!",Toast.LENGTH_SHORT).show();

                            initRecyclerView();

                        }else{
                            Toast.makeText(CashDistribution.this,"Product not add, unsuccessful!",Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private boolean isValid() {
        if(TextUtils.isEmpty(cashET.getText().toString().trim())){
            cashET.setError("Enter the amount");
            return false;
        }if(id==null){
            return false;
        }
        else{
            return true;
        }
    }
}
