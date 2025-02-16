package stepDefinations;

import static org.junit.Assert.assertEquals;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class CommonStepDefinations {
    private static  Response response;

    public void setResponse(Response response) {
    	CommonStepDefinations.response = response;
    }

    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(Integer code) {
        if (response == null) {
            throw new IllegalStateException("Response is null. Ensure API call was made before this assertion.");
        }
        System.out.println("Response received: " + response.asString());
        assertEquals((int)code, response.getStatusCode());
    }

	
}
