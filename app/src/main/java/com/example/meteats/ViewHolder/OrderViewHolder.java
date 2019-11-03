package com.example.meteats.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.meteats.Interface.ItemClickListener;
import com.example.meteats.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView txtOrderId,txtOrderStatus,txtOrderPhone;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemview){
        super(itemview);

        txtOrderId = (TextView)itemview.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemview.findViewById(R.id.order_status);
        txtOrderPhone = (TextView)itemview.findViewById(R.id.order_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

}

