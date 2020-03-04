package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ReminderModel implements Parcelable {
    String idmesin, nomesin, idkomponen, komponen;

    public ReminderModel(String idmesin, String nomesin, String idkomponen, String komponen) {
        this.idmesin = idmesin;
        this.nomesin = nomesin;
        this.idkomponen = idkomponen;
        this.komponen = komponen;
    }

    public String getAll(){
        return nomesin+komponen;
    }

    public String getIdmesin() {
        return idmesin;
    }

    public String getNomesin() {
        return nomesin;
    }

    public String getIdkomponen() {
        return idkomponen;
    }

    public String getKomponen() {
        return komponen;
    }

    protected ReminderModel(Parcel in) {
        idmesin = in.readString();
        nomesin = in.readString();
        idkomponen = in.readString();
        komponen = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idmesin);
        dest.writeString(nomesin);
        dest.writeString(idkomponen);
        dest.writeString(komponen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReminderModel> CREATOR = new Creator<ReminderModel>() {
        @Override
        public ReminderModel createFromParcel(Parcel in) {
            return new ReminderModel(in);
        }

        @Override
        public ReminderModel[] newArray(int size) {
            return new ReminderModel[size];
        }
    };
}
