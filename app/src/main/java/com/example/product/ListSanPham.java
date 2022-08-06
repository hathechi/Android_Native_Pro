package com.example.product;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.product.Adapter.ProductAdapter;
import com.example.product.DAO.ProductDAO;
import com.example.product.JavaClass.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ListSanPham extends AppCompatActivity {
    final int REQUEST_CODE = 123;
    public List<Product> arrayList;
    ImageButton imgButton_add;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    ImageView iv_view;
    Bitmap bitmap;
    Uri uri;
    byte[] img;
    ProductDAO dao;
    androidx.appcompat.widget.SearchView searchView; // khai báo SearchView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_san_pham);
        getSupportActionBar().setTitle("Wellcome Recycerview");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //chạy SQLite
        ProductDAO DAO = new ProductDAO(getBaseContext());

//lấy dữ liệu từ database
        dao = new ProductDAO(this);
        List<Product> list = dao.getAll();

        //các bước setup ở trang recycler view
        recyclerView = findViewById(R.id.id_listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(this, list);
        recyclerView.setAdapter(productAdapter);

        // tạo đường gạch ngang giữa những item trong recyclerview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        imgButton_add = findViewById(R.id.imgButton_add);
        imgButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogsheetview = LayoutInflater.from(ListSanPham.this).inflate(R.layout.add_listview, null);
                BottomSheetDialog dialog = new BottomSheetDialog(ListSanPham.this);
                dialog.setContentView(dialogsheetview);
                dialog.show();

                Button btnAdd = dialogsheetview.findViewById(R.id.btnAdd);
                EditText etTensp = dialogsheetview.findViewById(R.id.etTensp);
                EditText etGiasp = dialogsheetview.findViewById(R.id.etGia);
                EditText etMota = dialogsheetview.findViewById(R.id.etMota);
                iv_view = dialogsheetview.findViewById(R.id.iv_view);
                //btn CHọn hình
                Button btnTest = dialogsheetview.findViewById(R.id.btnChonhinh);
                btnTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);

                    }
                });


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tensp = etTensp.getText().toString();
                        Float giasp = Float.valueOf(etGiasp.getText().toString());
                        String mota = etMota.getText().toString();
                        if (tensp.isEmpty() || giasp == 0 || mota.isEmpty()) {
                            Toast.makeText(ListSanPham.this, "NHẬP ĐỦ DỮ LIỆU", Toast.LENGTH_SHORT).show();
                        } else {
                            dao = new ProductDAO(ListSanPham.this);
                            Product product = new Product();
                            product.setTenSp(tensp);
                            product.setGiaSp(giasp);
                            product.setMoTA(mota);
                            if (img == null) {
                                Toast.makeText(v.getContext(), "NHẬP ĐỦ DỮ LIỆU", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            product.setImageSp(img);
                            dao.insertProduct(product);
                            //gọi lại hàm Change
                            setChanged();
                        }

                    }
                });


            }

        });


    }

    private void setChanged() {
        dao = new ProductDAO(this);
        List<Product> list = dao.getAll();
        productAdapter = new ProductAdapter(this, list);
        recyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    public void Sua(int posision) {
        dao = new ProductDAO(ListSanPham.this);
        List<Product> list = dao.getAll();

        int id = list.get((posision)).getId();

        View dialogsheetview = LayoutInflater.from(ListSanPham.this).inflate(R.layout.edit_listview, null);
        BottomSheetDialog dialog = new BottomSheetDialog(ListSanPham.this);
        dialog.setContentView(dialogsheetview);
        dialog.show();

        EditText ten_edit = dialogsheetview.findViewById(R.id.etTensp_edit);
        EditText gia_edit = dialogsheetview.findViewById(R.id.etGiasp_edit);
        EditText mota_edit = dialogsheetview.findViewById(R.id.etMota_edit);
        iv_view = dialogsheetview.findViewById(R.id.iv_view);
        //btn CHọn hình
        Button btnTest = dialogsheetview.findViewById(R.id.btnChonhinh);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        Button edit = dialogsheetview.findViewById(R.id.btnEdit);
        ten_edit.setText(list.get(posision).getTenSp());
        gia_edit.setText(list.get(posision).getGiaSp() + "");
        mota_edit.setText(list.get(posision).getMoTA());
        //set hình preview
        byte[] byte1 = list.get(posision).getImageSp();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
        iv_view.setImageBitmap(bitmap);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = ten_edit.getText().toString();
                String gia = gia_edit.getText().toString();
                String mota = mota_edit.getText().toString();
                if (ten.isEmpty() || gia.isEmpty() || mota.isEmpty()) {
                    Toast.makeText(ListSanPham.this, "NHẬP ĐỦ DỮ LIỆU", Toast.LENGTH_SHORT).show();
                } else {
                    ProgressDialog progressDialog = ProgressDialog.show(ListSanPham.this, "Chờ Chút", "Xong ngay đây !!", true);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 700);
                    Product product = new Product();
                    product.setTenSp(ten);
                    product.setGiaSp(Float.valueOf(gia));
                    product.setMoTA(mota);
                    product.setImageSp(img);
                    Log.i("HTC", "onClick: " + product.getId() + "  " + id);
                    dao.updateProduct(product, String.valueOf(id));
                    setChanged();
                }
            }
        });


    }

    public void DeleteList(String i) {
        //Tạo dialog để hỏi người dùng
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("BẠN CÓ MUỐN XÓA VỊ TRÍ NÀY? ");
        builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressDialog progressDialog = ProgressDialog.show(ListSanPham.this, "Chờ Chút", "Xong ngay đây !!", true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 700);
                dao = new ProductDAO(ListSanPham.this);
                List<Product> list = dao.getAll();
                int id = list.get(Integer.parseInt(i)).getId();
                Log.i("HTC", "id = " + id);
                dao.delete(String.valueOf(id));
//set lại dữ liệu từ database lên listview
                setChanged();

            }
        });

        builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    //lấy dữ liệu hình từ máy
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
             uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
                img = byteArrayOutputStream.toByteArray();

                Log.i("HTC", "onActivityResult: " + img);
                iv_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Set custom menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
//set chạy searchView
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    // set onItem Click menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu3:
                Intent intent = new Intent(this, MainActivity3.class);
                startActivity(intent);
                break;
            case R.id.menu:
                Toast.makeText(this, "Setting product !!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_logout:
//                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("remember_username", "");
//                editor.putString("remember_password", "");
//                editor.apply();
//                Intent intent1 = new Intent(ListSanPham.this, MainActivity.class);
//                startActivity(intent1);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

