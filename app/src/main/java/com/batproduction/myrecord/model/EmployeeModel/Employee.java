package com.batproduction.myrecord.model.EmployeeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("employee_id")
    @Expose
    private String employee_id;
    @SerializedName("employee_name")
    @Expose
    private String employee_name;
    @SerializedName("employee_contact")
    @Expose
    private String employee_contact;
    @SerializedName("employee_address")
    @Expose
    private String employee_address;
    @SerializedName("employee_aadharcard")
    @Expose
    private String employee_aadharcard;
    @SerializedName("employee_bankname")
    @Expose
    private String employee_bankname;
    @SerializedName("employee_bankifsc")
    @Expose
    private String employee_bankifsc;
    @SerializedName("employee_bankaccount")
    @Expose
    private String employee_bankaccount;

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_contact() {
        return employee_contact;
    }

    public void setEmployee_contact(String employee_contact) {
        this.employee_contact = employee_contact;
    }

    public String getEmployee_address() {
        return employee_address;
    }

    public void setEmployee_address(String employee_address) {
        this.employee_address = employee_address;
    }

    public String getEmployee_aadharcard() {
        return employee_aadharcard;
    }

    public void setEmployee_aadharcard(String employee_aadharcard) {
        this.employee_aadharcard = employee_aadharcard;
    }

    public String getEmployee_bankname() {
        return employee_bankname;
    }

    public void setEmployee_bankname(String employee_bankname) {
        this.employee_bankname = employee_bankname;
    }

    public String getEmployee_bankifsc() {
        return employee_bankifsc;
    }

    public void setEmployee_bankifsc(String employee_bankifsc) {
        this.employee_bankifsc = employee_bankifsc;
    }

    public String getEmployee_bankaccount() {
        return employee_bankaccount;
    }

    public void setEmployee_bankaccount(String employee_bankaccount) {
        this.employee_bankaccount = employee_bankaccount;
    }
}
