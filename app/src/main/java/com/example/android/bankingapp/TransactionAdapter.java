package com.example.android.bankingapp;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Viewholder> {
    private Context context;
    private ArrayList<TransactionModel> transactionModelArrayList;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> transactionModelArrayList) {
        this.context = context;
        this.transactionModelArrayList = transactionModelArrayList;
    }

    @NonNull
    @Override
    public TransactionAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item,
                parent, false);
        return new TransactionAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.Viewholder holder, int position) {

        TransactionModel model = transactionModelArrayList.get(position);
        holder.senderTV.setText(model.getSender());
        holder.receiverTV.setText(model.getReceiver());
        holder.amountTV.setText("Rs." + String.format("%.2f", model.getAmount()) + "/-");
        holder.dateTimeTV.setText(model.getDate() + " " + model.getTime());
    }

    @Override
    public int getItemCount() {

        return transactionModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView senderTV, receiverTV, amountTV, dateTimeTV;
        private CardView card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            senderTV = itemView.findViewById(R.id.senderName);
            receiverTV = itemView.findViewById(R.id.receiverName);
            amountTV = itemView.findViewById(R.id.transactionAmount);
            dateTimeTV = itemView.findViewById(R.id.date_and_time);
            card = itemView.findViewById(R.id.card);

        }
    }
}
