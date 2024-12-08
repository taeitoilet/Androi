package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.constants.UserConstants;
import com.example.myapplication.dao.BookingDAO;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.models.Booking;
import com.example.myapplication.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class trangchu_sv extends Fragment {
    private static final String ARG_USER = "user";
    private User user;
    private TextView txtThoiGian,tv1,txtDiaDiem,txtDanhGia;
    private CardView cardView;
    private Button btnDatLich;

    public trangchu_sv() {
        // Required empty public constructor
    }

    public static trangchu_sv newInstance(User user) {
        trangchu_sv fragment = new trangchu_sv();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public void onStart() {
//        checkAndShowRatingDialog();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu_sv, container, false);
        cardView = view.findViewById(R.id.cardView);
        txtThoiGian = view.findViewById(R.id.txtThoiGian);
        tv1=view.findViewById(R.id.tv1);
        txtDiaDiem = view.findViewById(R.id.txtDiaDiem);
        txtDanhGia=view.findViewById(R.id.txtDanhGia);
        getBookingToday();
        setTeacher();
        Wigget();
        return view;
    }

    private void Wigget() {

    }

    private void getBookingToday() {
        try {
            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
            String currentDateString = dateFormat.format(today);


            BookingDAO bookingDAO = new BookingDAO(getContext());
            ArrayList<Booking> bookings = (ArrayList<Booking>) bookingDAO.getBookingsByDateAndUser(currentDateString, user.getId());

            if (bookings == null || bookings.isEmpty()) {
                cardView.setVisibility(View.GONE);
            } else {
                Booking firstBooking = bookings.get(0);
                if (firstBooking != null && firstBooking.getTime() != null) {
                    cardView.setVisibility(View.VISIBLE);
                    txtThoiGian.setText("Thời gian: " + firstBooking.getTime());
                } else {
                    tv1.setText("Trống");
                    txtThoiGian.setText("");
                    txtDiaDiem.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ẩn card view nếu có lỗi xảy ra
        }
    }
    private void setTeacher() {
        try {
            UserDAO userDAO = new UserDAO(getContext());
            ArrayList<User> users = (ArrayList<User>) userDAO.getAllUsers();

            if (users == null || users.isEmpty()) {
                Log.e("setTeacher", "No users found");
                return;
            }

            for (User user : users) {
                if (user.getRole().equals(UserConstants.ROLE_DOCTOR)) {
                    double averageRating = userDAO.calculateAverageRatingForUser1();
                    txtDanhGia.setText(String.valueOf(averageRating));
                    break; // If you only want to display the first teacher found
                }
            }
        } catch (Exception e) {
            Log.e("setTeacher", "Error setting teacher", e);
        }
    }
}
