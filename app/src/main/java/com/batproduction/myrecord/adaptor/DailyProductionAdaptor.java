package com.batproduction.myrecord.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.model.DailyProductionEntryModel.DailyProductModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DailyProductionAdaptor extends RecyclerView.Adapter<DailyProductionAdaptor.ViewHolder> {
    Context context;
    List<DailyProductModel> productModelList;


    public DailyProductionAdaptor(Context context, List<DailyProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_production_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DailyProductModel dailyProductModel = productModelList.get(position);
        holder.id.setText(dailyProductModel.getDpId());
        holder.emplid.setText(dailyProductModel.getEmployeeId());
        holder.date.setText(dailyProductModel.getDpDate());
        holder.product_id.setText(dailyProductModel.getProductId());
        holder.price.setText(dailyProductModel.getPrice());
        holder.qty.setText(dailyProductModel.getDpQty());
        holder.total.setText(dailyProductModel.getDpTotal());


    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, emplid, date, product_id, price, qty, total;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.IDTV);
            emplid = itemView.findViewById(R.id.employeeIDTV);
            date = itemView.findViewById(R.id.DateIDTV);
            product_id = itemView.findViewById(R.id.productIDTV);
            price = itemView.findViewById(R.id.priceIDTV);
            qty = itemView.findViewById(R.id.qtyTV);
            total = itemView.findViewById(R.id.totalTV);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
