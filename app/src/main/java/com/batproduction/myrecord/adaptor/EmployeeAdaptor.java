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
import com.batproduction.myrecord.model.EmployeeModel.Employee;

import java.util.List;

public class EmployeeAdaptor extends RecyclerView.Adapter<EmployeeAdaptor.ViewHolder> {
    Context context;
    List<Employee> employeeList;

    public EmployeeAdaptor(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_emplyee_itemview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);
        holder.emp_id.setText(employee.getEmployee_id());
        holder.emp_name.setText(employee.getEmployee_name());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emp_id, emp_name;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emp_id = itemView.findViewById(R.id.employeeIDTV);
            emp_name = itemView.findViewById(R.id.employeeNameTV);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
