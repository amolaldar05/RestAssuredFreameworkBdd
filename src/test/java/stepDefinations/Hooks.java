package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		GoogleApiStepDefinations s= new GoogleApiStepDefinations();
		if(GoogleApiStepDefinations.place_id==null) {
		s.add_place_payload_with("Aldar", "French", "France");
		s.user_calls_with_http_request("addPlaceAPI", "Post");
		s.verify_place_id_created_maps_to_using("Aldar", "getPlaceAPI");
		}
	}
	@Before("@AddProduct")
	public void beforeAddProdScenario(Scenario scenario) throws IOException {
		ProductStepDefinations s= new ProductStepDefinations();
		if(ProductStepDefinations.token==null) {
			s.login_to_the_website_using_and("jivan@gmail.com", "Jivan@9900");
			s.the_user_calls_using_http_method("logInAPI", "Post");
			s.in_response_body("token");
			s.in_response_body("userId");
		}
	}
}
