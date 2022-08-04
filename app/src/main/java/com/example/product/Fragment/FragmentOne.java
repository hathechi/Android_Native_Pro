package com.example.product.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.product.Adapter.ProductAdapterGridViewViewPager;
import com.example.product.DAO.ProductDAO;
import com.example.product.JavaClass.Product;
import com.example.product.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class FragmentOne extends Fragment {

    public List<Product> arrayList;
    ImageButton imgButton_add;
    RecyclerView recyclerView;
    ProductAdapterGridViewViewPager productAdapterGridView;
    ImageView iv_view;
    Bitmap bitmap;
    Uri uri;
    byte[] img;
    ProductDAO dao;
    androidx.appcompat.widget.SearchView searchView; // khai báo SearchView

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_list_san_pham_grid_view, container, false);

        //chạy SQLite
        ProductDAO DAO = new ProductDAO(view.getContext());

//lấy dữ liệu từ database
        dao = new ProductDAO(view.getContext());
        List<Product> list = dao.getAll();

        //các bước setup ở trang recycler view
        recyclerView = view.findViewById(R.id.id_listview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        productAdapterGridView = new ProductAdapterGridViewViewPager(this, list);
        recyclerView.setAdapter(productAdapterGridView);
        registerForContextMenu(recyclerView);
        // tạo đường gạch ngang giữa những item trong recyclerview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        imgButton_add = view.findViewById(R.id.imgButton_add);
        imgButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogsheetview = LayoutInflater.from(v.getContext()).inflate(R.layout.add_listview, null);
                BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());
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
                            Toast.makeText(v.getContext(), "NHẬP ĐỦ DỮ LIỆU", Toast.LENGTH_SHORT).show();
                        } else {
                            ProductDAO dao = new ProductDAO(v.getContext());
                            Product product = new Product();
                            product.setTenSp(tensp);
                            product.setGiaSp(giasp);
                            product.setMoTA(mota);
                            product.setImageSp(img);
                            dao.insertProduct(product);
                            //gọi lại hàm Change
                            setChanged();
                        }

                    }
                });


            }

        });

        return view;
    }

    public void Sua(int posision) {
        dao = new ProductDAO(getContext());
        List<Product> list = dao.getAll();

        int id = list.get((posision)).getId();

        View dialogsheetview = LayoutInflater.from(getContext()).inflate(R.layout.edit_listview, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
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
                    Toast.makeText(getContext(), "NHẬP ĐỦ DỮ LIỆU", Toast.LENGTH_SHORT).show();
                } else {
                    //Tạo dialog delay
                    ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Chờ Chút", "Xong ngay đây !!", true);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("BẠN CÓ MUỐN XÓA VỊ TRÍ NÀY? ");
        builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Chờ Chút", "Xong ngay đây !!", true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 700);

                dao = new ProductDAO(getContext());
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

    private void setChanged() {
        dao = new ProductDAO(getContext());
        List<Product> list = dao.getAll();
        productAdapterGridView = new ProductAdapterGridViewViewPager(this, list);
        recyclerView.setAdapter(productAdapterGridView);
        productAdapterGridView.notifyDataSetChanged();
    }

    //lấy dữ liệu hình từ máy
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null) {
            Uri uri = data.getData();
            Log.i("1", "uri: "+uri);
            try {
                //thêm requireActivity() trước .getContentResolver() khi dùng trong fragment
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
                img = byteArrayOutputStream.toByteArray();

                Log.i("2", "onActivityResult: " + bitmap);
                iv_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
