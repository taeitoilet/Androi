package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.constants.UserConstants;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookingApp";
    private static final int DATABASE_VERSION = 1;

    private static MyDatabaseHelper instance;

    public MyDatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static MyDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyDatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable= "CREATE TABLE " + UserConstants.TABLE_USER + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserConstants.USERNAME + " TEXT, " +
                UserConstants.PASSWORD + " TEXT, " +
                UserConstants.FULL_NAME + " TEXT, " +
                UserConstants.GENDER + " TEXT, " +
                UserConstants.ADDRESS + " TEXT, " +
                UserConstants.PLACE_OF_BIRTH + " TEXT, " +
                UserConstants.DATE_OF_BIRTH + " DATE, " +
                UserConstants.ID_CARD + " TEXT, " +
                UserConstants.EMAIL + " TEXT, " +
                UserConstants.PHONE + " TEXT, " +
                UserConstants.ROLE + " TEXT," +
                UserConstants.STUDENT_CODE + " TEXT," +
                UserConstants.TEACHER_ID + " TEXT, " +
                "FOREIGN KEY("+UserConstants.TEACHER_ID+ ") REFERENCES "+UserConstants.TABLE_USER+"(id)" +
                ")";
        db.execSQL(createUserTable);

        String createBookingTable= "CREATE TABLE "+ BookingConstants.TABLE_BOOKING +" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BookingConstants.DATE+ " DATE, " +
                BookingConstants.TIME+" TIME, " +
                BookingConstants.CONTENT+" TEXT, " +
                BookingConstants.STATUS+" TEXT, " +
                BookingConstants.RATING+" REAL, " +
                "userId INTEGER, " +
                "FOREIGN KEY(userId) REFERENCES "+UserConstants.TABLE_USER+"(id) " +
                ")";
        db.execSQL(createBookingTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UserConstants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+ BookingConstants.TABLE_BOOKING);
        onCreate(db);
    }

    public void deleteAllDataFromTable(String tableUser) {
    }
}
