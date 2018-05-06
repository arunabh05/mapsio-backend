package com.cmpe295.mapsio.domain;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author arunabh.shrivastava
 */
public class Trip {

    private Location startLocation;
    private Location endLocation;
    private String journeyTime;
    private String journeyDate;

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    void addVisit(){
        this.visits++;
    }

    private int visits;

    public Trip() {
        this.journeyDate = LocalDate.now().toString() + ", " + LocalDate.now().getDayOfWeek();
        this.journeyTime = LocalTime.now().toString();
    }

    public Trip(Location startLocation, Location endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.journeyDate = LocalDate.now().toString() + ", " + LocalDate.now().getDayOfWeek();
        this.journeyTime = LocalTime.now().toString();
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate() {
        this.journeyDate = LocalDate.now().toString() + ", " + LocalDate.now().getDayOfWeek();
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime() {
        this.journeyTime = LocalTime.now().toString();
    }
}
