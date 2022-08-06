package com.example.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.product.Fragment.ViewPaperAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class ViewPaper extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);
        viewPager2 =findViewById(R.id.viewpaper);
        tabLayout = findViewById(R.id.tablayout);
        ViewPaperAdapter viewPaperAdapter= new ViewPaperAdapter(ViewPaper.this);
        viewPager2.setAdapter(viewPaperAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                String title[]={"Danh Sách 1","Danh Sách 2"};
                tab.setText(title[position]);
            }
        }).attach();
    }
}