package com.example.product.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.product.JavaClass.ListUser;
import com.example.product.SQLite.RegisterDBhelper;

import java.util.ArrayList;
import java.util.List;

public class RegisterDAO {
    private SQLiteDatabase db;

    public RegisterDAO(Context context) {
        RegisterDBhelper sqLiteHelper = new RegisterDBhelper(context);
        this.db = sqLiteHelper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public List<ListUser> getList(String sql, String... selectArgs) {
        List<ListUser> list = new ArrayList<>();
//        String queryString = "SELECT * FROM product";
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()) {
            ListUser listUser = new ListUser();
            listUser.setId(cursor.getInt(cursor.getColumnIndex("id")));
            listUser.setUser(cursor.getString(cursor.getColumnIndex("tendangnhap")));
            listUser.setPassword(cursor.getString(cursor.getColumnIndex("matkhau")));
            listUser.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            list.add(listUser);
        }
        return list;
    }

    public List<ListUser> getbyID(String id) {
        String sql = "SELECT * FROM register WHERE id=?";
        List<ListUser> list = getList(sql, id);
        return (List<ListUser>) list.get(0);
    }

    public List<ListUser> getAll() {
        String sql = "SELECT * FROM register";

        return getList(sql);
    }

    public long insertRegister(ListUser listUser) {

        ContentValues contentValues = new ContentValues();

//        contentValues.put("id", product.getId());
        contentValues.put("tendangnhap", listUser.getUser());
        contentValues.put("matkhau", listUser.getPassword());
        contentValues.put("email", listUser.getEmail());
        return db.insert("register", null, contentValues);
    }

    public long updateRegister(ListUser listUser, String id) {

        ContentValues contentValues = new ContentValues();

        contentValues.put("tendangnhap", listUser.getUser());
        contentValues.put("matkhau", listUser.getPassword());
        contentValues.put("email", listUser.getEmail());

        return db.update("register", contentValues, "id = ?", new String[]{id});
    }

    public int delete(String id) {
        try {
            return db.delete("register", "id = ?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
