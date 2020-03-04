package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msadev.cmms.Model.SiteModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SiteAdapter extends ArrayAdapter<SiteModel> {

    List<SiteModel> siteModelList;
    Context context;
    ArrayList<SiteModel> arrayList;

    public SiteAdapter(List<SiteModel> siteModelList, Context context){
        super(context, R.layout.design_l__site, siteModelList);
        this.siteModelList = siteModelList;
        this.context = context;
        this.arrayList = new ArrayList<SiteModel>();
        this.arrayList.addAll(siteModelList);
    }
    @Override
    public View getView(final  int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__site, null, true);
        SiteModel sm = siteModelList.get(position);

        TextView tvsite = listData.findViewById(R.id.tvSite);

        tvsite.setText(sm.getNama());

        return listData;
    }

    public void filter(String filters){
        filters = filters.toLowerCase(Locale.getDefault());
        siteModelList.clear();
        if (filters.length()==0){
            siteModelList.addAll(arrayList);
        }else {
            for (SiteModel sm : arrayList){
                if (sm.getNama().toLowerCase(Locale.getDefault()).contains(filters)){
                    siteModelList.add(sm);
                }
            }
        }
        notifyDataSetChanged();
    }
}
