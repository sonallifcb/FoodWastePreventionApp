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
 * Use the {@link ViewItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewItemsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewItemsFragment newInstance(String param1, String param2) {
        ViewItemsFragment fragment = new ViewItemsFragment();
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
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_view_items, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_items, container, false);
        FWPADbHelper dbHelper = new FWPADbHelper(view.getContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery
                ("SELECT f.name as foodname, f.price, f.datetime, f.quantity, f._id FROM  food f", null);

        LinearLayout ll = view.findViewById(R.id.llviewItems);

        while(cursor.moveToNext()) {
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


            Log.d("storeFragment", "onCreateView: " + foodName + " " + price) ;


//            ll.addView(FoodCardView.createCard(view.getContext(),sellerName,foodName,
//                    "Today,",datetime,"4.6","0.8km","RM " + String.format("%.2f", price),quantity,foodRowID, dbHelper));
            ll.addView(FoodCardView.createViewItemsCard(view.getContext(),foodName,"Today,",
                    datetime,"RM " + String.format("%.2f", price),quantity,foodRowID,dbHelper));
        }

        cursor.close();

        String user = (String) getActivity().getIntent().getSerializableExtra("user");

        Log.d("viewItemsFragment","user " + user);
        return view;
    }


}
