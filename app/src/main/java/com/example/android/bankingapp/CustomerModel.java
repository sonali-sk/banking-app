package com.example.android.bankingapp;

public class CustomerModel {
    private String roll_no;
    private String customer_name;
    private double balance;
    private String email;
    private String ifsc;
    private String accountNo;

    public CustomerModel(String roll_no, String customer_name, double balance, String email,
                         String ifsc, String accountNo) {
        this.roll_no = roll_no;
        this.customer_name = customer_name;
        this.balance = balance;
        this.email = email;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
