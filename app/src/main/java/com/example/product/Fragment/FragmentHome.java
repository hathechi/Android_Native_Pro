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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.product.Adapter.ProductAdapterFragmentHome;
import com.example.product.Adapter.ProductAdapterGridViewViewPager;
import com.example.product.DAO.ProductDAO;
import com.example.product.DAO.ThuongHieuDAO;
import com.example.product.JavaClass.Product;
import com.example.product.JavaClass.ThuongHieu;
import com.example.product.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    public List<Product> arrayList;
    ImageButton imgButton_add;
    RecyclerView recyclerView;
    ProductAdapterFragmentHome productAdapterGridView;
    ImageView iv_view;
    Bitmap bitmap;
    Uri uri;
    byte[] img;
    ProductDAO dao;
    ThuongHieuDAO thuongHieuDAO;
    SearchView searchView; // khai báo SearchView
    Spinner spinnerAdd;
    int idSpin;
    String item_thuonghieu;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);






        //set nút back cho Activity
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Danh Sách Giày");
        //chạy SQLite
        thuongHieuDAO = new ThuongHieuDAO(view.getContext());


//lấy dữ liệu từ database
        dao = new ProductDAO(view.getContext());
        List<Product> list = dao.getAll();

        //các bước setup ở trang recycler view
        recyclerView = view.findViewById(R.id.id_listview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        productAdapterGridView = new ProductAdapterFragmentHome(this, list);
        recyclerView.setAdapter(productAdapterGridView);
        registerForContextMenu(recyclerView);
        // tạo đường gạch ngang giữa những item trong recyclerview
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        imgButton_add = view.findViewById(R.id.imgButton_add);
//        imgButton_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                View dialogsheetview = LayoutInflater.from(v.getContext()).inflate(R.layout.add_listview, null);
//                BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());
//                dialog.setContentView(dialogsheetview);
//                dialog.show();
//
//                Button btnAdd = dialogsheetview.findViewById(R.id.btnAdd);
//                EditText etTensp = dialogsheetview.findViewById(R.id.etTensp);
//                EditText etGiasp = dialogsheetview.findViewById(R.id.etGia);
//                EditText etMota = dialogsheetview.findViewById(R.id.etMota);
//                iv_view = dialogsheetview.findViewById(R.id.iv_view);
//
//                //Spinner
//                spinnerAdd = dialogsheetview.findViewById(R.id.spinnerAdd);
//
//                //gọi lại adapter Spiner
//                getSpinerAdapter();
//
//
//                spinnerAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        item_thuonghieu = spinnerAdd.getSelectedItem().toString();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//
//
//                ImageButton btnAddThuongHieu = dialogsheetview.findViewById(R.id.btnAddThuongHieu);
//                btnAddThuongHieu.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
//                        LayoutInflater layoutInflater = FragmentOne.this.getLayoutInflater();
//                        //Nhúng layout vào dialog alert
//                        View view = layoutInflater.inflate(R.layout.layout_dialog_addthuonghieu, null);
//                        builder.setView(view);
//                        builder.show();
//                        //Ánh xạ
//                        EditText etThuongHieu = view.findViewById(R.id.etThuongHieu);
//                        Button btnAddThuongHieu_dialog = view.findViewById(R.id.btnAddThuongHieu_dialog);
//                        btnAddThuongHieu_dialog.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String tenThuongHieu = etThuongHieu.getText().toString().toUpperCase();
//                                if (tenThuongHieu.isEmpty()) {
//                                    FancyToast.makeText(getContext(), "Chưa Nhập Tên Thương Hiệu ! ",
//                                            FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
//                                } else {
//
//                                    ThuongHieu thuongHieu = new ThuongHieu();
//                                    thuongHieu.setTenthuonghieu(tenThuongHieu);
//                                    thuongHieuDAO.insertThuongHieu(thuongHieu);
//
//                                    FancyToast.makeText(getContext(), "Thêm Thành Công !",
//                                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
//                                    //gọi lại adapter Spiner
//                                    getSpinerAdapter();
//                                }
//                            }
//                        });
//                    }
//                });
//
//                ImageButton btnXoaThuongHieu = dialogsheetview.findViewById(R.id.btnXoaThuongHieu);
//                btnXoaThuongHieu.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Tạo dialog để hỏi người dùng
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                        builder.setMessage("BẠN CÓ MUỐN XÓA TÊN THƯƠNG HIỆU CUỐI CÙNG NÀY? ");
//                        builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Chờ Chút", "Xong ngay đây !!", true);
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        progressDialog.dismiss();
//                                    }
//                                }, 500);
//
//                                List<ThuongHieu> spin = thuongHieuDAO.getAll();
//                                idSpin = spin.get(spin.size() - 1).getId();
//
//                                thuongHieuDAO.delete(String.valueOf(idSpin));
//
////set lại dữ liệu từ database lên spinner
//                                getSpinerAdapter();
//
//                            }
//                        });
//
//                        builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                        builder.show();
//
//                    }
//                });
//                //btn CHọn hình
//                Button btnTest = dialogsheetview.findViewById(R.id.btnChonhinh);
//                btnTest.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType("image/*");
//                        startActivityForResult(intent, 1);
//                    }
//                });
//
//
//                btnAdd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String tensp = etTensp.getText().toString();
//                        Float giasp = Float.valueOf(etGiasp.getText().toString());
//                        String mota = etMota.getText().toString();
//                        if (tensp.isEmpty() || giasp == 0 || mota.isEmpty()) {
//                            Toast.makeText(v.getContext(), "NHẬP ĐỦ DỮ LIỆU", Toast.LENGTH_SHORT).show();
//                        } else {
//                            ProductDAO dao = new ProductDAO(v.getContext());
//                            Product product = new Product();
//                            product.setTenSp(tensp);
//                            product.setGiaSp(giasp);
//                            product.setMoTA(mota);
//                            product.setThuongHieu(item_thuonghieu);
//                            if (img == null) {
//                                Toast.makeText(v.getContext(), "NHẬP ĐỦ DỮ LIỆU", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            product.setImageSp(img);
//                            dao.insertProduct(product);
//                            //gọi lại hàm Change
//                            setChanged();
//                        }
//
//                    }
//                });
//
//
//            }
//
//        });

        return view;
    }

    private void getSpinerAdapter() {
        List<ThuongHieu> spin = thuongHieuDAO.getAll();
        ArrayList<String> list1 = new ArrayList<>();
        for (ThuongHieu a : spin) {
            list1.add(a.getTenthuonghieu());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                list1);
        spinnerAdd.setAdapter(adapter);
    }

    public void XemChiTiet(int i) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        //Nhúng layout vào dialog alert
        View view = layoutInflater.inflate(R.layout.layout_chitiet, null);
        builder.setView(view);
        builder.show();

        ImageView iv_chitiet = view.findViewById(R.id.id_image_chitiet);
        TextView tvGia_chitiet = view.findViewById(R.id.tv_gia_chitiet);
        Button btnBuy_chitiet = view.findViewById(R.id.btnBuy_chitiet);

        dao = new ProductDAO(view.getContext());
        List<Product> list = dao.getAll();

        //đổi từ byte sang Bitmap
        byte[] byte1 = list.get(i).getImageSp();
        Log.i("buy", "XemChiTiet: " + byte1);

        Bitmap bitmap = BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
        Log.i("b", "bit map: " + bitmap);
        iv_chitiet.setImageBitmap(bitmap);
        tvGia_chitiet.setText(list.get(i).getGiaSp() + " $");


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

        //Spinner
        spinnerAdd = dialogsheetview.findViewById(R.id.spinnerAdd);

        //gọi lại adapter Spiner
        getSpinerAdapter();
        spinnerAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_thuonghieu = spinnerAdd.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                    product.setThuongHieu(item_thuonghieu);
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
        productAdapterGridView = new ProductAdapterFragmentHome(this, list);
        recyclerView.setAdapter(productAdapterGridView);
        productAdapterGridView.notifyDataSetChanged();
    }

    //lấy dữ liệu hình từ máy
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null) {
            Uri uri = data.getData();
            Log.i("1", "uri: " + uri);
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


    // Set custom menu bar
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
//set chạy searchView
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapterGridView.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapterGridView.getFilter().filter(newText);
                return false;
            }
        });
    }

    // bắt sự kiện cho nút trên Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.item_logout:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
