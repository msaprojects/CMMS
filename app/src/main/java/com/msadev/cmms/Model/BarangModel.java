package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BarangModel implements Parcelable {
    String idbarang, kode, nama, umur_pakai, keterangan, idsatuan, satuan;

    public BarangModel(String idbarang, String kode, String nama, String umur_pakai, String keterangan, String idsatuan, String satuan) {
        this.idbarang = idbarang;
        this.kode = kode;
        this.nama = nama;
        this.umur_pakai = umur_pakai;
        this.keterangan = keterangan;
        this.idsatuan = idsatuan;
        this.satuan = satuan;
    }

    public String getAll(){
        return kode+nama+keterangan;
    }

    public String getIdbarang() {
        return idbarang;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getUmur_pakai() {
        return umur_pakai;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getIdsatuan() {
        return idsatuan;
    }

    public String getSatuan() {
        return satuan;
    }

    protected BarangModel(Parcel in) {
        idbarang = in.readString();
        kode = in.readString();
        nama = in.readString();
        umur_pakai = in.readString();
        keterangan = in.readString();
        idsatuan = in.readString();
        satuan = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idbarang);
        dest.writeString(kode);
        dest.writeString(nama);
        dest.writeString(umur_pakai);
        dest.writeString(keterangan);
        dest.writeString(idsatuan);
        dest.writeString(satuan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BarangModel> CREATOR = new Creator<BarangModel>() {
        @Override
        public BarangModel createFromParcel(Parcel in) {
            return new BarangModel(in);
        }

        @Override
        public BarangModel[] newArray(int size) {
            return new BarangModel[size];
        }
    };

}
