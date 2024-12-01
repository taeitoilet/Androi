package com.example.myapplication.services;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragments.Gv_ChapNhanTab;
import com.example.myapplication.fragments.Gv_DangChoTab;
import com.example.myapplication.fragments.Gv_TuChoiTab;

public class GV_DatLichAdapter extends FragmentStatePagerAdapter {
    public GV_DatLichAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Gv_DangChoTab();
            case 1:
                return new Gv_ChapNhanTab();
            case 2:
                return new Gv_TuChoiTab();
        }
        return null;
    }

}
