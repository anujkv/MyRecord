package com.batproduction.myrecord.model.DailyProductionEntryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyProductModel {

    @SerializedName("employee_id")
    @Expose
    private String employee_id;
    @SerializedName("dp_date")
    @Expose
    private String dp_date;
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("product_cost")
    @Expose
    private String product_cost;
    @SerializedName("dp_qty")
    @Expose
    private String dp_qty;
    @SerializedName("dp_total")
    @Expose
    private String dp_total;

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getDp_date() {
        return dp_date;
    }

    public void setDp_date(String dp_date) {
        this.dp_date = dp_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_cost() {
        return product_cost;
    }

    public void setProduct_cost(String product_cost) {
        this.product_cost = product_cost;
    }

    public String getDp_qty() {
        return dp_qty;
    }

    public void setDp_qty(String dp_qty) {
        this.dp_qty = dp_qty;
    }

    public String getDp_total() {
        return dp_total;
    }

    public void setDp_total(String dp_total) {
        this.dp_total = dp_total;
    }
}
