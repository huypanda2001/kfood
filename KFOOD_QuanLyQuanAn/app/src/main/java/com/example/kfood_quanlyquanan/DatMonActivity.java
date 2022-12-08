package com.example.kfood_quanlyquanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kfood_quanlyquanan.Adapter.ThucDonAdapter;
import com.example.kfood_quanlyquanan.Models.CHITIETHD;
import com.example.kfood_quanlyquanan.Models.HOADON;
import com.example.kfood_quanlyquanan.Models.MONAN;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class DatMonActivity extends AppCompatActivity {

    ListView lstDatMon;
    TextView t1;
    Button btnOK, btnHuy;
    HOADON h;
    ArrayList<CHITIETHD> details;
    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_mon);
        setTitle("Chọn Món Ăn");
        lstDatMon= findViewById(R.id.lstDatMon);


        final ArrayList<MONAN> monans= new ArrayList<MONAN>();
        Cursor cursorMA= MainActivity.db.GetData("SELECT * FROM MONAN");
        while (cursorMA.moveToNext())
        {
            MONAN tmp= new MONAN();
            tmp.ID=cursorMA.getString(0);
            tmp.TenMon=cursorMA.getString(1);
            tmp.DVT=cursorMA.getString(2);
            tmp.Gia=Double.parseDouble(cursorMA.getString(3));
            monans.add(tmp);
        }

        ThucDonAdapter adapter= new ThucDonAdapter(DatMonActivity.this, R.layout.row_monan, monans);
        lstDatMon.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        Button btnTaoHD= findViewById(R.id.btnTaoHD);
        btnTaoHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText soban= findViewById(R.id.txtSoBan);
                if(soban.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(DatMonActivity.this, "Bạn phải nhập số bàn", Toast.LENGTH_LONG).show();
                    return;
                }
                if(details.size()<1)
                {
                    Toast.makeText(DatMonActivity.this, "Bạn phải chọn ít nhất 1 món", Toast.LENGTH_LONG).show();
                    return;
                }
                h.SoBan= Integer.parseInt(soban.getText().toString());
                MainActivity.db.AddHoaDon(h);
                for(int i=0;i<details.size();i++)
                {
                    MainActivity.db.AddChiTietHD(details.get(i));
                }
                Toast.makeText(DatMonActivity.this, "Xong", Toast.LENGTH_LONG).show();
                Intent intent3= new Intent(DatMonActivity.this, ChuaThanhToanActivity.class);
                //  intent.putExtra("lstMA", monans);
                startActivity(intent3);
            }
        });

        details= new ArrayList<>();
        h = new HOADON();

        try {
            h.NgayBan= curFormater.parse(curFormater.format(Calendar.getInstance().getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        h.TrangThai= -1;

        lstDatMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final MONAN m= monans.get(i);


                //Tao Hoa don


               final Dialog dialog= new Dialog(DatMonActivity.this);

               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.input_layout);
                t1= dialog.findViewById(R.id.txtTenMonPreview);
                btnOK= dialog.findViewById(R.id.btnOK);
                btnHuy= dialog.findViewById(R.id.btnHuy);
                t1.setText(m.TenMon);
               dialog.show();

               btnHuy.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       dialog.dismiss();
                   }
               });
               btnOK.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       EditText t2= dialog.findViewById(R.id.txtSlDat);
                       CHITIETHD c= new CHITIETHD();
                       c.SoLuong= Integer.parseInt(t2.getText()+"");
                       c.HoaDon_ID= h.ID;
                       c.MonAn_ID= m.ID;
                       c.Gia= m.Gia;
                       details.add(c);
                       dialog.dismiss();
                   }
               });




            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.navTroVe:
                Intent intent= new Intent(DatMonActivity.this, ChuaThanhToanActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}