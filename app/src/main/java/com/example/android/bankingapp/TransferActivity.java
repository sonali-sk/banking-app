package com.example.android.bankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransferActivity extends AppCompatActivity {
    String name, fromAccountNumber, receiverName, dateDb, timeDb;
    double balance, amount;
    private ArrayList<String> customersWithId;
    Button sendButton;
    int toArrayListLength, id;
    TextInputEditText fromET, amountET;
    boolean areAllFieldsCorrect = false;
    AutoCompleteTextView toAutoCompleteTextView;
    DatabaseHelper db;
    private ArrayList<CustomerModel> toArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Intent intent = getIntent();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        id = Integer.parseInt(intent.getStringExtra("id"));
        name = intent.getStringExtra("name");
        balance = intent.getDoubleExtra("balance", 0.00);
        fromAccountNumber = intent.getStringExtra("accountNo");
        db = new DatabaseHelper(this);
        fromET = findViewById(R.id.from);
        amountET = findViewById(R.id.amount);
        sendButton = findViewById(R.id.sendButton);
        toAutoCompleteTextView = findViewById(R.id.to);
        fromET.setText(name + "  A/c Balance: Rs." + String.format("%.2f", balance) + "/-");
        fromET.setFocusable(false);
        toArrayList = new ArrayList<>();
        retrieveData();
        toArrayListLength = toArrayList.size();
        customersWithId = new ArrayList<>();
        for (int i = 0; i < toArrayListLength; i++) {
            if (i != id - 1) {
                customersWithId.add("Name: " + toArrayList.get(i).getCustomer_name() + "  ID: " +
                        toArrayList.get(i).getRoll_no());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, customersWithId);

        toAutoCompleteTextView.setAdapter(adapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areAllFieldsCorrect = checkAllFields();

                if (areAllFieldsCorrect) {
                    String text = toAutoCompleteTextView.getText().toString();
                    int idIndex = text.indexOf("  ID: ");
                    int toId = Integer.parseInt(text.substring(idIndex + 6)) - 1;
                    receiverName = text.substring(6, idIndex);
                    Date date = new Date();
                    dateDb = dateFormat.format(date);
                    timeDb = timeFormat.format(date);
                    amount = Double.parseDouble(amountET.getText().toString());
                    db.addNewTransaction(name, receiverName, amount, dateDb, timeDb);
                    double fromBalance = balance - amount;
                    double toBalance = toArrayList.get(toId).getBalance() + amount;
                    db.updateBalance(fromAccountNumber, fromBalance, toArrayList.get(toId)
                            .getAccountNo(), toBalance);
                    toAutoCompleteTextView.setFocusable(false);
                    amountET.setFocusable(false);
                    Snackbar.make(sendButton, "Payment successful", Snackbar.LENGTH_INDEFINITE)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(TransferActivity.this,
                                            MainActivity.class);
                                    startActivity(i);
                                }
                            }).show();
                }
            }
        });


    }

    private boolean checkAllFields() {
        String text = toAutoCompleteTextView.getText().toString();
        if (text.length() == 0) {
            toAutoCompleteTextView.setError("This field is required");
            return false;
        } else if (!(customersWithId.contains(text))) {
            toAutoCompleteTextView.setError("Kindly select from the given list");
            return false;
        } else {

            toAutoCompleteTextView.setError(null);
        }

        if (amountET.length() == 0) {
            amountET.setError("This field is required");
            return false;
        }
        if (Double.parseDouble(amountET.getText().toString()) > balance) {
            amountET.setError("You do not have sufficient balance");
            return false;
        }

        return true;
    }

    public void retrieveData() {

        db = new DatabaseHelper(this);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        toArrayList = db.getCustomerDetails(this);
    }


}