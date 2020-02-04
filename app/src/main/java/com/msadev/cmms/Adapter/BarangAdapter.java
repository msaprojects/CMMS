package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msadev.cmms.Model.BarangModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BarangAdapter extends ArrayAdapter<BarangModel> {

    List<BarangModel> barangModelList;
    ArrayList<BarangModel> array;
    Context context;

    public BarangAdapter (List<BarangModel> barangModelList, Context context){
        super(context, R.layout.design_l__barang, barangModelList);
        this.barangModelList = barangModelList;
        this.context = context;
        this.array = new ArrayList<BarangModel>();
        this.array.addAll(barangModelList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__barang, null, true);
        BarangModel bm = barangModelList.get(position);

        TextView kode = (TextView) listData.findViewById(R.id.tvKode);
        TextView nama = (TextView) listData.findViewById(R.id.tvBarang);
        TextView satuan = (TextView) listData.findViewById(R.id.tvSatuan);
        TextView keterangan = (TextView) listData.findViewById(R.id.tvKeterangan);

        kode.setText(bm.getKode());
        nama.setText(bm.getNama());
        satuan.setText(bm.getSatuan());
        keterangan.setText(bm.getKeterangan());

        return listData;
    }
    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        barangModelList.clear();
        if (filters.length()==0){
            barangModelList.addAll(array);
        }else {
            for (BarangModel bm : array){
                if (bm.getAll().toLowerCase(Locale.getDefault()).contains(filters)){
                    barangModelList.add(bm);
                }
            }
        }
        notifyDataSetChanged();
    }
}
