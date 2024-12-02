package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.fragments.lichhen;
import com.example.myapplication.models.Booking;
import com.example.myapplication.dao.BookingDAO;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Booking> bookings;
    private BookingDAO bookingDAO; // Assume you have a DAO class for database operations

    // Constructor
    public Adapter(ArrayList<Booking> bookings, BookingDAO bookingDAO) {
        this.bookings = bookings;
        this.bookingDAO = bookingDAO;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiettheongay, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        Booking currentBooking = bookings.get(position);
        holder.time.setText(currentBooking.getTime());
        holder.content.setText(currentBooking.getContent());
        holder.nameGV.setText("Đặng Thị Nhung"); // You can adjust this with real data
        holder.Date.setText(currentBooking.getDate());

        // Set up the Cancel button click listener
        holder.btnCancelBooking.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xác nhận hủy lịch")
                    .setMessage("Bạn có chắc chắn muốn hủy lịch hẹn này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        // Remove from database
                        boolean isDeleted = bookingDAO.deleteBookingByUser(currentBooking.getId());

                        if (isDeleted) {
                            // Remove from RecyclerView
                            bookings.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, bookings.size());
                            Toast.makeText(v.getContext(), "Lịch hẹn đã được hủy", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Xóa lịch hẹn thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", (dialog, which) -> {
                        dialog.dismiss(); // Đóng dialog nếu chọn "Không"
                    })
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private TextView nameGV;
        private TextView content;
        private TextView Date;
        private Button btnCancelBooking; // Add Cancel button

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.txtThoiGian);
            nameGV = itemView.findViewById(R.id.txtTenGv);
            content = itemView.findViewById(R.id.edtNoiDung);
            Date = itemView.findViewById(R.id.txtDate);
            btnCancelBooking = itemView.findViewById(R.id.btnCancelBooking); // Initialize button
        }
    }


}
