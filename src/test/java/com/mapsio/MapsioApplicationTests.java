/*
package com.mapsio;

import com.cmpe295.mapsio.MapsioApplication;
import com.cmpe295.mapsio.controller.FavoriteController;
import com.cmpe295.mapsio.domain.Location;
import com.cmpe295.mapsio.domain.User;
import com.cmpe295.mapsio.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FavoriteController.class)
@ContextConfiguration(classes = com.cmpe295.mapsio.*/
/**//*
)
public class MapsioApplicationTests {

    @Autowired
    private MockMvc mockMvc;
	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8")
	);

	@Test
	public void getAllFavoritesForAUser() throws Exception {

        Location first = new Location();
        first.setPlaceId("1");
        Location second = new Location();
        second.setPlaceId("2");
        User user = new User();
        user.setId("1");

        ArrayList<Location> locationArrayList = new ArrayList<>();
        locationArrayList.add(first);
        locationArrayList.add(second);

		mockMvc.perform(get("/api/favorites"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)));

	}
}
*/
