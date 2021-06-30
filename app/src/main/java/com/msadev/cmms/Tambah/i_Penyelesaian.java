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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msadev.cmms.Adapter.CheckoutAdapter;
import com.msadev.cmms.List.L_Barang;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.Model.CheckoutModel;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_BARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDBARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDCHECKOUT;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDSATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KODE;
import static com.msadev.cmms.Util.JsonResponse.JRES_QTY;
import static com.msadev.cmms.Util.JsonResponse.JRES_SATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class i_Penyelesaian extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab;
    TextView tvMesin, tvSite, tvMasalah;
    ListView listView;
    Intent intent;
    EditText etTanggal, etKeterangan;
    Calendar kalender;
    DatePickerDialog.OnDateSetListener tanggal;
    Button btnSimpan;
    SwipeRefreshLayout refresh;

    String idmasalah;
    MasalahModel mm;
    CheckoutAdapter adapter;
    List<CheckoutModel> checkoutModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i__penyelesaian);
        this.setTitle("Penyelesaian");

        listView = (ListView) findViewById(R.id.listview);
        tvMesin = (TextView) findViewById(R.id.tvNoMesin);
        tvSite = (TextView) findViewById(R.id.tvSite);
        tvMasalah = (TextView) findViewById(R.id.tvMasalah);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        etTanggal = (EditText) findViewById(R.id.etTanggal);
        etKeterangan = (EditText) findViewById(R.id.etKeterangan);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        mm = getIntent().getParcelableExtra(DATAMASALAH);
        idmasalah = mm.getIdmasalah();
        tvMesin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());
        tvMasalah.setText(mm.getMasalah());

        fab.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);

        //DATE PICKER
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        Date Tanggal = new Date();
        etTanggal.setText(formatTanggal.format(Tanggal));

        kalender = Calendar.getInstance();
        tanggal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, month);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String FormatTanggal = "yyyy-MM-dd";
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

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        checkoutModelList.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

        checkoutModelList = new ArrayList<>();
        loadData();

    }

    private void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/checkout/"+idmasalah, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getString(TAG_RESULT).equalsIgnoreCase("[]")) {
                        JSONArray array = obj.getJSONArray(TAG_RESULT);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                             CheckoutModel cm = new CheckoutModel(
                                    object.getString(JRES_IDCHECKOUT),
                                     object.getString(JRES_IDMASALAH),
                                     object.getString(JRES_IDBARANG),
                                     object.getString(JRES_IDSATUAN),
                                     object.getString(JRES_TANGGAL),
                                     object.getString(JRES_KETERANGAN),
                                     object.getString(JRES_QTY),
                                     object.getString(JRES_BARANG),
                                     object.getString(JRES_KODE),
                                     object.getString(JRES_SATUAN)
                            );
                            checkoutModelList.add(cm);
                        }
                    }else {
//                        Toast.makeText(getApplicationContext(), "Oops, Data Mesin masih kosong!", Toast.LENGTH_LONG).show();
                    }
                    adapter = new CheckoutAdapter(checkoutModelList, getApplicationContext());
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void tambahData(){

        final String tanggal = etTanggal.getText().toString();
        final String keterangan = etKeterangan.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.POST, IPADDRESS + "/penyelesaian", new Response.Listener<String>() {
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
                params.put(JRES_TANGGAL, tanggal);
                params.put(JRES_KETERANGAN, keterangan);
                params.put(JRES_IDMASALAH, idmasalah);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == fab){
            intent = new Intent(getApplicationContext(), L_Barang.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }
        if (view == btnSimpan){
            String tgl = etTanggal.getText().toString().trim();
            String ket = etKeterangan.getText().toString().trim();
            if ((tgl.equals(""))||(ket.equals(""))){
                Toast.makeText(getApplicationContext(), "Kolom Harus Diisi", Toast.LENGTH_LONG).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(i_Penyelesaian.this);
                builder.setMessage("Pastikan Data yang anda masukkan benar!");
                builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tambahData();
                        Intent intent = new Intent(i_Penyelesaian.this, L_Masalah.class);
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
