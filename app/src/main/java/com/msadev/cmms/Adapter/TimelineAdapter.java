package com.msadev.cmms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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

        CardView crdview = listData.findViewById(R.id.crdview);

//        TextView status     = listData.findViewById(R.id.tvStatus);
        TextView tgl_masalah     = listData.findViewById(R.id.tgl);
        TextView jam = listData.findViewById(R.id.jam);
        TextView masalah = listData.findViewById(R.id.Masalah);
        TextView engginer = listData.findViewById(R.id.engginer);
        TextView tgl_progress = listData.findViewById(R.id.tgl_progress);
        TextView progress = listData.findViewById(R.id.progress);
        TextView tgl_selesai = listData.findViewById(R.id.tgl_selesai);
        TextView ket_selesai = listData.findViewById(R.id.keteranganselesai);
        TextView kode = listData.findViewById(R.id.kode);
        TextView barang = listData.findViewById(R.id.nama);
        TextView qty = listData.findViewById(R.id.qty);
        TextView satuan = listData.findViewById(R.id.satuan);
        TextView keteranganbrng = listData.findViewById(R.id.keteranganbrng);

        TableRow trtanggal = listData.findViewById(R.id.trTgl);
        TableRow trjam = listData.findViewById(R.id.trJam);
        TableRow trmasalah = listData.findViewById(R.id.trMasalah);
        TableRow trengginer = listData.findViewById(R.id.trEngginer);
        TableRow trtglprogres = listData.findViewById(R.id.trTglProgress);
        TableRow trprogress = listData.findViewById(R.id.trProgress);
        TableRow trtglselesai = listData.findViewById(R.id.trTglSelesai);
        TableRow trketselesai = listData.findViewById(R.id.trketeranganselesai);
        TableRow trKode = listData.findViewById(R.id.trkode);
        TableRow trBarang = listData.findViewById(R.id.trnama);
        TableRow trQty = listData.findViewById(R.id.trqty);
        TableRow trSatuan = listData.findViewById(R.id.trsatuan);
        TableRow trKetBrng = listData.findViewById(R.id.trketeranganbrng);

        TableLayout tl = listData.findViewById(R.id.tl);

        TextView tvStatus = listData.findViewById(R.id.tvvStatus);
        TextView tvTanggal = listData.findViewById(R.id.tvTgl);
        TextView tvJam = listData.findViewById(R.id.tvJam);
        TextView tvMasalah = listData.findViewById(R.id.tvMasalah);
        TextView tvengginer = listData.findViewById(R.id.tvEngginer);
        TextView tvTglProgress = listData.findViewById(R.id.tvTglProgress);
        TextView tvProgress = listData.findViewById(R.id.tvProgress);
        TextView tvTglSelesai = listData.findViewById(R.id.tvTglSelesai);
        TextView tvKeteranganSelesai = listData.findViewById(R.id.tvKeterangan);
        TextView tvKode = listData.findViewById(R.id.tvKode);
        TextView tvBarang = listData.findViewById(R.id.tvNama);
        TextView tvQty = listData.findViewById(R.id.tvqty);
        TextView tvSatuan = listData.findViewById(R.id.tvSatuan);
        TextView tvketbrng = listData.findViewById(R.id.tvKeteranganbrng);

        String Sengginer = tm.getEngginer();
        String Stglprogres = tm.getTanggalprog();
        String Stglselesai = tm.getTanggalsellesai();
        String Sketerangan = tm.getKeteranganselesai();
        String tipe = tm.getTipe();
        String vTipe = "";
        String Space = "";

//        status.setText("Tipe");
        if (tipe.equals("1")){
            vTipe = "Masalah";
            tvStatus.setText(vTipe);
            trtglselesai.setVisibility(GONE);
            trketselesai.setVisibility(GONE);
            trengginer.setVisibility(GONE);
            trtglprogres.setVisibility(GONE);
            trKode.setVisibility(GONE);
            trBarang.setVisibility(GONE);
            trQty.setVisibility(GONE);
            trSatuan.setVisibility(GONE);
            trKetBrng.setVisibility(GONE);
        }else if (tipe.equals("2")){
            vTipe = "\t\tProgress";
            tvStatus.setText(vTipe);
            trtanggal.setVisibility(GONE);
            trjam.setVisibility(GONE);
            trmasalah.setVisibility(GONE);
            trtglselesai.setVisibility(GONE);
            trketselesai.setVisibility(GONE);
            trKode.setVisibility(GONE);
            trBarang.setVisibility(GONE);
            trQty.setVisibility(GONE);
            trSatuan.setVisibility(GONE);
            trKetBrng.setVisibility(GONE);
//            crdview.setBackgroundResource(R.color.gold);
            tvengginer.setText("\t\tEngineer : \t");
            tvTglProgress.setText("\t\tTgl Progress : \t");
            tvProgress.setText("\t\tProgress : \t");
        }else if (tipe.equals("3")){
            vTipe = "Penyelesaian";
            tvStatus.setText(vTipe);
            trtanggal.setVisibility(GONE);
            trjam.setVisibility(GONE);
            trmasalah.setVisibility(GONE);
            trengginer.setVisibility(GONE);
            trtglprogres.setVisibility(GONE);
            trprogress.setVisibility(GONE);
            trKode.setVisibility(GONE);
            trBarang.setVisibility(GONE);
            trQty.setVisibility(GONE);
            trSatuan.setVisibility(GONE);
            trKetBrng.setVisibility(GONE);
        }else if (tipe.equals("4")){
            vTipe = "\t\tKomponen Yang di pakai";
            tvStatus.setText(vTipe);
            trtanggal.setVisibility(GONE);
            trjam.setVisibility(GONE);
            trmasalah.setVisibility(GONE);
            trengginer.setVisibility(GONE);
            trtglprogres.setVisibility(GONE);
            trprogress.setVisibility(GONE);
            trtglselesai.setVisibility(GONE);
            trketselesai.setVisibility(GONE);
            tvKode.setText("\t\tKode : \t");
            tvBarang.setText("\t\tBarang : \t");
            tvQty.setText("\t\tQty : \t");
            tvSatuan.setText("\t\tSatuan : \t");
            tvketbrng.setText("\t\tKeterangan : \t");
        }
        //masalah
        tgl_masalah.setText(tm.getMasalah());
        jam.setText(tm.getJam());
        masalah.setText(tm.getMasalah());
        //progress
        engginer.setText(tm.getEngginer());
        tgl_progress.setText(tm.getTanggalprog());
        progress.setText(tm.getPerbaikan());
        //penyelesaian
        tgl_selesai.setText(tm.getTanggalsellesai());
        ket_selesai.setText(tm.getKeteranganselesai());
        //barang
        kode.setText(tm.getKode());
        barang.setText(tm.getBarang());
        qty.setText(tm.getQty());
        satuan.setText(tm.getSaatuan());
        keteranganbrng.setText(tm.getKeterangancheckout());

        return listData;
    }

}
