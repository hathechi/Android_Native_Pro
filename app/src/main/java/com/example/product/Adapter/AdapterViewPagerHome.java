package com.example.product.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.product.Fragment.FragmentGioHang;
import com.example.product.Fragment.FragmentHome;

import org.jetbrains.annotations.NotNull;

public class AdapterViewPagerHome extends FragmentStateAdapter {
    public AdapterViewPagerHome(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentGioHang();
            default:
                return new FragmentHome();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
