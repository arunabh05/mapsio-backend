package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author arunabh.shrivastava
 */

@Service
public class TripServiceImpl implements TripService {

    private final
    UserRepository userRepository;

    @Autowired
    public TripServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Trip addNewTrip(String userId, Trip trip) {
        Optional<User> user1 = userRepository.findById(userId);
        if(user1.isPresent()){
            user1.get().addTrip(trip);
            userRepository.save(user1.get());
            return trip;
        }
        return null;
    }
}
