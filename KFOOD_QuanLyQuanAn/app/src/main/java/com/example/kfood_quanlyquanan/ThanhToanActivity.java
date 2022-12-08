package com.example.kfood_quanlyquanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kfood_quanlyquanan.Adapter.ThanhToanAdapter;
import com.example.kfood_quanlyquanan.Helper.FormatMoney;
import com.example.kfood_quanlyquanan.Models.HOADON;
import com.example.kfood_quanlyquanan.Models.HOADONMODEL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {

    TextView soHD, Ngay, TongTien;
    Button XacNhan;
    ListView lstChiTiet;

    public void FindID()
    {
        soHD= findViewById(R.id.tvSoHD);
        Ngay= findViewById(R.id.tvNgayDat);
        TongTien= findViewById(R.id.tvTongTien);
        XacNhan= findViewById(R.id.btnXacNhan);
        lstChiTiet= findViewById(R.id.lstChiTiet);
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        setTitle("Thanh Toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final HOADON hd = (HOADON) getIntent().getSerializableExtra("hoadon");
        Double tongTien = (Double) getIntent().getSerializableExtra("TongTien");
        ArrayList<HOADONMODEL> hoadonmodels = (ArrayList<HOADONMODEL>) getIntent().getSerializableExtra("hoadonmodels");
        FindID();
        soHD.setText(hd.ID);
        Ngay.setText(dateFormat.format(hd.NgayBan));
        TongTien.setText(FormatMoney.GetMoneyVND(tongTien));
        ThanhToanAdapter adapter= new ThanhToanAdapter(ThanhToanActivity.this, R.layout.chitiet_row, hoadonmodels);
        lstChiTiet.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    MainActivity.db.ExcuteNonQuery("Update HOADON set TrangThai=1 WHERE ID='"+hd.ID+"'");
                    Intent intent3= new Intent(ThanhToanActivity.this, ChuaThanhToanActivity.class);
                    startActivity(intent3);
            }
        });

    }


}