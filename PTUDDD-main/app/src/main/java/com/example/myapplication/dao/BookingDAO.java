package com.example.myapplication.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.database.MyDatabaseHelper;
import com.example.myapplication.models.Booking;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class BookingDAO {
    private SQLiteDatabase db;
    private MyDatabaseHelper dbHelper;

    public BookingDAO(Context context) {
        dbHelper = MyDatabaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }


//<<<<<<< HEAD
//    public void addBooking(Booking booking) {
//        ContentValues values = new ContentValues();
//        values.put(BookingConstants.DATE, booking.getDate());
//        values.put(BookingConstants.TIME, booking.getTime());
//        values.put(BookingConstants.CONTENT, booking.getContent());
//        values.put(BookingConstants.STATUS, booking.getStatus());
//        values.put(BookingConstants.RATING, booking.getRating());
//        values.put(BookingConstants.USER_ID, booking.getUserId());
//        db.insert(BookingConstants.TABLE_BOOKING, null, values);
//    }
//=======
//
//>>>>>>> 9d49a9c470b902c6204089bbe10072e12231eaa8

    public Booking getBookingById(int id) {
        Cursor cursor = db.query(BookingConstants.TABLE_BOOKING, new String[]{"id", BookingConstants.DATE, BookingConstants.TIME, BookingConstants.CONTENT, BookingConstants.STATUS, BookingConstants.RATING, BookingConstants.USER_ID},
                "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Booking booking = new Booking(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.CONTENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.STATUS)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(BookingConstants.RATING)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(BookingConstants.USER_ID))
                );
                cursor.close();
                return booking;
            }
            cursor.close();
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        Cursor cursor = db.query(BookingConstants.TABLE_BOOKING, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.DATE)));
                booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.TIME)));
                booking.setContent(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.CONTENT)));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.STATUS)));
                booking.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(BookingConstants.RATING)));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(BookingConstants.USER_ID)));

                bookings.add(booking);
            }
            cursor.close();
        }
        return bookings;
    }

    public List<Booking> getAllStatusBookings(String selectionArg) {
        List<Booking> bookings = new ArrayList<>();
        String[] columns = {"id", BookingConstants.DATE, BookingConstants.TIME, BookingConstants.CONTENT, BookingConstants.STATUS, BookingConstants.RATING, BookingConstants.USER_ID};
        String selection = "status = ?";
        String[] selectionArgs = { selectionArg };

        Cursor cursor = db.query(BookingConstants.TABLE_BOOKING, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.DATE)));
                booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.TIME)));
                booking.setContent(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.CONTENT)));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.STATUS)));
                booking.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(BookingConstants.RATING)));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(BookingConstants.USER_ID)));
                bookings.add(booking);
            }
            cursor.close();
        }
        return bookings;
    }


    public List<Booking> getAllAcceptedBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM " + BookingConstants.TABLE_BOOKING + " WHERE userId = ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), "Chấp nhận"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                booking.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                booking.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow("rating")));
                bookings.add(booking);
            }
            cursor.close();
        }
        return bookings;
    }
    // New method to get bookings by status and user_id
    public List<Booking> getAllStatusBookingsByUserId(String status, int userId) {
        List<Booking> bookings = new ArrayList<>();
        String[] columns = {"id", BookingConstants.DATE, BookingConstants.TIME, BookingConstants.CONTENT, BookingConstants.STATUS, BookingConstants.RATING, BookingConstants.USER_ID};
        String selection = "status = ? AND user_id = ?";
        String[] selectionArgs = { status, String.valueOf(userId) };

        Cursor cursor = db.query(BookingConstants.TABLE_BOOKING, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.DATE)));
                booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.TIME)));
                booking.setContent(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.CONTENT)));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(BookingConstants.STATUS)));
                booking.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(BookingConstants.RATING)));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(BookingConstants.USER_ID)));
                bookings.add(booking);
            }
            cursor.close();
        }
        return bookings;
    }
    public void deleteBooking(int bookingId) {
        db.delete(BookingConstants.TABLE_BOOKING, "id = ?", new String[]{String.valueOf(bookingId)});
    }

    public int updateBooking(Booking booking) {
        ContentValues values = new ContentValues();
        values.put(BookingConstants.DATE, booking.getDate());
        values.put(BookingConstants.TIME, booking.getTime());
        values.put(BookingConstants.CONTENT, booking.getContent());
        values.put(BookingConstants.STATUS, booking.getStatus());
        values.put(BookingConstants.RATING, booking.getRating());
        return db.update(BookingConstants.TABLE_BOOKING, values, "id = ?", new String[]{String.valueOf(booking.getId())});
    }

