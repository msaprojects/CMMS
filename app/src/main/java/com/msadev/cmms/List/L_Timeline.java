package com.msadev.cmms.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.Adapter.CheckoutAdapter;
import com.msadev.cmms.Adapter.CheckoutAdapter2;
import com.msadev.cmms.Adapter.progressAdapter;
import com.msadev.cmms.Model.CheckoutModel;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.Model.ProgressModel;
import com.msadev.cmms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_BARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_ENGGINER;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDBARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDCHECKOUT;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDPENGGUNA;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDPROGRESS;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDSATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_JAM;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KODE;
import static com.msadev.cmms.Util.JsonResponse.JRES_MASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_NOMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_PERBAIKAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_QTY;
import static com.msadev.cmms.Util.JsonResponse.JRES_SATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_SITE;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class L_Timeline extends AppCompatActivity implements ListView.OnScrollListener {

    ListView listView, listView2;
    List<ProgressModel> progressModelList;
    progressAdapter adapter;
    MasalahModel mm;
    CheckoutAdapter2 adapter2;
    List<CheckoutModel> checkoutModelList;
    SwipeRefreshLayout refresh;
    TextView tvmesin, tvSite, tvTanggal,tvJam, tvMasalah, tvTanggalP, tvKeterangan;
    String idmasalah;
    private int preLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l__timeline);
        this.setTitle("Progress Penyelesaian");

        MasalahModel mm = getIntent().getParcelableExtra(DATAMASALAH);
        idmasalah = mm.getIdmasalah();

        tvmesin = (TextView) findViewById(R.id.tvNoMesin);
        tvSite = (TextView) findViewById(R.id.tvSite);
//        refresh = findViewById(R.id.refresh);
        tvTanggal = (TextView) findViewById(R.id.tvTanggal);
        tvJam = (TextView) findViewById(R.id.tvJam);
        tvMasalah = (TextView) findViewById(R.id.tvMasalah);
        tvTanggalP = (TextView) findViewById(R.id.tvTanggalP);
        tvKeterangan = (TextView) findViewById(R.id.tvKeterangan);
        listView = (ListView) findViewById(R.id.listview);
        listView2 = (ListView) findViewById(R.id.listview2);
        progressModelList = new ArrayList<>();
        checkoutModelList = new ArrayList<>();
        loadData();
        loadDataBarang();

        tvmesin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());
        tvTanggal.setText(mm.getTanggal());
        tvJam.setText(mm.getJam());
        tvMasalah.setText(mm.getMasalah());
        listView.setOnScrollListener((AbsListView.OnScrollListener) this);
//        tvTanggalP.setText(mm.gett());
//        tvKeterangan.setText(mm.getMasalah());
//        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refresh.setRefreshing(false);
//                        progressModelList.clear();
//                        loadData();
//                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
//                    }
//                }, 3000);
//            }
//        });
    }

    private void loadData(){
//        Log.d("HASIL JSON ", IPADDRESS + "/progress1/"+idmasalah);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/progress1/"+idmasalah, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getString(TAG_RESULT).equalsIgnoreCase("[]")) {
                        JSONArray array = obj.getJSONArray(TAG_RESULT);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ProgressModel pm = new ProgressModel(
                                    object.getString(JRES_IDPROGRESS),
                                    object.getString(JRES_PERBAIKAN),
                                    object.getString(JRES_ENGGINER),
                                    object.getString(JRES_TANGGAL),
                                    object.getString(JRES_SHIFT),
                                    object.getString(JRES_IDMASALAH),
                                    object.getString(JRES_IDPENGGUNA),
//                                    object.getString(JRES_JAM),
                                    object.getString(JRES_MASALAH),
//                                    object.getString(JRES_IDMESIN),
                                    object.getString(JRES_NOMESIN),
                                    object.getString(JRES_SITE)
                            );
                            progressModelList.add(pm);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Oops, Belum Ada Progress Perbaikan", Toast.LENGTH_LONG).show();
                    }
                    adapter = new progressAdapter(progressModelList, getApplicationContext());
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

    private void loadDataBarang(){
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
                        Toast.makeText(getApplicationContext(), "Oops, Data Mesin masih kosong!", Toast.LENGTH_LONG).show();
                    }
                    adapter2 = new CheckoutAdapter2(checkoutModelList, getApplicationContext());
                    listView2.setAdapter(adapter2);
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
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (view.getId() == R.id.listview) {
//            if (firstVisibleItem == 0) {
//                refresh.setEnabled(true);
                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        preLast = lastItem;
                        //Toast.makeText(getActivity(), "In Last", Toast.LENGTH_SHORT).show();
                    }
                }else{}
//            } else {
//                refresh.setEnabled(false);
//            }
        }
    }
}
