package com.example.myapplication.services;

import com.example.myapplication.R;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.constants.BookingConstants;
import com.example.myapplication.dao.BookingDAO;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.fragments.GV_DatLichFragment;
import com.example.myapplication.models.Booking;
import com.example.myapplication.models.User;

import java.util.ArrayList;
import java.util.List;

public class GV_DatLichListAdapter extends ArrayAdapter<Booking> {
    TextView tvNumber;
    TextView tvName;
    Button btnView;
    private List<Booking> originalBooks;
    private List<Booking> filteredBooks;
    private BookingDAO bookingDAO;
    private GV_DatLichFragment fragment;

    public GV_DatLichListAdapter(GV_DatLichFragment fragment, List<Booking> bookings) {
        super(fragment.getContext(), 0, bookings);
        this.fragment = fragment;
        this.originalBooks = new ArrayList<>(bookings);
        this.filteredBooks = new ArrayList<>(bookings);
        this.bookingDAO = new BookingDAO(fragment.getContext());
    }
    @Override
    public int getCount() {
        return filteredBooks.size();
    }

    @Override
    public Booking getItem(int position) {
        return filteredBooks.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Booking booking = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_gv_datlich_custom_item_view, parent, false);
        }
        getWidth(convertView);

        tvNumber.setText(String.valueOf(position + 1));
        UserDAO userDAO = new UserDAO(getContext());
        User user = userDAO.getUserById(booking.getUserId());
        tvName.setText(user.getFullName());

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTab = fragment.getCurrentTabPosition();

                switch (currentTab) {
                    case 0:
                        openDialog(R.layout.layout_dialog_xemdangcho, booking);
                        break;
                    case 1:
                        openDialog(R.layout.layout_dialog_xemchapnhan, booking);
                        break;
                    case 2:
                        openDialog(R.layout.layout_dialog_khoiphuc, booking);
                        break;
                    default:
                        break;
                }
            }
        });

        return convertView;
    }
    private void openDialog(int layoutResId, Booking booking) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutResId);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);

        // Initialize common dialog views
        TextView txtStudentName = dialog.findViewById(R.id.txtStudenName);
        TextView txtStudentId = dialog.findViewById(R.id.txtStudentId);
        TextView txtClass = dialog.findViewById(R.id.txtClass);
        TextView txtLocation = dialog.findViewById(R.id.txtLocation);
        TextView txtTime = dialog.findViewById(R.id.txtTime);
        TextView txtReason = dialog.findViewById(R.id.txtReason);
        TextView txtSubmissionDate = dialog.findViewById(R.id.txtSubmissionDate);

        // Set the booking details in the dialog
        UserDAO userDAO = new UserDAO(getContext());
        User user = userDAO.getUserById(booking.getUserId());
        txtStudentName.setText(user.getFullName());
        txtClass.setText("2021DHCNTT01");  // Placeholder class, replace if needed
        txtLocation.setText("Phòng 601 - A1");
        txtTime.setText(booking.getTime());
        txtReason.setText(booking.getContent());
        txtSubmissionDate.setText(booking.getDate());

        // Initialize specific dialog views and actions
        if (layoutResId == R.layout.layout_dialog_xemdangcho) {
            Button btnAccept = dialog.findViewById(R.id.btnAccept);
            Button btnReject = dialog.findViewById(R.id.btnReject);
            btnAccept.setOnClickListener(v -> {
                // Update status to ACCEPT
                booking.setStatus(BookingConstants.ACCEPT);
                bookingDAO.updateBooking(booking);
                filteredBooks.remove(booking);
                notifyDataSetChanged();
                dialog.dismiss();
            });
            btnReject.setOnClickListener(v -> {
                // Update status to REJECT
                booking.setStatus(BookingConstants.REJECT);
                bookingDAO.updateBooking(booking);
                filteredBooks.remove(booking);
                notifyDataSetChanged();
                dialog.dismiss();
            });
        } else if (layoutResId == R.layout.layout_dialog_xemchapnhan) {
            Button btnComplete = dialog.findViewById(R.id.btnComplete);
            Button btnFail = dialog.findViewById(R.id.btnFail);
            btnComplete.setOnClickListener(v -> {
                // Handle complete action
                booking.setStatus(BookingConstants.SUCCESS);
                bookingDAO.updateBooking(booking);
                filteredBooks.remove(booking);
                notifyDataSetChanged();
                dialog.dismiss();
            });
            btnFail.setOnClickListener(v -> {
                // Handle fail action
                booking.setStatus(BookingConstants.FAIL);
                bookingDAO.deleteBooking(booking.getId()); // Remove from tab if failed
                filteredBooks.remove(booking);
                notifyDataSetChanged();
                dialog.dismiss();
            });
        } else if (layoutResId == R.layout.layout_dialog_khoiphuc) {
            Button btnRestore = dialog.findViewById(R.id.btnRestore);
            btnRestore.setOnClickListener(v -> {
                // Update status to PENDING
                booking.setStatus(BookingConstants.PENDING);
                bookingDAO.updateBooking(booking);
                filteredBooks.remove(booking);
                notifyDataSetChanged();
                dialog.dismiss();
            });
        }

        dialog.show();
    }
    private void getWidth(View convertView){
        tvNumber = convertView.findViewById(R.id.tvNumber);
        //ivIcon = convertView.findViewById(R.id.ivIcon);  // for User icon
        tvName = convertView.findViewById(R.id.tvName);
        btnView = convertView.findViewById(R.id.btnXem);
    }

    public void filter(String text) {
        filteredBooks.clear();
        if (text.isEmpty()) {
            filteredBooks.addAll(originalBooks);
        } else {
            if(tryParseInt(text)){
                int ID = Integer.parseInt(text);
                for (Booking booking : originalBooks) {
                    if (booking.getUserId() == (ID)) {
                        filteredBooks.add(booking);
                    }
                }
            }else{
                Toast.makeText(getContext(), "Nhập mã bệnh nhân là số ", Toast.LENGTH_SHORT).show();
            }
        }
        notifyDataSetChanged();
    }

    public boolean isFilteredListEmpty() {
        return filteredBooks.isEmpty();
    }

    private boolean tryParseInt(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}