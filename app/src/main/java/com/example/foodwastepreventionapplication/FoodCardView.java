package com.example.foodwastepreventionapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class FoodCardView {

    public class FoodCardProperties{

    }

    public static CardView createCard(Context c,  String restaurantName, String foodName,
                                      String day, String time, String rating, String distance,
                                      String price, Integer quantity, Integer foodRowID, FWPADbHelper dbHelper){

        androidx.cardview.widget.CardView cv = new CardView(c);
        cv.setRadius(30);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (1100, 600);
        layout.setMargins(0,0,20,0);
        cv.setLayoutParams(layout);
        cv.setMaxCardElevation(60);
        cv.setElevation(50);
        cv.setPreventCornerOverlap(true);
        cv.setUseCompatPadding(true);
        cv.setClickable(true);

        int restaurantID = View.generateViewId();
        int foodID = View.generateViewId();
        int clockImage = View.generateViewId();
        int dayID = View.generateViewId();
        int starImage = View.generateViewId();
        int ratingID = View.generateViewId();
        int locationImage = View.generateViewId();
        int distanceID =View.generateViewId();
        int priceID = View.generateViewId();
        int foodImage = View.generateViewId();
        int unitID = View.generateViewId();
        int foodRowIDID = View.generateViewId();
        int quantityID = View.generateViewId();
        RelativeLayout rl = new RelativeLayout(c);

        rl.addView(createTextView(c,restaurantID, 15,15,0,0,restaurantName,
                32, 106, 93, Typeface.BOLD,0,0,0,0,0,false));
        rl.addView(createTextView(c,foodID,15,9,0,0,foodName,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,restaurantID,0,0,0,false));
        rl.addView(createImageView(c,clockImage,60,60,0,9,0,0,60,
                R.drawable.clock_foreground,RelativeLayout.BELOW,foodID,0,0,0,false));
        rl.addView(createTextView(c,dayID,6,18,0,0,day,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,clockImage,12,false));
        rl.addView(createTextView(c,0,9,18,0,0,time,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,dayID,12,false));
        rl.addView(createImageView(c,starImage,60,60,0,0,0,0,60,
                R.drawable.star_foreground,RelativeLayout.BELOW,clockImage,0,0,0,false));
        rl.addView(createTextView(c,ratingID,6,9,0,0, rating,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,clockImage,RelativeLayout.END_OF,starImage,12,false));
        rl.addView(createImageView(c,locationImage,60,60,9,0,0,0,60,
                R.drawable.location_foreground,RelativeLayout.BELOW,clockImage,RelativeLayout.END_OF,ratingID,0,false));
        rl.addView(createTextView(c,distanceID,0,9,0,0,distance,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,clockImage,RelativeLayout.END_OF,locationImage,12,false));
        rl.addView(createTextView(c,priceID,15,0,0,0,price,255,0,0,
                Typeface.BOLD,RelativeLayout.BELOW,starImage,0,0,0,false));
        rl.addView(createImageView(c,foodImage,450,450,0,0,0,0,450,
                R.drawable.food_photo,0,0,0,0,1,false));
        rl.addView(createTextView(c,unitID,15,6,0,0,quantity + " Units Left",255,255,255,Typeface.BOLD,
                RelativeLayout.BELOW,starImage,RelativeLayout.END_OF,priceID,12,true));

        rl.addView(createHiddenTextView(c, foodRowID.toString(), foodRowIDID));
        rl.addView(createHiddenTextView(c, quantity.toString(), quantityID));

        rl.setPadding(36,36,36,36);
//
        cv.addView(rl);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                TextView orderID = (TextView) view.findViewById(foodRowIDID);
                                Log.d("rowID",orderID.getText().toString());
                                //Yes button clicked

                                SQLiteDatabase db = dbHelper.getReadableDatabase();
                                Cursor cursor = db.rawQuery("SELECT COUNT(foodId) AS totalOrder FROM receipt WHERE foodId = "  +  orderID.getText().toString(), null);

                                int totalOrder = 0;
                                while(cursor.moveToNext()) {
                                     totalOrder = cursor.getInt(
                                            cursor.getColumnIndexOrThrow("totalOrder"));
                                }

                                ContentValues values = new ContentValues();
                                values.put(FWPAContract.Receipt.COLUMN_NAME_FOODID, Integer.valueOf(orderID.getText().toString()));
                                values.put(FWPAContract.Receipt.COLUMN_NAME_STATUS, "TO BE COLLECTED");
                                values.put(FWPAContract.Receipt.COLUMN_NAME_TOKEN, "AK-" + orderID.getText().toString() + "-" + Integer.toString(totalOrder + 1));

                                SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

                                long newRowId = dbWrite.insert(FWPAContract.Receipt.TABLE_NAME, null, values);
                                Log.d("newItem", "new item added with id " + newRowId + "with value " + values.toString());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage("Would you like to redeem this item?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        return cv;
    }

    public static CardView createOrderCard (Context c, String foodName, String restaurantName, String day,
                                       String time, String price, String token){
        androidx.cardview.widget.CardView cv = new CardView(c);
        cv.setRadius(13);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,60,0,0);
        cv.setLayoutParams(layout);
        cv.setMaxCardElevation(33);;
        cv.setElevation(27);
        cv.setPreventCornerOverlap(true);
        cv.setUseCompatPadding(true);

        int foodID = View.generateViewId();
        int restaurantID = View.generateViewId();
        int clockImage = View.generateViewId();
        int dayID = View.generateViewId();
        int priceID = View.generateViewId();
        int foodImage = View.generateViewId();
        int tokenID = View.generateViewId();
        int statusID = View.generateViewId();
        int textID = View.generateViewId();
        RelativeLayout rl = new RelativeLayout(c);
        RelativeLayout.LayoutParams layoutrl = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.setPadding(41,41,41,41);
        rl.setLayoutParams(layoutrl);


        rl.addView(createTextView(c,foodID,14,27,0,0,foodName,32,106,93,
                Typeface.BOLD,0,0,0,0,18,false));
        rl.addView(createTextView(c,restaurantID,14,14,0,0,restaurantName,0,0,0,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,0,0,15,false));
        rl.addView(createImageView(c,clockImage,68,68,14,14,0,0,68,
                R.drawable.clock_foreground,RelativeLayout.BELOW,restaurantID,0,0,0,false));
        rl.addView(createTextView(c,dayID,9,27,0,0,day,0,0,0, Typeface.BOLD,
                RelativeLayout.BELOW,restaurantID,RelativeLayout.END_OF,clockImage,12,false));
        rl.addView(createTextView(c,0,9,27,0,0,time,0,0,0,Typeface.BOLD,
                RelativeLayout.BELOW,restaurantID,RelativeLayout.END_OF,dayID,12,false));
        rl.addView(createTextView(c,priceID,14,14,0,0,price,255,0,0,Typeface.BOLD,
                RelativeLayout.BELOW,clockImage,0,0,18,false));
        rl.addView(createImageView(c,foodImage,405,288,0,27,14,0,288,
                R.drawable.food_photo,0,0,0,0,1,true));
        rl.addView(createTextView(c,tokenID,14,52,0,0,token,0,0,0,Typeface.BOLD,
                RelativeLayout.BELOW,priceID,0,0,20,false));
        rl.addView(createTextView(c,statusID,390,60,0,0,"TO BE COLLECTED",255,255,255,
                Typeface.BOLD,0,0,RelativeLayout.BELOW,priceID,16,true));
        rl.addView(createTextView(c,textID,14,44,0,0,"Go to the restaurant at the specified date and time and show them this receipt to redeem your order",
                117,117,117,Typeface.NORMAL,RelativeLayout.BELOW,statusID,0,0,11,false));

        cv.addView(rl);

        return cv;
    }


    public static CardView createViewItemsCard(Context c, String foodName, String day, String time,
                                               String price, Integer quantity, Integer foodRowID,
                                               FWPADbHelper dbHelper){

        androidx.cardview.widget.CardView cv = new CardView(c);
        cv.setRadius(30);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (1100, 600);
        layout.setMargins(0,0,20,0);
        cv.setLayoutParams(layout);
        cv.setMaxCardElevation(60);
        cv.setElevation(50);
        cv.setPreventCornerOverlap(true);
        cv.setUseCompatPadding(true);
        cv.setClickable(true);

        int foodID = View.generateViewId();
        int clockImage = View.generateViewId();
        int dayID = View.generateViewId();
        int priceID = View.generateViewId();
        int foodImage = View.generateViewId();
        int unitID = View.generateViewId();
        int foodRowIDID = View.generateViewId();
        int quantityID = View.generateViewId();
        RelativeLayout rl = new RelativeLayout(c);

        rl.addView(createTextView(c,foodID,15,9,0,0,foodName,32,106,93,
                Typeface.BOLD,0,0,0,0,16,false));
        rl.addView(createImageView(c,clockImage,60,60,0,18,0,0,60,
                R.drawable.clock_foreground,RelativeLayout.BELOW,foodID,0,0,0,false));
        rl.addView(createTextView(c,dayID,6,21,0,0,day,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,clockImage,0,false));
        rl.addView(createTextView(c,0,9,21,0,0,time,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,dayID,0,false));
        rl.addView(createTextView(c,priceID,15,21,0,0,price,255,0,0,
                Typeface.BOLD,RelativeLayout.BELOW,clockImage,0,0,16,false));
        rl.addView(createImageView(c,foodImage,450,450,0,0,0,0,450,
                R.drawable.food_photo,0,0,0,0,1,false));
        rl.addView(createTextView(c,unitID,15,21,0,0,quantity + " Units Left",255,255,255,Typeface.BOLD,
                RelativeLayout.BELOW,clockImage,RelativeLayout.BELOW,priceID,16,true));

        rl.addView(createHiddenTextView(c, foodRowID.toString(), foodRowIDID));
        rl.addView(createHiddenTextView(c, quantity.toString(), quantityID));

        rl.setPadding(36,36,36,36);

        cv.addView(rl);


        return cv;
    }




    public static TextView createTextView(Context c,int ID, int ml, int mt, int mr, int mb, String text,
                                          int r, int g, int b, int TypefaceStyle,int verb, int subject,
                                          int verb2, int subject2, int fontsize,boolean backgroundGreen){
        TextView tv = new TextView(c);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(ml,mt,mr,mb);

        if (verb != 0){
            layout.addRule(verb,subject);
        }

        if (verb2 != 0){
            layout.addRule(verb2,subject2);
        }

        if (fontsize !=0){
            tv.setTextSize(fontsize);
        }

        if (backgroundGreen){
            tv.setBackgroundColor(Color.parseColor("#206A5D"));
            tv.setPadding(16,0,16,0);
        }

        tv.setLayoutParams(layout);
        tv.setTypeface(Typeface.create("sans-serif",TypefaceStyle));
        tv.setText(text);
        tv.setTextColor(Color.rgb(r,g,b));



        if (ID !=0){
            tv.setId(ID);
        }

        return tv;
    }

    public static TextView createHiddenTextView(Context c, String value,int ID){
        TextView tv = new TextView(c);
        tv.setText(value);
        tv.setId(ID);
        tv.setVisibility(TextView.GONE);

        return tv;
    }

    public static ImageView createImageView(Context c,int ID, int width, int height,
                                            int ml, int mt, int mr, int mb,
                                            int maxheight, int resid, int verb, int subject,
                                            int verb2, int subject2, int right, boolean centerCrop){
        ImageView iv = new ImageView(c);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (width,height);
        layout.setMargins(ml,mt,mr,mb);

        if (verb != 0){
            layout.addRule(verb,subject);
        }

        if (verb2 != 0){
            layout.addRule(verb2,subject2);
        }

        if (ID !=0){
            iv.setId(ID);
        }

        if (right != 0){
            layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        if (centerCrop){
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        iv.setLayoutParams(layout);
        iv.setMaxHeight(maxheight);
        iv.setImageResource(resid);



        return iv;
    }

    public static Button createButton(Context c,int ID, int width, int height, int ml, int mt, int mr,
                                      int mb, boolean backgroundGreen, int r, int g, int b,String text, int right){
        Button button = new Button(c);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (width,height);
        layout.setMargins(ml,mt,mr,mb);
        button.setTextColor(Color.rgb(r,g,b));
        button.setText(text);

        if (ID !=0){
            button.setId(ID);
        }

        if (backgroundGreen){
            button.setBackgroundColor(Color.parseColor("#206A5D"));
        }

        if (right != 0){
            layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        return button;
    }
}
