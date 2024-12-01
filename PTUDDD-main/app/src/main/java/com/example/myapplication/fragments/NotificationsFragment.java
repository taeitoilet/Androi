package com.example.myapplication.fragments;

import android.app.Notification;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.dao.BookingDAO;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.models.Booking;
import com.example.myapplication.models.User;
import com.example.myapplication.services.NotificationAdapter;
//import com.example.myapplication.services.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerViewNotifications;
    private NotificationAdapter notificationAdapter;

    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    private User user;
    private List<Booking> notificationList;

    private static final String ARG_USER = "user";
    public NotificationsFragment() {
        // Required empty public constructor

    }
    public void Notification(String type, String message, String subtitle, String time) {
        // Initialization code
    }
    public static NotificationsFragment newInstance(User user) {
        NotificationsFragment fragment = new NotificationsFragment();
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
        bookingDAO = new BookingDAO(getContext());

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerViewNotifications = view.findViewById(R.id.recyclerViewNotifications);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list of notifications
        notificationList = new ArrayList<>();

        // Populate the notification list with ACCEPT bookings
        populateBookings();


        // Set the adapter
        notificationAdapter = new NotificationAdapter(getContext(), notificationList);
        recyclerViewNotifications.setAdapter(notificationAdapter);

        return view;
    }

    private void populateBookings() {
        List<Booking> acceptBookings = bookingDAO.getAllStatusBookings(BookingConstants.ACCEPT);
        List<Booking> rejectBookings = bookingDAO.getAllStatusBookings(BookingConstants.REJECT);

        notificationList.addAll(acceptBookings);
        notificationList.addAll(rejectBookings);

        // Notify adapter about data changes
        if (notificationAdapter != null) {
            notificationAdapter.notifyDataSetChanged();
        }
    }

}
