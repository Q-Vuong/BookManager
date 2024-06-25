package com.example.booksmanagerager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booksmanagerager.database.DbHelper;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login, btn_go_to_register;
    private EditText edt_username, edt_password;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        btn_go_to_register = findViewById(R.id.btn_go_to_register);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);

        dbHelper = new DbHelper(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{

                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    String[] projection = { "TENDANGNHAP", "MATKHAU", "HOTEN" };
                    String selection = "TENDANGNHAP = ? AND MATKHAU = ?";
                    String[] selectionArgs = { username, password };
                    Cursor cursor = db.query("NGUOIDUNG", projection, selection, selectionArgs, null, null, null);

                    if (cursor.moveToFirst()) {

                        String fullName = cursor.getString(cursor.getColumnIndexOrThrow("HOTEN"));
                        Intent intent = new Intent(LoginActivity.this, HomeMainActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Chào mừng " + fullName, Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    }

                    cursor.close();
                    db.close();
                }

            }
        });
        btn_go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}