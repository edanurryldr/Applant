package com.edanuryildirim.applant.Activities.Model.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edanuryildirim.applant.Activities.Model.Interface.ItemClickListener;
import com.edanuryildirim.applant.R;

public class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView plant_name;
    public ImageView plant_image;

    private ItemClickListener itemClickListener;

    public PlantViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
    }

    public PlantViewHolder(@NonNull View itemView) {
        super(itemView);

        plant_name=(TextView)itemView.findViewById(R.id.plant_name);
        plant_image=(ImageView) itemView.findViewById(R.id.plant_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener ıtemClickListener) {

    }
}
