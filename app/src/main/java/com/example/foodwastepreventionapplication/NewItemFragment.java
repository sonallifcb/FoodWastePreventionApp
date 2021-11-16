package com.example.foodwastepreventionapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText title;
    private EditText description;
    private EditText time;
    private EditText price;
    private EditText quantity;
    private Spinner category;


    public NewItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewItemFragment newInstance(String param1, String param2) {
        NewItemFragment fragment = new NewItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        String[] items = new String[]{"Meals", "Dessert", "Beverages"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        title = (EditText)view.findViewById(R.id.newitemtitle);
        description = (EditText)view.findViewById(R.id.newitemdesc);
        time = (EditText)view.findViewById(R.id.newitemtime);
        price = (EditText)view.findViewById(R.id.newitemprice);
        quantity = (EditText)view.findViewById(R.id.newitemquantity);
        category = (Spinner)view.findViewById(R.id.spinner);
        // Inflate the layout for this fragment

        Button button = (Button) view.findViewById(R.id.createitembutton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v)
            {
                Log.d("TAG", "onClick: "+title.getText().toString());
                Log.d("TAG", "onClick: "+description.getText().toString());
                Log.d("TAG", "onClick: "+time.getText().toString());
                Log.d("TAG", "onClick: "+price.getText().toString());
                Log.d("TAG", "onClick: "+quantity.getText().toString());
                Log.d("TAG", "onClick: "+category.getSelectedItem().toString());

//                String title = ((EditText)v.findViewById(R.id.newitemtitle)).getText().toString();
//                String description = ((EditText)v.findViewById(R.id.newitemdesc)).getText().toString();
//                String time = ((EditText)v.findViewById(R.id.newitemtime)).getText().toString();
//                String price = ((EditText)v.findViewById(R.id.newitemprice)).getText().toString();
//                String quantity = ((EditText)v.findViewById(R.id.newitemquantity)).getText().toString();
//                Log.d("onCreate", title+" "+description+" "+time+" "+price+" "+quantity+" ");

                ContentValues values = new ContentValues();
                values.put(FWPAContract.Food.COLUMN_NAME_NAME, title.getText().toString());
                values.put(FWPAContract.Food.COLUMN_NAME_DESCRIPTION, description.getText().toString());
                values.put(FWPAContract.Food.COLUMN_NAME_QUANTITY,quantity.getText().toString());
                values.put(FWPAContract.Food.COLUMN_NAME_CATEGORY, category.getSelectedItem().toString());
                values.put(FWPAContract.Food.COLUMN_NAME_PRICE,price.getText().toString());
                values.put(FWPAContract.Food.COLUMN_NAME_SELLERID, "1");
                values.put(FWPAContract.Food.COLUMN_NAME_DATETIME, time.getText().toString());

                FWPADbHelper dbHelper = new FWPADbHelper(v.getContext());
                SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

                long newRowId = dbWrite.insert(FWPAContract.Food.TABLE_NAME, null, values);
                Log.d("db", "onCreateView: insertNewRowID " + newRowId);

            }
        });
        return view;
    }

}