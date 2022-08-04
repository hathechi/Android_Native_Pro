package com.example.product.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegisterDBhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sqlProduct";
    public static final int DB_VERSION = 1;
    public static final String ID = "id";
    public static final String TEN = "tensp";
    public static final String GIA = "giasp";
    public static final String MOTA = "mota";

    //table register
    public static final String DB_NAME1 = "sqlRegister";
    public static final int DB_VERSION1 = 1;
    public static final String ID_REGIS = "id";
    public static final String TEN_REGIS = "tendangnhap";
    public static final String EMAIL = "email";
    public static final String MATKHAU = "matkhau";
    public static final String GIOITINH = "gioitinh";
    public static final String NGAYSINH = "ngaysinh";

    public RegisterDBhelper(Context context) {
        super(context, DB_NAME1, null, DB_VERSION1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_register = String.format("CREATE TABLE register(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)", ID_REGIS, TEN_REGIS, MATKHAU, EMAIL);
        db.execSQL(sql_register);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        String droptable1 = "DROP TABLE IF EXISTS register";
        db.execSQL(droptable1);
        onCreate(db);
    }
}
