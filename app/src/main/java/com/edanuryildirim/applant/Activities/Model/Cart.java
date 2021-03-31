package com.edanuryildirim.applant.Activities.Model;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edanuryildirim.applant.Activities.Model.Common.Common;
import com.edanuryildirim.applant.Activities.Model.Database.Database;
import com.edanuryildirim.applant.Activities.Model.Model.Order;
import com.edanuryildirim.applant.Activities.Model.Model.Request;
import com.edanuryildirim.applant.Activities.Model.ViewHolder.CartAdapter;
import com.edanuryildirim.applant.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

        List<Order> cart =new ArrayList<>();

        CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests=database.getReference("Request");

      //Init
      recyclerView=(RecyclerView)findViewById(R.id.listCart);
      recyclerView.setHasFixedSize(true);
      layoutManager = new LinearLayoutManager(this);
      recyclerView.setLayoutManager(layoutManager);

      txtTotalPrice=(TextView)findViewById(R.id.total);
      btnPlace =(Button)findViewById(R.id.btnPlaceOrder);

      btnPlace.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              showAlertDialog();
          }
      });

      loadListPlant();


    }

    private void showAlertDialog() {
        AlertDialog.Builder  alertDialog=new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step! ");
        alertDialog.setMessage("Enter your address:");

        final EditText edtAddress =new EditText(Cart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress); //Add edit Text to alert dialog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Request request=new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
        //Submit to Firebase
                //We will using System.CurrentMilli tok key
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //Delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank you,Order Place ",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });


        alertDialog.show();
    }

    private void loadListPlant() {
        cart=new Database(this).getCarts();
        adapter=new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);
        //Calculate total price
        int total=0 ;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQunatity()));
        Locale locale =new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }
}
