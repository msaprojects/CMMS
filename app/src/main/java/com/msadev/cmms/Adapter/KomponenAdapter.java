package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msadev.cmms.Model.KomponenModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KomponenAdapter extends ArrayAdapter<KomponenModel> {

    Context context;
    List<KomponenModel> komponenModelList;
    ArrayList<KomponenModel> arrayList;
    String id, idmesin;

    public KomponenAdapter (List<KomponenModel> komponenModelList, Context context){
        super(context, R.layout.design_l__komponen, komponenModelList);
        this.komponenModelList = komponenModelList;
        this.arrayList = new ArrayList<KomponenModel>();
        this.arrayList.addAll(komponenModelList);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__komponen, null, true);
        KomponenModel km = komponenModelList.get(position);

        TextView nama = (TextView) listData.findViewById(R.id.tvNamaKomponen);
        TextView jumlah = (TextView) listData.findViewById(R.id.tvJumlahKomponen);

        nama.setText(km.getNama());
        jumlah.setText(km.getJumlah());

        return listData;
    }

    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        komponenModelList.clear();
        if (filters.length()==0){
            komponenModelList.addAll(arrayList);
        }else {
            for (KomponenModel cm : arrayList){
                if (cm.getAll().toLowerCase(Locale.getDefault()).contains(filters)){
                    komponenModelList.add(cm);
                }
            }
        }
        notifyDataSetChanged();
    }

}
