package com.msadev.cmms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.List.L_Mesin;
import com.msadev.cmms.List.L_Reminder;
import com.msadev.cmms.List.L_Site;

import static com.msadev.cmms.Util.JsonResponse.DATAPILIHAN;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView tvJMasalah, tvJSelesai;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav);
        tvJMasalah = findViewById(R.id.tvJMasalah);
        tvJSelesai = findViewById(R.id.tvJSelesai);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.buka,R.string.tutup);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.Home);
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