package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.LatLng;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.repository.UserRepository;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlaceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * @author arunabh.shrivastava
 */
@Service
public class GoogleServiceImpl implements GoogleService{

    private final
    UserRepository userRepository;

    @Value("${google.api.key}")
    private String GOOGLE_MAPS_API_KEY;

    @Autowired
    public GoogleServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserAccessToken(User user){

/*
        // (Receive authCode via HTTPS POST)
        if (request.getHeader('X-Requested-With') == null) {
            // Without the `X-Requested-With` header, this request could be forged. Aborts.
        }
*/
        GoogleClientSecrets clientSecrets;

        try {
            clientSecrets = getClientSecrets();

            String REDIRECT_URI = "";
            GoogleTokenResponse tokenResponse =
                    new GoogleAuthorizationCodeTokenRequest(
                            new NetHttpTransport(),
                            JacksonFactory.getDefaultInstance(),
                            "https://www.googleapis.com/oauth2/v4/token",
                            clientSecrets.getDetails().getClientId(),
                            clientSecrets.getDetails().getClientSecret(),
                            user.getAuthCode(),
                            REDIRECT_URI)
                            .execute();

            user.setAccessToken(tokenResponse.getAccessToken());
            user.setRefreshToken(tokenResponse.getRefreshToken());
            userRepository.save(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private Calendar getUserCalendar(User user){
        Calendar calendar;

        GoogleCredential credentials = getUserGoogleCredentials(user);
        calendar = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                        credentials).setApplicationName("mapsio")
                        .build();
        return calendar;
    }

    public List<Event> getUserCalendarEvents(User user){
            Calendar calendar = getUserCalendar(user);

        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime end = new DateTime(System.currentTimeMillis() + 3600000);
        Events events;
        try {
            events = calendar.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setTimeMax(end)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        List<Event> items = null;
        if (events != null) {
            items = events.getItems();
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        } else {
            System.out.println("No upcoming events found.");
        }
        return items;
    }


    private GoogleClientSecrets getClientSecrets(){
        GoogleClientSecrets clientSecrets = null;

        try {
/*
            String CLIENT_SECRET_FILE = this.getClass().getClassLoader().getResource("client_secret.json").toURI().toString();
            System.out.println(CLIENT_SECRET_FILE);
            CLIENT_SECRET_FILE = CLIENT_SECRET_FILE.substring(9);
            System.out.println(CLIENT_SECRET_FILE);
*/

            InputStream in = getClass().getResourceAsStream("/client_secret.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

/*
            FileReader fileReader = new FileReader(CLIENT_SECRET_FILE);
*/
            clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientSecrets;
    }

    private GoogleCredential getUserGoogleCredentials(User user){
        HttpTransport transport;
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleCredential credentials = null;

        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();
            credentials = new GoogleCredential.Builder().setTransport(transport)
                    .setJsonFactory(jsonFactory)
                    .setClientSecrets(getClientSecrets())
                    .build()
                    .setAccessToken(user.getAccessToken())
                    .setRefreshToken(user.getRefreshToken());

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }


    @Override
    public Location geocodeLocation(String locationName) {
        Location location = new Location();
        location.setName(locationName);

        GeoApiContext context = new GeoApiContext();
        context.setApiKey(GOOGLE_MAPS_API_KEY);

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, locationName).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            location.setLatitude(Double.parseDouble(gson.toJson(results[0].geometry.location.lat)));
            location.setLongitude(Double.parseDouble(gson.toJson(results[0].geometry.location.lng)));

        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
            return location;
        }
        return location;
    }

    @Override
    public Location reverseGeocodeLocation(LatLng latLng){

        Location location = new Location();
        location.setLatLng(latLng);
        com.google.maps.model.LatLng locationLatLng = new com.google.maps.model.LatLng(latLng.lat, latLng.lng);

        GeoApiContext context = new GeoApiContext();
        context.setApiKey(GOOGLE_MAPS_API_KEY);

        try {
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context, locationLatLng).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String placeId = gson.toJson(results[0].placeId);
            location.setPlaceId(placeId.substring(1, placeId.length() - 1));
            System.out.println((gson.toJson(results[0].geometry.location.lat)));
            System.out.println((gson.toJson(results[0].geometry.location.lng)));
            System.out.println(placeId);

        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
            return location;
        }

        return location;
    }


    @Override
    public Location getPlaceDetails(LatLng latLng) {

        Location location = reverseGeocodeLocation(latLng);

        System.out.println(location.getPlaceId());
        GeoApiContext context = new GeoApiContext();
        context.setApiKey(GOOGLE_MAPS_API_KEY);
        PlaceDetailsRequest placeDetailsRequest = new PlaceDetailsRequest(context);
        placeDetailsRequest = placeDetailsRequest.placeId(location.getPlaceId());
        PlaceDetails placeDetails;
        try {
            placeDetails = placeDetailsRequest.await();
            location.setName(placeDetails.name);
            location.setAddress(placeDetails.formattedAddress);
            location.setRating(placeDetails.rating);
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return location;
    }
}