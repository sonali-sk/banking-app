package com.example.android.bankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {
    DatabaseHelper db;
    private RecyclerView customerRV;
    private ArrayList<CustomerModel> customerModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        customerRV = findViewById(R.id.customerRV);
        db = new DatabaseHelper(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        customerRV.setLayoutManager(linearLayoutManager);
        customerModelArrayList = new ArrayList<>();
        retrieveData();
    }

    public void retrieveData() {
        db = new DatabaseHelper(this);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerModelArrayList = db.getCustomerDetails(this);
        CustomerAdapter customerAdapter = new CustomerAdapter(this, customerModelArrayList);
        customerRV.setAdapter(customerAdapter);
    }

}