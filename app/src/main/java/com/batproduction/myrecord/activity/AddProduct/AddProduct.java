package com.batproduction.myrecord.activity.AddProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.adaptor.ProductAdaptor;
import com.batproduction.myrecord.databinding.ActivityAddProductBinding;
import com.batproduction.myrecord.model.Product;
import com.batproduction.myrecord.sqliteDB.DBHandler;

import java.util.List;

import butterknife.BindView;

public class AddProduct extends AppCompatActivity {
    ActivityAddProductBinding apbi;
    @BindView(R.id.title_toolbar)
    TextView title_toolbar;

    ProductAdaptor productAdaptor;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apbi = DataBindingUtil.setContentView(this, R.layout.activity_add_product);
        setSupportActionBar((Toolbar) apbi.toolbar);
//        title_toolbar.setText("Add Product");
        ImageButton backbtn_toolbar = findViewById(R.id.backbtn_toolbar);
        backbtn_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initRecyclerView();

        DBHandler dbHandler = new DBHandler(this);
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();


    }

    private void initRecyclerView() {
        productAdaptor = new ProductAdaptor(this, productList);
        apbi.addProductRV.setLayoutManager(new LinearLayoutManager(this));
        apbi.addProductRV.setAdapter(productAdaptor);
    }
}
