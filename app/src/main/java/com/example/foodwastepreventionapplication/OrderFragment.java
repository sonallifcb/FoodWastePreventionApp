package com.example.foodwastepreventionapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        FWPADbHelper dbHelper = new FWPADbHelper(view.getContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery
                ("SELECT r._id AS _id, f.name AS foodName, s.name AS restaurantName, f.datetime AS time, r.status AS status, f.price AS price, r.token AS token FROM receipt r " +
                        "INNER JOIN food f ON f._id = r.foodId " +
                        "INNER JOIN seller s ON s._id = f.sellerid", null);
        LinearLayout ll = view.findViewById(R.id.llorder);
//
        while(cursor.moveToNext()) {
            String _id = cursor.getString(
                    cursor.getColumnIndexOrThrow("_id"));
//            String foodId = cursor.getString(
//                    cursor.getColumnIndexOrThrow("foodId"));
            String status = cursor.getString(
                    cursor.getColumnIndexOrThrow("status"));
            String token = cursor.getString(
                    cursor.getColumnIndexOrThrow("token"));
            String foodName = cursor.getString(
                    cursor.getColumnIndexOrThrow("foodName"));
            String restaurantName = cursor.getString(
                    cursor.getColumnIndexOrThrow("restaurantName"));
            String time = cursor.getString(
                    cursor.getColumnIndexOrThrow("time"));
            Double price = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("price"));


            Log.d("orderFragment", "reading receipt: _id=" + _id + " status=" + status + " token=" + token) ;


            ll.addView(FoodCardView.createOrderCard(view.getContext(),foodName,restaurantName,"Today,",
                    time,"RM " + String.format("%.2f", price),token));
        }

        cursor.close();

//        ll.addView(FoodCardView.createOrderCard(view.getContext(),"Fried Rice","Ali Kopitiam","Today,",
//                "15:00","RM " + String.format("%.2f", 2.50),"A K 0 0 1"));

        return view;
    }

}

