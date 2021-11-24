package com.example.foodwastepreventionapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClaimFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClaimFragment extends Fragment {

    private static final String ARG_USERID = "userId";

    private Integer mUserId;

    public ClaimFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClaimFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClaimFragment newInstance(int mUserId) {
        ClaimFragment fragment = new ClaimFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USERID, mUserId);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_claim, container, false);
        Button claimButton = (Button) view.findViewById(R.id.claimButton);
        EditText claimToken = (EditText) view.findViewById(R.id.claimToken);
        TextView response = (TextView) view.findViewById(R.id.response);

        claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = claimToken.getText().toString();

                FWPADbHelper dbHelper = new FWPADbHelper(view.getContext());

                SQLiteDatabase db = dbHelper.getReadableDatabase();

                Cursor cursor = db.rawQuery
                        ("SELECT * FROM receipt r INNER JOIN food f ON r.foodId = f._id " +
                                " WHERE r.token =? AND r.status =? AND f.sellerid =?",
                                new String[]{token, "TO BE COLLECTED", mUserId.toString()});

                Log.d("claim","query result count " + cursor.getCount() + " for token " + token);

                if(cursor.getCount() != 0){
                    SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("status", "COLLECTED");
                    dbWrite.update("receipt",values,"token =?", new String[]{token});

                    response.setText(token + " successfully claimed");
                    Log.d("claim","update receipt with token " + token + " with status collected");

                }
                else{
                    response.setText("Failed to claim " + token);
                }

//                while(cursor.moveToNext()) {}
            }
        });
        return view;
    }
}