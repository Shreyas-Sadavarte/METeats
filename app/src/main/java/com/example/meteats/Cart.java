package com.example.meteats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meteats.Common.Common;
import com.example.meteats.Database.Database;
import com.example.meteats.ViewHolder.CartAdapter;
import com.example.meteats.data.model.Order;
import com.example.meteats.ui.data.model.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    Bundle p1;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order>cart =new ArrayList<>();
    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database =FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");


        //Init the request

        recyclerView =(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace =(Button)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showAlertDialog();


            }
        });

        loadListFood();

    }

    private void showAlertDialog(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more Step");
        alertDialog.setMessage("Enter Your Phone");
        final EditText editphone = new EditText(Cart.this);
        String phone1 = editphone.toString();

        p1 =new Bundle();
        p1.putString("m1",phone1);
        LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          LinearLayout.LayoutParams.MATCH_PARENT
        );
        editphone.setLayoutParams(lp);
        alertDialog.setView(editphone);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        editphone.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        Common.currentuser,
                        cart
                );

                //to firrbase
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                //delete cart

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thank You Order Placed", Toast.LENGTH_SHORT).show();
                finish();


            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        alertDialog.show();

    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);


        //To Calculate Total Price

        int total=0;
        for(Order order: cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));


    }
}
