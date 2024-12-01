package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragments.GV_DatLichFragment;
import com.example.myapplication.fragments.PersonalDetail;
import com.example.myapplication.fragments.trangchu_gv;
import com.example.myapplication.models.User;

public class MainActivity_GV extends AppCompatActivity {

    private ImageView imgTrangChuIcon;
    private ImageView imgCaNhanIcon;
    private ImageView imgDatLichGV;
    private TextView txtTenUser;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gv);
        User user = (User) getIntent().getSerializableExtra("user");
        WIDGET();
        displayTrangChuFragment(user);
        imgTrangChuIcon.setSelected(true);
    }

    private void WIDGET() {
        User user = (User) getIntent().getSerializableExtra("user");
        imgTrangChuIcon = findViewById(R.id.btnTrangChuGv);
        imgDatLichGV = findViewById(R.id.btnDatlichGv);
        imgCaNhanIcon = findViewById(R.id.btnCaNhan);
        txtTenUser = findViewById(R.id.txtTenUsergv);
        btnLogout = findViewById(R.id.btnLogoutgv);
        txtTenUser.setText("Hi, " + user.getFullName());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_GV.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetIconsState();
                v.setSelected(true);
                int id = v.getId();
                if (id == R.id.btnTrangChuGv) {
                    displayTrangChuFragment(user);
                }else if (id == R.id.btnCaNhan) {
                    displayCaNhanFragment(user);
                }else if (id == R.id.btnDatlichGv) {
                    displayDatLichFragment(user);
                }
            }
        };
        imgTrangChuIcon.setOnClickListener(listener);
        imgCaNhanIcon.setOnClickListener(listener);
        imgDatLichGV.setOnClickListener(listener);
    }

    private void displayDatLichFragment(User user) {
        Fragment datLichFragment = GV_DatLichFragment.newInstance(user);
        displayFragment(datLichFragment);
    }

    private void resetIconsState() {
        imgTrangChuIcon.setSelected(false);
        imgCaNhanIcon.setSelected(false);
    }

    private void displayTrangChuFragment(User user) {
        Fragment trangChuFragment = trangchu_gv.newInstance(user);
        displayFragment(trangChuFragment);
    }
    private void displayCaNhanFragment(User user) {
        Fragment caNhanFragment = PersonalDetail.newInstance(user);
        displayFragment(caNhanFragment);
    }

    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.trangchuFragment, fragment);
        transaction.commit();
    }

}