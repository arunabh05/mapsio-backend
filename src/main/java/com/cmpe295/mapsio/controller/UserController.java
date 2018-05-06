package com.cmpe295.mapsio.controller;

import com.cmpe295.mapsio.domain.Error;
import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.LatLng;
import com.cmpe295.mapsio.service.UserService;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.service.ValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cmpe295.mapsio.config.Constants.ERROR_NULL_LOCATION;
import static com.cmpe295.mapsio.config.Constants.ERROR_USER_NOT_FOUND;

/**
 * @author arunabh.shrivastava
 *
 * UserController
 *
 */
@Api(tags = "/users")
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final
    ValidationService validationService;
    private final
    UserService userService;

    @Autowired
    public UserController(ValidationService validationService, UserService userService) {
        this.validationService = validationService;
        this.userService = userService;
    }


    @ApiOperation(value = "Registers a new user.", tags = "users")
    @PostMapping(value = "/authCode")
    public ResponseEntity<?> setUserAuthCode(@RequestBody User user) {
        if(!validationService.isValidUser(user))
            return new ResponseEntity<>(new Error(ERROR_USER_NOT_FOUND), HttpStatus.BAD_REQUEST);

        user = userService.getUserAccessToken(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @ApiOperation(value = "Return recommended locations for a user.", tags = "users")
    @PostMapping(value = "/recommendations")
    public ResponseEntity<?> test(@RequestParam(value = "userId") String userId,
                                  @RequestBody LatLng latlng) {
        if(!validationService.isValidLatlng(latlng))
            return new ResponseEntity<>(new Error(ERROR_NULL_LOCATION), HttpStatus.BAD_REQUEST);

        User user = userService.getUser(userId);
        if(user == null)
            return new ResponseEntity<>(new Error(ERROR_USER_NOT_FOUND), HttpStatus.NOT_FOUND);

        List<Location> locationList = userService.getRecommendedLocations(user, latlng);
        return new ResponseEntity<>(locationList, HttpStatus.OK);
    }
}