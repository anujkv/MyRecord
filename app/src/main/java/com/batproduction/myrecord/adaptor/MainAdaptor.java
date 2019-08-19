package com.batproduction.myrecord.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.batproduction.myrecord.R;
import com.batproduction.myrecord.model.MainModel;

import java.util.List;

public class MainAdaptor extends RecyclerView.Adapter<MainAdaptor.HolderView> {
    private Context context;
    private List<MainModel> mainModel;

//    CheckAccessRole accessRoleInstance;

    public MainAdaptor(Context context, List<MainModel> mainModel) {
        this.context = context;
        this.mainModel = mainModel;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_cardview_items,viewGroup,false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, final int i) {
        holderView.textViewOptionName.setText(mainModel.get(i).getOptionName());
        holderView.appRoleImage.setImageResource(mainModel.get(i).getImage());
//        accessRoleInstance = new CheckAccessRole();
//        list = (ArrayList<String>) loadSharedPreferencesLogList(context);
//        Log.e("listAdaptor",new Gson().toJson(list));
        holderView.cardViewL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 0:
//                        check("operations");
//                        accessRoleInstance.check("operations",context);
                        Toast.makeText(context, "Add Product",
                                Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, HRMS.class));
                        break;
                    case 1:
//                        accessRoleInstance.check("hrms",context);
                        Toast.makeText(context, "Add Employee",
                                Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, CustomerVisit.class));
                        break;
                    case 2:
//                        accessRoleInstance.check("crm",context);
                        Toast.makeText(context, "Daily Production Entry",
                                Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, PurchaseOrderList.class));
                        break;
                    case 3:
//                        accessRoleInstance.check("activities",context);
                        Toast.makeText(context, "Daily Distribution Cash",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context, "Employee Income",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mainModel.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        TextView textViewOptionName;
        ImageView appRoleImage;
        LinearLayout cardViewL;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            textViewOptionName = itemView.findViewById(R.id.optionName);
            appRoleImage = itemView.findViewById(R.id.app_role_image);
            cardViewL = itemView.findViewById(R.id.cardview_linearLayout);
        }
    }
}
