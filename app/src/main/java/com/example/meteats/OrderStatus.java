package com.example.meteats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.meteats.Common.Common;
import com.example.meteats.ViewHolder.OrderViewHolder;
import com.example.meteats.ui.data.model.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {
    String m2;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;


    FirebaseDatabase database;
    DatabaseReference requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        m2 =  bundle.getString("m1");
        setContentView(R.layout.activity_order_status);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

         recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


            loadOrders(Common.currentuser);



    }

    private void loadOrders(String name) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
            Request.class,
            R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("name").equalTo(name)

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder,Request model,int position){
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(m2);

            }

        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status){
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "Food is Cooking";
        else
            return "Prepared";

    }
}
