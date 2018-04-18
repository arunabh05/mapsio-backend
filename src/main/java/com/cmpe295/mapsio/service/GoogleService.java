package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import com.google.api.services.calendar.model.Event;

import java.util.List;

/**
 * @author arunabh.shrivastava
 */
public interface GoogleService {
    User getUserAccessToken(User user);
    List<Event> getUserCalendarEvents(User user);
    Location geocodeLocation(String location);
}
