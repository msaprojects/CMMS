package com.msadev.cmms.Tambah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.Model.BarangModel;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATABARANG;
import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_ENGGINER;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDBARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDSATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_PERBAIKAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_QTY;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class i_Checkout extends AppCompatActivity implements View.OnClickListener {

    TextView tvBarang, tvSatuan, tvMasalah, tvKode;
    EditText etQty, etTanggal, etKeterangan;
    Button btnsSimpan;
    MasalahModel mm;
    BarangModel bm;
    String idmasalah, idbarang, idsatuan, satuan;
    Calendar kalender;
    DatePickerDialog.OnDateSetListener tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i__checkout);

        tvKode = (TextView) findViewById(R.id.tvKode);
        tvBarang = (TextView) findViewById(R.id.tvBarang);
        tvSatuan = (TextView) findViewById(R.id.tvSatuan);
        tvMasalah = (TextView) findViewById(R.id.tvMasalah);
        etQty = (EditText) findViewById(R.id.etQty);
        etTanggal = (EditText) findViewById(R.id.etTanggal);
        etKeterangan = (EditText) findViewById(R.id.etKeterangan);
        btnsSimpan = (Button) findViewById(R.id.btnSimpan);

        btnsSimpan.setOnClickListener(this);
        mm = getIntent().getParcelableExtra(DATAMASALAH);
        bm = getIntent().getParcelableExtra(DATABARANG);
        idmasalah = mm.getIdmasalah();
        idbarang = bm.getIdbarang();
        idsatuan = bm.getIdsatuan();
        tvKode.setText(bm.getKode());
        tvBarang.setText(bm.getNama());
        tvSatuan.setText(bm.getSatuan());
        tvMasalah.setText(mm.getMasalah());
        satuan = bm.getSatuan();
        Toast.makeText(getApplicationContext(), satuan+" - "+idmasalah+" - "+idbarang, Toast.LENGTH_LONG).show();
        //DATE PICKER
        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MM-yyyy");
        Date Tanggal = new Date();
        etTanggal.setText(formatTanggal.format(Tanggal));

        kalender = Calendar.getInstance();
        tanggal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, month);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String FormatTanggal = "dd-MM-yyy";
                SimpleDateFormat sdf = new SimpleDateFormat(FormatTanggal, Locale.US);
                etTanggal.setText(sdf.format(kalender.getTime()));
            }
        };
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getApplicationContext(), tanggal,
                        kalender.get(Calendar.YEAR),
                        kalender.get(Calendar.MONTH),
                        kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //END DATE PICKER

    }

    private void tambahData(){
        final String qty = etQty.getText().toString();
        final String tanggal = etTanggal.getText().toString();
        final String keterangan = etKeterangan.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.POST, IPADDRESS + "/checkout", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(JRES_IDMASALAH, idmasalah);
                params.put(JRES_IDBARANG, idbarang);
                params.put(JRES_IDSATUAN, satuan);
                params.put(JRES_QTY, qty);
                params.put(JRES_TANGGAL, tanggal);
                params.put(JRES_KETERANGAN, keterangan);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnsSimpan){
            String qty = etQty.getText().toString().trim();
            String ket = etKeterangan.getText().toString().trim();
            if ((qty.equals(""))||(ket.equals(""))){
                Toast.makeText(getApplicationContext(), "Kolom Harus Diisi!", Toast.LENGTH_LONG).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(i_Checkout.this);
                builder.setMessage("Pastikan Data yang anda masukkan benar!");
                builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tambahData();
                        Intent intent = new Intent(getApplicationContext(), i_Penyelesaian.class);
                        intent.putExtra(DATAMASALAH, mm);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cek Lagi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
}
