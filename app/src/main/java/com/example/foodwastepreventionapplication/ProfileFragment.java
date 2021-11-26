package com.example.foodwastepreventionapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodwastepreventionapplication.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_USERID = "userId";

    private Integer mUserId;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance(int _userId) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        EditText editTextUsername = view.findViewById(R.id.profileUsername);
        EditText editTextEmail = view.findViewById(R.id.profileEmail);
        EditText editTextLocation = view.findViewById(R.id.profileLocation);

        FWPADbHelper dbHelper = new FWPADbHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM seller s WHERE s._id =?", new String[]{mUserId.toString()});

        while (cursor.moveToNext()){
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name"));
            String email = cursor.getString(
                    cursor.getColumnIndexOrThrow("email"));
            String location = cursor.getString(
                    cursor.getColumnIndexOrThrow("location"));

            editTextUsername.setText(name);
            editTextEmail.setText(email);
            editTextLocation.setText(location);
        }

        Button logoutButton = (Button) view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}