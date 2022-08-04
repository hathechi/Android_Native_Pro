package com.example.product;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.product.DAO.RegisterDAO;
import com.example.product.JavaClass.ListUser;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {
    Button btnForotpass, btnResendpassword;
    LinearLayout layout_email, layout_enterpass;
    TextView tv_error_enterpass, tv_error_email;
    EditText etEmail, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        btnForotpass = findViewById(R.id.btnResendpass);

        layout_email = findViewById(R.id.layout_email);

        tv_error_email = findViewById(R.id.tv_error_email);

        etEmail = findViewById(R.id.etEmail);

        RegisterDAO registerDAO = new RegisterDAO(this);
        List<ListUser> list = registerDAO.getAll();
        btnForotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etEmail.getText().toString()).isEmpty()) {
                    tv_error_email.setText("CHƯA NHẬP EMAIL !");
                } else {
                    for (ListUser listUser : list) {
                        if ((etEmail.getText().toString()).equalsIgnoreCase(listUser.getEmail())) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                            LayoutInflater layoutInflater = ForgotPassword.this.getLayoutInflater();
                            //Nhúng layout vào dialog alert
                            View view = layoutInflater.inflate(R.layout.layout_dialog_resetpass, null);
                            builder.setView(view);
                            builder.show();
                            //ánh xạ
                            btnResendpassword = view.findViewById(R.id.btnResendpassword);
                            etPass = view.findViewById(R.id.etPass);

                            btnResendpassword.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String pass = etPass.getText().toString();
                                    if (pass.isEmpty()) {
                                        tv_error_enterpass.setText("CHƯA NHẬP PASSWORD !");
                                    } else {
                                        ProgressDialog progressDialog = ProgressDialog.show(ForgotPassword.this, "Chờ Chút", "Xong ngay đây !!", true);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                            }
                                        }, 700);
                                        ListUser product = new ListUser();
                                        String id = String.valueOf(listUser.getId());
                                        Log.i("HTC", "onClick: " + id);
                                        product.setPassword(pass);
                                        product.setUser(list.get(Integer.parseInt(id) - 1).getUser());
                                        product.setEmail(list.get(Integer.parseInt(id) - 1).getEmail());
                                        registerDAO.updateRegister(product, id);

                                        RegisterDAO registerDAO = new RegisterDAO(ForgotPassword.this);
                                        List<ListUser> list = registerDAO.getAll();
                                        for (ListUser listUser1 : list) {
                                            Log.i("HTC", listUser1.getId() + listUser1.getUser() + listUser1.getPassword() + listUser1.getEmail());
                                        }

                                    }
                                }
                            });
                            return;
                        } else {
                            tv_error_email.setText("EMAIL KHÔNG ĐÚNG !");
                        }
                    }
                }
            }
        });


    }
}