//    public void updateBookingStatus(int bookingId, String status) {
//        ContentValues values = new ContentValues();
//        values.put(BookingConstants.STATUS, status);
//        db.update(BookingConstants.TABLE_BOOKING, values, "id = ?", new String[]{String.valueOf(bookingId)});
//    }

    public void close() {
        dbHelper.close();
    }


    public void addBooking(Booking booking) {
        ContentValues values = new ContentValues();
        values.put("date", booking.getDate());
        values.put("time", booking.getTime());
        values.put("content", booking.getContent());
        values.put("status", booking.getStatus());
        values.put("rating", booking.getRating());
        values.put("userId", booking.getUserId());
        db.insert(BookingConstants.TABLE_BOOKING, null, values);
    }

    public List<String> getTimeByDate(String date) {
        List<String> times = new ArrayList<>();
        String query = "SELECT DISTINCT time FROM " + BookingConstants.TABLE_BOOKING + " WHERE date = ? AND status = ?";


        Cursor cursor = db.rawQuery(query, new String[]{date, BookingConstants.ACCEPT});

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                    times.add(time);
                }
            } finally {

            }
        }
        return times;
    }

    public List<Booking> getBookingsByDateAndUser(String date, int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM " + BookingConstants.TABLE_BOOKING + " WHERE date = ? AND userId = ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date, String.valueOf(userId), "Chấp nhận"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                booking.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                bookings.add(booking);
            }
        }
        return bookings;
    }
    public List<Booking> getBookingsByDate(String date) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM " + BookingConstants.TABLE_BOOKING + " WHERE date = ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date, "Chấp nhận"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                booking.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                bookings.add(booking);
            }
        }
        return bookings;
    }
    public void resetData(){
        dbHelper.deleteAllDataFromTable(BookingConstants.TABLE_BOOKING);
    }

    public List<String> getDistinctBookingDatesFromMonth(int userId) {
        List<String> distinctDates = new ArrayList<>();

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dbSdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng cho cơ sở dữ liệu

        // Tính ngày đầu tháng và cuối tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = dbSdf.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = dbSdf.format(calendar.getTime());

        String query = "SELECT DISTINCT date FROM " + BookingConstants.TABLE_BOOKING + " WHERE date >= ? AND date <= ? AND userId = ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate, String.valueOf(userId), "Chấp nhận"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String dbDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                distinctDates.add(dbDate);
            }
        }

        return distinctDates;
    }
    public List<String> getDistinctBookingDatesFromMonth2() {
        List<String> distinctDates = new ArrayList<>();

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dbSdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng cho cơ sở dữ liệu

        // Tính ngày đầu tháng và cuối tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = dbSdf.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = dbSdf.format(calendar.getTime());

        String query = "SELECT DISTINCT date FROM " + BookingConstants.TABLE_BOOKING + " WHERE date >= ? AND date <= ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate, "Chấp nhận"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String dbDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                distinctDates.add(dbDate);
            }
        }

        return distinctDates;
    }

    public List<String> getDistinctBookingDatesFromMonth3() {
        List<String> distinctDates = new ArrayList<>();

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dbSdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng cho cơ sở dữ liệu

        // Tính ngày đầu tháng và cuối tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = dbSdf.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = dbSdf.format(calendar.getTime());

        String query = "SELECT date FROM " + BookingConstants.TABLE_BOOKING + " WHERE date >= ? AND date <= ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate, "Chấp nhận"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String dbDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                distinctDates.add(dbDate);
            }
        }

        return distinctDates;
    }

    public List<String> getDistinctBookingDatesFromMonth4(int userId) {
        List<String> distinctDates = new ArrayList<>();

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dbSdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng cho cơ sở dữ liệu

        // Tính ngày đầu tháng và cuối tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = dbSdf.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = dbSdf.format(calendar.getTime());

        String query = "SELECT date FROM " + BookingConstants.TABLE_BOOKING + " WHERE date >= ? AND date <= ? AND userId = ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate, String.valueOf(userId), "Chấp nhận"});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String dbDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                distinctDates.add(dbDate);
            }
        }

        return distinctDates;
    }

    public List<Booking> getCompletedBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM " + BookingConstants.TABLE_BOOKING + " WHERE userId = ? AND status = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), BookingConstants.SUCCESS});

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Booking booking = new Booking();
                    booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
                    booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    booking.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                    booking.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
                    booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                    booking.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow("rating")));
                    bookings.add(booking);
                }
            } finally {
                cursor.close();
            }
        }
        return bookings;
    }

    public void updateBookingStatus(int bookingId, String status) {
        ContentValues values = new ContentValues();
        values.put("status", status);
        int rowsAffected = db.update(BookingConstants.TABLE_BOOKING, values, "id = ?", new String[]{String.valueOf(bookingId)});
        if (rowsAffected > 0) {
            Log.i("BookingDAO", "Booking status updated successfully.");
        } else {
            Log.e("BookingDAO", "Error updating booking status.");
        }
    }
    public void updateBookingRating(int bookingId, float rating) {
        ContentValues values = new ContentValues();
        values.put("rating", rating);
        int rowsAffected = db.update(BookingConstants.TABLE_BOOKING, values, "id = ?", new String[]{String.valueOf(bookingId)});
        if (rowsAffected > 0) {
            Log.i("BookingDAO", "Booking rating updated successfully.");
        } else {
            Log.e("BookingDAO", "Error updating booking rating.");
        }
    }

    public boolean deleteBookingByUser(int bookingId) {
        int rowsAffected = db.delete("bookings", "id = ?", new String[]{String.valueOf(bookingId)});
        return rowsAffected > 0;
    }

}
