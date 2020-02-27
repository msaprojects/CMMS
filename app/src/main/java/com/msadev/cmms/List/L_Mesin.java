package com.msadev.cmms.List;

import androidx.appcompat.app.AppCompatActivity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.msadev.cmms.Adapter.MesinAdapter;
import com.msadev.cmms.MainActivity;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;
import com.msadev.cmms.Tambah.i_Permasalahan;
import com.msadev.cmms.Trigger.MenuMesin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.msadev.cmms.Util.JsonResponse.DATAMESIN;
import static com.msadev.cmms.Util.JsonResponse.DATAPILIHAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_NOMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_SITE;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class L_Mesin extends AppCompatActivity implements ListView.OnScrollListener{

    ListView listView;
    List<MesinModel> mesinModelList;
    FloatingActionButton fab;
    SwipeRefreshLayout refresh;
    SearchView searchView;
    MenuItem menuItem;
    Menu menu;
    MesinAdapter adapter;
    private int preLast;
    String values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        this.setTitle("Pilih Mesin");

        Intent i = getIntent();
        values = i.getStringExtra(DATAPILIHAN);
        Toast.makeText(getApplicationContext(), values, Toast.LENGTH_LONG).show();

        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        mesinModelList = new ArrayList<>();

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        mesinModelList.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MesinModel mesinModel = mesinModelList.get(position);
                if (values.equals("From_Menu")) {
                    Intent i = new Intent(getApplicationContext(), MenuMesin.class);
                    i.putExtra(DATAMESIN, mesinModel);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), i_Permasalahan.class);
                    i.putExtra(DATAMESIN, mesinModel);
                    startActivity(i);
                }
            }
        });
        listView.setOnScrollListener((AbsListView.OnScrollListener) this);
        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionsearch, menu);
        menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
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
        return true;
    }

    private void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/mesin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getString(TAG_RESULT).equalsIgnoreCase("[]")) {
                        JSONArray array = obj.getJSONArray(TAG_RESULT);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            MesinModel mesinModel = new MesinModel(
                                    object.getString(JRES_IDMESIN),
                                    object.getString(JRES_NOMESIN),
                                    object.getString(JRES_SITE),
                                    object.getString(JRES_KETERANGAN)
                            );
                            mesinModelList.add(mesinModel);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Oops, Data Mesin masih kosong!", Toast.LENGTH_LONG).show();
                    }
                    adapter = new MesinAdapter(mesinModelList, getApplicationContext());
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
    public  void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(L_Mesin.this, MainActivity.class);
        startActivity(i);
        finish();
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
}