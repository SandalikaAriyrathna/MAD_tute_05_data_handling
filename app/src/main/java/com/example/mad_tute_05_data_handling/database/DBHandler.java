package com.example.mad_tute_05_data_handling.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public Context context;
    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE_USERS =
                "CREATE TABLE IF NOT EXISTS " + Usersmaster.Users.TABLE_NAME + " (" +
                        Usersmaster.Users._ID + " INTEGER PRIMARY KEY," +
                        Usersmaster.Users.COLUMN_USER_NAME + " TEXT," +
                        Usersmaster.Users.COLUMN_PASSWORD + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USERS);
    }

    public Long addInfo(String username, String password){

        // Create database reference object with write permission
        SQLiteDatabase db = getWritableDatabase();

        // Create Object to from ContentValues class
        ContentValues values = new ContentValues();

        // add given data as key-value pair
        values.put(Usersmaster.Users.COLUMN_USER_NAME, username);
        values.put(Usersmaster.Users.COLUMN_PASSWORD, password);

        // Push ContentValues object into  database
       return db.insert(Usersmaster.Users.TABLE_NAME, null, values);
    }
    public List readAll(){

        SQLiteDatabase db = getReadableDatabase();

        String [] projection = {
                Usersmaster.Users._ID,
                Usersmaster.Users.COLUMN_USER_NAME,
                Usersmaster.Users.COLUMN_PASSWORD
        };

        String sortOrder = Usersmaster.Users._ID + " DESC";

        Cursor cursor = db.query(
                Usersmaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List info = new ArrayList<>();

        while(cursor.moveToNext()){

            // crete List object to add single row data
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(Usersmaster.Users.COLUMN_USER_NAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(Usersmaster.Users.COLUMN_PASSWORD));


            info.add(userName + ":" + password);
        }

        cursor.close();
        return  info;

    }

    public  void  deleteInfo(String userName){

        SQLiteDatabase db = getReadableDatabase();

        String selection = Usersmaster.Users.COLUMN_USER_NAME + " LIKE ?";
        String[] stringArgs = {userName};

        db.delete(Usersmaster.Users.TABLE_NAME,selection,stringArgs);

    }

    public void updateInfo(View view, String username, String password){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Usersmaster.Users.COLUMN_PASSWORD, password);

        String selection = Usersmaster.Users.COLUMN_USER_NAME + " LIKE ?";
        String[] selectionArgs = {username};

        int count = db.update(
                Usersmaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        Snackbar snackbar = Snackbar.make(view, count + "rows were affected!", Snackbar.LENGTH_LONG);
        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
