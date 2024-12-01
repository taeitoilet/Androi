package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Booking;

import java.text.BreakIterator;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Booking> bookings;
    public Adapter(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }
    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiettheongay, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        Booking currentEmp = bookings.get(position);
        holder.time.setText(currentEmp.getTime());
        holder.content.setText(currentEmp.getContent());
        holder.nameGV.setText("Đặng Thị Nhung");
        holder.Date.setText(currentEmp.getDate());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView time;
        private TextView nameGV;
        private TextView content;
        private TextView Date;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.txtThoiGian);
            nameGV = itemView.findViewById(R.id.txtTenGv);
            content = itemView.findViewById(R.id.edtNoiDung);
            Date = itemView.findViewById(R.id.txtDate);
        }
    }
}
