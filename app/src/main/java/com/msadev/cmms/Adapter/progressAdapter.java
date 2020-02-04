package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msadev.cmms.Model.ProgressModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class progressAdapter extends ArrayAdapter<ProgressModel> {
    List<ProgressModel> progressModelList;
    ArrayList<ProgressModel> arrayList;
    Context context;

    public progressAdapter(List<ProgressModel> progressModelList, Context context){
        super(context, R.layout.design_l__progress, progressModelList);
        this.context = context;
        this.progressModelList = progressModelList;
//        this.arrayList = new ArrayList<ProgressModel>();
//        this.arrayList.addAll(progressModelList);
    }

    @Override
    public View getView(final  int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__progress, null, true);
        ProgressModel pm = progressModelList.get(position);

        TextView tgl = (TextView) listData.findViewById(R.id.tgl);
        TextView jam = (TextView) listData.findViewById(R.id.jam);
        TextView nomesin = (TextView) listData.findViewById(R.id.nomesin);
        TextView engginer = (TextView) listData.findViewById(R.id.engginer);
        TextView shift = (TextView) listData.findViewById(R.id.tvMasalah);
        TextView site = (TextView) listData.findViewById(R.id.site);
        TextView perbaikan = (TextView) listData.findViewById(R.id.perbaikan);

        tgl.setText(pm.getTanggal());
        jam.setText(pm.getJam());
        nomesin.setText(pm.getNomesin());
        engginer.setText(pm.getEngginer());
        shift.setText(pm.getMasalah());
        site.setText(pm.getSite());
        perbaikan.setText(pm.getPerbaikan());

        return listData;
    }

    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        progressModelList.clear();
        if (filters.length()==0){
            progressModelList.addAll(arrayList);
        }else {
            for (ProgressModel pm : arrayList){
                if (pm.getAll().toLowerCase(Locale.getDefault()).contains(filters)){
                    progressModelList.add(pm);
                }
            }
        }
        notifyDataSetChanged();
    }

}
