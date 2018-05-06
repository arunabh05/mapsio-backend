package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.LatLng;
import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author arunabh.shrivastava
 */
public interface UserService {
    User getUserAccessToken(User user);
    User getUser(String userId);
    List<Location> getUserCalendarLocations(User user);
    List<Location> getRecommendedLocations(User user, Location location);
}
