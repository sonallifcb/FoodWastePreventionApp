package com.example.foodwastepreventionapplication;

import android.provider.BaseColumns;

public final class FWPAContract {

    private FWPAContract() {}

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "seller";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_TYPE = "type";


    }

    public static class Food implements BaseColumns{
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SELLERID =  "sellerid";
        public static final String COLUMN_NAME_DATETIME =  "datetime";
        public static final String COLUMN_NAME_IMAGEPATH = "imagepath";


    }

    public static class Receipt implements BaseColumns{
        public static final String TABLE_NAME = "receipt";
        public static final String COLUMN_NAME_FOODID = "foodId";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_TOKEN = "token";
    }
}
