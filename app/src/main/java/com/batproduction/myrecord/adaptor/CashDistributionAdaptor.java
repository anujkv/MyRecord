package com.batproduction.myrecord.adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.model.CashDModule.CashDistributionResponse;
import com.batproduction.myrecord.sqliteDB.DBHandler;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CashDistributionAdaptor extends RecyclerView.Adapter<CashDistributionAdaptor.ViewHolder> {
    Context context;
    List<CashDistributionResponse> distributionResponseList;

    public CashDistributionAdaptor(Context context, List<CashDistributionResponse> distributionResponseList) {
        this.context = context;
        this.distributionResponseList = distributionResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cash_recyclerview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CashDistributionResponse response = distributionResponseList.get(position);
        DBHandler dbHandler = new DBHandler(context);
        holder.cashId.setText(response.getCashId());
        holder.employee_nameTV.setText(response.getEmployeeId());
        holder.dateTV.setText(response.getDpDate());
        holder.cash_distributeED.setText(response.getCash());

        holder.cash_distributeED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                Log.e("char", String.valueOf(charSequence));
                if(dbHandler.updateProductHandler(response.getCashId(),Double.parseDouble(String.valueOf(charSequence)))){
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
        return distributionResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView employee_nameTV, dateTV, cashId;
        EditText cash_distributeED;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cashId = itemView.findViewById(R.id.cashIdTV);
            employee_nameTV = itemView.findViewById(R.id.employeeTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            cash_distributeED = itemView.findViewById(R.id.cashED);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
