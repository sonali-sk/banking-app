package com.example.android.bankingapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "customers.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private SQLiteOpenHelper sqLiteOpenHelper;
    public static final String CUSTOMER = "customer";
    public static final String TRANSACTION = "transact";
    private static final String ID_COL = "_id";
    private static final String SENDER_COL = "sender";
    private static final String RECEIVER_COL = "receiver";
    private static final String AMOUNT_COL = "amount";
    private static final String DATE_COL = "date";
    private static final String TIME_COL = "time";
    private static final String BALANCE_COL = "balance";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(DB_NAME).toString();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.e("message", "" + e);
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase()
            throws IOException {

        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<CustomerModel> getCustomerDetails(
            Activity activity) {
        sqLiteOpenHelper = new DatabaseHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ArrayList<CustomerModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + CUSTOMER;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new CustomerModel(cursor.getString(0), cursor.getString(1),
                        cursor.getDouble(5), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void addNewTransaction(String sender, String receiver, double amount, String date,
                                  String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SENDER_COL, sender);
        values.put(RECEIVER_COL, receiver);
        values.put(AMOUNT_COL, amount);
        values.put(DATE_COL, date);
        values.put(TIME_COL, time);
        db.insert(TRANSACTION, null, values);
        db.close();
    }

    public ArrayList<TransactionModel> getTransactionDetails(Activity activity) {
        sqLiteOpenHelper = new DatabaseHelper(activity);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ArrayList<TransactionModel> transactionModelArrayList = new ArrayList<>();
        String query = "SELECT * FROM " + TRANSACTION + " ORDER " + "BY " + ID_COL + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                transactionModelArrayList.add(new TransactionModel(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getDouble(3),
                        cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactionModelArrayList;
    }

    public void updateBalance(String fromAccountNumber, double fromBalance, String toAccountNumber,
                              double toBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BALANCE_COL, fromBalance);
        db.update(CUSTOMER, values, "account_no=?", new String[]{fromAccountNumber});
        values.put(BALANCE_COL, toBalance);
        db.update(CUSTOMER, values, "account_no=?", new String[]{toAccountNumber});
        db.close();
    }
}
