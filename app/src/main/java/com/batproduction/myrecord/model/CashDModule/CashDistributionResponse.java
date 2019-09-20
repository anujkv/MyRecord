package com.batproduction.myrecord.model.CashDModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashDistributionResponse {
    @SerializedName("cash_id")
    @Expose
    private String cashId;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("dp_date")
    @Expose
    private String dpDate;
    @SerializedName("cash")
    @Expose
    private String cash;

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
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

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

}
