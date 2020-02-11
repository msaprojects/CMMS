package com.msadev.cmms.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MasalahAdapter extends ArrayAdapter<MasalahModel> {

    List<MasalahModel> masalahModelList;
    ArrayList<MasalahModel> array;
    Context context;

    public MasalahAdapter(List<MasalahModel> masalahModelList, Context context){
        super(context, R.layout.design_l__masalah, masalahModelList);
        this.context = context;
        this.masalahModelList = masalahModelList;
        this.array = new ArrayList<MasalahModel>();
        this.array.addAll(masalahModelList);
    }

    @Override
    public View getView(final  int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__masalah, null, true);
        MasalahModel mm = masalahModelList.get(position);

        TextView tgl = listData.findViewById(R.id.tgl);
        TextView jam = listData.findViewById(R.id.jam);
        TextView nomesin = listData.findViewById(R.id.nomesin);
        TextView site = listData.findViewById(R.id.site);
        TextView keterangan = listData.findViewById(R.id.keterangan);
        TextView status = listData.findViewById(R.id.tvStatus);
        LinearLayout warnastatus = listData.findViewById(R.id.llstatus);

        tgl.setText(mm.getTanggal());
        jam.setText(mm.getJam());
        nomesin.setText(mm.getNomesin());
        site.setText(mm.getSite());
        keterangan.setText(mm.getMasalah());
        status.setText(mm.getStatus());
        String var = mm.getStatus();
        Log.d("hasil output", var);

        if (var.trim().equals("0")){
            status.setText("Belum Selesai");
            warnastatus.setBackgroundResource(R.color.red);
        }else if (var.equals("1")){
            status.setText("Sudah Selesai");
            warnastatus.setBackgroundResource(R.color.green);
        } else {
            status.setText("Tidak Valid");
            warnastatus.setBackgroundResource(R.color.green);
        }

        return listData;
    }

    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        masalahModelList.clear();
        if (filters.length()==0){
            masalahModelList.addAll(array);
        }else {
            for (MasalahModel mm : array){
                if (mm.getAll().toLowerCase(Locale.getDefault()).contains(filters)){
                    masalahModelList.add(mm);
                }
            }
        }
        notifyDataSetChanged();
    }

}
