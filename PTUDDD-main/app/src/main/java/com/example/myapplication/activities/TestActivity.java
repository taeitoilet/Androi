package com.example.myapplication.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.models.User;

public class TestActivity extends AppCompatActivity {

    TextView txtFullname,txtStudentIdCard,txtBirth,txtGender,txtPlace,txtIdCard,txtSDT,txtEmail,txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWidth();
        initUser();
    }
    public void getWidth(){
        txtFullname=findViewById(R.id.txtFullname);
        txtStudentIdCard=findViewById(R.id.txtStudentIdCard);
        txtBirth=findViewById(R.id.txtBirth);
        txtGender=findViewById(R.id.txtGender);
        txtPlace=findViewById(R.id.txtPlace);
        txtIdCard=findViewById(R.id.txtIdCard);
        txtSDT=findViewById(R.id.txtSDT);
        txtEmail=findViewById(R.id.txtEmail);
        txtAddress=findViewById(R.id.txtAddress);
    }
    public void initUser(){
        User user=(User) getIntent().getSerializableExtra("user");
        txtFullname.setText(user.getFullName());
        txtStudentIdCard.setText(user.getStudentCode());
        txtBirth.setText(user.getDateOfBirth());
        txtGender.setText(user.getGender());
        txtPlace.setText(user.getPlaceOfBirth());
        txtIdCard.setText(user.getIdCard());
        txtSDT.setText(user.getPhone());
        txtEmail.setText(user.getEmail());
        txtAddress.setText(user.getAddress());
    }
}