package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.LatLng;
import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author arunabh.shrivastava
 */


@Service
public class ValidationServiceImpl implements ValidationService{

    private final
    UserService userService;

    @Autowired
    public ValidationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public boolean isValidLocation(Location location) {
        System.out.println(location.getPlaceId());
        System.out.print(location.getName());
        return !(location.getName() == null || location.getPlaceId() == null);
    }

    @Override
    public boolean isValidTrip(Trip trip) {
        return !(trip == null || trip.getStartLocation().getName().equalsIgnoreCase(""));
    }

    public User isValidUser(String userId) {return userService.getUser(userId);
    }

    @Override
    public boolean isValidUser(User user) {
        return !(user.getAuthCode() == null || user.getId() == null);
    }

    @Override
    public boolean isValidLatlng(LatLng latLng){
        return !(latLng == null || latLng.lat == null || latLng.lng == null);
    }
}
