package com.cmpe295.mapsio.domain;

/**
 * @author arunabh.shrivastava
 */
public class LatLng {
    public Double lat;
    public Double lng;

    public LatLng(){}

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
