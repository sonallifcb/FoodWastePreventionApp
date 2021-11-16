package com.example.foodwastepreventionapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu1);
        user = (String) getIntent().getSerializableExtra("user");
        Integer menuSize = bottomNavigationView.getMenu().size();

        for (int i = 0; i < menuSize; i++)
        {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            int id = menuItem.getItemId();
            if (id == R.id.menu1) {
                if (user.equals("customer")) {
                    menuItem.setIcon(R.drawable.ic_order);
                }
                else if (user.equals("restaurant"))
                {
                    menuItem.setIcon(R.drawable.listeditems_foreground);
                    menuItem.setTitle("View Items");
                }

            }
            else if (id == R.id.menu2) {
                if (user.equals("customer")) {
                    menuItem.setIcon(R.drawable.ic_store);
                }
                else if (user.equals("restaurant"))
                {
                    menuItem.setIcon(R.drawable.newitems_foreground);
                    menuItem.setTitle("Add Items");
                }

            }
            if (id == R.id.menu3) {
                    menuItem.setIcon(R.drawable.ic_profile);
            }

        }

    }

    public FavouriteFragment favouriteFragment = new FavouriteFragment();
    public OrderFragment orderFragment = new OrderFragment();
    public StoreFragment storeFragment = new StoreFragment();
    public ProfileFragment profileFragment = new ProfileFragment();
    public NewItemFragment newItemFragment = new NewItemFragment();
    public ViewItemsFragment viewItemsFragment = new ViewItemsFragment();
    public String user = "";

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
//            case R.id.favourite:
//                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, favouriteFragment).commit();
//                return true;

            case R.id.menu1:
                if (user.equals("customer") ){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, orderFragment).commit();
                }
                else if (user.equals("restaurant")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, viewItemsFragment).commit();
                }
                return true;

            case R.id.menu2:
                if (user.equals("customer") ){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, storeFragment).commit();

                }
                else if (user.equals("restaurant")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, newItemFragment).commit();

                }
                return true;

            case R.id.menu3:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, profileFragment).commit();
                return true;
        }
        return false;
    }

}

