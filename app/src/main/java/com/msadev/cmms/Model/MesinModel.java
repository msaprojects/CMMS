package com.msadev.cmms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MesinModel implements Parcelable {

    String idmesin, nomesin, site, keterangan, idsite;

    public MesinModel(String idmesin, String nomesin, String site, String keterangan, String idsite) {
        this.idmesin = idmesin;
        this.nomesin = nomesin;
        this.site = site;
        this.keterangan = keterangan;
        this.idsite = idsite;
    }

    public String getIdmesin() {
        return idmesin;
    }

    public String getNomesin() {
        return nomesin;
    }

    public String getSite() {
        return site;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getIdsite() {
        return idsite;
    }

    protected MesinModel(Parcel in) {
        idmesin = in.readString();
        nomesin = in.readString();
        site = in.readString();
        keterangan = in.readString();
        idsite = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idmesin);
        dest.writeString(nomesin);
        dest.writeString(site);
        dest.writeString(keterangan);
        dest.writeString(idsite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MesinModel> CREATOR = new Creator<MesinModel>() {
        @Override
        public MesinModel createFromParcel(Parcel in) {
            return new MesinModel(in);
        }

        @Override
        public MesinModel[] newArray(int size) {
            return new MesinModel[size];
        }
    };

    public String getAll(){
        return nomesin+site+keterangan;
    }

   }
