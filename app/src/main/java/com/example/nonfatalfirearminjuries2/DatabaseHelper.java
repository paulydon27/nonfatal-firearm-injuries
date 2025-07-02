package com.example.nonfatalfirearminjuries2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    // User table constants
    public static final String USER_TABLE = "User_Table";
    public static final String ID = "ID";
    public static final String PASSWORD = "PASSWORD";
    public static final String USERNAME = "USERNAME";
    public static final String LASTNAME = "LASTNAME";
    public static final String FIRSTNAME = "FIRSTNAME";

    // External DB (prebuilt) constants
    private static final String DB_NAME = "firearm_data.db";
    private static final String DB_PATH = "/data/data/com.example.nonfatalfirearminjuries2/databases/";
    private final Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Demo.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User Table
        String creatTableQuery = "CREATE TABLE " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRSTNAME + " TEXT, " + LASTNAME + " TEXT, " + USERNAME + " TEXT, " + PASSWORD + " TEXT);";
        db.execSQL(creatTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle future upgrades
    }

    // Register new user
    public boolean register(UserInfo userInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIRSTNAME, UserInfo.getFirstname());
        cv.put(LASTNAME, UserInfo.getLastname());
        cv.put(USERNAME, UserInfo.getUsername());
        cv.put(PASSWORD, UserInfo.getPassword());

        long result = db.insert(USER_TABLE, null, cv);
        db.close();
        return result != -1;
    }

    // Check user credentials
    public UserInfo check(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String queryStmt = "SELECT * FROM " + USER_TABLE + " WHERE " + USERNAME + "=? AND " + PASSWORD + "=?";
        String[] Arguments = {username, password};

        Cursor cursor = db.rawQuery(queryStmt, Arguments);
        UserInfo userInfo = null;

        if (cursor.moveToFirst()) {
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            userInfo = new UserInfo(firstname, lastname, username, password);
        }

        cursor.close();
        db.close();
        return userInfo;
    }

    // ---------- PREBUILT DATABASE METHODS ----------

    public void createDatabase() {
        boolean dbExists = checkDatabase();
        if (!dbExists) {
            this.getReadableDatabase(); // Create empty db
            copyDatabase();             // Copy from assets
        }
    }

    private boolean checkDatabase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDatabase() {
        try {
            InputStream input = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream output = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase openExternalDatabase() {
        return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
