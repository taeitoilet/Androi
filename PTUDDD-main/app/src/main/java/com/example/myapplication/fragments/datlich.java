package com.example.myapplication.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.dao.BookingDAO;
import com.example.myapplication.models.Booking;
import com.example.myapplication.models.User;

import java.util.Calendar;
import java.util.List;

public class datlich extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText editTextDate;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private String mParam1;
    private String mParam2;
    private Button selectedButton;
    private Button button0800;
    private Button button0830;
    private Button button0900;
    private Button button0930;
    private Button button1000;
    private Button button1030;
    private Button button1100;
    private Button button1400;
    private Button button1430;
    private Button button1500;
    private Button button1530;
    private Button button1600;
    private Button buttonAdd;
    private EditText edtLyDo;
    private static final String ARG_USER = "user";
    private User user;

    public datlich() {
        // Required empty public constructor
    }

    public static datlich newInstance(User user) {
        datlich fragment = new datlich();
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
//        editTextDate.setText();
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_datlich, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonAdd = view.findViewById(R.id.btnDatlichGv);
        buttonAdd.setOnClickListener(v -> addLich());
        calendar = Calendar.getInstance();
        String today = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);


        
        editTextDate = view.findViewById(R.id.date_picker);
        editTextDate.setHint(today);
        editTextDate.setOnClickListener(v -> showDatePickerDialog());
        updateButton(view);
    }

    private void onButtonClicked(Button button) {
        if (button.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.button_background_unavailable).getConstantState())) {
            // Nếu button không khả dụng, không làm gì cả
            return;
        }

        if (selectedButton != null) {
            selectedButton.setBackgroundResource(R.drawable.rounded_button);
        }

        button.setBackgroundResource(R.drawable.button_background_selected);
        selectedButton = button;
    }

    private void updateButton(View view) {
        button0800 = view.findViewById(R.id.button0800);
        button0830 = view.findViewById(R.id.button0830);
        button0900 = view.findViewById(R.id.button0900);
        button0930 = view.findViewById(R.id.button0930);
        button1000 = view.findViewById(R.id.button1000);
        button1030 = view.findViewById(R.id.button1030);
        button1100 = view.findViewById(R.id.button1100);
        button1400 = view.findViewById(R.id.button1400);
        button1430 = view.findViewById(R.id.button1430);
        button1500 = view.findViewById(R.id.button1500);
        button1530 = view.findViewById(R.id.button1530);
        button1600 = view.findViewById(R.id.button1600);

        button0800.setOnClickListener(v -> onButtonClicked(button0800));
        button0830.setOnClickListener(v -> onButtonClicked(button0830));
        button0900.setOnClickListener(v -> onButtonClicked(button0900));
        button0930.setOnClickListener(v -> onButtonClicked(button0930));
        button1000.setOnClickListener(v -> onButtonClicked(button1000));
        button1030.setOnClickListener(v -> onButtonClicked(button1030));
        button1100.setOnClickListener(v -> onButtonClicked(button1100));
        button1400.setOnClickListener(v -> onButtonClicked(button1400));
        button1430.setOnClickListener(v -> onButtonClicked(button1430));
        button1500.setOnClickListener(v -> onButtonClicked(button1500));
        button1530.setOnClickListener(v -> onButtonClicked(button1530));
        button1600.setOnClickListener(v -> onButtonClicked(button1600));
    }

    private void updateButtonState(Button button, boolean isAvailable) {
        if (isAvailable) {
            button.setBackgroundResource(R.drawable.rounded_button);
            button.setEnabled(true);
        } else {
            button.setBackgroundResource(R.drawable.button_background_unavailable);
            button.setEnabled(false);
        }
    }

    private void showDatePickerDialog() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editTextDate.setText(selectedDate);
                    updateAvailableTimes(selectedDate); // Cập nhật trạng thái nút thời gian
                }, year, month, day);

        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }


    private void updateAvailableTimes(String date) {
        BookingDAO bookingDAO = new BookingDAO(getContext());
        List<String> bookedTimes = bookingDAO.getTimeByDate(date);

        updateButtonState(button0800, !bookedTimes.contains("08:00"));
        updateButtonState(button0830, !bookedTimes.contains("08:30"));
        updateButtonState(button0900, !bookedTimes.contains("09:00"));
        updateButtonState(button0930, !bookedTimes.contains("09:30"));
        updateButtonState(button1000, !bookedTimes.contains("10:00"));
        updateButtonState(button1030, !bookedTimes.contains("10:30"));
        updateButtonState(button1100, !bookedTimes.contains("11:00"));
        updateButtonState(button1400, !bookedTimes.contains("14:00"));
        updateButtonState(button1430, !bookedTimes.contains("14:30"));
        updateButtonState(button1500, !bookedTimes.contains("15:00"));
        updateButtonState(button1530, !bookedTimes.contains("15:30"));
        updateButtonState(button1600, !bookedTimes.contains("16:00"));
    }

    private void addLich() {
        if (editTextDate.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedButton == null) {
            Toast.makeText(getContext(), "Vui lòng chọn thời gian", Toast.LENGTH_SHORT).show();
            return;
        }

        // Truy cập EditText edtLyDo từ view của Fragment
        edtLyDo = getView().findViewById(R.id.edtLyDo);

        String date = editTextDate.getText().toString();
        String time = selectedButton.getText().toString();

        Booking booking = new Booking();
        booking.setUserId(user.getId());
        booking.setDate(date);
        booking.setTime(time);
        booking.setContent(edtLyDo.getText().toString());
        booking.setStatus(BookingConstants.PENDING);
        BookingDAO bookingDAO = new BookingDAO(datlich.this.getContext());
        bookingDAO.addBooking(booking);
        Toast.makeText(this.getContext(), "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
        editTextDate.setText("");
        edtLyDo.setText("");
        updateAvailableTimes(date);
    }

}
