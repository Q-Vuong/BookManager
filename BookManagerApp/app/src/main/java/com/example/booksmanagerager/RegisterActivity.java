package com.example.booksmanagerager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booksmanagerager.dao.NguoiDungDAO;
import com.example.booksmanagerager.database.DbHelper;
import com.example.booksmanagerager.model.NguoiDung;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private Button btn_go_to_login, btn_register;
    private EditText edt_user, edt_password, edt_hoten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_go_to_login = findViewById(R.id.btn_go_to_login);
        btn_register = findViewById(R.id.btn_register);
        edt_hoten = findViewById(R.id.edt_register_hoten);
        edt_password = findViewById(R.id.edt_register_password);
        edt_user = findViewById(R.id.edt_register_username);
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);
        DbHelper dbHelper = new DbHelper(this);

        btn_go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edt_user.getText().toString();
                String pass = edt_password.getText().toString();
                String hoten = edt_hoten.getText().toString();

                if(user.isEmpty() || pass.isEmpty() || hoten.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {

                    nguoiDungDAO.open();

                    NguoiDung nguoiDung = new NguoiDung(user, pass, hoten);

                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    String[] projection = { "TENDANGNHAP"};
                    String selection = "TENDANGNHAP = ?";
                    String[] selectionArgs = { user};
                    Cursor cursor = db.query("NGUOIDUNG", projection, selection, selectionArgs, null, null, null);
                    if (cursor.moveToFirst()) {

                        Toast.makeText(RegisterActivity.this, "User này đã tồn tại\nĐăng ký thất bại", Toast.LENGTH_SHORT).show();

                        nguoiDungDAO.close();
                    } else {

                        boolean success = nguoiDungDAO.addNguoiDung(nguoiDung);

                        nguoiDungDAO.close();
                        if (success) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }
}