package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;

import java.util.List;

/**
 * @author arunabh.shrivastava
 */
public interface LocationService {
    Location addFavorite(String userId, Location location);
    Location removeFavorite(String userId, Location location);
    List<Location> getAllFavorites(String userId);
}
