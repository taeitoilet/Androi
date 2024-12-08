package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.R;
import com.example.myapplication.constants.UserConstants;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.models.User;

public class DangkyActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etFullName, etPhone, etEmail;
    AppCompatButton btnRegister, btnBack;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        // Khởi tạo các thành phần UI
        init();

        // Xử lý sự kiện bấm nút Đăng ký
        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String fullName = etFullName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (isValidInput(username, password, fullName, phone, email)) {
                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setFullName(fullName);
                newUser.setPhone(phone);
                newUser.setEmail(email);
                newUser.setRole(UserConstants.ROLE_PATIENT);

                long result = userDAO.addUser(newUser);
                if (result != -1) {
                    Toast.makeText(DangkyActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DangkyActivity.this, "Đã có lỗi xảy ra, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện bấm nút Quay lại
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DangkyActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Khởi tạo các thành phần UI
    public void init() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        userDAO = new UserDAO(this);
    }

    // Kiểm tra tính hợp lệ của dữ liệu
    public boolean isValidInput(String username, String password, String fullName, String phone, String email) {
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải ít nhất 6 ký tự.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.contains("@")) {
            Toast.makeText(this, "Email không hợp lệ.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() < 10 || phone.length() > 11) {
            Toast.makeText(this, "Số điện thoại không hợp lệ.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
