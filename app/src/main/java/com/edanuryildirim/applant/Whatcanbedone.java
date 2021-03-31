package com.edanuryildirim.applant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.edanuryildirim.applant.Activities.Model.Model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Whatcanbedone extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView done_item_name;
    ImageView done_item_count;

    List<Order> done =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatcanbedone);


    }
}
