package com.msadev.cmms.Detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.MainActivity;
import com.msadev.cmms.Model.KomponenModel;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;

import java.util.HashMap;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAKOMPONEN;
import static com.msadev.cmms.Util.JsonResponse.DATAMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDKOMPONEN;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class d_Komponen extends AppCompatActivity implements View.OnClickListener {

    TextView tvNama, tvJumlah, tvMensin, tvSite;
    Button btnHapus;
    String idkomponen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d__komponen);
        getSupportActionBar().setTitle("Detail Komponen");

        KomponenModel km =getIntent().getParcelableExtra(DATAKOMPONEN);
        MesinModel mm =getIntent().getParcelableExtra(DATAMESIN);

        tvNama = findViewById(R.id.tvNamaKomponen);
        tvJumlah = findViewById(R.id.tvJumlahKomponen);
        tvMensin = findViewById(R.id.tvNoMesin);
        tvSite = findViewById(R.id.tvSite);
        btnHapus = findViewById(R.id.btnHapus);
        btnHapus.setOnClickListener(this);

        idkomponen = km.getIdkomponen();

        tvNama.setText(km.getNama());
        tvJumlah.setText(km.getJumlah());
        tvMensin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());

    }

    private void hapusData(){
        Log.d("Link Hapus", IPADDRESS+"/hapuskomponen/"+idkomponen);
        StringRequest tambah = new StringRequest(Request.Method.GET, IPADDRESS + "/hapuskomponen/"+idkomponen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
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
                params.put(JRES_IDKOMPONEN, idkomponen);
//                params.put(JRES_IDMASALAH, idmasalah);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnHapus) {
            AlertDialog.Builder builder = new AlertDialog.Builder((d_Komponen.this));
            builder.setMessage("Apakah Anda Yakin Menghapus Komponen ");
            builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    hapusData();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i=new Intent(Intent.ACTION_MAIN);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }
}
