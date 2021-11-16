package com.example.foodwastepreventionapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FWPADbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_SELLER =
            "CREATE TABLE " + FWPAContract.SellerEntry.TABLE_NAME + " (" +
                    FWPAContract.SellerEntry._ID + " INTEGER PRIMARY KEY," +
                    FWPAContract.SellerEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    FWPAContract.SellerEntry.COLUMN_NAME_PASSWORD + " TEXT,"+
                    FWPAContract.SellerEntry.COLUMN_NAME_NAME + " TEXT,"+
                    FWPAContract.SellerEntry.COLUMN_NAME_LOCATION + " TEXT)";

    private static final String SQL_CREATE_FOOD =
            "CREATE TABLE " + FWPAContract.Food.TABLE_NAME + " (" +
                    FWPAContract.Food._ID + " INTEGER PRIMARY KEY," +
                    FWPAContract.Food.COLUMN_NAME_NAME + " TEXT,"+
                    FWPAContract.Food.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FWPAContract.Food.COLUMN_NAME_QUANTITY + " INTEGER," +
                    FWPAContract.Food.COLUMN_NAME_CATEGORY+ " TEXT," +
                    FWPAContract.Food.COLUMN_NAME_PRICE+ " REAL," +
                    FWPAContract.Food.COLUMN_NAME_SELLERID+ " INTEGER," +
                    FWPAContract.Food.COLUMN_NAME_DATETIME+ " TEXT)";

    private static final String SQL_CREATE_RECEIPT =
            "CREATE TABLE " + FWPAContract.Receipt.TABLE_NAME + " (" +
                    FWPAContract.Receipt._ID + " INTEGER PRIMARY KEY," +
                    FWPAContract.Receipt.COLUMN_NAME_FOODID + " INTEGER,"+
                    FWPAContract.Receipt.COLUMN_NAME_STATUS + " TEXT,"+
                    FWPAContract.Receipt.COLUMN_NAME_TOKEN + " TEXT)";

    private static final String SQL_DELETE_SELLER =
            "DROP TABLE IF EXISTS " + FWPAContract.SellerEntry.TABLE_NAME;

    private static final String SQL_DELETE_FOOD =
            "DROP TABLE IF EXISTS " + FWPAContract.Food.TABLE_NAME;

    private static final String SQL_DELETE_ORDER =
            "DROP TABLE IF EXISTS " + FWPAContract.Receipt.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION =5;
    public static final String DATABASE_NAME = "FWPA.db";

    public FWPADbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_SELLER);
        db.execSQL(SQL_CREATE_FOOD);
        db.execSQL(SQL_CREATE_RECEIPT);
//        db.rawQuery("INSERT INTO seller (email, password, name, location) " +
//                "VALUES (?,?,?,?)",new String[]{"alikopitiam@gmail.com","123456","Ali Kopitiam","Subang Jaya"});
//        db.rawQuery("INSERT INTO food (name, description, quantity, category, price, sellerid, datetime) " +
//                "VALUES (?,?,?,?,?,?,?)",new String[]{"Nasi Lemak", "Coconut rice with sambal", "5", "Meals", "1.50", "1", "13/11/2021 16:00"});
        ContentValues valuesSeller = new ContentValues();
        valuesSeller.put(FWPAContract.SellerEntry.COLUMN_NAME_EMAIL, "alikopitiam@gmail.com");
        valuesSeller.put(FWPAContract.SellerEntry.COLUMN_NAME_NAME, "Ali Kopitiam");
        valuesSeller.put(FWPAContract.SellerEntry.COLUMN_NAME_LOCATION,"Subang Jaya");
        valuesSeller.put(FWPAContract.SellerEntry.COLUMN_NAME_PASSWORD, "123456");

        db.insert(FWPAContract.SellerEntry.TABLE_NAME, null, valuesSeller);

        ContentValues valuesFood = new ContentValues();
        valuesFood.put(FWPAContract.Food.COLUMN_NAME_NAME, "Nasi Lemak");
        valuesFood.put(FWPAContract.Food.COLUMN_NAME_DESCRIPTION, "Coconut rice with sambal");
        valuesFood.put(FWPAContract.Food.COLUMN_NAME_QUANTITY,"5");
        valuesFood.put(FWPAContract.Food.COLUMN_NAME_CATEGORY, "Meals");
        valuesFood.put(FWPAContract.Food.COLUMN_NAME_PRICE,"1.50");
        valuesFood.put(FWPAContract.Food.COLUMN_NAME_SELLERID, "1");
        valuesFood.put(FWPAContract.Food.COLUMN_NAME_DATETIME, "16:00");

        db.insert(FWPAContract.Food.TABLE_NAME, null, valuesFood);

        ContentValues valuesOrder = new ContentValues();
        valuesOrder.put(FWPAContract.Receipt.COLUMN_NAME_FOODID, "1");
        valuesOrder.put(FWPAContract.Receipt.COLUMN_NAME_STATUS, "TO BE COLLECTED");
        valuesOrder.put(FWPAContract.Receipt.COLUMN_NAME_TOKEN,"AK-1-1");

        db.insert(FWPAContract.Receipt.TABLE_NAME, null, valuesFood);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_SELLER);
        db.execSQL(SQL_DELETE_FOOD);
//        db.execSQL(SQL_DELETE_ORDER);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
