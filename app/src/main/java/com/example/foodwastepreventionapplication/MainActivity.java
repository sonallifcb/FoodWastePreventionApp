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
        bottomNavigationView.setSelectedItemId(R.id.favourite);

    }

    FavouriteFragment favouriteFragment = new FavouriteFragment();
    OrderFragment orderFragment = new OrderFragment();
    StoreFragment storeFragment = new StoreFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.favourite:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, favouriteFragment).commit();
                return true;

            case R.id.order:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, orderFragment).commit();
                return true;

            case R.id.store:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, storeFragment).commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, profileFragment).commit();
                return true;
        }
        return false;
    }

}

