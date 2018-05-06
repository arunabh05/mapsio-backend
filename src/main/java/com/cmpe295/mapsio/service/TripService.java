package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.domain.User;

/**
 * @author arunabh.shrivastava
 */
public interface TripService {
    Trip addNewTrip(User user, Trip trip);
}
