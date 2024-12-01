package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.models.Booking;
import com.example.myapplication.models.User;

import java.util.ArrayList;

public class Adapter_gv extends RecyclerView.Adapter<Adapter_gv.MyViewHolder> {
    private ArrayList<Booking> bookings;

    public Adapter_gv(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public Adapter_gv.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiettheongay_gv, parent, false);
        return new MyViewHolder(itemView1); // Change ViewGroup to View
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_gv.MyViewHolder holder, int position) {
        Booking currentEmp = bookings.get(position);
        UserDAO userDAO = new UserDAO(holder.itemView.getContext());
        User user = userDAO.getUserById(currentEmp.getUserId());
        if (user != null) {
            holder.txtNgay.setText(currentEmp.getDate());
            holder.txtTenSv.setText(user.getFullName());
            holder.txtGio.setText(currentEmp.getTime());
            holder.txtMaSV.setText(user.getIdCard());
            holder.txtLopSV.setText("2021DHCNTT01"); // This should ideally come from user data
            holder.edtNoiDungSV.setText(currentEmp.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTenSv;
        private TextView txtGio;
        private TextView txtMaSV;
        private TextView txtLopSV;
        private TextView edtNoiDungSV;
        private TextView txtNgay;

        public MyViewHolder(@NonNull View itemView) { // Change ViewGroup to View
            super(itemView);
            txtTenSv = itemView.findViewById(R.id.txtTenSV);
            txtGio = itemView.findViewById(R.id.txtThoiGian);
            txtMaSV = itemView.findViewById(R.id.txtMaSV);
            txtLopSV = itemView.findViewById(R.id.txtLopSV);
            edtNoiDungSV = itemView.findViewById(R.id.edtNoiDungSV);
            txtNgay= itemView.findViewById(R.id.txtNgay);
        }
    }
}
