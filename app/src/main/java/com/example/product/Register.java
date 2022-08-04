package com.example.product;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.product.DAO.RegisterDAO;
import com.example.product.JavaClass.ListUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;
import java.util.List;


public class Register extends AppCompatActivity implements View.OnClickListener {


    private Button btn;
    private EditText User, Pass, CFpass, Email, Ngaysinh;
    private TextView show_err;
    private Handler handler = new Handler();
    private RegisterDAO registerDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        User = findViewById(R.id.etUsername);
        Pass = findViewById(R.id.etPass);
        CFpass = findViewById(R.id.etCFpass);
        Email = findViewById(R.id.etEmail);
        show_err = findViewById(R.id.tvError_regis);
        Ngaysinh = findViewById(R.id.etNgaysinh);
//chạy SQLite
        RegisterDAO DAO = new RegisterDAO(Register.this);


        //date picker dialog
        Button button = findViewById(R.id.btnChonngay);
        EditText etDate = findViewById(R.id.etNgaysinh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        etDate.setText(date);
                    }
                };
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this,
                        dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

                datePickerDialog.show();

            }
        });

        btn = findViewById(R.id.btnRegister_regis);
        btn.setOnClickListener(this);

        Button btnRegister = findViewById(R.id.btnLogin_regis);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Home.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister_regis:
                String username = User.getText().toString();
                String pass = Pass.getText().toString();
                String cfpass = CFpass.getText().toString();
                String email = Email.getText().toString();
                if (BatLoiSua()) {
                    registerDAO = new RegisterDAO(this);
                    List<ListUser> list = registerDAO.getAll();
                    for(ListUser a:list){
                        if(username.equalsIgnoreCase(a.getUser())){
                            FancyToast.makeText(Register.this,
                                    "TÊN TÀI KHOẢN ĐÃ TỒN TẠI !",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false).show();
                            return;
                        }
                    }
                    ListUser listUser = new ListUser();
                    listUser.setUser(username);
                    listUser.setPassword(pass);
                    listUser.setEmail(email);
                    registerDAO.insertRegister(listUser);
                    FancyToast.makeText(Register.this,
                            "ĐĂNG KÍ THÀNH CÔNG !",
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,
                            false).show();

                }
                break;
        }
    }

    boolean BatLoiSua() {
        //Bat loi du lieu
        String username = User.getText().toString();
        String pass = Pass.getText().toString();
        String cfpass = CFpass.getText().toString();
        String email = Email.getText().toString();
        String ngaysinh = Ngaysinh.getText().toString();

        if (username.isEmpty() || pass.isEmpty() || cfpass.isEmpty() || email.isEmpty() || ngaysinh.isEmpty()) {
            show_err.setText("KHÔNG BỎ TRỐNG DỮ LIỆU !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    show_err.setText("");
                }
            }, 1500);
//            Toast.makeText(this, "NHẬP ĐỦ TRƯỜNG DỮ LIỆU !!", Toast.LENGTH_LONG).show();
            return false;
        }

        String regexTen = "[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{0,50}";
        if (!username.matches(regexTen)) {
            show_err.setText("CHỈ NHẬP TÊN BẰNG CHỮ !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    show_err.setText("");
                }
            }, 1500);
            return false;
        }
        if (!pass.equalsIgnoreCase(cfpass)) {
            show_err.setText("HAI PASSWORD PHẢI TRÙNG NHAU!");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    show_err.setText("");
                }
            }, 1500);
            return false;
        }
        String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(regexEmail)) {
            show_err.setText("NHẬP ĐÚNG ĐỊNH DẠNG EMAIL !");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    show_err.setText("");
                }
            }, 1500);
            return false;
        }
        return true;
    }

}

