package com.example.product.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.product.JavaClass.ListUser;
import com.example.product.JavaClass.ThuongHieu;
import com.example.product.SQLite.DBhelper;
import com.example.product.SQLite.RegisterDBhelper;

import java.util.ArrayList;
import java.util.List;

public class ThuongHieuDAO {
    private SQLiteDatabase db;

    public ThuongHieuDAO(Context context) {
        DBhelper sqLiteHelper = new DBhelper(context);
        this.db = sqLiteHelper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public List<ThuongHieu> getList(String sql, String... selectArgs) {
        List<ThuongHieu> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()) {
            ThuongHieu thuongHieu = new ThuongHieu();
            thuongHieu.setId(cursor.getInt(cursor.getColumnIndex("id")));
            thuongHieu.setTenthuonghieu(cursor.getString(cursor.getColumnIndex("tenthuonghieu")));


            list.add(thuongHieu);
        }
        return list;
    }

//    public List<ThuongHieu> getbyID(String id) {
//        String sql = "SELECT * FROM thuonghieu WHERE id=?";
//        List<ThuongHieu> list = getList(sql, id);
//        return (List<ThuongHieu>) list.get(0);
//    }

    public List<ThuongHieu> getAll() {
        String sql = "SELECT * FROM thuonghieu";

        return getList(sql);
    }

    public long insertThuongHieu(ThuongHieu thuongHieu) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenthuonghieu", thuongHieu.getTenthuonghieu());

        return db.insert("thuonghieu", null, contentValues);
    }

//    public long updateRegister(ListUser listUser, String id) {
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("tendangnhap", listUser.getUser());
//        contentValues.put("matkhau", listUser.getPassword());
//        contentValues.put("email", listUser.getEmail());
//
//        return db.update("register", contentValues, "id = ?", new String[]{id});
//    }

    public int delete(String id) {
        try {
            return db.delete("thuonghieu", "id = ?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
