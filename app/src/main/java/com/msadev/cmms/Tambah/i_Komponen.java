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
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;

import java.util.HashMap;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_JUMLAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_NAMA;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class i_Komponen extends AppCompatActivity implements View.OnClickListener {

    TextView tvMesin, tvSite;
    EditText etKomponen, etJumlah;
    Button btnSimpan;
    String idmesin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i__komponen);

        tvMesin = findViewById(R.id.tvNoMesin);
        tvSite = findViewById(R.id.tvSite);
        etKomponen = findViewById(R.id.etKomponen);
        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(this);

        MesinModel mm =getIntent().getParcelableExtra(DATAMESIN);
        idmesin = mm.getIdmesin();
        tvMesin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());

    }

    private void tambahData(){
        final String komponen = etKomponen.getText().toString();
        final String jumlah = etJumlah.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.POST, IPADDRESS + "/komponen", new Response.Listener<String>() {
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
                params.put(JRES_NAMA, komponen);
                params.put(JRES_JUMLAH, jumlah);
                params.put(JRES_IDMESIN, idmesin);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            String komponen = etKomponen.getText().toString().trim();
            String jumlah = etJumlah.getText().toString().trim();
            if ((komponen.equals(""))||(jumlah.equals(""))){
                Toast.makeText(getApplicationContext(), "Kolom Harus Diisi!", Toast.LENGTH_LONG).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(i_Komponen.this);
                builder.setMessage("Pastikan Data yang anda masukkan benar!");
                builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tambahData();
                        Intent intent = new Intent(i_Komponen.this, MainActivity.class);
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
