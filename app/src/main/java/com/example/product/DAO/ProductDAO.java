package com.example.product.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.product.JavaClass.Product;
import com.example.product.SQLite.DBhelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteDatabase db;

    public ProductDAO(Context context) {
        DBhelper sqLiteHelper = new DBhelper(context);
        this.db = sqLiteHelper.getReadableDatabase();
    }

    @SuppressLint("Range")
    public List<Product> getList(String sql, String... selectArgs) {
        List<Product> list = new ArrayList<>();
//        String queryString = "SELECT * FROM product";
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()) {
            Product product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndex("id")));
            product.setTenSp(cursor.getString(cursor.getColumnIndex("tensp")));
            product.setGiaSp(cursor.getFloat(cursor.getColumnIndex("giasp")));
            product.setMoTA(cursor.getString(cursor.getColumnIndex("mota")));
            product.setThuongHieu(cursor.getString(cursor.getColumnIndex("thuonghieu")));
            product.setImageSp((cursor.getBlob(cursor.getColumnIndex("hinhsp"))));

            list.add(product);
        }
        return list;
    }

    public List<Product> getbyID(String id) {
        String sql = "SELECT * FROM product WHERE id=?";
        List<Product> list = getList(sql, id);
        return (List<Product>) list.get(0);
    }

    public List<Product> getAll() {
        String sql = "SELECT * FROM product";
        return getList(sql);
    }

    public long insertProduct(Product product) {

        ContentValues contentValues = new ContentValues();

//        contentValues.put("id", product.getId());
        contentValues.put("tensp", product.getTenSp());
        contentValues.put("giasp", product.getGiaSp());
        contentValues.put("mota", product.getMoTA());
        contentValues.put("thuonghieu", product.getThuongHieu());
        contentValues.put("hinhsp", product.getImageSp());

        return db.insert("product", null, contentValues);
    }

    public long updateProduct(Product product, String id) {

        ContentValues contentValues = new ContentValues();

//        contentValues.put("id", product.getId());
        contentValues.put("tensp", product.getTenSp());
        contentValues.put("giasp", product.getGiaSp());
        contentValues.put("mota", product.getMoTA());
        contentValues.put("thuonghieu", product.getThuongHieu());
        contentValues.put("hinhsp", product.getImageSp());
        return db.update("product", contentValues, "id = ?", new String[]{id});
    }

    public int delete(String id) {
        try {
            return db.delete("product", "id = ?", new String[]{id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
