package com.example.foodwastepreventionapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

    private static final String ARG_USERID = "userId";

    private Integer mUserId;

    public ViewItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViewItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewItemsFragment newInstance(int mUserId) {
        ViewItemsFragment fragment = new ViewItemsFragment();
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
                ("SELECT f.name as foodname, f.price, f.datetime, f.quantity, f._id, f.imagepath FROM  food f WHERE f.sellerid =?",new String[]{mUserId.toString()});

        LinearLayout ll = view.findViewById(R.id.llviewItems);

        Fragment currentFragment = this;

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
            String imagepath = cursor.getString(
                    cursor.getColumnIndexOrThrow("imagepath"));


            Log.d("storeFragment", "onCreateView: " + foodName + " " + price) ;

            class onDeleteClick implements View.OnClickListener{
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    SQLiteDatabase db = dbHelper.getReadableDatabase();

                                    db.delete(FWPAContract.Food.TABLE_NAME,"_id=?",new String[]{foodRowID.toString()});
                                    Log.d("deleteViewItem", "Deleted foodRowId " + foodRowID);

                                    //Refresh
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    if (Build.VERSION.SDK_INT >= 26) {
                                        ft.setReorderingAllowed(false);
                                    }
                                    ft.detach(currentFragment).attach(currentFragment).commit();

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//
                    builder.setMessage("Would you like to delete this item?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }

            CardView cv = FoodCardView.createViewItemsCard(view.getContext(),foodName,"Today,",
                    datetime,"RM " + String.format("%.2f", price),quantity,foodRowID,dbHelper,
                    new onDeleteClick(),imagepath);


            ll.addView(cv);
        }

        cursor.close();

        String user = (String) getActivity().getIntent().getSerializableExtra("user");

        Log.d("viewItemsFragment","user " + user);
        return view;
    }


}
