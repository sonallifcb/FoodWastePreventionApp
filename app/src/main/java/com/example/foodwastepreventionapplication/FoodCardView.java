package com.example.foodwastepreventionapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
                                      String day, String time, String rating, String location,
                                      String price, Integer quantity, Integer foodRowID,
                                      FWPADbHelper dbHelper, String imagepath){

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
                32, 106, 93, Typeface.BOLD,0,0,0,0,0,false,0));
        rl.addView(createTextView(c,foodID,15,9,0,0,foodName,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,restaurantID,0,0,0,false,0));
        rl.addView(createImageView(c,clockImage,60,60,0,9,0,0,60,
                R.drawable.clock_foreground,RelativeLayout.BELOW,foodID,0,0,0,false, null));
        rl.addView(createTextView(c,dayID,6,18,0,0,day,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,clockImage,12,false,0));
        rl.addView(createTextView(c,0,9,18,0,0,time,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,dayID,12,false,0));
        rl.addView(createImageView(c,starImage,60,60,0,0,0,0,60,
                R.drawable.star_foreground,RelativeLayout.BELOW,clockImage,0,0,0,false, null));
        rl.addView(createTextView(c,ratingID,6,9,0,0, rating,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,clockImage,RelativeLayout.END_OF,starImage,12,false,0));
        rl.addView(createImageView(c,locationImage,60,60,9,0,0,0,60,
                R.drawable.location_foreground,RelativeLayout.BELOW,clockImage,RelativeLayout.END_OF,ratingID,0,false, null));
        rl.addView(createTextView(c,distanceID,0,9,0,0,location,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,clockImage,RelativeLayout.END_OF,locationImage,12,false,0));
        rl.addView(createTextView(c,priceID,15,0,0,0,price,255,0,0,
                Typeface.BOLD,RelativeLayout.BELOW,starImage,0,0,0,false,0));
        rl.addView(createImageView(c,foodImage,420,420,0,0,0,0,420,
                R.drawable.food_photo,0,0,0,0,1,false, imagepath));

        if (quantity != 0){
        rl.addView(createTextView(c,unitID,15,6,0,0,quantity + " Units Left",255,255,255,Typeface.BOLD,
                RelativeLayout.BELOW,starImage,RelativeLayout.END_OF,priceID,12,true,0));
        }
        else {
            rl.addView(createTextView(c,unitID,15,6,0,0,"SOLD OUT",255,255,255,Typeface.BOLD,
                    RelativeLayout.BELOW,starImage,RelativeLayout.END_OF,priceID,12,true,1));
        }

        rl.addView(createHiddenTextView(c, foodRowID.toString(), foodRowIDID));
        rl.addView(createHiddenTextView(c, quantity.toString(), quantityID));

        rl.setPadding(36,36,36,36);
