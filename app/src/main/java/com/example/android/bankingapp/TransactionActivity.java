package com.example.android.bankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    private RecyclerView transactionRV;
    private ArrayList<TransactionModel> transactionModelArrayList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        transactionRV = findViewById(R.id.customerRV);
        db = new DatabaseHelper(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);


        transactionRV.setLayoutManager(linearLayoutManager);
        transactionModelArrayList = new ArrayList<>();
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

        transactionModelArrayList = db.getTransactionDetails(this);

        TransactionAdapter transactionAdapter = new TransactionAdapter(this,
                transactionModelArrayList);
        transactionRV.setAdapter(transactionAdapter);
    }

}