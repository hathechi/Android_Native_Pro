package com.example.product;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.product.SendEmail.SendMailService;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {
    Button btnForotpass, btnResendpassword;
    LinearLayout layout_email;
    TextView tv_error_enterpass, tv_error_email;
    EditText etEmail, etPass;
    String pass;

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
                        if ((etEmail.getText().toString().trim()).equalsIgnoreCase(listUser.getEmail())) {
                            FancyToast.makeText(ForgotPassword.this, "Email Hợp Lệ",
                                    FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            //set thời gian 10s sau 1 lần nhấn để tránh spqm
                            btnForotpass.setEnabled(false);
                            FancyToast.makeText(ForgotPassword.this, "Bạn chỉ có thể gửi code sau mỗi 15 giây !",
                                    FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btnForotpass.setEnabled(true);
                                }
                            }, 15000);


                            String input_email = etEmail.getText().toString().trim();
                            //gửi email code để xác nhận và lấy ra được số code random
                            int code = sendEmail(input_email);
                            Log.i("a", "sendEmail: " + code);

                            //Mở dialog comfirm code
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                            LayoutInflater layoutInflater = ForgotPassword.this.getLayoutInflater();
                            //Nhúng layout vào dialog alert
                            View view = layoutInflater.inflate(R.layout.layout_dialog_comfirmcode, null);
                            builder.setView(view);
                            builder.show();

                            //ánh xạ
                            Button btnComfirmCode = view.findViewById(R.id.btnComfirmCode);
                            EditText etCode = view.findViewById(R.id.etCode);

                            //Ấn nút Comfirm code
                            btnComfirmCode.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.i("B", "onClick: " + (etCode.getText().toString().trim()) + code);
                                    String input_code = etCode.getText().toString().trim();
                                    if (input_code.equals(String.valueOf(code))) {

                                        FancyToast.makeText(ForgotPassword.this, "CODE Hợp Lệ",
                                                FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgotPassword.this);
                                        LayoutInflater layoutInflater1 = ForgotPassword.this.getLayoutInflater();
                                        //Nhúng layout vào dialog alert
                                        View view1 = layoutInflater1.inflate(R.layout.layout_dialog_resetpass, null);
                                        builder1.setView(view1);
                                        builder1.show();

                                        //ánh xạ
                                        Button btnLogin_resetpass = view1.findViewById(R.id.btnLogin_resetpass);
                                        btnResendpassword = view1.findViewById(R.id.btnResendpassword);
                                        etPass = view1.findViewById(R.id.etPass);
                                        btnLogin_resetpass.setVisibility(View.INVISIBLE);
                                        btnResendpassword.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                pass = etPass.getText().toString().trim();
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


                                                    FancyToast.makeText(ForgotPassword.this, "LẤY LẠI MẬT KHẨU THÀNH CÔNG",
                                                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                                    //Ẩn hiện nút khi lấy pass thành công
                                                    btnResendpassword.setVisibility(View.INVISIBLE);
                                                    btnLogin_resetpass.setVisibility(View.VISIBLE);
                                                    btnLogin_resetpass.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    RegisterDAO registerDAO = new RegisterDAO(ForgotPassword.this);
                                                    List<ListUser> list = registerDAO.getAll();
                                                    for (ListUser listUser1 : list) {
                                                        Log.i("HTC", listUser1.getId() + listUser1.getUser() + listUser1.getPassword() + listUser1.getEmail());
                                                    }

                                                }
                                            }
                                        });
                                    } else {
                                        FancyToast.makeText(ForgotPassword.this, "CODE Không Hợp Lệ",
                                                FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
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

    public int sendEmail(String email) {
        double randomDouble = Math.random();
        randomDouble = randomDouble * 8999 + 1000;
        int randomInt = (int) randomDouble;

        SendMailService javaMailAPI = new SendMailService(getBaseContext(), email,
                "LẤY LẠI MẬT KHẨU", "ĐÂY LÀ MÃ CODE ĐỂ LẤY LẠI MẬT KHẨU : " + randomInt);

        javaMailAPI.execute();
        return randomInt;
    }
}