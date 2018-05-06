package com.cmpe295.mapsio.domain;

/**
 * @author arunabh.shrivastava
 */
public class Location {

    private String placeId;
    private String name;
    private String address;
    private LatLng latLng;
    private String imageURL;
    private boolean isFavorite;
    private float rating;

    public Location(){}

    public Location(String placeId, String name, LatLng latLng, String address,
                    boolean isFavorite, float rating) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.latLng = latLng;
        this.isFavorite = isFavorite;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setLatitude(double latitude) {
        this.latLng.lat = latitude;
    }
    public void setLongitude(double longitude) {
        this.latLng.lng = longitude;
    }


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
