package com.example.booksmanagerager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class BootActivity extends AppCompatActivity {
    Button btn_login_boot;
    private ImageView imgView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        btn_login_boot = findViewById(R.id.btn_login_boot);
        imgView2 = findViewById(R.id.imageView);

        Animation animation = AnimationUtils.loadAnimation(BootActivity.this, R.anim.anim_boot_screen_img);
        imgView2.startAnimation(animation);

        btn_login_boot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BootActivity.this, LoginActivity.class);
                startActivity(intent);
                // Áp dụng hiệu ứng trượt từ bên phải qua khi chuyển sang Activity B.
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }
}