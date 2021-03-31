package com.edanuryildirim.applant.Activities.Model;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.edanuryildirim.applant.Activities.Model.Database.Database;
import com.edanuryildirim.applant.Activities.Model.Model.Order;
import com.edanuryildirim.applant.Activities.Model.Model.Plant;
import com.edanuryildirim.applant.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PlantDetail extends AppCompatActivity {

    TextView plant_name,plant_price,plant_description;
    ImageView plant_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String plantId="";

    FirebaseDatabase database;
    DatabaseReference plants;
    Plant currentPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);
        //Firebase
        database=FirebaseDatabase.getInstance();
        plants=database.getReference("Plants");

        //Init view
        numberButton=(ElegantNumberButton)findViewById(R.id.number_button);
        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plantId=getIntent().getStringExtra("PlantId");
             new Database(getBaseContext()).addToCart(new Order(
                 plantId,
                 currentPlant.getName(),
                 numberButton.getNumber(),
                 currentPlant.getPrice(),
                 currentPlant.getDiscount()
             ));
                Toast.makeText(PlantDetail.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });

        plant_description=(TextView)findViewById(R.id.plant_description);
        plant_name=(TextView)findViewById(R.id.plant_name);
        plant_price=(TextView)findViewById(R.id.plant_price);
        plant_image=(ImageView)findViewById(R.id.img_plant);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        //Get Plant Id from Intent
    if(getIntent()!=null){
        plantId=getIntent().getStringExtra("PlantId");
        if (plantId != null) {
            if (!plantId.isEmpty()) {
                getDetailPlant(plantId);
            }
        }
    }

    }

    private void getDetailPlant(String plantId) {
        plants.child(plantId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentPlant =dataSnapshot.getValue(Plant.class);


                //Set Image
                Picasso.with(getBaseContext()).load(currentPlant.getImage())
                .into(plant_image);

            //collapsingToolbarLayout.setTitle(currentPlant.getName());

            plant_price.setText(currentPlant.getPrice());

            plant_name.setText(currentPlant.getName());

            plant_description.setText(currentPlant.getDescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
