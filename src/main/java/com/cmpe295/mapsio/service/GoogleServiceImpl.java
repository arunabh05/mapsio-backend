package com.cmpe295.mapsio.service;

import com.cmpe295.mapsio.domain.Location;
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
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
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
        // Set path to the Web application client_secret_*.json file you downloaded from the
        // Google API Console: https://console.developers.google.com/apis/credentials
        // You can also find your Web application client ID and client secret from the
        // console and specify them directly when you create the GoogleAuthorizationCodeTokenRequest
        // object.
        // Exchange auth code for access token
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
                            REDIRECT_URI)  // Specify the same redirect URI that you use with your web
                            // app. If you don't have a web version of your app, you can
                            // specify an empty string.
                            .execute();

            user.setAccessToken(tokenResponse.getAccessToken());
            user.setRefreshToken(tokenResponse.getRefreshToken());
            userRepository.save(user);

/*
            // Get profile info from ID token
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();
            String userId = payload.getSubject();  // Use this value as a key to identify a user.
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            System.out.println("pic:: "+pictureUrl);
*/
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
        DateTime end = new DateTime(System.currentTimeMillis() + 86400000);
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
            String CLIENT_SECRET_FILE = "src/main/resources/client_secret.json";
            clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
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

    public Location geocodeLocation(String locationName){
        Location location = new Location();
        location.setName(locationName);

        GeoApiContext context = new GeoApiContext();
        context.setApiKey(GOOGLE_MAPS_API_KEY);

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, locationName).await();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            location.setLatitude(gson.toJson(results[0].geometry.location.lat));
            location.setLongitude(gson.toJson(results[0].geometry.location.lng));
            System.out.println(gson.toJson(results[0].geometry.location.lat));
            System.out.println(gson.toJson(results[0].geometry.location.lng));

        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
            return location;
        }
        return location;
    }
}