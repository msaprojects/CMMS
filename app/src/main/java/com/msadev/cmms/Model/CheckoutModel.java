package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckoutModel implements Parcelable {

    String idcheckout, idmasalah, idbarang, idsatuan, tanggal, keterangan, qty, barang, kode, satuan;

    public CheckoutModel(String idcheckout, String idmasalah, String idbarang, String idsatuan, String tanggal, String keterangan, String qty, String barang, String kode, String satuan) {
        this.idcheckout = idcheckout;
        this.idmasalah = idmasalah;
        this.idbarang = idbarang;
        this.idsatuan = idsatuan;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.qty = qty;
        this.barang = barang;
        this.kode = kode;
        this.satuan = satuan;
    }

    public String getAll(){
        return barang+kode+keterangan;
    }

    public String getIdcheckout() {
        return idcheckout;
    }

    public String getIdmasalah() {
        return idmasalah;
    }

    public String getIdbarang() {
        return idbarang;
    }

    public String getIdsatuan() {
        return idsatuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getQty() {
        return qty;
    }

    public String getBarang() {
        return barang;
    }

    public String getKode() {
        return kode;
    }

    public String getSatuan() {
        return satuan;
    }

    protected CheckoutModel(Parcel in) {
        idcheckout = in.readString();
        idmasalah = in.readString();
        idbarang = in.readString();
        idsatuan = in.readString();
        tanggal = in.readString();
        keterangan = in.readString();
        qty = in.readString();
        barang = in.readString();
        kode = in.readString();
        satuan = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idcheckout);
        dest.writeString(idmasalah);
        dest.writeString(idbarang);
        dest.writeString(idsatuan);
        dest.writeString(tanggal);
        dest.writeString(keterangan);
        dest.writeString(qty);
        dest.writeString(barang);
        dest.writeString(kode);
        dest.writeString(satuan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckoutModel> CREATOR = new Creator<CheckoutModel>() {
        @Override
        public CheckoutModel createFromParcel(Parcel in) {
            return new CheckoutModel(in);
        }

        @Override
        public CheckoutModel[] newArray(int size) {
            return new CheckoutModel[size];
        }
    };
}
