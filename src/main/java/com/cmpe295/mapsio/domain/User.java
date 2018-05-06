package com.cmpe295.mapsio.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arunabh.shrivastava
 */
public class User {

    @Id
    private String id;
    private String authCode;
    private String accessToken;
    private String refreshToken;
    private List<Location> favorites = new ArrayList<>();
    private List<Location> calendarEvents = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();

    public User() {}

    public User(String id, String authCode) {
        this.id = id;
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void addFavoriteLocation(Location location){
        Location favoriteLocation = getFavoriteLocation(location);
        if(favoriteLocation != null)    return;
        favorites.add(location);
    }

    public Location getFavoriteLocation(Location location){
        for(Location favoriteLocations : favorites){
            if(favoriteLocations.getPlaceId().equals(location.getPlaceId()))    return favoriteLocations;
        }
        return null;
    }

    public void removeFavoriteLocation(Location location){
        favorites.remove(location);
    }

    public void addTrip(Trip trip){
        for(Trip previousTrip : trips){
            if(previousTrip.getStartLocation().getName().equals(trip.getStartLocation().getName()) &&
                    previousTrip.getEndLocation().getName().equals(trip.getEndLocation().getName())){
                previousTrip.addVisit();
                return;
            }
        }
        trips.add(trip);
    }

    public void addCalendarEvent(Location location){
        calendarEvents.add(location);
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public List<Location> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Location> favorites) {
        this.favorites = favorites;
    }

    public List<Location> getCalendarEvents() {
        return calendarEvents;
    }

    public void setCalendarEvents(List<Location> calendarEvents) {
        this.calendarEvents = calendarEvents;
    }
}