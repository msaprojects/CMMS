package com.msadev.cmms.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
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
import com.msadev.cmms.Adapter.progressAdapter;
import com.msadev.cmms.Detail.d_Progress;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.Model.ProgressModel;
import com.msadev.cmms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.DATAPROGRESS;
import static com.msadev.cmms.Util.JsonResponse.JRES_ENGGINER;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDPENGGUNA;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDPROGRESS;
import static com.msadev.cmms.Util.JsonResponse.JRES_JAM;
import static com.msadev.cmms.Util.JsonResponse.JRES_MASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_NOMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_PERBAIKAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_SITE;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class L_Progress extends AppCompatActivity implements ListView.OnScrollListener {

    ListView listView;
    List<ProgressModel> progressModelList;
    progressAdapter adapter;
    FloatingActionButton fab;
    SwipeRefreshLayout refresh;
    SearchView searchView;
    MenuItem menuItem;
    Menu menu;
    private int preLast;

    String idmasalah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        MasalahModel mm = getIntent().getParcelableExtra(DATAMASALAH);
        idmasalah = mm.getIdmasalah();
        this.setTitle("Record Perbaikan Mesin "+mm.getNomesin());

        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        progressModelList = new ArrayList<>();

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        progressModelList.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProgressModel pm = progressModelList.get(position);
                Intent i = new Intent(getApplicationContext(), d_Progress.class );
                i.putExtra(DATAPROGRESS, pm);
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
                                    object.getString(JRES_JAM),
                                    object.getString(JRES_MASALAH),
                                    object.getString(JRES_IDMESIN),
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
