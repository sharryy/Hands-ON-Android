package com.anonymous.sqliterecyclerview;

import android.provider.BaseColumns;

public class ItemContract {

    public ItemContract() {
    }

    public static final class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME =  "itemList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
