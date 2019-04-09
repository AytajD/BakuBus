package com.dadashova.aytaj.bakubustest.POJOS;

import com.google.gson.annotations.SerializedName;

public class Attributes {

    @SerializedName("BUS_ID")
    private String mBusId;
    @SerializedName("PLATE")
    private String mPlate;

    @SerializedName("CURRENT_STOP")
    private String mCurrentStop;

    @SerializedName("PREV_STOP")
    private String mPrevStop;

    @SerializedName("LATITUDE")
    private String mLatitude;

    @SerializedName("LONGITUDE")
    private String mLongitude;

    @SerializedName("ROUTE_NAME")
    private String mRouteName;

    @SerializedName("LAST_UPDATE_TIME")
    private String mLastUpdate;

    @SerializedName("SVCOUNT")
    private String mSvCount;

    @SerializedName("DISPLAY_ROUTE_CODE")
    private String mDisplayRouteCode;

    public String getmDisplayRouteCode() {
        return mDisplayRouteCode;
    }

    public void setmDisplayRouteCode(String mDisplayRouteCode) {
        this.mDisplayRouteCode = mDisplayRouteCode;
    }

    public String getmBusId() {
        return mBusId;
    }

    public String getmPlate() {
        return mPlate;
    }

    public String getmCurrentStop() {
        return mCurrentStop;
    }

    public String getmPrevStop() {
        return mPrevStop;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public String getmRouteName() {
        return mRouteName;
    }

    public String getmLastUpdate() {
        return mLastUpdate;
    }

    public String getmSvCount() {
        return mSvCount;
    }

    @Override
    public String toString() {

        return "Attributes{" +
                "mBusId='" + mBusId + '\'' +
                ", mPlate='" + mPlate + '\'' +
                ", mCurrentStop='" + mCurrentStop + '\'' +
                ", mPrevStop='" + mPrevStop + '\'' +
                ", mLatitude='" + mLatitude + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                ", mRouteName='" + mRouteName + '\'' +
                ", mLastUpdate='" + mLastUpdate + '\'' +
                ", mSvCount='" + mSvCount + '\'' +
                '}';
    }
}
