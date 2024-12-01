package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.models.Booking;

import java.util.List;

public class Gv_TuChoiTab extends GV_BaseBookingTab{

    public Gv_TuChoiTab() {
        // Required empty public constructor
    }

    public static Gv_TuChoiTab newInstance(String param1, String param2) {
        Gv_TuChoiTab fragment = new Gv_TuChoiTab();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected List<Booking> getBookings() {
        return bookingDAO.getAllStatusBookings(BookingConstants.REJECT);
    }

    @Override
    protected int getContextMenuResource() {
        return R.menu.gv_datlich_tuchoi_listview_item_select_context_menu;
    }

    @Override
    protected boolean handleContextItemSelected(MenuItem item, Booking selectedBooking) {
        if(item.getItemId() == R.id.menu_retrieve) {
            //thêm vào list chờ và xóa khỏi list này. (thay đổi status của booking để query đúng)
            updateBookingStatus(selectedBooking, BookingConstants.PENDING);
            return true;
        }else{
            return false;
        }
    }
    private void updateBookingStatus(Booking booking, String status) {
        booking.setStatus(status);
        bookingDAO.updateBooking(booking);
        setupListView();
    }
}

