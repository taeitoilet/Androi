package com.example.myapplication.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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


public class PersonalDetail extends Fragment {

    public static final int requestcode = 1;
    private static final String ARG_USER = "user";
    private User user;

    private TextView txtFullname, txtStudentIdCard, txtBirth, txtGender, txtPlace, txtIdCard, txtSDT, txtEmail, txtAddress,txtRating;
    private ImageView imgStar;



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
                user.setIdCard(nextLine[7]);
                user.setEmail(nextLine[8]);
                user.setPhone(nextLine[9]);
                user.setRole(nextLine[10]);
                user.setStudentCode(nextLine[11]);
                user.setTeacherId(nextLine[12]);

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
        btnImportData=view.findViewById(R.id.btnImportData);
    }


    private void initUser() {
        if (user != null) {
            txtFullname.setText(user.getFullName());
            txtStudentIdCard.setText(user.getStudentCode());
            txtBirth.setText(user.getDateOfBirth());
            txtGender.setText(user.getGender());
            txtPlace.setText(user.getPlaceOfBirth());
            txtIdCard.setText(user.getIdCard());
            txtSDT.setText(user.getPhone());
            txtEmail.setText(user.getEmail());
            txtAddress.setText(user.getAddress());
            if(user.getRole().equals(UserConstants.ROLE_STUDENT)){
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
}
