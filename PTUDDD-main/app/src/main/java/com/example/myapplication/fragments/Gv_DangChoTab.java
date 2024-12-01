package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.models.Booking;

import java.util.List;

public class Gv_DangChoTab extends GV_BaseBookingTab{

    public Gv_DangChoTab() {
        // Required empty public constructor
    }

    public static Gv_DangChoTab newInstance(String param1, String param2) {
        Gv_DangChoTab fragment = new Gv_DangChoTab();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected List<Booking> getBookings() {
        return bookingDAO.getAllStatusBookings(BookingConstants.PENDING);
    }

    @Override
    protected int getContextMenuResource() {
        return R.menu.gv_datlich_dangcho_listview_item_select_context_menu;
    }

    @Override
    protected boolean handleContextItemSelected(MenuItem item, Booking selectedBooking) {
        if(item.getItemId()==  R.id.menu_accept) {
            updateBookingStatus(selectedBooking, BookingConstants.ACCEPT);
            return true;
        }else if(item.getItemId()== R.id.menu_reject){
            updateBookingStatus(selectedBooking, BookingConstants.REJECT);
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