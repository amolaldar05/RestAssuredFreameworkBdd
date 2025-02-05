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

public class StepDefinations extends Utils {
	RequestSpecification req;
	ResponseSpecification respSpec;
	Response response;
	static String place_id;
	TestDataBuilder testDataBuilder = new TestDataBuilder();

	@Given("Add place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {

		req = given().spec(requestSpecification()).body(testDataBuilder.addPlacePayload(name, language, address));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		ApiResources resourceApi = ApiResources.valueOf(resource);
		System.out.println(resourceApi.getResource());
		respSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("Post")) {
			response = req.when().post(resourceApi.getResource());
		} else if (method.equalsIgnoreCase("Get")) {
			response = req.when().post(resourceApi.getResource());
		} else {
			response = req.when().post(resourceApi.getResource());
		}

	}

	@Then("API call got success with status code {int}")
	public void api_call_got_success_with_status_code(Integer code) {
		assertEquals(response.getStatusCode(), 200);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {

		String actualValue = getJsonPath(response, keyValue);
		assertEquals(expectedValue, actualValue);
	}

	@Then("Verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_id = getJsonPath(response, "place_id");
		req = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "Get");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	}

	@Given("Delete place Payload")
	public void delete_place_payload() throws IOException {
		req = given().spec(requestSpecification()).body(testDataBuilder.deletePayload(place_id));
	}

}