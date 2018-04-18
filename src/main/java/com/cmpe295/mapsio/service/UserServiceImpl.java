package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author arunabh.shrivastava
 */

@Service
public class UserServiceImpl implements UserService {

    private final
    UserRepository userRepository;
    private final
    GoogleService googleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GoogleService googleService) {
        this.userRepository = userRepository;
        this.googleService = googleService;
    }

    @Override
    public User getUserCalendarEvents(User user) {
        Optional<User> user1 = userRepository.findById(user.getId());
        if(user1.isPresent()){
            user = user1.get();
            List<Event> events = googleService.getUserCalendarEvents(user);
            for(Event event : events){
                Location location = googleService.geocodeLocation(event.getLocation());
                if(location != null)    user.addCalendarEvent(location);
            }
            userRepository.save(user);
        }
        return user1.orElse(user);
    }
}