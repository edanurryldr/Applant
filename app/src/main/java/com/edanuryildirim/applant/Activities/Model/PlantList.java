package com.edanuryildirim.applant.Activities.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edanuryildirim.applant.Activities.Model.Interface.ItemClickListener;
import com.edanuryildirim.applant.Activities.Model.Model.Plant;
import com.edanuryildirim.applant.Activities.Model.ViewHolder.PlantViewHolder;
import com.edanuryildirim.applant.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PlantList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference plantList;

    String categoryId= "";

    FirebaseRecyclerAdapter<Plant, PlantViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        //Firebase

        database=FirebaseDatabase.getInstance();
        plantList=database.getReference("Plants");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_plant);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    //Get Intent here
        if(getIntent()!= null){
            categoryId=getIntent().getStringExtra("CategoryId");
            if(!categoryId.isEmpty() && categoryId != null){
                loadListPlant(categoryId);
            }
        }

    }

         private void loadListPlant(String categoryId) {
         adapter=new FirebaseRecyclerAdapter<Plant, PlantViewHolder>(Plant.class,
            R.layout.plant_item,
            PlantViewHolder.class,
            plantList.orderByChild("MenuId").equalTo(categoryId)
            ) {
        @Override
        protected void populateViewHolder(PlantViewHolder viewHolder, Plant model, int position) {
        viewHolder.plant_name.setText(model.getName());
            Picasso.with(getBaseContext()).load(model.getImage())
                    .into(viewHolder.plant_image);

            final Plant local = model;
            viewHolder.setItemClickListener(new ItemClickListener(){
                @Override
                public void onClick(View view, int position, boolean isLongClick){
                //Start New Activity
                    Intent plantDetail =new Intent(PlantList.this, PlantDetail.class);
                    plantDetail.putExtra("PlantId",adapter.getRef(position).getKey());
                    startActivity(plantDetail);
                }
            });
        }
    };

      recyclerView.setAdapter(adapter);


    }
}
