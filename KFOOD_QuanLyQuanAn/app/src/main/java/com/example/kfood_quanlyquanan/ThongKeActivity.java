package com.example.kfood_quanlyquanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kfood_quanlyquanan.Helper.FormatMoney;

import java.util.Date;

public class ThongKeActivity extends AppCompatActivity {

    TextView homnay, tuannay, thangnay, namnay;

    public void FindID()
    {
        homnay= findViewById(R.id.txtTongTienHomNay);
        tuannay= findViewById(R.id.txtTongTienTuanNay);
        thangnay= findViewById(R.id.txtTongTienThangNay);
        namnay= findViewById(R.id.txtTongTienNamNay);
    }

    public Double getTongTienHomNay()
    {
        Cursor cursor= MainActivity.db.GetData("SELECT SUM(CHITIETHD.SoLuong*Gia) FROM HOADON INNER JOIN CHITIETHD ON HOADON.ID==CHITIETHD.HoaDon_ID WHERE DATE(HOADON.NgayBan)= DATE('now', 'localtime')");
        cursor.moveToNext();
        Double val = cursor.getDouble(0);
        return val;
    }

    public Double getTongTienTuanNay()
    {
        Cursor cursor= MainActivity.db.GetData("SELECT SUM(CHITIETHD.SoLuong*Gia) FROM HOADON INNER JOIN CHITIETHD ON HOADON.ID==CHITIETHD.HoaDon_ID WHERE strftime('%W%m%Y', DATE(HOADON.NgayBan)) = strftime('%W%m%Y', 'now', 'localtime') ");
        cursor.moveToNext();
        Double val = cursor.getDouble(0);
        return val;
    }

    public Double getTongTienThangNay()
    {
        Cursor cursor= MainActivity.db.GetData("SELECT SUM(CHITIETHD.SoLuong*Gia) FROM HOADON INNER JOIN CHITIETHD ON HOADON.ID==CHITIETHD.HoaDon_ID WHERE strftime('%m%Y', DATE(HOADON.NgayBan)) = strftime('%m%Y', 'now', 'localtime') ");
        cursor.moveToNext();
        Double val = cursor.getDouble(0);
        return val;
    }

    public Double getTongTienNamNay()
    {
        Cursor cursor= MainActivity.db.GetData("SELECT SUM(CHITIETHD.SoLuong*Gia) FROM HOADON INNER JOIN CHITIETHD ON HOADON.ID==CHITIETHD.HoaDon_ID WHERE strftime('%Y', DATE(HOADON.NgayBan)) = strftime('%Y', 'now', 'localtime') ");
        cursor.moveToNext();
        Double val = cursor.getDouble(0);
        return val;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setTitle("Thống Kê");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FindID();
        homnay.setText(FormatMoney.GetMoneyVND(getTongTienHomNay()));
        tuannay.setText(FormatMoney.GetMoneyVND(getTongTienTuanNay()));
        thangnay.setText(FormatMoney.GetMoneyVND(getTongTienThangNay()));
        namnay.setText(FormatMoney.GetMoneyVND(getTongTienNamNay()));


    }

}