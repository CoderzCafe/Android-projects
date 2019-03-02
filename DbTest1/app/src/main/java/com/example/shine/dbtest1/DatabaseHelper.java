package com.example.shine.dbtest1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "students.db";
    private static final String TABLE_NAME = "student_details";
    private static final int DATABASE_VERSION = 2;

    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String GENDER = "gender";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(200), "+AGE+" INTEGER, "+GENDER+" VARCHAR(10));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_QUERY);
            Toast.makeText(context, "Table created successfully", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(context, "Exception onCreate: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE " +TABLE_NAME);
            onCreate(db);
            Toast.makeText(context, "Database is upgraded", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(context, "Exception onUpgrade: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    //  insert data into database
    public long insertData(String name, String age, String gender) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(GENDER, gender);
        /**
         * sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
         * it returns a long value
         * if data is inserted the rowId = 1
         * id data is not inserted successfully the it will be -1
         * **/
        long rowId = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return rowId;
    }


    //  show all the data
    public Cursor showData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +TABLE_NAME, null);
        return cursor;
    }

}
