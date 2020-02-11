package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.msadev.cmms.Model.TimelineModel;
import com.msadev.cmms.R;

import java.util.List;

import static android.view.View.GONE;

public class TimelineAdapter extends ArrayAdapter<TimelineModel> {

    List<TimelineModel> timelineModelList;
    Context context;

    public TimelineAdapter(List<TimelineModel> timelineAdapterList, Context context){
        super(context, R.layout.design_l__timeline2, timelineAdapterList);
        this.context = context;
        this.timelineModelList = timelineAdapterList;
    }

    @Override
    public View getView(final  int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View listData = inflater.inflate(R.layout.design_l__timeline2, null, true);
        TimelineModel tm = timelineModelList.get(position);

        TextView tgl_masalah     = listData.findViewById(R.id.tgl);
        TextView jam = listData.findViewById(R.id.jam);
        TextView masalah = listData.findViewById(R.id.Masalah);
        TextView engginer = listData.findViewById(R.id.engginer);
        TextView tgl_progress = listData.findViewById(R.id.tgl_progress);
        TextView progress = listData.findViewById(R.id.progress);
        TextView tgl_selesai = listData.findViewById(R.id.tgl_selesai);
        TextView ket_selesai = listData.findViewById(R.id.keteranganselesai);

        TableRow trtanggal = listData.findViewById(R.id.trTgl);
        TableRow trjam = listData.findViewById(R.id.trJam);
        TableRow trmasalah = listData.findViewById(R.id.trMasalah);
        TableRow trengginer = listData.findViewById(R.id.trEngginer);
        TableRow trtglprogres = listData.findViewById(R.id.trTglProgress);
        TableRow trprogress = listData.findViewById(R.id.trProgress);
        TableRow trtglselesai = listData.findViewById(R.id.trTglSelesai);
        TableRow trketselesai = listData.findViewById(R.id.trketeranganselesai);

        TableLayout tl = listData.findViewById(R.id.tl);

        TextView tvTanggal = listData.findViewById(R.id.tvTgl);
        TextView tvJam = listData.findViewById(R.id.tvJam);
        TextView tvMasalah = listData.findViewById(R.id.tvMasalah);
        TextView tvengginer = listData.findViewById(R.id.tvEngginer);
        TextView tvTglProgress = listData.findViewById(R.id.tvTglProgress);
        TextView tvProgress = listData.findViewById(R.id.tvProgress);
        TextView tvTglSelesai = listData.findViewById(R.id.tvTglSelesai);
        TextView tvKeteranganSelesai = listData.findViewById(R.id.tvKeterangan);

        String Sengginer = tm.getEngginer();
        String Stglprogres = tm.getTanggalprog();
        String Stglselesai = tm.getTanggalsellesai();
        String Sketerangan = tm.getKeteranganselesai();
        String tipe = tm.getTipe();
        String Space = "";

        if (tipe.equals("2")){
            tl.setBackgroundResource(R.color.gold);
            tvTanggal.setText("\t\tTanggal : \t");
            tvJam.setText("\t\tJam : \t");
            tvMasalah.setText("\t\tMasalah : \t");
            tvengginer.setText("\t\tEngginer : \t");
            tvTglProgress.setText("\t\tTgl Progress : \t");
            tvProgress.setText("\t\tProgress : \t");
            tvTglSelesai.setText("\t\tTgl Selesai : \t");
            tvKeteranganSelesai.setText("\t\tKeterangan : \t");
        }else if (tipe.equals("3")){
            tl.setBackgroundResource(R.color.green);
            tvTanggal.setText("\t\t\t\tTanggal : \t");
            tvJam.setText("\t\t\t\tJam : \t");
            tvMasalah.setText("\t\t\t\tMasalah : \t");
            tvengginer.setText("\t\t\t\tEngginer : \t");
            tvTglProgress.setText("\t\t\t\tTgl Progress : \t");
            tvProgress.setText("\t\t\t\tProgress : \t");
            tvTglSelesai.setText("\t\t\t\tTgl Selesai : \t");
            tvKeteranganSelesai.setText("\t\t\t\tKeterangan : \t");
        }

        tgl_masalah.setText(tm.getTanggal());
        jam.setText(tm.getJam());
        masalah.setText(tm.getMasalah());
        engginer.setText(tm.getEngginer());
        tgl_progress.setText(tm.getTanggalprog());
        progress.setText(tm.getPerbaikan());
        tgl_selesai.setText(tm.getTanggalsellesai());
        ket_selesai.setText(tm.getKeteranganselesai());

        return listData;
    }

}
