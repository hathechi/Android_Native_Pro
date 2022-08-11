package com.example.product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.product.DAO.RegisterDAO;
import com.example.product.JavaClass.ListUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public EditText username, password;
    public TextView forgotpass, tvErrorUser, tvErrorPass;
    public CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//Ẩn thanh bar
//        getSupportActionBar().hide();

        username = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        checkBox = findViewById(R.id.cb_login);
        forgotpass = findViewById(R.id.forgotpassword);
        tvErrorPass = findViewById(R.id.tvError_pass);
        tvErrorUser = findViewById(R.id.tvError_user);
        Button signup = findViewById(R.id.btnRegister);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        //Check dữ liệu  ở dưới checkbox rồi setText lên user và pass
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String remember_username = preferences.getString("remember_username", "");
        String remember_password = preferences.getString("remember_password", "");
        Log.i("HTC", remember_username + " " + remember_password);
        if (remember_password != "" && remember_username != "") {
            username.setText(remember_username);
            password.setText(remember_password);
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FancyToast.makeText(MainActivity.this, "isChecked", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                } else {
                    FancyToast.makeText(MainActivity.this, "UnChecked", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }
        });
    }


    public void btnLogin_onclick(View view) {
        TextView error = findViewById(R.id.tvError);

        String user = username.getText().toString();
        String pass = password.getText().toString();

        //tạo Handler làm khoảng delay cho sự kiện
        Handler handler = new Handler();
        if (user.equals("") && pass.equals("")) {
            tvErrorUser.setText("KHÔNG BỎ TRỐNG USER !");
            tvErrorPass.setText("KHÔNG BỎ TRỐNG PASSWORD !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvErrorUser.setText("USER NAME : ");
                    tvErrorPass.setText("PASSWORD : ");
                }
            }, 1500);
            return;
        }
        if (user.equals("")) {
            tvErrorUser.setText("KHÔNG BỎ TRỐNG USER !");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvErrorUser.setText("USER NAME : ");

                }
            }, 1500);
        } else if (pass.equals("")) {
            tvErrorPass.setText("KHÔNG BỎ TRỐNG PASSWORD !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvErrorPass.setText("PASSWORD : ");
                }
            }, 1500);


        } else {
            RegisterDAO registerDAO = new RegisterDAO(this);
            List<ListUser> list = registerDAO.getAll();
            for (ListUser listUser : list) {
                Log.i("HTC", listUser.getUser() + listUser.getPassword() + listUser.getEmail());

                if (user.equalsIgnoreCase(listUser.getUser()) && pass.equalsIgnoreCase(listUser.getPassword())) {

                    //remember login
                    if (checkBox.isChecked()) {
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember_username", listUser.getUser());
                        editor.putString("remember_password", listUser.getPassword());
                        editor.apply();
                        FancyToast.makeText(MainActivity.this, "isChecked", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                    } else {
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("remember_username", "");
                        editor.putString("remember_password", "");
                        editor.apply();
                        FancyToast.makeText(MainActivity.this, "UnChecked", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                    }

                    //Chuyển trang
                    Intent intent1 = new Intent(this, ViewPaper.class);
                    startActivity(intent1);
                   break;
                } else {
                    error.setText("SAI TÀI KHOẢN HOẶC MẬT KHẨU!");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            error.setText("");
                        }
                    }, 1500);
                }
            }

        }
    }
}