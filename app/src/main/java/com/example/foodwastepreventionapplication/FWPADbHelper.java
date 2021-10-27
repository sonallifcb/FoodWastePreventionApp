package com.example.foodwastepreventionapplication;

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

    private static final String SQL_DELETE_SELLER =
            "DROP TABLE IF EXISTS " + FWPAContract.SellerEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FWPA.db";

    public FWPADbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SELLER);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_SELLER);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
