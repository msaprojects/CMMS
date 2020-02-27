package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class KomponenModel implements Parcelable {

    String idkomponen, nama, jumlah, idmesin;

    public KomponenModel(String idkomponen, String nama, String jumlah, String idmesin) {
        this.idkomponen = idkomponen;
        this.nama = nama;
        this.jumlah = jumlah;
        this.idmesin = idmesin;
    }

    public String getAll(){
        return nama;
    }

    public String getIdkomponen() {
        return idkomponen;
    }

    public String getNama() {
        return nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getIdmesin() {
        return idmesin;
    }

    protected KomponenModel(Parcel in) {
        idkomponen = in.readString();
        nama = in.readString();
        jumlah = in.readString();
        idmesin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idkomponen);
        dest.writeString(nama);
        dest.writeString(jumlah);
        dest.writeString(idmesin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KomponenModel> CREATOR = new Creator<KomponenModel>() {
        @Override
        public KomponenModel createFromParcel(Parcel in) {
            return new KomponenModel(in);
        }

        @Override
        public KomponenModel[] newArray(int size) {
            return new KomponenModel[size];
        }
    };
}
