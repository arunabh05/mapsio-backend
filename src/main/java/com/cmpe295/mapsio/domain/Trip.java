package com.cmpe295.mapsio.domain;

import com.google.api.client.util.DateTime;

/**
 * @author arunabh.shrivastava
 */
public class Trip {

    private Location startLocation;
    private Location endLocation;
    private DateTime journeyDate;

    public Trip() {}

    public Trip(Location startLocation, Location endLocation, DateTime journeyDate) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.journeyDate = journeyDate;
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

    public DateTime getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(DateTime journeyDate) {
        this.journeyDate = journeyDate;
    }
}
