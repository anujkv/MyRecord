package com.batproduction.myrecord.model.DailyProductionEntryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyProductModel {

    @SerializedName("dp_id")
    @Expose
    private String dpId;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("dp_date")
    @Expose
    private String dpDate;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("dp_qty")
    @Expose
    private String dpQty;
    @SerializedName("dp_total")
    @Expose
    private String dpTotal;

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDpDate() {
        return dpDate;
    }

    public void setDpDate(String dpDate) {
        this.dpDate = dpDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDpQty() {
        return dpQty;
    }

    public void setDpQty(String dpQty) {
        this.dpQty = dpQty;
    }

    public String getDpTotal() {
        return dpTotal;
    }

    public void setDpTotal(String dpTotal) {
        this.dpTotal = dpTotal;
    }

}
