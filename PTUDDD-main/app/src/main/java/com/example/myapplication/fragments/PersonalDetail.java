package com.example.myapplication.fragments;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.constants.UserConstants;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.models.User;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.DatePickerDialog;
import java.util.Calendar;
import java.util.Locale;

public class PersonalDetail extends Fragment {

    public static final int requestcode = 1;
    private static final String ARG_USER = "user";
    private User user;

    private TextView txtFullname, txtStudentIdCard, txtBirth, txtGender, txtPlace, txtIdCard, txtSDT, txtEmail, txtAddress,txtRating;
    private ImageView imgStar;

    private AppCompatButton btnEdit;

    private AppCompatButton btnImportData;
    public PersonalDetail() {
        // Required empty public constructor
    }

    public static PersonalDetail newInstance(User user) {
        PersonalDetail fragment = new PersonalDetail();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_detail, container, false);
        initViews(view);
        initUser();

        //Manh
        btnEdit = view.findViewById(R.id.btnEdit);
        if (user != null && user.getRole().equals(UserConstants.ROLE_PATIENT)) {
            btnEdit.setVisibility(View.VISIBLE);
            btnEdit.setOnClickListener(v -> showEditDialog());
        } else {
            btnEdit.setVisibility(View.GONE);
        }
        //Manh

        btnImportData.setOnClickListener(v -> {
            Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
            fileintent.setType("text/csv");
            try {
                startActivityForResult(fileintent, requestcode);
            } catch (ActivityNotFoundException e) {
                Log.e("error", "onCreateView: \"No activity can handle picking a file. Showing alternatives. " );
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data==null){
            return;
        }
        Uri uri=data.getData();
        try {
            InputStream inputStream = this.getContext().getContentResolver().openInputStream(uri);
            readCsv(inputStream);
        } catch (IOException e) {
            Log.e("error", "Error reading CSV file", e);
        }
    }
    private void readCsv(InputStream inputStream) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] nextLine;
            UserDAO userDAO = new UserDAO(getContext());
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                // Assuming the CSV has the columns in the same order as the User model
                User user = new User();
                user.setUsername(nextLine[0]);
                user.setPassword(nextLine[1]);
                user.setFullName(nextLine[2]);
                user.setGender(nextLine[3]);
                user.setAddress(nextLine[4]);
                user.setPlaceOfBirth(nextLine[5]);
                user.setDateOfBirth(nextLine[6]);
                user.setEmail(nextLine[8]);
                user.setPhone(nextLine[9]);
                user.setRole(nextLine[10]);
                user.setPatientCode(nextLine[11]);
                user.setDoctorId(nextLine[12]);

