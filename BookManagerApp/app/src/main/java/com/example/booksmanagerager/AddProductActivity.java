package com.example.booksmanagerager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booksmanagerager.model.SanPham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {
    EditText edt_bookname, edt_author, edt_quantity, edt_price;
    private Button btnAdd;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        btnAdd = findViewById(R.id.btnAdd);
        edt_bookname = findViewById(R.id.edt_addBookname);
        edt_author = findViewById(R.id.edt_addAuthor);
        edt_quantity = findViewById(R.id.edt_addQuantity);
        edt_price = findViewById(R.id.edt_addPrice);


        database = FirebaseFirestore.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String bookname = edt_bookname.getText().toString();
                String author = edt_author.getText().toString();
                int quantity = Integer.parseInt(edt_quantity.getText().toString());
                float price = Float.parseFloat(edt_price.getText().toString());

                // Generate a unique ID
                String id = UUID.randomUUID().toString();

                // Create a SanPham object
                SanPham sanPham = new SanPham(id, bookname, author, quantity, price);

                // Add the SanPham object to Firestore
                database.collection("SANPHAM").document(id)
                        .set(sanPham.convertHashMap()) // Convert the object to a HashMap
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddProductActivity.this, "Thêm vào trên FireBase thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddProductActivity.this, HomeMainActivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddProductActivity.this, "Thêm vào trên FireBase thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
