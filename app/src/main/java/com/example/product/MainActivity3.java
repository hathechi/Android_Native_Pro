package com.example.product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    EditText etID, etTen, etGia, etMota;
    Bitmap bitmap;
    ImageView imageView;
    ListView listView;
    int REQUEST_CODE = 123;
    Button btn_popup, btnContextmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //nút back trên thanh bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set logo
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //set title
        getSupportActionBar().setTitle("Hello World");

        btnContextmenu = findViewById(R.id.btnContextmenu);
        btn_popup = findViewById(R.id.btnPopup);
        btnContextmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add("List View");
        }
        ListView listView = findViewById(R.id.listviewMain);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, arrayList);
        listView.setAdapter(stringArrayAdapter);
        registerForContextMenu(listView);



        //popup
        btn_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity3.this, btn_popup);
                getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.title1) {
                            Toast.makeText(MainActivity3.this, "Item 1", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        Button btnImage = findViewById(R.id.btnChonhinh1);
        imageView = findViewById(R.id.iv_view1);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(pickIntent,"Pic image"),1);
            }
        });
    }

    //lấy dữ liệu hình từ máy
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Log.i("HTC", "onImageSelected: " + bitmap);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_2, menu);

//bắt sự kiện mở Switch trên ActionBar
        final MenuItem toggleservice = menu.findItem(R.id.app_bar_switch);
        final Switch actionView = (Switch) toggleservice.getActionView();

        actionView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity3.this, "ON !!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.download:
                Toast.makeText(this, "Download", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reload:
                Toast.makeText(this, "Reload", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo Info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = Info.position;

        switch (item.getItemId()) {
            case R.id.title1:
                Toast.makeText(this, "title1 " + vitri, Toast.LENGTH_SHORT).show();
                break;

            case R.id.title2:
                Toast.makeText(this, "title2 ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.title3:
                Toast.makeText(this, "title3 ", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onContextItemSelected(item);
    }

}