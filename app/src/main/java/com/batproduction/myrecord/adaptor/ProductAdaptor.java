package com.batproduction.myrecord.adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.model.ProductModel.Product;
import com.batproduction.myrecord.sqliteDB.DBHandler;

import java.util.List;

public class ProductAdaptor extends RecyclerView.Adapter<ProductAdaptor.ViewHolder> {
    Context context;
    List<Product> productList;

    public ProductAdaptor(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_product_itemview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = productList.get(position);
        DBHandler dbHandler = new DBHandler(context);
        holder.product_id.setText(product.getProduct_id());
        holder.product_name.setText(product.getProduct_name());
        holder.product_cost.setText(product.getProduct_cost());


        holder.product_cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                Log.e("char", String.valueOf(charSequence));
                if(dbHandler.updateProductHandler(product.getProduct_id(),Double.parseDouble(String.valueOf(charSequence)))){
                    Toast.makeText(context,"Cost Updated!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_id, product_name, product_cost;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_cost = itemView.findViewById(R.id.product_priceED);
            product_id = itemView.findViewById(R.id.productIDTV);
            product_name = itemView.findViewById(R.id.product_nameTV);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
