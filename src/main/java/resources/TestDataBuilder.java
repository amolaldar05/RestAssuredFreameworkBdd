package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pojo.AddPlace;
import pojo.Location;
import pojo.LoginPayload;

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

	public LoginPayload loginPayload(String userEmail, String userPassword) {
		LoginPayload logIn = new LoginPayload();
		logIn.setUserEmail("jivan@gmail.com");
		logIn.setUserPassword("Jivan@9900");
		return logIn;
	}
	
	public Map<String, String> addProductPayload(String productName, String price, String userId) {
		Map<String, String> productMap= new HashMap<String, String>();
		productMap.put("productName", productName);
		productMap.put("productAddedBy", userId);
		productMap.put("productCategory", "fashion");
		productMap.put("productSubCategory", "Sneaker");
		productMap.put("productPrice", price);
		productMap.put("productDescription", "Addias Originals");
		productMap.put("productFor", "Men");
		return productMap;
		
		
	}
}
