package com.example.skillspace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Skillspace extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "Skillspace.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names for user data
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Table and columns for course progress (already present)
    private static final String TABLE_COURSE_PROGRESS = "course_progress";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_IS_COMPLETED = "is_completed";
    private static final String COLUMN_IS_TEST_TAKEN = "is_test_taken";

    public DB_Skillspace(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for user registration data
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USERNAME + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Create table for course progress
        String CREATE_TABLE = "CREATE TABLE " + TABLE_COURSE_PROGRESS + " (" +
                COLUMN_COURSE_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_IS_COMPLETED + " INTEGER, " +
                COLUMN_IS_TEST_TAKEN + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_PROGRESS);
        onCreate(db);
    }

    // Method to insert a new user into the database
    public void registerUser(String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        // Insert the user into the users table
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    // Method to validate user login credentials
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        // If user is found
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }
}
