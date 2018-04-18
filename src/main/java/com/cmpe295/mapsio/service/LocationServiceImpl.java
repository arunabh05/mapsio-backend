package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author arunabh.shrivastava
 */

@Service
public class LocationServiceImpl implements LocationService{

    private final UserRepository userRepository;

    @Autowired
    public LocationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Location> getAllFavorites(String userId) {
        Optional<User> user1 = userRepository.findById(userId);
        return user1.map(User::getFavorites).orElse((null));
    }

    @Override
    public Location addFavorite(String userId, Location location) {
        Optional<User> user1 = userRepository.findById(userId);
        if(user1.isPresent()){
            user1.get().addFavoriteLocation(location);
            userRepository.save(user1.get());
            return location;
        }
        return null;
    }

    @Override
    public Location removeFavorite(String userId, Location location) {
        Optional<User> user1 = userRepository.findById(userId);
        if(user1.isPresent()){
            user1.get().removeFavoriteLocation(location);
            userRepository.save(user1.get());
            return location;
        }
        return null;
    }
}