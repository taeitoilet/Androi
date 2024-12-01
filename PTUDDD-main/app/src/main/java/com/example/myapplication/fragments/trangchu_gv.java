package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.myapplication.Adapter.Adapter_gv;
import com.example.myapplication.R;
import com.example.myapplication.dao.BookingDAO;
import com.example.myapplication.models.Booking;
import com.example.myapplication.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class trangchu_gv extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_USER = "user";
    private User user;
    private RecyclerView recyclerView;
    private Adapter_gv itemAdapter;
    private TextView txtThang,txtTongSo;
    private String mParam1;
    private String mParam2;

    public trangchu_gv() {
        // Required empty public constructor
    }
    public static trangchu_gv newInstance(User user) {
        trangchu_gv fragment = new trangchu_gv();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu_gv, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.recyclerViewGV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar selectedDate = eventDay.getCalendar();
            String formattedDate = getFormattedDate(selectedDate);

            // Lấy danh sách booking theo ngày và userId
            BookingDAO bookingDAO = new BookingDAO(getContext());
            ArrayList<Booking> bookings = (ArrayList<Booking>) bookingDAO.getBookingsByDate(formattedDate);

            // Cập nhật adapter với danh sách mới
            itemAdapter = new Adapter_gv(bookings);
            recyclerView.setAdapter(itemAdapter);
        });

        BookingDAO bookingDAO = new BookingDAO(getContext());
        ArrayList<String> bookings = (ArrayList<String>) bookingDAO.getDistinctBookingDatesFromMonth2();
        ArrayList<String> bookings1 = (ArrayList<String>) bookingDAO.getDistinctBookingDatesFromMonth3();
        txtTongSo=view.findViewById(R.id.txtTongSo);
        txtTongSo.setText("Tổng số lịch hẹn: " + bookings1.size());
        txtThang=view.findViewById(R.id.txtThang);

        ArrayList<EventDay> events = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar today = Calendar.getInstance();
        txtThang.setText("Tháng: " + (today.get(Calendar.MONTH) + 1));
        String todayStr = sdf.format(today.getTime());

        for (String bookingDate : bookings) {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(bookingDate));

                // Định dạng ngày đặt chỗ
                String bookingDateStr = sdf.format(calendar.getTime());

                // So sánh ngày đặt chỗ với ngày hiện tại
                if (bookingDateStr.equals(todayStr)) {
                    // Ngày đã qua
                    events.add(new EventDay(calendar, R.drawable.ic_chuthich_red));
                } else if (calendar.before(today)) {
                    // Ngày hôm nay
                    events.add(new EventDay(calendar, R.drawable.ic_chuthich_gray));
                } else {
                    // Ngày chưa đến
                    events.add(new EventDay(calendar, R.drawable.ic_chuthich_yellow));
                }

                Log.d("EventDate", "EventDate: " + sdf.format(calendar.getTime())); // Log ngày được thêm vào sự kiện
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        calendarView.setEvents(events);

        return view;
    }

    private String getFormattedDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String formattedMonth = (month < 10) ? "" +  month : String.valueOf(month);
        String formattedDay = (day < 10) ? "" + day : String.valueOf(day);

        return formattedDay + "/" + formattedMonth + "/" + year;
    }
}