package com.msadev.cmms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.msadev.cmms.Adapter.KomponenAdapter;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.List.L_Mesin;
import com.msadev.cmms.List.L_Reminder;
import com.msadev.cmms.List.L_Site;
import com.msadev.cmms.Model.KomponenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.msadev.cmms.Util.JsonResponse.DATAPILIHAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDKOMPONEN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_JUMLAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_NAMA;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tvJMasalah, tvJSelesai;
    Intent i;
    private static final String TAG = "";
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav);
        tvJMasalah = findViewById(R.id.tvJMasalah);
        tvJSelesai = findViewById(R.id.tvJSelesai);
        loaddata();
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.buka,R.string.tutup);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.Home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic("topic").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = getString(R.string.msg_subscribed);
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
//                            msg = getString(R.string.msg_subscribe_failed);
                    return;
                }else{
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
                Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }
                token = task.getResult().getToken();
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
            }
        });

    }

    public void loaddata(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/dashboard", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getString(TAG_RESULT).equalsIgnoreCase("[]")){
                        JSONArray array = obj.getJSONArray(TAG_RESULT);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            tvJMasalah.setText(object.getString("jml_masalah"));
                            tvJSelesai.setText(object.getString("jml_selesai"));
//                            KomponenModel km = new KomponenModel(
//                                    object.getString(JRES_IDKOMPONEN),
//                                    object.getString(JRES_NAMA),
//                                    object.getString(JRES_JUMLAH),
//                                    object.getString(JRES_IDMESIN)
//                            );
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Data Kosong!", Toast.LENGTH_LONG).show();
                    }
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
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Mesin:
                i = new Intent(MainActivity.this, L_Mesin.class);
                i.putExtra(DATAPILIHAN, "From_Menu");
                startActivity(i);
                finish();
                break;
            case R.id.addMesin:
                i = new Intent(MainActivity.this, L_Site.class);
//                i.putExtra(DATAPILIHAN, "");
                startActivity(i);
                finish();
                break;
            case R.id.AddMasalah:
                i = new Intent(MainActivity.this, L_Mesin.class);
                i.putExtra(DATAPILIHAN, "");
                startActivity(i);
                finish();
                break;
            case R.id.Masalah:
                i = new Intent(MainActivity.this, L_Masalah.class);
                startActivity(i);
                finish();
                break;
            case R.id.reminder:
                i = new Intent(MainActivity.this, L_Reminder.class);
                startActivity(i);
                finish();
                break;
        }
        return false;
    }
}