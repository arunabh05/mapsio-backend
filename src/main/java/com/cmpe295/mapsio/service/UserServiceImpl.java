package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.LatLng;
import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

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
    public User getUserAccessToken(User user){

        user = googleService.getUserAccessToken(user);
        return user;
    }

    @Override
    public List<Location> getUserCalendarLocations(User user) {
            user.setCalendarEvents(new ArrayList<>());

            List<Event> events = googleService.getUserCalendarEvents(user);
            if(events != null){
                for(Event event : events){
                    Location location = googleService.geocodeLocation(event.getLocation());
                    if(location != null)    user.addCalendarEvent(location);
                }
            }
        return user.getCalendarEvents();
    }

    @Override
    public List<Location> getRecommendedLocations(User user, LatLng latLng) {

        Location location = googleService.reverseGeocodeLocation(latLng);
        LocalTime now = LocalTime.now().minusHours(2);
        LocalTime end = (LocalTime.now().plusHours(2).isAfter(now))
                ?LocalTime.now().plusHours(2):LocalTime.of(23,59);

        List<Location> calendarLocationList = getUserCalendarLocations(user);

        List<Trip> tripList = user.getTrips();
        List<Location> tripLocationList = new ArrayList<>();
        List<Trip>  filteredList = new ArrayList<>();

        for(Trip trip : tripList){
            System.out.println(trip.getJourneyTime() +" "+ end);

            if((trip.getStartLocation().getName().equalsIgnoreCase(location.getName())) &&
                    (LocalTime.parse(trip.getJourneyTime()).toSecondOfDay() >= now.toSecondOfDay())
                    && (LocalTime.parse(trip.getJourneyTime()).toSecondOfDay() <= end.toSecondOfDay())){
                filteredList.add(trip);
            }
        }

        filteredList.sort((t1, t2) -> (t1.getVisits() - t2.getVisits()));
        Collections.reverse(filteredList);

        filteredList.forEach(f -> tripLocationList.add(f.getEndLocation()));
        tripLocationList.addAll(calendarLocationList);
        return tripLocationList;
    }

    @Override
    public User getUser(String userId) {
        Optional<User> user1 = userRepository.findById(userId);
        return user1.orElse(null);
    }
}