                userDAO.addUser(user);
                Log.i("ìnor", "readCsv: "+nextLine[0]);
            }
            Toast.makeText(getContext(), "Thêm Danh sách bệnh nhân thành công!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("PersonalDetail", "Error reading CSV file", e);
        }
    }

    private void initViews(View view) {
        txtFullname = view.findViewById(R.id.txtFullname);
        txtStudentIdCard = view.findViewById(R.id.txtStudentIdCard);
        txtBirth = view.findViewById(R.id.txtBirth);
        txtGender = view.findViewById(R.id.txtGender);
        txtPlace = view.findViewById(R.id.txtPlace);
        txtIdCard = view.findViewById(R.id.txtIdCard);
        txtSDT = view.findViewById(R.id.txtSDT);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtRating=view.findViewById(R.id.txtRating);
        imgStar=view.findViewById(R.id.imgStar);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnImportData=view.findViewById(R.id.btnImportData);
    }


    private void initUser() {
        if (user != null) {
            txtFullname.setText(user.getFullName());
            txtStudentIdCard.setText(user.getPatientCode());
            txtBirth.setText(user.getDateOfBirth());
            txtGender.setText(user.getGender());
            txtPlace.setText(user.getPlaceOfBirth());
            txtSDT.setText(user.getPhone());
            txtEmail.setText(user.getEmail());
            txtAddress.setText(user.getAddress());
            if(user.getRole().equals(UserConstants.ROLE_PATIENT)){
                txtRating.setText("");
                imgStar.setVisibility(View.INVISIBLE);
                btnImportData.setVisibility(View.INVISIBLE);
            }
            else{
                UserDAO userDAO=new UserDAO(this.getContext());
                txtRating.setText(userDAO.calculateAverageRatingForUser()+"");
                imgStar.setVisibility(View.VISIBLE);
            }
        }
    }

    //Manh
    private boolean isValidPhoneNumber(String phone){
        return phone != null && phone.matches("^0[0-9]{9}$");
    }
    private boolean isValidEmail(String email){
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void showEditDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_edit_patient);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    // Ánh xạ các view trong dialog
        EditText edtFullName = dialog.findViewById(R.id.edtFullName);
        EditText edtDateOfBirth = dialog.findViewById(R.id.edtDateOfBirth);
        RadioGroup radioGroupGender = dialog.findViewById(R.id.radioGroupGender);
        RadioButton radioMale = dialog.findViewById(R.id.radioMale);      // Thêm dòng này
        RadioButton radioFemale = dialog.findViewById(R.id.radioFemale);
        EditText edtPlaceOfBirth = dialog.findViewById(R.id.edtPlaceOfBirth);
        EditText edtPhone = dialog.findViewById(R.id.edtPhone);
        EditText edtEmail = dialog.findViewById(R.id.edtEmail);
        EditText edtAddress = dialog.findViewById(R.id.edtAddress);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

    // Set dữ liệu hiện tại vào các trường
        edtFullName.setText(user.getFullName());
        edtDateOfBirth.setText(user.getDateOfBirth());
        if ("Nam".equals(user.getGender())) {
            radioMale.setChecked(true);
        } else if ("Nữ".equals(user.getGender())) {
            radioFemale.setChecked(true);
        }
        edtPlaceOfBirth.setText(user.getPlaceOfBirth());
        edtPhone.setText(user.getPhone());
        edtEmail.setText(user.getEmail());
        edtAddress.setText(user.getAddress());

        edtDateOfBirth.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year, month, dayOfMonth) -> {
                        String date = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                        edtDateOfBirth.setText(date);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

    // Xử lý sự kiện nút Hủy
        btnCancel.setOnClickListener(v -> dialog.dismiss());

    // Xử lý sự kiện nút Cập nhật
        btnUpdate.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            boolean isValid = true;

            if(!isValidPhoneNumber(phone)){
                edtPhone.setError("Số điện thoại không hợp lệ");
                isValid=false;
            }else{
                edtPhone.setError(null);
            }

            if(!isValidEmail(email)){
                edtEmail.setError("Email không hợp lệ");
                isValid=false;
            }else{
                edtEmail.setError(null);
            }

            if(isValid){
                user.setFullName(edtFullName.getText().toString());
                user.setDateOfBirth(edtDateOfBirth.getText().toString());
                user.setGender(radioMale.isChecked() ? "Nam" : "Nữ");
                user.setPlaceOfBirth(edtPlaceOfBirth.getText().toString());
                user.setPhone(phone);
                user.setEmail(email);
                user.setAddress(edtAddress.getText().toString());

        // Cập nhật vào database
                UserDAO userDAO = new UserDAO(getContext());
                boolean success = userDAO.updateUser(user);

                if (success) {
                // Cập nhật UI
                    initUser();
                    Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                } 
            }
        // Cập nhật thông tin user

        });

        dialog.show();
    }
    //Manh

    //Manh
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ... (Các dòng code khác)

        if (user.getRole().equals(UserConstants.ROLE_PATIENT)) {
            // ... (Các dòng code khác)
            btnEdit.setVisibility(View.VISIBLE); // Hiển thị nút Edit cho bệnh nhân
            btnImportData.setVisibility(View.GONE); // Ẩn nút Import cho bệnh nhân
        } else {
            // ... (Các dòng code khác)
            btnEdit.setVisibility(View.GONE); // Ẩn nút Edit cho bác sĩ
            btnImportData.setVisibility(View.VISIBLE); // Hiển thị nút Import cho bác sĩ
        }
    }
    //Manh

    // ... (Các phương thức khác)
}

