package com.example.foodwastepreventionapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public class FoodItem {
        public String restaurantname;
        public String foodname;
        public String day;
        public String time;
        public String rating;
        public String distance;
        public String price;
    }

    public StoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        FWPADbHelper dbHelper = new FWPADbHelper(view.getContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery
        ("SELECT s.name as sellername, f.name as foodname, f.price, f.datetime, f.quantity, f._id, f.imagepath FROM seller s INNER JOIN food f ON s._id = f.sellerid", null);

        LinearLayout ll = view.findViewById(R.id.llstore);

        Fragment currentFragment = this;

        while(cursor.moveToNext()) {
            String sellerName = cursor.getString(
                    cursor.getColumnIndexOrThrow("sellername"));
            String foodName = cursor.getString(
                    cursor.getColumnIndexOrThrow("foodname"));
            Double price = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("price"));
            String datetime = cursor.getString(
                    cursor.getColumnIndexOrThrow("datetime"));
            Integer quantity = cursor.getInt(
                    cursor.getColumnIndexOrThrow("quantity"));
            Integer foodRowID = cursor.getInt(
                    cursor.getColumnIndexOrThrow("_id"));
            String imagepath = cursor.getString(
                    cursor.getColumnIndexOrThrow("imagepath"));


            Log.d("storeFragment", "onCreateView: " + sellerName + " " + foodName + " " + price) ;

            CardView cv = FoodCardView.createCard(view.getContext(),sellerName,foodName,
                    "Today,",datetime,"4.6","0.8km","RM " + String.format("%.2f", price),
                    quantity,foodRowID, dbHelper, imagepath);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                                    Cursor cursor = db.rawQuery("SELECT COUNT(foodId) AS totalOrder FROM receipt WHERE foodId = "  +  foodRowID, null);

                                    int totalOrder = 0;
                                    while(cursor.moveToNext()) {
                                        totalOrder = cursor.getInt(
                                                cursor.getColumnIndexOrThrow("totalOrder"));
                                    }

                                    if (quantity != 0){
                                        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

                                        ContentValues values = new ContentValues();
                                        values.put(FWPAContract.Receipt.COLUMN_NAME_FOODID, Integer.valueOf(foodRowID));
                                        values.put(FWPAContract.Receipt.COLUMN_NAME_STATUS, "TO BE COLLECTED");
                                        values.put(FWPAContract.Receipt.COLUMN_NAME_TOKEN, "AK-" + foodRowID + "-" + Integer.toString(totalOrder + 1));

                                        long newRowId = dbWrite.insert(FWPAContract.Receipt.TABLE_NAME, null, values);
                                        Log.d("newItem", "new item added with id " + newRowId + "with value " + values.toString());

                                        ContentValues UpdateValues = new ContentValues();
                                        UpdateValues.put(FWPAContract.Food.COLUMN_NAME_QUANTITY, quantity - 1);
                                        dbWrite.update(FWPAContract.Food.TABLE_NAME, UpdateValues, "_id=?", new String[]{foodRowID.toString()});

                                        Log.d("newItem", "Updated the quantity for _id " + foodRowID + " with " + (quantity - 1));

                                        //Refresh the fragment
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        if (Build.VERSION.SDK_INT >= 26) {
                                            ft.setReorderingAllowed(false);
                                        }
                                        ft.detach(currentFragment).attach(currentFragment).commit();

                                    }

                                    else{
                                        Log.d("newItem", "Quantity not equal to 0");
                                    }

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//
                    if (quantity != 0){
                        builder.setMessage("Would you like to redeem this item?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }

                    else{
                        builder.setMessage("Sorry this item is sold out").setPositiveButton("Yes",null).show();
                    }

                }
            });


            ll.addView(cv);
        }

        cursor.close();

        String user = (String) getActivity().getIntent().getSerializableExtra("user");

        Log.d("storeFragment","user " + user);
        return view;
    }
}