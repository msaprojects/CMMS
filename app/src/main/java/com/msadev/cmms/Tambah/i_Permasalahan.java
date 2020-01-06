package com.msadev.cmms.Tambah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;
import com.msadev.cmms.Util.RequestHandler;

import java.util.HashMap;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAMESIN;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class i_Permasalahan extends AppCompatActivity implements View.OnClickListener {

    TextView tvMesin, tvSite;
    EditText etKeterangan, etEngginer, etShift;
    Button btnSimpan;
    String idmesin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i__permasalahan);
        this.setTitle("Masukkan Permasalahan");

        etKeterangan = (EditText) findViewById(R.id.etKeterangan);
        etEngginer = (EditText) findViewById(R.id.etEngginer);
        etShift = (EditText) findViewById(R.id.etShift);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        tvMesin = (TextView) findViewById(R.id.tvNoMesin);
        tvSite = (TextView) findViewById(R.id.tvSite);
        btnSimpan.setOnClickListener(this);

        MesinModel mm =getIntent().getParcelableExtra(DATAMESIN);
        idmesin = mm.getIdmesin();
        tvMesin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());


    }

    private void tambahData(){
        final String keterangan = etKeterangan.getText().toString();
        final String engginer = etEngginer.getText().toString();
        final String shift = etShift.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.POST, IPADDRESS + "/masalah", new Response.Listener<String>() {
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
                params.put("keterangan", keterangan);
                params.put("shift", shift);
                params.put("engginer", engginer);
                params.put("idmesin", idmesin);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            tambahData();
        }
    }
}
