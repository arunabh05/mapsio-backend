package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Trip;

/**
 * @author arunabh.shrivastava
 */
public interface TripService {
    Trip addNewTrip(String userId, Trip trip);
}
