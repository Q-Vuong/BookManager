package com.example.booksmanagerager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.booksmanagerager.database.DbHelper;
import com.example.booksmanagerager.model.NguoiDung;

public class NguoiDungDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public NguoiDungDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean addNguoiDung(NguoiDung nguoiDung) {
        ContentValues values = new ContentValues();
        values.put("TENDANGNHAP", nguoiDung.getTendangnhap());
        values.put("MATKHAU", nguoiDung.getMatkhau());
        values.put("HOTEN", nguoiDung.getHoten());
        long check = database.insert("NGUOIDUNG", null, values);
        return check != -1;
    }

    public boolean updateNguoiDung(NguoiDung nguoiDung) {
        ContentValues values = new ContentValues();
        values.put("TENDANGNHAP", nguoiDung.getTendangnhap());
        values.put("MATKHAU", nguoiDung.getMatkhau());
        values.put("HOTEN", nguoiDung.getHoten());
        String selection = "TENDANGNHAP = ?";
        String[] selectionArgs = { nguoiDung.getTendangnhap() };
        long check = database.update("NGUOIDUNG", values, selection, selectionArgs);

        return check != -1;
    }

    public boolean deleteNguoiDung(String tenDangNhap) {
        String selection = "TENDANGNHAP = ?";
        String[] selectionArgs = { tenDangNhap };
        long row = database.delete("NGUOIDUNG", selection, selectionArgs);
        return row != -1;
    }

    /*public NguoiDung getNguoiDungByTenDangNhap(String tenDangNhap) {
        NguoiDung nguoiDung = null;
        String[] projection = { "tendangnhap", "matkhau", "hoten" };
        String selection = "tendangnhap = ?";
        String[] selectionArgs = { tenDangNhap };
        Cursor cursor = database.query("NguoiDung", projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String tendangnhap = cursor.getString(cursor.getColumnIndex("tendangnhap"));
            String matkhau = cursor.getString(cursor.getColumnIndex("matkhau"));
            String hoten = cursor.getString(cursor.getColumnIndex("hoten"));
            nguoiDung = new NguoiDung(tendangnhap, matkhau, hoten);
        }
        cursor.close();
        return nguoiDung;
    }*/
}
