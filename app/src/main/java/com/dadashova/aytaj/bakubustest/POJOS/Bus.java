package com.dadashova.aytaj.bakubustest.POJOS;

import com.google.gson.annotations.SerializedName;

public class Bus {

    @SerializedName("@attributes")
   private Attributes mBus;

    public Attributes getmBus() {
        return mBus;
    }

    public void setmBus(Attributes mBus) {
        this.mBus = mBus;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "mBus=" + mBus +
                '}';
    }
}
