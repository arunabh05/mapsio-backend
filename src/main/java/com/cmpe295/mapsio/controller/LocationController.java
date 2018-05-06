package com.cmpe295.mapsio.controller;

import com.cmpe295.mapsio.domain.LatLng;
import com.cmpe295.mapsio.domain.Location;

import com.cmpe295.mapsio.service.LocationService;

import com.cmpe295.mapsio.service.ValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cmpe295.mapsio.config.Constants.ERROR_NULL_LATLNG;

/**
 * @author arunabh.shrivastava
 *
 * LocationController
 */

@Api(tags = "/locations")
@RestController
@RequestMapping(value = "/api/locations")
public class LocationController {

    private final
    LocationService locationService;
    private final
    ValidationService validationService;

    @Autowired
    public LocationController(LocationService locationService, ValidationService validationService) {
        this.locationService = locationService;
        this.validationService = validationService;
    }

    @ApiOperation(value = "Returns the location with place details.", tags = "locations")
    @PostMapping
    public ResponseEntity<?> getLocationPlaceDetails(@RequestBody LatLng latLng) {
        if(!validationService.isValidLatlng(latLng))
            return new ResponseEntity<>(new Error(ERROR_NULL_LATLNG), HttpStatus.BAD_REQUEST);

        Location location = locationService.getLocationPlaceDetails(latLng);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}