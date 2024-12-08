package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.constants.UserConstants;
import com.example.myapplication.database.MyDatabaseHelper;
import com.example.myapplication.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase db;
    private MyDatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserConstants.USERNAME, user.getUsername());
        values.put(UserConstants.PASSWORD, user.getPassword());
        values.put(UserConstants.FULL_NAME, user.getFullName());
        values.put(UserConstants.GENDER, user.getGender());
        values.put(UserConstants.ADDRESS, user.getAddress());
        values.put(UserConstants.PLACE_OF_BIRTH, user.getPlaceOfBirth());
        values.put(UserConstants.DATE_OF_BIRTH, user.getDateOfBirth());
        values.put(UserConstants.EMAIL, user.getEmail());
        values.put(UserConstants.PHONE, user.getPhone());
        values.put(UserConstants.ROLE, user.getRole());
        values.put(UserConstants.PATIENT_CODE, user.getPatientCode());
        values.put(UserConstants.DOCTOR_ID, user.getDoctorId());

        // Chèn vào bảng và trả về ID của bản ghi mới
        return db.insert(UserConstants.TABLE_USER, null, values);

//        db.insert(UserConstants.TABLE_USER, null, values);
//        db.close();

    }

    public User getUserByUsername(String username) {
        Cursor cursor = db.query(UserConstants.TABLE_USER, new String[]{"id", UserConstants.USERNAME, UserConstants.PASSWORD, UserConstants.FULL_NAME, UserConstants.GENDER, UserConstants.ADDRESS, UserConstants.PLACE_OF_BIRTH, UserConstants.DATE_OF_BIRTH,  UserConstants.EMAIL, UserConstants.PHONE, UserConstants.ROLE, UserConstants.PATIENT_CODE, UserConstants.DOCTOR_ID},
                UserConstants.USERNAME + "=?", new String[]{username}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                User user = new User(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12)
                );
                user.setId(cursor.getInt(0));
//                db.close();
//                cursor.close();
                return user;
            }
//            db.close();
//            cursor.close();
        }
//        cursor.close();
//        db.close();
        return null;
    }
    public User getUserByStudentId(String studentId) {
        Cursor cursor = null;
        try {
            cursor = db.query(
                    UserConstants.TABLE_USER,
                    new String[]{"id", UserConstants.USERNAME, UserConstants.PASSWORD, UserConstants.FULL_NAME, UserConstants.GENDER, UserConstants.ADDRESS, UserConstants.PLACE_OF_BIRTH, UserConstants.DATE_OF_BIRTH,  UserConstants.EMAIL, UserConstants.PHONE, UserConstants.ROLE, UserConstants.PATIENT_CODE, UserConstants.DOCTOR_ID},
                    UserConstants.PATIENT_CODE + "=?",
                    new String[]{studentId},
                    null, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.USERNAME));
                String studentIdFromDb = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.PATIENT_CODE));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.EMAIL));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.PASSWORD));
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.FULL_NAME));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.GENDER));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.ADDRESS));
                String placeOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.PLACE_OF_BIRTH));
                String dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.DATE_OF_BIRTH));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.PHONE));
                String role = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.ROLE));
                String doctorId = cursor.getString(cursor.getColumnIndexOrThrow(UserConstants.DOCTOR_ID));
                return new User(username, password, fullName, gender, address, placeOfBirth, dateOfBirth, email,phone, role, studentIdFromDb,doctorId);
            } else {
                return null;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public User getUserById(int userId) {
        Cursor cursor = db.query(UserConstants.TABLE_USER, new String[]{"id", UserConstants.USERNAME, UserConstants.PASSWORD, UserConstants.FULL_NAME, UserConstants.GENDER, UserConstants.ADDRESS, UserConstants.PLACE_OF_BIRTH, UserConstants.DATE_OF_BIRTH,  UserConstants.EMAIL, UserConstants.PHONE, UserConstants.ROLE, UserConstants.PATIENT_CODE, UserConstants.DOCTOR_ID},
                "id = ?", new String[]{String.valueOf(userId)}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                User user = new User(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12)
                );
                user.setId(cursor.getInt(0));
                return user;
            }
        }
        return null;
    }

    public void deleteUser(int userId) {
        db.delete(UserConstants.TABLE_USER, "id" + " = ?", new String[]{String.valueOf(userId)});
//        db.close();
    }
    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserConstants.USERNAME, user.getUsername());
        values.put(UserConstants.PASSWORD, user.getPassword());
        values.put(UserConstants.FULL_NAME, user.getFullName());
        values.put(UserConstants.GENDER, user.getGender());
        values.put(UserConstants.ADDRESS, user.getAddress());
        values.put(UserConstants.PLACE_OF_BIRTH, user.getPlaceOfBirth());
        values.put(UserConstants.DATE_OF_BIRTH, user.getDateOfBirth());
        values.put(UserConstants.EMAIL, user.getEmail());
        values.put(UserConstants.PHONE, user.getPhone());
        values.put(UserConstants.ROLE, user.getRole());
        values.put(UserConstants.PATIENT_CODE, user.getPatientCode());
        values.put(UserConstants.DOCTOR_ID, user.getDoctorId());
        return db.update(UserConstants.TABLE_USER, values, "id" + " = ?", new String[]{String.valueOf(user.getId())});
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + UserConstants.TABLE_USER;
//         db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12)
                );
                user.setId(cursor.getInt(0));
                userList.add(user);
            } while (cursor.moveToNext());
        }
//        cursor.close();
        return userList;
    }
    public float calculateAverageRatingForUser() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String query = "SELECT AVG(" + BookingConstants.RATING + ") as average_rating " +
                "FROM " + BookingConstants.TABLE_BOOKING ;
        Cursor cursor = db.rawQuery(query, null);
        float averageRating = 0;
        if (cursor.moveToFirst()) {
            averageRating = cursor.getFloat(cursor.getColumnIndexOrThrow("average_rating"));
        }
        return  Math.round(averageRating * 10.0) / 10.0f;
    }
    public float calculateAverageRatingForUser1() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String query = "SELECT AVG(" + BookingConstants.RATING + ") as average_rating " +
                "FROM " + BookingConstants.TABLE_BOOKING +
                " WHERE " + BookingConstants.STATUS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{BookingConstants.FINISH});
        float averageRating = 0;
        if (cursor.moveToFirst()) {
            averageRating = cursor.getFloat(cursor.getColumnIndexOrThrow("average_rating"));
        }
        cursor.close(); // Don't forget to close the cursor
        return Math.round(averageRating * 10.0) / 10.0f;
    }


    public void close() {
        this.dbHelper.close();
    }
}
