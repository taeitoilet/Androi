package com.example.myapplication.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.dao.BookingDAO;
import com.example.myapplication.models.Booking;
import com.example.myapplication.services.GV_DatLichListAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class GV_BaseBookingTab extends Fragment {

    protected TextView txt_null;
    protected ListView listView;
    protected EditText searchEditText;
    protected GV_DatLichListAdapter adapter;
    protected BookingDAO bookingDAO;

    public GV_BaseBookingTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookingDAO = new BookingDAO(requireContext()); // Use requireContext() for non-nullable context
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gv_viewbag, container, false);
        initializeViews(view);
        setupListView();
        setupSearchFunctionality();
        checkIfBookingsEmpty();
        return view;
    }

    private void initializeViews(View view) {
        listView = view.findViewById(R.id.ListView);
        searchEditText = view.findViewById(R.id.searchEditText);
        //searchButton = view.findViewById(R.id.searchButton);
        txt_null = view.findViewById(R.id.txt_null);
    }

    public void setupListView() {
        List<Booking> bookings = getBookings();
        adapter = new GV_DatLichListAdapter((GV_DatLichFragment) requireParentFragment(), bookings);  // Pass the parent fragment
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    private void checkIfBookingsEmpty() {
        if (adapter.isFilteredListEmpty()) {
            txt_null.setVisibility(View.VISIBLE);
        } else {
            txt_null.setVisibility(View.GONE);
        }
    }

    private void setupSearchFunctionality() {
        //searchButton.setOnClickListener(v -> adapter.filter(searchEditText.getText().toString()));
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
                checkIfBookingsEmpty();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    protected abstract List<Booking> getBookings();

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(getContextMenuResource(), menu);
    }

    protected abstract int getContextMenuResource();

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Booking selectedBooking = adapter.getItem(info.position);

        return handleContextItemSelected(item, selectedBooking) || super.onContextItemSelected(item);
    }

    protected abstract boolean handleContextItemSelected(MenuItem item, Booking selectedBooking);
    @Override
    public void onResume() {
        super.onResume();
        setupListView();
    }
}
