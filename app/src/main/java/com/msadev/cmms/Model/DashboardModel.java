package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class DashboardModel implements Parcelable {
    String jml_masalah, jml_selesai;

    protected DashboardModel(Parcel in) {
        jml_masalah = in.readString();
        jml_selesai = in.readString();
    }

    public DashboardModel(String jml_masalah, String jml_selesai) {
        this.jml_masalah = jml_masalah;
        this.jml_selesai = jml_selesai;
    }


    public String getJml_masalah() {
        return jml_masalah;
    }

    public String getJml_selesai() {
        return jml_selesai;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jml_masalah);
        dest.writeString(jml_selesai);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DashboardModel> CREATOR = new Creator<DashboardModel>() {
        @Override
        public DashboardModel createFromParcel(Parcel in) {
            return new DashboardModel(in);
        }

        @Override
        public DashboardModel[] newArray(int size) {
            return new DashboardModel[size];
        }
    };
}
