package com.cmpe295.mapsio.controller;

import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.cmpe295.mapsio.config.Constants.ERROR_USER_NOT_FOUND;

/**
 * @author arunabh.shrivastava
 */
@RestController
public class TripController {

    private final
    TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping(value = "/api/trips/")
    public ResponseEntity<?> addFavorite(@RequestParam(value = "userId") String userId,
                                         @RequestBody Trip trip) {
        trip = tripService.addNewTrip(userId, trip);
        if(trip == null)    return new ResponseEntity<>(ERROR_USER_NOT_FOUND,
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

}
