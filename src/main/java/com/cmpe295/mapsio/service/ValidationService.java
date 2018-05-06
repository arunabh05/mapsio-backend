package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.domain.LatLng;

/**
 * @author arunabh.shrivastava
 */
public interface ValidationService {

    User isValidUser(String userId);
    boolean isValidUser(User user);
    boolean isValidLocation(Location location);
    boolean isValidTrip(Trip trip);
    boolean isValidLatlng(LatLng latlng);
}
