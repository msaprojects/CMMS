package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TimelineModel implements Parcelable {

    String tipe, jam, tanggal, masalah, shift, perbaikan, engginer, tanggalprog, shiftprog, tanggalsellesai, keteranganselesai, kode, barang, saatuan, qty, keterangancheckout;

    public TimelineModel(String tipe, String jam, String tanggal, String masalah, String shift, String perbaikan, String engginer, String tanggalprog, String shiftprog, String tanggalsellesai, String keteranganselesai, String kode, String barang, String saatuan, String qty, String keterangancheckout) {
        this.tipe = tipe;
        this.jam = jam;
        this.tanggal = tanggal;
        this.masalah = masalah;
        this.shift = shift;
        this.perbaikan = perbaikan;
        this.engginer = engginer;
        this.tanggalprog = tanggalprog;
        this.shiftprog = shiftprog;
        this.tanggalsellesai = tanggalsellesai;
        this.keteranganselesai = keteranganselesai;
        this.kode = kode;
        this.barang = barang;
        this.saatuan = saatuan;
        this.qty = qty;
        this.keterangancheckout = keterangancheckout;
    }

    public String getTipe() {
        return tipe;
    }

    public String getJam() {
        return jam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getMasalah() {
        return masalah;
    }

    public String getShift() {
        return shift;
    }

    public String getPerbaikan() {
        return perbaikan;
    }

    public String getEngginer() {
        return engginer;
    }

    public String getTanggalprog() {
        return tanggalprog;
    }

    public String getShiftprog() {
        return shiftprog;
    }

    public String getTanggalsellesai() {
        return tanggalsellesai;
    }

    public String getKeteranganselesai() {
        return keteranganselesai;
    }

    public String getKode() {
        return kode;
    }

    public String getBarang() {
        return barang;
    }

    public String getSaatuan() {
        return saatuan;
    }

    public String getQty() {
        return qty;
    }

    public String getKeterangancheckout() {
        return keterangancheckout;
    }

    protected TimelineModel(Parcel in) {
        tipe = in.readString();
        jam = in.readString();
        tanggal = in.readString();
        masalah = in.readString();
        shift = in.readString();
        perbaikan = in.readString();
        engginer = in.readString();
        tanggalprog = in.readString();
        shiftprog = in.readString();
        tanggalsellesai = in.readString();
        keteranganselesai = in.readString();
        kode = in.readString();
        barang = in.readString();
        saatuan = in.readString();
        qty = in.readString();
        keterangancheckout = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipe);
        dest.writeString(jam);
        dest.writeString(tanggal);
        dest.writeString(masalah);
        dest.writeString(shift);
        dest.writeString(perbaikan);
        dest.writeString(engginer);
        dest.writeString(tanggalprog);
        dest.writeString(shiftprog);
        dest.writeString(tanggalsellesai);
        dest.writeString(keteranganselesai);
        dest.writeString(kode);
        dest.writeString(barang);
        dest.writeString(saatuan);
        dest.writeString(qty);
        dest.writeString(keterangancheckout);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimelineModel> CREATOR = new Creator<TimelineModel>() {
        @Override
        public TimelineModel createFromParcel(Parcel in) {
            return new TimelineModel(in);
        }

        @Override
        public TimelineModel[] newArray(int size) {
            return new TimelineModel[size];
        }
    };
}
