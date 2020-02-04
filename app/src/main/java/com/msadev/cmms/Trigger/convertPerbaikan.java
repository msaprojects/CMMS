package com.msadev.cmms.Trigger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.Edit.e_Permasalahan;
import com.msadev.cmms.List.L_Barang;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.List.L_Progress;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.Tambah.i_Checkout;
import com.msadev.cmms.Tambah.i_Penyelesaian;
import com.msadev.cmms.Tambah.i_Progress;

import java.util.HashMap;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.MENUPERBAIKAN;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class convertPerbaikan extends AppCompatActivity {

    String item, idmasalah;
    MasalahModel mm;

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        item = intent.getStringExtra(MENUPERBAIKAN);

        mm = getIntent().getParcelableExtra(DATAMASALAH);

        if (item.equals("Tambah Progress")){
            intent = new Intent(getApplicationContext(), i_Progress.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }else if (item.equals("Lihat Progress")) {
            intent = new Intent(getApplicationContext(), L_Progress.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }else if (item.equals("Edit Masalah")){
            intent = new Intent(getApplicationContext(), e_Permasalahan.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }else if (item.equals("Tambah Penyelesaian Masalah")){
            intent = new Intent(getApplicationContext(), i_Penyelesaian.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }else if (item.equals("Hapus Penyelesaian Masalah")){
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Pastikan Data yang anda masukkan benar!");
            builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    hapusData();
                    Intent intent = new Intent(getApplicationContext(), L_Masalah.class);
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
        }else {
            Toast.makeText(getApplicationContext(), "Menu yang anda pilih tidak sesuai...", Toast.LENGTH_SHORT).show();
        }
    }
    private void hapusData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.DELETE, IPADDRESS + "/hapuspenyelesaian", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(JRES_IDMASALAH, idmasalah);
                return params;
            }
        };
        queue.add(tambah);
    }
}
