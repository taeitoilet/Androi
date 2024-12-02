package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;

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
    AppCompatButton login;

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
        login.setOnClickListener(v -> {
            String reqUsername=editUsername.getText().toString();
            String reqPassword=editPassword.getText().toString();
            try{
                User user=userDAO.getUserByUsername(reqUsername);
                if(user!=null&&user.getPassword().equals(reqPassword)){
                    Intent intent;
                    if (user.getRole().equals(UserConstants.ROLE_PATIENT)){
                        intent = new Intent(this, MainActivity.class);
                    } else {
                        intent = new Intent(this, MainActivity_GV.class);
                    }
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else {
                    txtError.setText("Sai tên đăng nhập hoặc mặt khẩu");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        });
    }
    public void getWidth(){
        editUsername=findViewById(R.id.txtUsername);
        editPassword=findViewById(R.id.txtPassword);
        txtError=findViewById(R.id.txtError);
        login=findViewById(R.id.btnSubmit);
    }
    public void init(){
        getWidth();
        userDAO=new UserDAO(this);
        // Chay 1 lan xong comment lai
//        User tanh=new User();
//        tanh.setUsername("admin");
//        tanh.setPassword("123");
//        tanh.setFullName("huan hoa hong");
//        tanh.setPhone("0865923203");
//        tanh.setDoctorId("12345678");
//        tanh.setGender(UserConstants.GENDER_FEMALE);
//        tanh.setEmail("dtienanh1213@gmail.com");
//        tanh.setDateOfBirth("19/09/2003");
//        tanh.setAddress("Ha Noi");
//        tanh.setPlaceOfBirth("Ha Noi");
//        tanh.setRole(UserConstants.ROLE_DOCTOR);
//        userDAO.addUser(tanh);

    }
}