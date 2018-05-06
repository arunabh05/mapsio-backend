package com.cmpe295.mapsio.controller;

import com.cmpe295.mapsio.domain.Error;
import com.cmpe295.mapsio.domain.Trip;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.service.TripService;
import com.cmpe295.mapsio.service.ValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cmpe295.mapsio.config.Constants.ERROR_NULL_LOCATION;
import static com.cmpe295.mapsio.config.Constants.ERROR_NULL_TRIP;

/**
 * @author arunabh.shrivastava
 *
 * TripController
 *
 */
@Api(tags = "/trips")
@RestController
@RequestMapping(value = "/api/trips")
public class TripController {

    private final
    TripService tripService;
    private final
    ValidationService validationService;

    @Autowired
    public TripController(TripService tripService, ValidationService validationService) {
        this.tripService = tripService;
        this.validationService = validationService;
    }

    @ApiOperation(value = "Adds a new trip.", tags = "trips")
    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestParam(value = "userId") String userId,
                                         @RequestBody Trip trip) {

        if(!validationService.isValidTrip(trip))
            return new ResponseEntity<>(new Error(ERROR_NULL_TRIP), HttpStatus.BAD_REQUEST);

        User user = validationService.isValidUser(userId);
        if(user == null)
            return new ResponseEntity<>(new Error(ERROR_NULL_LOCATION), HttpStatus.BAD_REQUEST);

        trip = tripService.addNewTrip(user, trip);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }
}
