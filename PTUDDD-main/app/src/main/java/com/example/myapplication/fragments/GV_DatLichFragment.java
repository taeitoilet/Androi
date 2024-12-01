package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.models.User;
import com.example.myapplication.services.GV_DatLichAdapter;
import com.google.android.material.tabs.TabLayout;

public class GV_DatLichFragment extends Fragment {
    User user;
    GV_DatLichAdapter adapter;
    private static final String ARG_USER = "user";

    public static GV_DatLichFragment newInstance(User user) {
        GV_DatLichFragment fragment = new GV_DatLichFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gv_datlich, container, false);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        adapter = new GV_DatLichAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        // Set up viewpager with tablayout
        tabLayout.setupWithViewPager(viewPager);
        setColor(tabLayout, 0, "Đang chờ", true);
        setColor(tabLayout, 1, "Chấp nhận", false);
        setColor(tabLayout, 2, "Từ chối", false);

        TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                setColor(tabLayout, position, tab.getText().toString(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setColor(tabLayout, tab.getPosition(), tab.getText().toString(), false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        };

        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        return view;
    }
    public int getCurrentTabPosition() {
        TabLayout tabLayout = getView().findViewById(R.id.tabLayout);
        return tabLayout.getSelectedTabPosition();
    }
    public void setColor(TabLayout tabLayout, int position, String text, boolean isActive) {
        SpannableString spannableString = new SpannableString(text);
        if (isActive) {
            switch (position) {
                case 0:
                    spannableString.setSpan(new ForegroundColorSpan(Color.rgb(155, 135, 12)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                case 1:
                    spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                case 2:
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                default:
                    break;
            }
        } else {
            spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tabLayout.getTabAt(position).setText(spannableString);
    }
}
