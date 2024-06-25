package com.example.booksmanagerager.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booksmanagerager.R;
import com.example.booksmanagerager.model.SanPham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SanPham> list;
    FirebaseFirestore database;

    public SanPhamAdapter(Context context, ArrayList<SanPham> list, FirebaseFirestore database) {
        this.context = context;
        this.list = list;
        this.database = database; // Khởi tạo biến database
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int itemPosition = position; // Store the position as a final variable

        Log.d("GetList", String.valueOf(list));

        holder.txt_bookname.setText("Sách: " + list.get(position).getBookname());
        holder.txt_author.setText("Tác giả: " + list.get(position).getAuthor());
        holder.txt_price.setText("Giá: " + String.valueOf(list.get(position).getPrice()));
        holder.txt_quantity.setText("Số lượng: " + String.valueOf(list.get(position).getQuantity()));

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xoá công việc này không");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = list.get(itemPosition).getId();
                        database = FirebaseFirestore.getInstance();
                        database.collection("SANPHAM").document(id)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Lỗi", "onFailure: " + e);
                                    }
                                });
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham sanPham = list.get(itemPosition);
                DialogUpdateSanPham(itemPosition, sanPham);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_bookname, txt_author, txt_price, txt_quantity;
        Button btn_Update, btn_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_author = itemView.findViewById(R.id.txt_author);
            txt_bookname = itemView.findViewById(R.id.txt_bookname);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_quantity = itemView.findViewById(R.id.txt_quantity);
            btn_Update = itemView.findViewById(R.id.btn_Update);
            btn_Delete = itemView.findViewById(R.id.btn_Delete);
        }
    }

    public void DialogUpdateSanPham(int position, SanPham sanPhamUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edt_bookname = view.findViewById(R.id.edt_bookname);
        EditText edt_author = view.findViewById(R.id.edt_author);
        EditText edt_quantity = view.findViewById(R.id.edt_quantity);
        EditText edt_price = view.findViewById(R.id.edt_price);
        Button btn_update = view.findViewById(R.id.btn_updete);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);

        edt_bookname.setText(sanPhamUpdate.getBookname());
        edt_author.setText(sanPhamUpdate.getAuthor());
        edt_quantity.setText(String.valueOf(sanPhamUpdate.getQuantity()));
        edt_price.setText(String.valueOf(sanPhamUpdate.getPrice()));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bookname = edt_bookname.getText().toString();
                String author = edt_author.getText().toString();
                int quantity = Integer.parseInt(edt_quantity.getText().toString());
                float price = Float.parseFloat(edt_price.getText().toString());

                SanPham sanPham = new SanPham(sanPhamUpdate.getId(), bookname, author, quantity, price);

                database = FirebaseFirestore.getInstance();
                database.collection("SANPHAM").document(sanPham.getId())
                        .set(sanPham)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Cập nhật trên FireBase thành công", Toast.LENGTH_SHORT).show();

                                list.set(position, sanPham);
                                notifyItemChanged(position);

                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Lỗi", "onFailure: " + e);
                            }
                        });
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
