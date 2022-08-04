package com.example.product.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.product.JavaClass.Product;
import com.example.product.ListSanPham;
import com.example.product.ListSanPhamGridView;
import com.example.product.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// implment Filterable để tìm kiếm
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHoder> implements Filterable {
    // tạo biến môi trường ở đây để gọi hàm ở ativity khác
    private ListSanPham listSanPham;
    private List<Product> mProduct;
    private List<Product> mProductSearch; // list trung gian để tìm kiếm
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ProductAdapter(ListSanPham listSanPham, List<Product> mProduct) {
        this.mProduct = mProduct;
        this.listSanPham = listSanPham;
        this.mProductSearch = mProduct;  // list trung gian để tìm kiếm
    }


    @NonNull
    @NotNull
    @Override
    public ProductAdapter.ProductViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listview_swipe, parent, false);
        return new ProductViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ProductViewHoder holder, int position) {
        Product product = mProduct.get(position);
        if (product == null) {
            return;
        }
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(product.getId()));
//đổi từ byte sang Bitmap
        byte[] byte1 = product.getImageSp();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
        Log.i("HTC2", "bitmap: " + bitmap);

        holder.image.setImageBitmap(bitmap);
        holder.tensp.setText(product.getTenSp());
        holder.giasp.setText(product.getGiaSp() + " $");
        holder.mota.setText(product.getMoTA());

        holder.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListSanPhamGridView.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                thongTin.Xoa(position);
//                mInfoStudent.remove(holder.getAbsoluteAdapterPosition());
//                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                listSanPham.DeleteList(String.valueOf(position));

            }

        });

        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSanPham.Sua(position);

            }
        });
// setOnclick item recyclerview 1. đặt id cho layout được click 2. tạo biến toàn cục ở dưới và ánh xạ, xong setOnlick ở đây
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(thongTin, InfoListView.class); // Intent trong onBindViewHolder không dùng "this" mà dùng "v.getContext()"
//                InfoStudent dataSend = mInfoStudent.get(position);
//                if (dataSend != null) {
//                    intent.putExtra("data", (Serializable) dataSend);
//                    v.getContext().startActivity(intent); // dùng v.getContext() hoặc thongTin trong trường hợp này đều được
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (mProduct != null) {
            return mProduct.size();
        }
        return 0;
    }



    public class ProductViewHoder extends RecyclerView.ViewHolder {
        public SwipeRevealLayout swipeRevealLayout;
        public LinearLayout linearLayout;
        public TextView sua, xoa, id, tensp, giasp, mota;
        public ImageView image;
        private Button btnChiTiet;

        public ProductViewHoder(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.Swipe_Layout);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.click_item);
            sua = itemView.findViewById(R.id.btntvSua);
            xoa = itemView.findViewById(R.id.btntvXoa);
//            id = itemView.findViewById(R.id.tv_id);
            tensp = itemView.findViewById(R.id.tv_ten);
            giasp = itemView.findViewById(R.id.tv_gia);
            mota = itemView.findViewById(R.id.tv_mota);
            image = itemView.findViewById(R.id.id_image);
            btnChiTiet=itemView.findViewById(R.id.btnChiTiet1);

        }
    }



    // getFilter để tìm kiếm
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Search = constraint.toString();
                if (Search.isEmpty()) {
                    mProduct = mProductSearch;
                } else {
                    List<Product> list = new ArrayList<>();
                    for (Product product : mProductSearch) {
                        if (product.getTenSp().toLowerCase().contains(Search.toLowerCase())
                                || (product.getGiaSp()).toString().contains(Search.toLowerCase())
                                || product.getMoTA().toLowerCase().contains(Search.toLowerCase())) {
                            list.add(product);
                        }
                    }
                    mProduct = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mProduct;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mProduct = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
