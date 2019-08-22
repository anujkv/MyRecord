package com.batproduction.myrecord.activity.AddProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.adaptor.ProductAdaptor;
import com.batproduction.myrecord.databinding.ActivityAddProductBinding;
import com.batproduction.myrecord.model.ProductModel.Product;
import com.batproduction.myrecord.sqliteDB.DBHandler;
import com.batproduction.myrecord.utils.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    ActivityAddProductBinding apbi;
    @BindView(R.id.title_toolbar)
    TextView title_toolbar;
    private String TAG = this.getClass().getSimpleName();

    ProductAdaptor productAdaptor;
    List<Product> productList;
    DBHandler dbH;
    ImageButton backbtn_toolbar;
    EditText input_product_id, input_product_name, input_product_cost;
    String id = "", name = "";
    String cost = "";
    AlertDialog.Builder builder;
    AlertDialog dialog;

    String arrr;
    int ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apbi = DataBindingUtil.setContentView(this, R.layout.activity_add_product);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) apbi.toolbar);
        title_toolbar.setText("Add Product");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(this);
        apbi.fabBtn.setOnClickListener(this);
        dbH = new DBHandler(this);
        initRecyclerView();


//        //firebase DataBase - getInstance
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");
//
//        //firebase - onDataChange
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

//        DBHandler dbHandler = new DBHandler(this);
//        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
//
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT "+ CONSTANT.product_id +", "+
//                CONSTANT.product_name+", "+CONSTANT.product_cost+" FROM "+
//                CONSTANT.product_table, new String[]{});
//        if(cursor!=null){
//            cursor.moveToFirst();
//        }
//        StringBuilder stringBuilder= new StringBuilder();
//        List<Product> list = new ArrayList<>();
//        do{
//            try{
//                if (cursor != null) {
//                    String id = cursor.getString(0);
//                    String name = cursor.getString(1);
//                    double cost = cursor.getDouble(2);
//                    stringBuilder.append(CONSTANT.product_id + " - " + id +"\t" + CONSTANT.product_name +
//                            " - " + name +"\t" + CONSTANT.product_cost+ " - " +cost );
////                    list.addAll(cursor.)
//
//                }else{
//                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
//                }
//            }catch (CursorIndexOutOfBoundsException e){
//                e.printStackTrace();
//                Toast.makeText(this,new Gson().toJson(e),Toast.LENGTH_LONG).show();
//
//            }


//            productList=  dbH.getdata();
//            productAdaptor =new ProductAdaptor(this, productList);
//
//
//            Log.i("HIteshdata",""+productList);
//            RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext());
//            apbi.addProductRV.setLayoutManager(reLayoutManager);
//            apbi.addProductRV.setItemAnimator(new DefaultItemAnimator());
//            apbi.addProductRV.setAdapter(productAdaptor);
//        }
//        while (cursor.moveToNext());

    }

    private void initRecyclerView() {
        productList = dbH.getProductData();
        productAdaptor = new ProductAdaptor(this, productList);
        apbi.addProductRV.setLayoutManager(new LinearLayoutManager(this));
        apbi.addProductRV.setAdapter(productAdaptor);

        apbi.addProductRV.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),apbi.addProductRV,new RecyclerItemClickListener
                .OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String arrr= productList.get(position).getProduct_id();
                Toast.makeText(AddProduct.this, ""+arrr, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                arrr= productList.get(position).getProduct_id();
//                ar = Integer.parseInt(arrr);

                Log.e("ID",arrr);
                final AlertDialog alertDialog =new AlertDialog.Builder(AddProduct.this).create();
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
                        DBHandler databaseHelper = new DBHandler(AddProduct.this);
                        boolean trm=databaseHelper.deleteitem(TAG, arrr);
                        if (trm){

                            alertDialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        } }
                });
                alertDialog.show();

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
        final View addLayout = layoutInflater.inflate(R.layout.add_new_product_layout, null);
        input_product_id = addLayout.findViewById(R.id.input_product_id);
        input_product_name = addLayout.findViewById(R.id.input_product_name);
        input_product_cost = addLayout.findViewById(R.id.input_product_cost);
        builder = new AlertDialog.Builder(addLayout.getContext());
        builder.setView(addLayout);
        builder.setCancelable(false);


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
                    id = input_product_id.getText().toString().trim();
                    name = input_product_name.getText().toString().trim();
                    cost = input_product_cost.getText().toString().trim();

                    DBHandler dbHandler = new DBHandler(AddProduct.this);
                    if(dbHandler.addProduct(id, name, Double.parseDouble(cost))){
                     Toast.makeText(AddProduct.this,"Product add, successfully!",Toast.LENGTH_SHORT).show();

                        initRecyclerView();

                    }else{
                        Toast.makeText(AddProduct.this,"Product not add, unsuccessful!",Toast.LENGTH_SHORT).show();
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
