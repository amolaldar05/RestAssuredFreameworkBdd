package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		StepDefinations s= new StepDefinations();
		if(StepDefinations.place_id==null) {
		s.add_place_payload_with("Aldar", "French", "France");
		s.user_calls_with_http_request("addPlaceAPI", "Post");
		s.verify_place_id_created_maps_to_using("Aldar", "getPlaceAPI");
		}
	}
}