//
        cv.addView(rl);

        return cv;
    }

    public static CardView createOrderCard (Context c, String foodName, String restaurantName, String day,
                                       String time, String price, String token, String imagepath, String location,
                                            String status){
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
        int addressID = View.generateViewId();
        int locationImage = View.generateViewId();
        RelativeLayout rl = new RelativeLayout(c);
        RelativeLayout.LayoutParams layoutrl = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.setPadding(41,41,41,41);
        rl.setLayoutParams(layoutrl);


        rl.addView(createTextView(c,foodID,14,27,0,0,foodName,32,106,93,
                Typeface.BOLD,0,0,0,0,16,false,0));
        rl.addView(createTextView(c,restaurantID,14,14,0,0,restaurantName,0,0,0,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,0,0,15,false,0));
        rl.addView(createImageView(c,clockImage,68,68,14,14,0,0,68,
                R.drawable.clock_foreground,RelativeLayout.BELOW,restaurantID,0,0,0,false, null));
        rl.addView(createTextView(c,dayID,9,27,0,0,day,0,0,0, Typeface.BOLD,
                RelativeLayout.BELOW,restaurantID,RelativeLayout.END_OF,clockImage,12,false,0));
        rl.addView(createTextView(c,0,9,27,0,0,time,0,0,0,Typeface.BOLD,
                RelativeLayout.BELOW,restaurantID,RelativeLayout.END_OF,dayID,12,false,0));
        rl.addView(createTextView(c,priceID,14,14,0,0,price,255,0,0,Typeface.BOLD,
                RelativeLayout.BELOW,clockImage,0,0,18,false,0));
        rl.addView(createImageView(c,foodImage,380,288,0,27,14,0,288,
                R.drawable.food_photo,0,0,0,0,1,true, imagepath));
        rl.addView(createTextView(c,tokenID,14,52,0,0,token,0,0,0,Typeface.BOLD,
                RelativeLayout.BELOW,priceID,0,0,20,false,0));
        rl.addView(createTextView(c,textID,14,44,0,0,"Go to the restaurant at the specified date and time and show them this receipt to redeem your order",
                117,117,117,Typeface.NORMAL,RelativeLayout.BELOW,statusID,0,0,11,false,0));
        rl.addView(createTextView(c, addressID,14, 15, 0,0 ,location,117,117,117,
                Typeface.NORMAL, RelativeLayout.END_OF, locationImage,RelativeLayout.BELOW,textID,11,false,0));
        rl.addView(createImageView(c,locationImage,45,45,12,15,0,0,60,
                R.drawable.location_foreground,RelativeLayout.BELOW,textID,0,0,0,false, null));

        if("COLLECTED".equals(status)){
            rl.addView(createTextView(c,statusID,460,60,0,0,status,255,255,255,
                    Typeface.BOLD,0,0,RelativeLayout.BELOW,priceID,16,false,1));
        }
        else{
            rl.addView(createTextView(c,statusID,420,60,0,0,status,255,255,255,
                    Typeface.BOLD,0,0,RelativeLayout.BELOW,priceID,0,true,0));
        }

        cv.addView(rl);

        return cv;
    }


    public static CardView createViewItemsCard(Context c, String foodName, String day, String time,
                                               String price, Integer quantity, Integer foodRowID,
                                               FWPADbHelper dbHelper, View.OnClickListener Listener,
                                               String imagepath){

        androidx.cardview.widget.CardView cv = new CardView(c);
        cv.setRadius(30);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (1100, 610);
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
        int deleteButtonId = View.generateViewId();
        RelativeLayout rl = new RelativeLayout(c);

        rl.addView(createTextView(c,foodID,15,9,0,0,foodName,32,106,93,
                Typeface.BOLD,0,0,0,0,16,false,0));
        rl.addView(createImageView(c,clockImage,60,60,0,9,0,0,60,
                R.drawable.clock_foreground,RelativeLayout.BELOW,foodID,0,0,0,false,null));
        rl.addView(createTextView(c,dayID,6,12,0,0,day,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,clockImage,0,false,0));
        rl.addView(createTextView(c,0,9,12,0,0,time,72,72,72,
                Typeface.BOLD,RelativeLayout.BELOW,foodID,RelativeLayout.END_OF,dayID,0,false,0));
        rl.addView(createTextView(c,priceID,15,12,0,0,price,255,0,0,
                Typeface.BOLD,RelativeLayout.BELOW,clockImage,0,0,16,false,0));
        rl.addView(createImageView(c,foodImage,450,450,0,0,0,0,450,
                R.drawable.food_photo,0,0,0,0,1,false,imagepath));
//        rl.addView(createTextView(c,unitID,15,12,0,0,quantity + " Units Left",255,255,255,Typeface.BOLD,
//                RelativeLayout.BELOW,clockImage,RelativeLayout.BELOW,priceID,0,true,0));
        Button deleteButton = createButton(c,deleteButtonId,15,12,0,0,true,
                255,255,255,"DELETE",0,RelativeLayout.BELOW,unitID,12);
        deleteButton.setOnClickListener(Listener);
        rl.addView(deleteButton);

        rl.addView(createHiddenTextView(c, foodRowID.toString(), foodRowIDID));
        rl.addView(createHiddenTextView(c, quantity.toString(), quantityID));

        if (quantity != 0){
            rl.addView(createTextView(c,unitID,15,12,0,0,quantity + " Units Left",255,255,255,Typeface.BOLD,
                    RelativeLayout.BELOW,clockImage,RelativeLayout.BELOW,priceID,0,true,0));
        }
        else {
            rl.addView(createTextView(c,unitID,15,12,0,0,"SOLD OUT",255,255,255,Typeface.BOLD,
                    RelativeLayout.BELOW,clockImage,RelativeLayout.BELOW,priceID,0,true,1));
        }

        rl.setPadding(36,36,36,36);

        cv.addView(rl);


        return cv;
    }

    public static TextView createTextView(Context c,int ID, int ml, int mt, int mr, int mb, String text,
                                          int r, int g, int b, int TypefaceStyle,int verb, int subject,
                                          int verb2, int subject2, int fontsize,boolean backgroundGreen,
                                          int backgroundLightGreen){
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
            tv.setPadding(18,3,18,3);
        }

        if (backgroundLightGreen != 0){
            tv.setBackgroundColor(Color.parseColor("#69B84A"));
            tv.setPadding(18,3,18,3);
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
                                            int verb2, int subject2, int right, boolean centerCrop,
                                            String imagepath){
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

        if (imagepath  != null){
            Bitmap image = BitmapFactory.decodeFile(imagepath);
            iv.setImageBitmap(image);
        }
        else{
            iv.setImageResource(resid);
        }

        iv.setLayoutParams(layout);
        iv.setMaxHeight(maxheight);


        return iv;
    }

    public static Button createButton(Context c,int ID, int ml, int mt, int mr,
                                      int mb, boolean backgroundRed, int r, int g, int b,String text,
                                      int right,int verb,int subject,int fontsize){
        Button button = new Button(c);
//        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
//                (width,height);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(ml,mt,mr,mb);
        button.setTextColor(Color.rgb(r,g,b));
        button.setText(text);

        if (ID !=0){
            button.setId(ID);
        }

        if (backgroundRed){
            button.setBackgroundColor(Color.parseColor("#FF0000"));
            button.setPadding(10,3,10,3);
        }

        if (right != 0){
            layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        if (verb != 0){
            layout.addRule(verb,subject);
        }

        if (fontsize !=0){
            button.setTextSize(fontsize);
        }

        button.setLayoutParams(layout);
        return button;
    }
}
