package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.ApiResources;
import resources.TestDataBuilder;
import resources.Utils;

public class GoogleApiStepDefinations extends Utils {
	RequestSpecification req;
	ResponseSpecification respSpec;
	Response response;
	static String place_id;
	TestDataBuilder testDataBuilder = new TestDataBuilder();
	CommonStepDefinations commonStepDefinations = new CommonStepDefinations();

	@Given("Add place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		req = given().spec(requestSpecification(ContentType.JSON)).body(testDataBuilder.addPlacePayload(name, language, address)).queryParam("key", "qaclick123");
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		ApiResources resourceApi = ApiResources.valueOf(resource);
		System.out.println("Calling API: " + resourceApi.getResource());

		respSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		switch (method.toUpperCase()) {
		case "POST":
			response = req.when().post(resourceApi.getResource());
			break;
		case "GET":
			response = req.when().get(resourceApi.getResource());
			break;
		case "DELETE":
			response = req.when().delete(resourceApi.getResource());
			break;
		case "PUT":
			response = req.when().put(resourceApi.getResource());
			break;
		default:
			throw new IllegalArgumentException("Invalid HTTP Method: " + method);
		}
		commonStepDefinations.setResponse(response);
		commonStepDefinations.api_call_is_successful_with_status_code(200);
	}

//	@Then("API call got success with status code {int}")
//	public void api_call_got_success_with_status_code(Integer code) {
//		assertEquals((int) code, response.getStatusCode());
//	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		String actualValue = getJsonPath(response, keyValue);
		assertEquals(expectedValue, actualValue);
	}

	@Then("Verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_id = getJsonPath(response, "place_id");
		req = given().spec(requestSpecification(ContentType.JSON)).queryParam("key", "qaclick123").queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		System.out.println("placeId: "+place_id);
		String actualName = getJsonPath(response, "name");
		assertEquals(expectedName, actualName);
	}

	@Given("Delete place Payload")
	public void delete_place_payload() throws IOException {
		req = given().spec(requestSpecification(ContentType.JSON)).body(testDataBuilder.deletePayload(place_id));
	}

}
