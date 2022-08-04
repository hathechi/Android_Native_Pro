package com.example.product.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.product.R;

import org.jetbrains.annotations.NotNull;

public class FragmentTwo extends Fragment {
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_list_san_pham, container, false);

//        try {
//            String user = this.getArguments().getString("username");
//            Log.i("HTC", user +"");
//            TextView editText = view.findViewById(R.id.etFragment2);
//            editText.setText(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return view;
    }
}
