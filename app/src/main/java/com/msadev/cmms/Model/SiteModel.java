package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SiteModel implements Parcelable {

    String idsite, nama;

    public SiteModel(String idsite, String nama) {
        this.idsite = idsite;
        this.nama = nama;
    }

    public String getIdsite() {
        return idsite;
    }

    public String getNama() {
        return nama;
    }

    protected SiteModel(Parcel in) {
        idsite = in.readString();
        nama = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idsite);
        dest.writeString(nama);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SiteModel> CREATOR = new Creator<SiteModel>() {
        @Override
        public SiteModel createFromParcel(Parcel in) {
            return new SiteModel(in);
        }

        @Override
        public SiteModel[] newArray(int size) {
            return new SiteModel[size];
        }
    };
}
