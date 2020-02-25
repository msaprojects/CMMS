package com.msadev.cmms.Trigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msadev.cmms.List.L_Mesin;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;

public class MenuMesin extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fab;

    ArrayAdapter<String> adapter;
    Intent intent;
    String idmesin;
    MesinModel mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        mm = getIntent().getParcelableExtra(DATAMASALAH);
        idmesin = mm.getIdmesin();
        this.setTitle("Action Mesin "+mm.getNomesin());


        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        String menu[] = {
                "Tambah Komponen Mesin", "List Komponen Mesin"
        };
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listSelected);
    }

    private AdapterView.OnItemClickListener listSelected = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String itemView = (String) listView.getItemAtPosition(position);
            if(itemView.equals("Tambah Komponen Mesin")) {
                intent = new Intent(getApplicationContext(), L_Mesin.class);
                intent.putExtra(DATAMASALAH, mm);
                startActivity(intent);
                finish();
            }else if(itemView.equals("List Komponen Mesin")) {
                intent = new Intent(getApplicationContext(), L_Mesin.class);
                intent.putExtra(DATAMASALAH, mm);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MenuMesin.this, "Menu Yang anda pilih tidak sesuai!", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
