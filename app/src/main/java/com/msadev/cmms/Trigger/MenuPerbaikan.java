package com.msadev.cmms.Trigger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msadev.cmms.Edit.e_Permasalahan;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.List.L_Progress;
import com.msadev.cmms.List.L_Timeline;
import com.msadev.cmms.List.L_Timeline2;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.R;
import com.msadev.cmms.Tambah.i_Penyelesaian;
import com.msadev.cmms.Tambah.i_Progress;

import java.util.HashMap;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.MENUPERBAIKAN;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class MenuPerbaikan extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fab;

    ArrayAdapter<String> adapter;
    Intent intent;
    String idmasalah;
    MasalahModel mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        mm = getIntent().getParcelableExtra(DATAMASALAH);
        idmasalah = mm.getIdmasalah();
        this.setTitle("Action masalah Mesin "+mm.getNomesin());


        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        if (mm.getStatus().equals("0")){
            String menu[] = {
                    "Edit Masalah", "Timeline Masalah", "Tambah Progress", "Lihat Progress", "Tambah Penyelesaian Masalah"
            };
            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, menu);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listSelected);
        }else if (mm.getStatus().equals("1")){
            String menu[] = {
                    "Edit Masalah", "Timeline Masalah", "Tambah Progress", "Lihat Progress", "Hapus Penyelesaian Masalah"
            };
            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, menu);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listSelected);
        }else {
            Toast.makeText(getApplicationContext(), "Harap Kembali ke menu sebelumnya", Toast.LENGTH_LONG).show();
        }
    }

    private AdapterView.OnItemClickListener listSelected = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String itemView = (String) listView.getItemAtPosition(position);
            if (itemView.equals("Hapus Penyelesaian Masalah")){
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuPerbaikan.this);
                builder.setMessage("Pastikan Data yang anda masukkan benar!");
                builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hapusData();
                        Intent intent = new Intent(getApplicationContext(), L_Masalah.class);
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
            }else if(itemView.equals("Tambah Progress")) {
                intent = new Intent(getApplicationContext(), i_Progress.class);
                intent.putExtra(DATAMASALAH, mm);
                startActivity(intent);
                finish();
            } else if (itemView.equals("Lihat Progress")) {
                intent = new Intent(getApplicationContext(), L_Progress.class);
                intent.putExtra(DATAMASALAH, mm);
                startActivity(intent);
                finish();
            }else if (itemView.equals("Edit Masalah")){
                intent = new Intent(getApplicationContext(), e_Permasalahan.class);
                intent.putExtra(DATAMASALAH, mm);
                startActivity(intent);
                finish();
            }else if (itemView.equals("Tambah Penyelesaian Masalah")){
                intent = new Intent(getApplicationContext(), i_Penyelesaian.class);
                intent.putExtra(DATAMASALAH, mm);
                startActivity(intent);
                finish();
            }else if (itemView.equals("Timeline Masalah")){
                intent = new Intent(getApplicationContext(), L_Timeline2.class);
                intent.putExtra(DATAMASALAH, mm);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(MenuPerbaikan.this, "Menu Yang anda pilih tidak sesuai!", Toast.LENGTH_SHORT).show();
//                intent = new Intent(MenuPerbaikan.this, convertPerbaikan.class);
//                intent.putExtra(MENUPERBAIKAN, itemView);
//                intent.putExtra(DATAMASALAH, mm);
//                startActivity(intent);
//                finish();
            }
        }
    };
    private void hapusData(){
        RequestQueue queue = Volley.newRequestQueue(this);
//        Log.d("Hapus Penyelesaian",IPADDRESS + "/hapuspenyelesaian/"+idmasalah);
        StringRequest tambah = new StringRequest(Request.Method.GET, IPADDRESS + "/hapuspenyelesaian/"+idmasalah, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
//                params.put(JRES_IDMASALAH, idmasalah);
                return params;
            }
        };
        queue.add(tambah);
    }
}
