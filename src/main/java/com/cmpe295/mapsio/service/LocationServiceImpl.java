package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import com.cmpe295.mapsio.domain.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author arunabh.shrivastava
 */

@Service
public class LocationServiceImpl implements LocationService{

    private final UserRepository userRepository;
    private final GoogleService googleService;

    @Autowired
    public LocationServiceImpl(UserRepository userRepository, GoogleService googleService) {
        this.userRepository = userRepository;
        this.googleService = googleService;
    }

    @Override
    public List<Location> getAllFavorites(User user) {
        return user.getFavorites();
    }

    @Override
    public Location getLocationPlaceDetails(LatLng latLng) {
        return googleService.getPlaceDetails(latLng);
    }

    @Override
    public Location addFavorite(User user, Location location) {
        user.addFavoriteLocation(location);
        userRepository.save(user);
        return location;
    }

    @Override
    public Location removeFavorite(User user, Location location) {
        location = user.getFavoriteLocation(location);
        if(location == null)    return null;
        user.removeFavoriteLocation(location);
        userRepository.save(user);
        return location;
    }
}