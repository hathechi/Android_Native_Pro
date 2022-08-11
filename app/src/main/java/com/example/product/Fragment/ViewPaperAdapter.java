package com.example.product.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPaperAdapter extends FragmentStateAdapter {
    public ViewPaperAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentTwo();
            case 1:
                return new FragmentOne();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
