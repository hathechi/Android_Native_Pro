package com.example.product.Onboarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OnboardingFragment1();
            case 1:
                return new OnboardingFragment2();
            case 2:
                return new OnboardingFragment3();
            default:
                return new OnboardingFragment1();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
