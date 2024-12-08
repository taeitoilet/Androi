package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.constants.UserConstants;
import com.example.myapplication.dao.UserDAO;
import com.example.myapplication.models.User;

public class LoginActivity extends AppCompatActivity {

    UserDAO userDAO;
    EditText editUsername, editPassword;
    TextView txtError;
    AppCompatButton login, btnRegister;  // Thêm btnRegister

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        // Xử lý sự kiện bấm nút Đăng Nhập
        login.setOnClickListener(v -> {
            String reqUsername = editUsername.getText().toString();
            String reqPassword = editPassword.getText().toString();
            try {
                User user = userDAO.getUserByUsername(reqUsername);
                if (user != null && user.getPassword().equals(reqPassword)) {
                    Intent intent;
                    if (user.getRole().equals(UserConstants.ROLE_PATIENT)){
                        intent = new Intent(this, MainActivity.class);
                    } else {
                        intent = new Intent(this, MainActivity_GV.class);
                    }
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    txtError.setText("Sai tên đăng nhập hoặc mật khẩu");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        // Xử lý sự kiện bấm nút Đăng Ký
        btnRegister.setOnClickListener(v -> {
            try {
                // Mở DangKyActivity
                Intent intent = new Intent(LoginActivity.this, DangkyActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace(); // In chi tiết lỗi ra Logcat
                Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getWidth() {
        editUsername = findViewById(R.id.txtUsername);
        editPassword = findViewById(R.id.txtPassword);
        txtError = findViewById(R.id.txtError);
        login = findViewById(R.id.btnSubmit);
        btnRegister = findViewById(R.id.btnRegister);  // Khởi tạo btnRegister
    }

    public void init() {
        getWidth();

        userDAO = new UserDAO(this);
        // Cấu hình thêm user
        User tanh = new User();
        tanh.setUsername("admin");
        tanh.setPassword("123");
        tanh.setFullName("huan hoa hong");
        tanh.setPhone("0865923203");
        tanh.setDoctorId("12345678");
        tanh.setGender(UserConstants.GENDER_FEMALE);
        tanh.setEmail("dtienanh1213@gmail.com");
        tanh.setDateOfBirth("19/09/2003");
        tanh.setAddress("Ha Noi");
        tanh.setPlaceOfBirth("Ha Noi");
        tanh.setRole(UserConstants.ROLE_PATIENT);
        userDAO.addUser(tanh);

    }
}
