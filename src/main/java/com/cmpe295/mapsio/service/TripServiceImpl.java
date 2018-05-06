package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Trip addNewTrip(User user, Trip trip) {
        trip.setVisits(1);
        user.addTrip(trip);
        userRepository.save(user);
        return trip;
    }
}
