package com.msadev.cmms.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msadev.cmms.Adapter.BarangAdapter;
import com.msadev.cmms.Model.BarangModel;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.R;
import com.msadev.cmms.Tambah.i_Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.msadev.cmms.Util.JsonResponse.DATABARANG;
import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDBARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDSATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KODE;
import static com.msadev.cmms.Util.JsonResponse.JRES_NAMA;
import static com.msadev.cmms.Util.JsonResponse.JRES_SATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_UMURPAKAI;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class L_Barang extends AppCompatActivity implements ListView.OnScrollListener {

    ListView listView;
    List<BarangModel> barangModelList;
    SwipeRefreshLayout refresh;
    FloatingActionButton fab;
    SearchView searchView;
    MenuItem menuItem;
    Menu menu;
    String idmasalah, nomesin;
    BarangAdapter adapter;
    MasalahModel mm;
    private int preLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        this.setTitle("Barang");

        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        barangModelList = new ArrayList<>();

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        barangModelList.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BarangModel bm = barangModelList.get(position);
                mm = getIntent().getParcelableExtra(DATAMASALAH);
                Intent i = new Intent(getApplicationContext(), i_Checkout.class );
                i.putExtra(DATABARANG, bm);
                i.putExtra(DATAMASALAH, mm);
                startActivity(i);
            }
        });
        listView.setOnScrollListener((AbsListView.OnScrollListener) this);
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionsearch, menu);
        menuItem = menu.findItem(R.id.search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) menuItem.getActionView();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    if (TextUtils.isEmpty(s)){
                        adapter.filter("");
                        listView.clearTextFilter();
                    }else {
                        adapter.filter(s);
                    }
                    return true;
                }
            });
        }
        return true;
    }

    private void loadData(){
        Log.d("Url Barang", IPADDRESS+"/barang");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/barang", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
//                    if (!obj.getString(TAG_RESULT).equalsIgnoreCase("[]")){
                        JSONArray array = obj.getJSONArray(TAG_RESULT);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            BarangModel bm = new BarangModel(
                                    object.getString(JRES_IDBARANG),
                                    object.getString(JRES_KODE),
                                    object.getString(JRES_NAMA),
                                    object.getString(JRES_UMURPAKAI),
                                    object.getString(JRES_KETERANGAN),
                                    object.getString(JRES_IDSATUAN),
                                    object.getString(JRES_SATUAN)
                            );
                            barangModelList.add(bm);
                        }
//                    }else {
//                        Toast.makeText(getApplicationContext(), "Tenang, Belum ada masalah!", Toast.LENGTH_LONG).show();
//                    }
                    adapter = new BarangAdapter(barangModelList, getApplicationContext());
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
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (view.getId() == R.id.listview) {
            if (firstVisibleItem == 0) {
                refresh.setEnabled(true);
                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        preLast = lastItem;
                        //Toast.makeText(getActivity(), "In Last", Toast.LENGTH_SHORT).show();
                    }
                }else{}
            } else {
                refresh.setEnabled(false);
            }
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
