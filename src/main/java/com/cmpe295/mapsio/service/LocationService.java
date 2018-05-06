package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.domain.LatLng;

import java.util.List;

/**
 * @author arunabh.shrivastava
 */
public interface LocationService {
    Location addFavorite(User user, Location location);
    Location removeFavorite(User user, Location location);
    List<Location> getAllFavorites(User user);
    Location getLocationPlaceDetails(LatLng latLng);
}
