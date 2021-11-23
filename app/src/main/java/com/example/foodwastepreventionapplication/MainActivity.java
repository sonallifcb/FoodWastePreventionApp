package com.example.foodwastepreventionapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        bottomNavigationView.setSelectedItemId(R.id.menu2);
        userId = (Integer) getIntent().getSerializableExtra("userId");
        newItemFragment = new NewItemFragment().newInstance(userId);
        viewItemsFragment = new ViewItemsFragment().newInstance(userId);
        Integer menuSize = bottomNavigationView.getMenu().size();
        FWPADbHelper dbHelper = new FWPADbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + FWPAContract.Users.COLUMN_NAME_TYPE + " FROM " + FWPAContract.Users.TABLE_NAME + " WHERE " + FWPAContract.Users._ID + "=" + userId,null);
        cursor.moveToNext();

        userType = cursor.getString(cursor.getColumnIndexOrThrow(FWPAContract.Users.COLUMN_NAME_TYPE));

        for (int i = 0; i < menuSize; i++)
        {
//            bottomNavigationView.addView();
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            int id = menuItem.getItemId();
            if (id == R.id.menu1) {
                if (userType.equals("customer")) {
                    menuItem.setIcon(R.drawable.ic_order);
                }
                else if (userType.equals("seller"))
                {
                    menuItem.setIcon(R.drawable.listeditems_foreground);
                    menuItem.setTitle("View Items");
                }

            }
            else if (id == R.id.menu2) {
                if (userType.equals("customer")) {
                    menuItem.setIcon(R.drawable.ic_store);
                }
                else if (userType.equals("seller"))
                {
                    menuItem.setIcon(R.drawable.newitems_foreground);
                    menuItem.setTitle("Add Items");
                }

            }
//            if (id == R.id.menu3) {
//                    menuItem.setIcon(R.drawable.ic_profile);
//            }
//
//            if (id == R.id.menu4){
//                if (user.equals("customer")) {
//                    menuItem.setVisible(false);
//                }
//                else if (user.equals("restaurant")){
//                    menuItem.setVisible(true);
//                    menuItem.setIcon(R.drawable.claim_foreground);
//                }

             if (id == R.id.menu3) {
                if (userType.equals("customer")) {
                    menuItem.setIcon(R.drawable.ic_profile);
                    menuItem.setTitle("Profile");
                }
                else if (userType.equals("seller"))
                {
                    menuItem.setIcon(R.drawable.ic_profile);
                    menuItem.setTitle("Profile");
                }

            }

             if (id == R.id.menu4) {

                 if (userType.equals("customer")) {
                     menuItem.setVisible(false);
                 }
                 else if (userType.equals("seller"))
                 {
                     menuItem.setVisible(true);
                     menuItem.setIcon(R.drawable.claim_foreground);
                     menuItem.setTitle("Claim");
                 }

             }

//            if (id == R.id.menu4) {
//                menuItem.setIcon(R.drawable.ic_profile);
//            }
//
//            if (id == R.id.menu3){
//                if (user.equals("customer")) {
//                    menuItem.setVisible(false);
//                }
//                else if (user.equals("restaurant")){
//                    menuItem.setVisible(true);
//                    menuItem.setIcon(R.drawable.claim_foreground);
//                }
//            }

        }

    }

    public FavouriteFragment favouriteFragment = new FavouriteFragment();
    public OrderFragment orderFragment = new OrderFragment();
    public StoreFragment storeFragment = new StoreFragment();
    public ProfileFragment profileFragment = new ProfileFragment();
    public NewItemFragment newItemFragment = new NewItemFragment();
    public ViewItemsFragment viewItemsFragment = new ViewItemsFragment();
    public ClaimFragment claimFragment = new ClaimFragment();
    public Integer userId = 0;
    public String userType = "";

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu1:
                if (userType.equals("customer") ){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, orderFragment).commit();
                }
                else if (userType.equals("seller")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, viewItemsFragment).commit();
                }
                return true;

            case R.id.menu2:
                if (userType.equals("customer") ){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, storeFragment).commit();

                }
                else if (userType.equals("seller")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, newItemFragment).commit();

                }
                return true;

            case R.id.menu3:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, profileFragment).commit();
                return true;

            case R.id.menu4:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutBottomNav, claimFragment).commit();
                return true;

        }
        return false;
    }

}

