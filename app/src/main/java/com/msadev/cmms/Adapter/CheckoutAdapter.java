package com.msadev.cmms.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.Model.CheckoutModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.JRES_IDBARANG;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class CheckoutAdapter extends ArrayAdapter<CheckoutModel> {

    Context context;
    List<CheckoutModel> checkoutModelList;
    ArrayList<CheckoutModel> arrayList;
    String id, idmasalah;

    public CheckoutAdapter (List<CheckoutModel> checkoutModelList, Context context){
        super(context, R.layout.design_l__checkout, checkoutModelList);
        this.checkoutModelList = checkoutModelList;
        this.arrayList = new ArrayList<CheckoutModel>();
        this.arrayList.addAll(checkoutModelList);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__checkout, null, true);
        CheckoutModel cm = checkoutModelList.get(position);
        id = cm.getIdcheckout();

        TextView kode = (TextView) listData.findViewById(R.id.tvKode);
        TextView nama = (TextView) listData.findViewById(R.id.tvBarang);
        TextView satuan = (TextView) listData.findViewById(R.id.tvSatuan);
        TextView keterangan = (TextView) listData.findViewById(R.id.tvKet);
        final ImageButton hapus = (ImageButton) listData.findViewById(R.id.btnHapus);

        kode.setText(cm.getKode());
        nama.setText(cm.getBarang());
        satuan.setText(cm.getSatuan());
        keterangan.setText(cm.getKeterangan());
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusData();
                checkoutModelList.remove(position);
            }
        });

        return listData;
    }

    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        checkoutModelList.clear();
        if (filters.length()==0){
            checkoutModelList.addAll(arrayList);
        }else {
            for (CheckoutModel cm : arrayList){
                if (cm.getAll().toLowerCase(Locale.getDefault()).contains(filters)){
                    checkoutModelList.add(cm);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void hapusData(){

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest tambah = new StringRequest(Request.Method.GET, IPADDRESS + "/hapusCheckout/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                notifyDataSetChanged();
                Log.d("Response", response);
                Toast.makeText(getContext(), "Data Berhassil Dihapus", Toast.LENGTH_LONG).show();
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
//                params.put(JRES_IDBARANG, id);
//                params.put(JRES_IDMASALAH, idmasalah);
                return params;
            }
        };
        queue.add(tambah);
    }
}
