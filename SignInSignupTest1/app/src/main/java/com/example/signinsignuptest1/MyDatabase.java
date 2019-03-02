package com.example.signinsignuptest1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VAERSION = 2;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VAERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableQuery = "CREATE TABLE user_details(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(255), email VARCHAR(255), userName VARCHAR(255), password VARCHAR(255));";
        try {
            db.execSQL(tableQuery);
            Toast.makeText(context, "onCreate is called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS user_details;";
        try {
            db.execSQL(query);
            onCreate(db);
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /** insert to database **/
    public long insertData(UserDetails userDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", userDetails.getName());
        contentValues.put("email", userDetails.getEmail());
        contentValues.put("userName", userDetails.getUserName());
        contentValues.put("password", userDetails.getPassword());

        long rowId = db.insert("user_details", null, contentValues);
        return rowId;
    }

    /** match user name and the password **/
    public boolean matchUser(String userName, String password) {
        String query = "SELECT * FROM user_details;";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        boolean result = false;

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "There no user with with this user name and password", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String uName = cursor.getString(3);
                String pwd = cursor.getString(4);

                if (uName.equals(userName) && pwd.equals(password)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
