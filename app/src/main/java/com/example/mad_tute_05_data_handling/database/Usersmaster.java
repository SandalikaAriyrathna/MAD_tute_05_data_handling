package com.example.mad_tute_05_data_handling.database;

import android.provider.BaseColumns;

public class Usersmaster {

    private Usersmaster() {

    }

    public static class Users implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";


    }

}
