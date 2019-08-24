package com.batproduction.myrecord.model.EmployeeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeNameList {
    @SerializedName("employee_name")
    @Expose
    private String employee_name;

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
}
