package com.example.foodwastepreventionapplication;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewItemFragment extends Fragment {

    private static final String ARG_USERID = "userId";

    private Integer mUserId;
    private EditText title;
    private EditText description;
    private EditText time;
    private EditText price;
    private EditText quantity;
    private Spinner category;
    private ImageView addImageIcon;
    private ImageView foodImage;
    private TextView addImageText;
    private String filePath;


    public NewItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewItemFragment.
     */
    public static NewItemFragment newInstance(int _userId) {
        NewItemFragment fragment = new NewItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USERID, _userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_USERID);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = this.getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
            try {
                Bitmap image = BitmapFactory.decodeFile(filePath);
                foodImage.setImageBitmap(image);
                addImageIcon.setVisibility(View.INVISIBLE);
                addImageText.setVisibility(View.INVISIBLE);
            }
            catch (OutOfMemoryError e){
                Log.d ("abcd" ,"abcd");
            }
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
        addImageIcon = (ImageView)view.findViewById(R.id.addImageIcon);
        foodImage = (ImageView)view.findViewById(R.id.foodImage);
        addImageText = (TextView) view.findViewById(R.id.addImageText);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog  = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Integer hour = timePicker.getHour();
                        Integer minute = timePicker.getMinute();
                        time.setText(hour + ":" + minute);
                    }
                },0,0,true);
                timePickerDialog.show();
            }
        });

        addImageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });
        // Inflate the layout for this fragment

        Button button = (Button) view.findViewById(R.id.createitembutton);
        button.setOnClickListener(new View.OnClickListener(){

            public boolean validate(EditText et, String errorMessage){
                if (et.getText().toString().length() == 0){
                    et.requestFocus();
                    et.setError(errorMessage);
                    return false;
                }
                else{
                    return true;
                }
            }

            public void onClick (View v)
            {
                boolean valid = true;

                valid = validate(quantity,"Quantity required") && valid;
                valid = validate(price,"Price required") && valid;
                valid = validate(time,"Time required") && valid;
                valid = validate(description,"Description required") && valid;
                valid = validate(title,"Title required") && valid;

                if(valid){
                    Log.d("TAG", "onClick: "+title.getText().toString());
                    Log.d("TAG", "onClick: "+description.getText().toString());
                    Log.d("TAG", "onClick: "+time.getText().toString());
                    Log.d("TAG", "onClick: "+price.getText().toString());
                    Log.d("TAG", "onClick: "+quantity.getText().toString());
                    Log.d("TAG", "onClick: "+category.getSelectedItem().toString());

                    ContentValues values = new ContentValues();
                    values.put(FWPAContract.Food.COLUMN_NAME_NAME, title.getText().toString());
                    values.put(FWPAContract.Food.COLUMN_NAME_DESCRIPTION, description.getText().toString());
                    values.put(FWPAContract.Food.COLUMN_NAME_QUANTITY,quantity.getText().toString());
                    values.put(FWPAContract.Food.COLUMN_NAME_CATEGORY, category.getSelectedItem().toString());
                    values.put(FWPAContract.Food.COLUMN_NAME_PRICE,price.getText().toString());
                    values.put(FWPAContract.Food.COLUMN_NAME_SELLERID, mUserId);
                    values.put(FWPAContract.Food.COLUMN_NAME_DATETIME, time.getText().toString());
                    values.put(FWPAContract.Food.COLUMN_NAME_IMAGEPATH, filePath);


                    FWPADbHelper dbHelper = new FWPADbHelper(v.getContext());
                    SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

                    long newRowId = dbWrite.insert(FWPAContract.Food.TABLE_NAME, null, values);
                    Log.d("db", "onCreateView: insertNewRowID " + newRowId);

                    title.setText("");
                    description.setText("");
                    time.setText("");
                    price.setText("");
                    quantity.setText("");

                    foodImage.setImageBitmap(null);
                    addImageIcon.setVisibility(View.VISIBLE);
                    addImageText.setVisibility(View.VISIBLE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Listing created").setPositiveButton("OK",null).show();
                }

            }
        });
        return view;
    }

}