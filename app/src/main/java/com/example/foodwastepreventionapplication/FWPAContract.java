package com.example.foodwastepreventionapplication;

import android.provider.BaseColumns;

public final class FWPAContract {

    private FWPAContract() {}

    public static class SellerEntry implements BaseColumns {
        public static final String TABLE_NAME = "seller";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LOCATION = "location";

    }
}
