package com.cmpe295.mapsio.controller;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cmpe295.mapsio.config.Constants.ERROR_USER_NOT_FOUND;

/**
 * @author arunabh.shrivastava
 */

@RestController
public class LocationController {

    private final
    LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/api/favorites/")
    public ResponseEntity<?> getAllFavorites(@RequestParam(value = "userId") String userId) {
        List<Location> locationList = locationService.getAllFavorites(userId);
        if(locationList == null)    return new ResponseEntity<>(ERROR_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(locationList, HttpStatus.OK);
    }

    @PostMapping(value = "/api/favorites/")
    public ResponseEntity<?> addFavorite(@RequestParam(value = "userId") String userId,
                                         @RequestBody Location location) {
        location = locationService.addFavorite(userId, location);
        if(location == null)    return new ResponseEntity<>(ERROR_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/favorites/")
    public ResponseEntity<?> removeFavorite(@RequestParam(value = "userId") String userId,
                                            @RequestBody Location location) {
        location = locationService.removeFavorite(userId, location);
        if(location == null)    return new ResponseEntity<>(ERROR_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}