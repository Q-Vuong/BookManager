package com.example.booksmanagerager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "bookManagerDatabase", null, 5);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //-----------
        String dbNguoiDung = "CREATE TABLE NGUOIDUNG(TENDANGNHAP TEXT PRIMARY KEY, MATKHAU TEXT, HOTEN TEXT)";
        db.execSQL(dbNguoiDung);
        //-------------
        String dataNguoiDung = "INSERT INTO NGUOIDUNG VALUES('vuong', 'vuong123', 'Diệp Quốc Vương')";
        db.execSQL(dataNguoiDung);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            onCreate(db);
        }
    }
}
