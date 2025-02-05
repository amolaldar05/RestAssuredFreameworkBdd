package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuilder {
	public AddPlace addPlacePayload(String name, String language, String address) {
		AddPlace payload = new AddPlace();
		payload.setAccuracy(50);
		payload.setAddress(address);
		payload.setLanguage(language);
		Location location = new Location();
		location.setLat(30.00567);
		location.setLng(-98.767);
		payload.setLocation(location);
		List<String> list = new ArrayList<String>();
		list.add("shoes Park");
		list.add("Shop");
		payload.setTypes(list);
		payload.setName(name);
		payload.setPhone_number("+91888888888888");
		payload.setWebsite("https://www.website.com");
		return payload;
	}

	public String deletePayload(String place_id) {
		return "{\"place_id\": \"" + place_id + "\"}";
	}
}
