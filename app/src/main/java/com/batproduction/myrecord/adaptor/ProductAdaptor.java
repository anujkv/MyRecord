package com.batproduction.myrecord.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.model.Product;

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
        holder.product_id.setText(product.getProduct_id());
        holder.product_name.setText(product.getProduct_name());
        holder.product_cost.setText(product.getProduct_cost());
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
