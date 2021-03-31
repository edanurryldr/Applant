package com.edanuryildirim.applant.Activities.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edanuryildirim.applant.Activities.Model.Common.Common;
import com.edanuryildirim.applant.Activities.Model.Interface.ItemClickListener;
import com.edanuryildirim.applant.Activities.Model.Model.Category;
import com.edanuryildirim.applant.Activities.Model.ViewHolder.MenuViewHolder;
import com.edanuryildirim.applant.Camera;
import com.edanuryildirim.applant.Profile;
import com.edanuryildirim.applant.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseDatabase database;
    DatabaseReference category;


    TextView txtFullName;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav_view;
    ActionBarDrawerToggle actionBarDrawerToggle;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

    database =FirebaseDatabase.getInstance();
    category=database.getReference("Category");

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent cartIntent =new Intent(Home.this,Cart.class);
             startActivity(cartIntent);
            }
        });




        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
        txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        recycler_menu=(RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

         loadMenu();

    }


    private void loadMenu() {

            adapter=new FirebaseRecyclerAdapter<Category,MenuViewHolder>
            (Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder,Category model,int position) {
            viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickItem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent plantList=new Intent(Home.this, PlantDetail.class);
                        plantList.putExtra("PlantId",adapter.getRef(position).getKey());
                        plantList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(plantList);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer =(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            }
        else  {
            super.onBackPressed();
            }

        }

    @Override
   public  boolean onOptionsItemSelected(MenuItem item){


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        System.out.println("id: " + id);
        if (id == R.id.nav_menu) {

        }  else if (id == R.id.nav_camera) {
            Intent cameraIntent =new Intent(Home.this, Camera.class);
            startActivity(cameraIntent);

        } else if (id == R.id.nav_cart) {
            Intent cartIntent =new Intent(Home.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {

        }else if (id == R.id.nav_profile) {
            profile();
            Intent profileintent =new Intent(Home.this,Profile.class);
            startActivity(profileintent);

        }else if (id==R.id.nav_done){


        }else if (id == R.id.nav_log_out) {
           logout();
        }

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void profile() {
        Intent orderIntent = new Intent(getApplicationContext(), Profile.class);

        startActivity(orderIntent);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Common.currentUser = null;
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}