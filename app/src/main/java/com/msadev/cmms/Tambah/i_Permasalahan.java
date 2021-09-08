package com.msadev.cmms.Tambah;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.Edit.e_Permasalahan;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;
import com.msadev.cmms.Util.RequestHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_JAM;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_MASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class i_Permasalahan extends AppCompatActivity implements View.OnClickListener {

    TextView tvMesin, tvSite;
    EditText etMasalah, etJam, etTanggal, etShift;
    Button btnSimpan;
    String idmesin;
    Calendar kalender;
    TimePickerDialog time;
    DatePickerDialog.OnDateSetListener tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i__permasalahan);
        this.setTitle("Masukkan Permasalahan");

        etMasalah = (EditText) findViewById(R.id.etMasalah);
        etTanggal = (EditText) findViewById(R.id.etTanggal);
        etJam = (EditText) findViewById(R.id.etJam);
        etShift = (EditText) findViewById(R.id.etShift);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        tvMesin = (TextView) findViewById(R.id.tvNoMesin);
        tvSite = (TextView) findViewById(R.id.tvSite);
        btnSimpan.setOnClickListener(this);

        MesinModel mm =getIntent().getParcelableExtra(DATAMESIN);
        idmesin = mm.getIdmesin();
        tvMesin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());

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
                new DatePickerDialog(i_Permasalahan.this, tanggal,
                        kalender.get(Calendar.YEAR),
                        kalender.get(Calendar.MONTH),
                        kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });
        //END DATE PICKER


    }

    private void showTimeDialog() {
        kalender = Calendar.getInstance();
        time = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etJam.setText(hourOfDay+":"+minute);
            }
        },
        kalender.get(Calendar.HOUR_OF_DAY), kalender.get(Calendar.MINUTE),
        DateFormat.is24HourFormat(this));
        time.show();
    }

    private void tambahData(){
        final String masalah = etMasalah.getText().toString();
        final String tanggal = etTanggal.getText().toString();
        final String jam = etJam.getText().toString();
        final String shift = etShift.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.POST, IPADDRESS + "/masalah", new Response.Listener<String>() {
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
                params.put(JRES_MASALAH, masalah);
                params.put(JRES_SHIFT, shift);
                params.put(JRES_TANGGAL, tanggal);
                params.put(JRES_JAM, jam);
                params.put(JRES_IDMESIN, idmesin);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            String masalah = etMasalah.getText().toString().trim();
            String tgl = etTanggal.getText().toString().trim();
            String jam = etJam.getText().toString().trim();
            String shift = etShift.getText().toString().trim();
            if ((masalah.equals(""))||(tgl.equals(""))||(jam.equals(""))||(shift.equals(""))){
                Toast.makeText(getApplicationContext(), "Kolom Harus Diisi!", Toast.LENGTH_LONG).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(i_Permasalahan.this);
                builder.setMessage("Pastikan Data yang anda masukkan benar!");
                builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tambahData();
                        Intent intent = new Intent(i_Permasalahan.this, L_Masalah.class);
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
