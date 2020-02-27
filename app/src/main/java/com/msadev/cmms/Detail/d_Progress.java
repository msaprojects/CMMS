package com.msadev.cmms.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.msadev.cmms.Model.ProgressModel;
import com.msadev.cmms.R;

import static com.msadev.cmms.Util.JsonResponse.DATAPROGRESS;

public class d_Progress extends AppCompatActivity {

    TextView tvnomesin, tvtanggal, tvjam, tvsite, tvengginer, tvshift, tvMasalah, tvperbaikan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d__progress);
        this.setTitle("Detail Progress Perbaikan");

        tvnomesin = (TextView) findViewById(R.id.tvNoMesin);
        tvtanggal = (TextView) findViewById(R.id.tvTanggal);
        tvjam = (TextView) findViewById(R.id.tvJam);
        tvsite = (TextView) findViewById(R.id.tvSite);
        tvengginer = (TextView) findViewById(R.id.tvEngginer);
        tvshift = (TextView) findViewById(R.id.tvShift);
        tvMasalah = (TextView) findViewById(R.id.tvMasalah);
        tvperbaikan = (TextView) findViewById(R.id.tvPerbaikan);

        ProgressModel pm = getIntent().getParcelableExtra(DATAPROGRESS);
        tvnomesin.setText(pm.getNomesin());
        tvtanggal.setText(pm.getTanggal());
        tvjam.setText(pm.getJam());
        tvsite.setText(pm.getSite());
        tvengginer.setText(pm.getEngginer());
        tvshift.setText(pm.getShift());
        tvMasalah.setText(pm.getMasalah());
        tvperbaikan.setText(pm.getPerbaikan());
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
