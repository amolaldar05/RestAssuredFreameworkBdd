package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	private static ProductStepDefinations productSteps;

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {

		GoogleApiStepDefinations s = new GoogleApiStepDefinations();
		if (GoogleApiStepDefinations.place_id == null) {
			s.add_place_payload_with("Aldar", "French", "France");
			s.user_calls_with_http_request("addPlaceAPI", "Post");
			s.verify_place_id_created_maps_to_using("Aldar", "getPlaceAPI");
		}
	}

	@Before("@AddProduct or @DeleteProduct")
	public void setupAPI(Scenario scenario) throws IOException {
		// Initialize API-related setup
		productSteps = new ProductStepDefinations();

		// Ensure login token is available before executing product scenarios
		if (ProductStepDefinations.token == null) {
			productSteps.login_to_the_website_using_and("jivan@gmail.com", "Jivan@9900");
			productSteps.the_user_calls_using_http_method("logInAPI", "POST");
			productSteps.in_response_body("token");
			productSteps.in_response_body("userId");
		}
	}

	@Before("@DeleteProduct")
	public void beforeAddProductScenario(Scenario scenario) throws IOException {
		// Ensure a product is added before verifying or deleting it
		if (ProductStepDefinations.productId == null) {
			productSteps.add_a_product_with_price("Shoes", "2000");
		}
	}

}
