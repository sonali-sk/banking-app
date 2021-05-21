package com.example.android.bankingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.Viewholder> {
    private Context context;
    private ArrayList<CustomerModel> customerModelArrayList;

    public CustomerAdapter(Context context, ArrayList<CustomerModel> customerModelArrayList) {
        this.context = context;
        this.customerModelArrayList = customerModelArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent,
                false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.Viewholder holder, int position) {
        CustomerModel model = customerModelArrayList.get(position);
        holder.rollNo.setText(model.getRoll_no());
        holder.customerName.setText(model.getCustomer_name());
        holder.balance.setText("Rs." + String.format("%.2f", model.getBalance()) + "/-");
        holder.email.setText(model.getEmail());
        holder.ifsc.setText(model.getIfsc());
        holder.accountNo.setText(model.getAccountNo());
        holder.viewDetailsButton.setOnClickListener(view -> {
            if (holder.hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(holder.card,
                        new AutoTransition());
                holder.hiddenView.setVisibility(View.GONE);
                holder.viewDetailsButton.setText("VIEW DETAILS");
            } else {
                TransitionManager.beginDelayedTransition(holder.card,
                        new AutoTransition());
                holder.hiddenView.setVisibility(View.VISIBLE);
                holder.viewDetailsButton.setText("HIDE DETAILS");
            }
        });
        holder.transferMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TransferActivity.class);
                intent.putExtra("id", model.getRoll_no());
                intent.putExtra("name", model.getCustomer_name());
                intent.putExtra("balance", model.getBalance());
                intent.putExtra("accountNo", model.getAccountNo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView rollNo, customerName, balance, email, ifsc, accountNo;
        private Button viewDetailsButton, transferMoneyButton;
        private CardView card;
        private LinearLayout hiddenView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rollNo = itemView.findViewById(R.id.roll_noTV);
            customerName = itemView.findViewById(R.id.customer_nameTV);
            balance = itemView.findViewById(R.id.balanceTV);
            email = itemView.findViewById(R.id.emailTV);
            ifsc = itemView.findViewById(R.id.ifscTV);
            accountNo = itemView.findViewById(R.id.accountTV);
            viewDetailsButton = itemView.findViewById(R.id.view_details);
            transferMoneyButton = itemView.findViewById(R.id.transfer);
            card = itemView.findViewById(R.id.card);
            hiddenView = itemView.findViewById(R.id.hidden_view);
        }
    }
}
