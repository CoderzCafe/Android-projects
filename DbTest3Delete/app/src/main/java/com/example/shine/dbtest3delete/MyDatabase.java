package com.example.shine.dbtest3delete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "userDb.db";
    private static final int DATABAE_VERSION = 3;


    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABAE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE user_details(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(255), age VARCHAR(255), gender VARCHAR(255));";
        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS user_details;";
        try {
            db.execSQL(query);
            onCreate(db);
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /** insert data in database **/
    public long insertData(String name, String age, String gender) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("gender", gender);

        long rowId = sqLiteDatabase.insert("user_details", null, contentValues);
        return rowId;
    }

    /** show all the data **/
    public Cursor showAllData() {
        String query = "SELECT * FROM user_details;";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

    /** update data **/
    public int updateData(String id, String name, String age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("gender", gender);

        return db.update("user_details", contentValues, "id=?", new String[]{id});
    }

    /** delete data from database **/
    public int deleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("user_details", "id=?", new String[]{id});
    }
}
