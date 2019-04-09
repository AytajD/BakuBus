package com.dadashova.aytaj.bakubustest.POJOS;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {

    @SerializedName("BUS")
    private List<Bus> mBusList;

    public List<Bus> getmBusList() {
        return mBusList;
    }

    public void setmBusList(List<Bus> mBusList) {
        this.mBusList = mBusList;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "mBusList=" + mBusList +
                '}';
    }
}
