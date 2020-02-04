package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.msadev.cmms.Model.CheckoutModel;
import com.msadev.cmms.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckoutAdapter2 extends ArrayAdapter<CheckoutModel> {

    Context context;
    List<CheckoutModel> checkoutModelList;
    ArrayList<CheckoutModel> arrayList;
    String id, idmasalah;

    public CheckoutAdapter2(List<CheckoutModel> checkoutModelList, Context context) {
        super(context, R.layout.design_l__checkout, checkoutModelList);
        this.checkoutModelList = checkoutModelList;
        this.arrayList = new ArrayList<CheckoutModel>();
        this.arrayList.addAll(checkoutModelList);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__checkout, null, true);
        CheckoutModel cm = checkoutModelList.get(position);
        id = cm.getIdcheckout();

        TextView kode = (TextView) listData.findViewById(R.id.tvKode);
        TextView nama = (TextView) listData.findViewById(R.id.tvBarang);
        TextView satuan = (TextView) listData.findViewById(R.id.tvSatuan);
        TextView keterangan = (TextView) listData.findViewById(R.id.tvKet);
        final ImageButton hapus = (ImageButton) listData.findViewById(R.id.btnHapus);
        TableRow trHapus = (TableRow) listData.findViewById(R.id.trHapus);

        kode.setText(cm.getKode());
        nama.setText(cm.getBarang());
        satuan.setText(cm.getSatuan());
        keterangan.setText(cm.getKeterangan());
        hapus.setVisibility(View.GONE);
        trHapus.setVisibility(View.GONE);
        return listData;
    }

    public void filter(String filters) {
        filters = filters.toLowerCase(Locale.getDefault());
        checkoutModelList.clear();
        if (filters.length() == 0) {
            checkoutModelList.addAll(arrayList);
        } else {
            for (CheckoutModel cm : arrayList) {
                if (cm.getAll().toLowerCase(Locale.getDefault()).contains(filters)) {
                    checkoutModelList.add(cm);
                }
            }
        }
        notifyDataSetChanged();
    }
}