package com.cmpe295.mapsio.controller;

import com.cmpe295.mapsio.domain.Error;
import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.service.LocationService;
import com.cmpe295.mapsio.service.ValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cmpe295.mapsio.config.Constants.ERROR_LOCATION_NOT_FOUND;
import static com.cmpe295.mapsio.config.Constants.ERROR_NULL_LOCATION;
import static com.cmpe295.mapsio.config.Constants.ERROR_USER_NOT_FOUND;

/**
 * @author arunabh.shrivastava
 */
@Api(tags = "/favorites")
@RestController
@RequestMapping(value = "/api/favorites")
public class FavoriteController {
    private final
    LocationService locationService;
    private final
    ValidationService validationService;

    @Autowired
    public FavoriteController(LocationService locationService, ValidationService validationService) {
        this.locationService = locationService;
        this.validationService = validationService;
    }

    @ApiOperation(value = "Returns list of all favorite locations of a user.", tags = "locations")
    @GetMapping
    public ResponseEntity<?> getAllFavorites(@RequestParam(value = "userId") String userId) {

        User user = validationService.isValidUser(userId);
        if(user == null)
            return new ResponseEntity<>(new Error(ERROR_USER_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<Location> locationList = locationService.getAllFavorites(user);
        return new ResponseEntity<>(locationList, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds a locations to favorite locations of a user.", tags = "locations")
    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestParam(value = "userId") String userId,
                                         @RequestBody Location location) {

        if(!validationService.isValidLocation(location))
            return new ResponseEntity<>(new Error(ERROR_NULL_LOCATION), HttpStatus.BAD_REQUEST);

        User user = validationService.isValidUser(userId);
        if(user == null)
            return new ResponseEntity<>(new Error(ERROR_NULL_LOCATION), HttpStatus.BAD_REQUEST);

        location = locationService.addFavorite(user, location);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @ApiOperation(value = "Removes a location from favorite locations of a user.", tags = "locations")
    @DeleteMapping
    public ResponseEntity<?> removeFavorite(@RequestParam(value = "userId") String userId,
                                            @RequestBody Location location) {

        if(!validationService.isValidLocation(location))
            return new ResponseEntity<>(new Error(ERROR_NULL_LOCATION), HttpStatus.BAD_REQUEST);

        User user = validationService.isValidUser(userId);
        if(user == null)
            return new ResponseEntity<>(new Error(ERROR_NULL_LOCATION), HttpStatus.BAD_REQUEST);

        location = locationService.removeFavorite(user, location);
        if(location == null)
            return new ResponseEntity<>(new Error(ERROR_LOCATION_NOT_FOUND), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
