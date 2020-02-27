package com.msadev.cmms.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
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
import com.msadev.cmms.Adapter.KomponenAdapter;
import com.msadev.cmms.Detail.d_Komponen;
import com.msadev.cmms.Model.KomponenModel;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAKOMPONEN;
import static com.msadev.cmms.Util.JsonResponse.DATAMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDKOMPONEN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_JUMLAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_NAMA;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;
import static java.security.AccessController.getContext;

public class L_Komponen extends AppCompatActivity implements ListView.OnScrollListener{

    ListView listView;
    List<KomponenModel> komponenModelList;
    SwipeRefreshLayout refresh;
    FloatingActionButton fab;
    SearchView searchView;
    MenuItem menuItem;
    Menu menu;
    String idmesin, nomesin;
    KomponenAdapter adapter;
    KomponenModel km;
    private int preLast;
    String idkomponen;
    MesinModel mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);


        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        komponenModelList = new ArrayList<>();
        mm = getIntent().getParcelableExtra(DATAMESIN);
        nomesin = mm.getNomesin();
        idmesin = mm.getIdmesin();
        this.setTitle("Komponen Mesin "+nomesin);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        komponenModelList.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                KomponenModel km = komponenModelList.get(position);
                Intent i = new Intent(getApplicationContext(), d_Komponen.class );
                i.putExtra(DATAKOMPONEN, km);
                i.putExtra(DATAMESIN, mm);
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/komponen/"+idmesin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getString(TAG_RESULT).equalsIgnoreCase("[]")){
                        JSONArray array = obj.getJSONArray(TAG_RESULT);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            KomponenModel km = new KomponenModel(
                                    object.getString(JRES_IDKOMPONEN),
                                    object.getString(JRES_NAMA),
                                    object.getString(JRES_JUMLAH),
                                    object.getString(JRES_IDMESIN)
                            );
                            komponenModelList.add(km);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Komponen Masih Kosong!", Toast.LENGTH_LONG).show();
                    }
                    adapter = new KomponenAdapter(komponenModelList, getApplicationContext());
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
                    }
                }else{}
            } else {
                refresh.setEnabled(false);
            }
        }
    }
}
