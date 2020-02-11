package com.msadev.cmms.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msadev.cmms.Adapter.TimelineAdapter;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.Model.TimelineModel;
import com.msadev.cmms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_BARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_ENGGINER;
import static com.msadev.cmms.Util.JsonResponse.JRES_JAM;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGANCHECKOUT;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGANSELESAI;
import static com.msadev.cmms.Util.JsonResponse.JRES_KODE;
import static com.msadev.cmms.Util.JsonResponse.JRES_MASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_PERBAIKAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_QTY;
import static com.msadev.cmms.Util.JsonResponse.JRES_SATUAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFTPROG;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGALPROG;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGALSELESAI;
import static com.msadev.cmms.Util.JsonResponse.JRES_TIPE;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class L_Timeline2 extends AppCompatActivity implements ListView.OnScrollListener {

    ListView listView;
    List<TimelineModel> timelineModelList;
    TimelineAdapter adapter;
    FloatingActionButton fab;
    SwipeRefreshLayout refresh;
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

        timelineModelList = new ArrayList<>();

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        timelineModelList.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                ProgressModel pm = progressModelList.get(position);
//                Intent i = new Intent(getApplicationContext(), d_Progress.class );
//                i.putExtra(DATAPROGRESS, pm);
//                startActivity(i);
//            }
//        });
        listView.setOnScrollListener((AbsListView.OnScrollListener) this);
        loadData();

    }

    private void loadData(){
//        Log.d("HASIL JSON ", IPADDRESS + "/progress1/"+idmasalah);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/timeline/"+idmasalah, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getString(TAG_RESULT).equalsIgnoreCase("[]")) {
                        JSONArray array = obj.getJSONArray(TAG_RESULT);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            TimelineModel pm = new TimelineModel(
                                    object.getString(JRES_TIPE),
                                    object.getString(JRES_JAM),
                                    object.getString(JRES_TANGGAL),
                                    object.getString(JRES_MASALAH),
                                    object.getString(JRES_SHIFT),
                                    object.getString(JRES_PERBAIKAN),
                                    object.getString(JRES_ENGGINER),
                                    object.getString(JRES_TANGGALPROG),
                                    object.getString(JRES_SHIFTPROG),
                                    object.getString(JRES_TANGGALSELESAI),
                                    object.getString(JRES_KETERANGANSELESAI),
                                    object.getString(JRES_KODE),
                                    object.getString(JRES_BARANG),
                                    object.getString(JRES_SATUAN),
                                    object.getString(JRES_QTY),
                                    object.getString(JRES_KETERANGANCHECKOUT)
                            );
                            timelineModelList.add(pm);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Oops, Belum Ada Progress Perbaikan", Toast.LENGTH_LONG).show();
                    }
                    adapter = new TimelineAdapter(timelineModelList, getApplicationContext());
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
