package com.example.shine.dbtest2update;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "user_details";
    private static final int DATABASE_VERSION = 2;

    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String GENDER = "gender";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(200), "+AGE+" INTEGER, "+GENDER+" VARCHAR(10));";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        /** drop the table **/
        try {
            String query = "DROP TABLE IF EXISTS user_details";
            db.execSQL(query);
            onCreate(db);
        } catch (SQLException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /** insert into the database **/
    public long insertData(String name, String age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("gender", gender);
        long rowId = db.insert("user_details", null, contentValues);
        return rowId;
    }

    /** show all data in the database **/
    public Cursor showAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM user_details;";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

    /** show the specific data from the database **/
    //  not work
    public Cursor showSpecificData(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT name, age, gender FROM user_details WHERE id="+id+";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }


    /** update database ***/
    public boolean updateData(String id, String name, String age, String gender) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("gender", gender);
        sqLiteDatabase.update("user_details", contentValues, "id=?", new String[] {id});
        return true;
    }

}
