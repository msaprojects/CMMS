package com.msadev.cmms.Tambah;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.MainActivity;
import com.msadev.cmms.Model.SiteModel;
import com.msadev.cmms.R;

import java.util.HashMap;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATASITE;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDSITE;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_NOMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class i_Mesin extends AppCompatActivity implements View.OnClickListener {

    EditText etNoMesin, etKeterangan;
    TextView tvSite;
    Button btnSimpan;
    String idsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i__mesin);

        SiteModel mm = getIntent().getParcelableExtra(DATASITE);
        idsite = mm.getIdsite();

        etNoMesin = findViewById(R.id.etNoMesin);
        etKeterangan = findViewById(R.id.etKeterangan);
        tvSite = findViewById(R.id.tvSite);
        tvSite.setText(mm.getNama());
        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(this);
    }
    private void tambahData(){
        final String nomesin = etNoMesin.getText().toString();
        final String keterangan = etKeterangan.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.POST, IPADDRESS + "/mesin", new Response.Listener<String>() {
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
                params.put(JRES_NOMESIN, nomesin);
                params.put(JRES_KETERANGAN, keterangan);
                params.put(JRES_IDSITE, idsite);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            String nomesin  = etNoMesin.getText().toString().trim();
            String keterangan  = etKeterangan.getText().toString().trim();
            if ((nomesin.equals(""))||(keterangan.equals(""))){
                Toast.makeText(getApplicationContext(), "Kolom Harus Diisi!", Toast.LENGTH_LONG).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(i_Mesin.this);
                builder.setMessage("Pastikan Data yang anda masukkan benar!");
                builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tambahData();
                        Intent intent = new Intent(i_Mesin.this, MainActivity.class);
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
