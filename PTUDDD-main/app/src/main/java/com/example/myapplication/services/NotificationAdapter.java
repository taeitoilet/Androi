package com.example.myapplication.services;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.models.Booking;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Booking> notificationList;
    private Context context;

    public NotificationAdapter(Context context, List<Booking> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Booking booking = notificationList.get(position);

        if (BookingConstants.ACCEPT.equals(booking.getStatus())) {
            String message = "Bạn đã đặt lịch khám ngày " + booking.getDate();
            SpannableString spannable = new SpannableString(message);
            int start = message.indexOf("thành công");
            int end = start + "thành công".length();
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.notificationMessage.setText(spannable);
            holder.notificationSubtitle.setText("*Yêu cầu bệnh nhân đến đúng giờ");
        } else if (BookingConstants.REJECT.equals(booking.getStatus())) {
            String message = "Bạn đã đặt lịch thất bại";
            SpannableString spannable = new SpannableString(message);
            int start = message.indexOf("thất bại");
            int end = start + "thất bại".length();
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.red)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.notificationMessage.setText(spannable);
            holder.notificationSubtitle.setText("");
        }

        holder.notificationTime.setText(booking.getTime());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView notificationMessage;
        TextView notificationSubtitle;
        TextView notificationTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationMessage = itemView.findViewById(R.id.notificationMessage);
            notificationSubtitle = itemView.findViewById(R.id.notificationSubtitle);
            notificationTime = itemView.findViewById(R.id.notificationTime);
        }
    }
}
