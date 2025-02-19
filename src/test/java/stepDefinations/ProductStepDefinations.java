package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import baseTestComponent.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.LoginResponse;
import resources.ApiResources;
import resources.TestDataBuilder;
import resources.Utils;

public class ProductStepDefinations extends Utils {
	// Request & Response Specifications
	private RequestSpecification req;
	private ResponseSpecification respSpec;
	private Response response;

	// API Resources and Test Data
	private ApiResources resourceApi;
	private TestDataBuilder testDataBuilder = new TestDataBuilder();
	private CommonStepDefinations commonStepDefinations = new CommonStepDefinations();

	// WebDriver
	public static WebDriver driver;

	// Static variables for persistence
	public static String productId;
	public static String token;
	private static String userId;
	private static String addedProductName;

	// Configurable image path
	private static final String IMAGE_PATH = System.getProperty("user.dir")
			+ "/src/main/java/resources/imageData/Adidas.jpg";

	/**
	 * Step: Login to the website using email and password
	 **/
	@Given("Login to the website using {string} and {string}")
	public void login_to_the_website_using_and(String userEmail, String userPassword) throws IOException {
		req = given().spec(requestSpecification(ContentType.JSON))
				.body(testDataBuilder.loginPayload(userEmail, userPassword));
	}

	/**
	 * Step: Call an API with a specified HTTP method
	 **/
	@When("The user calls {string} using {string} HTTP method")
	public void the_user_calls_using_http_method(String resource, String method) {
		resourceApi = ApiResources.valueOf(resource);

		respSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();

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

	/**
	 * Step: Validate a key-value pair in response body
	 **/
	@Then("{string} in response body {string}")
	public void in_response_body(String keyValue, String expectedValue) {
		assertEquals("Response value mismatch for key: " + keyValue, expectedValue, getJsonPath(response, keyValue));
	}

	/**
	 * Step: Extract token and userId from response
	 **/
	@Then("{string} in response body")
	public void in_response_body(String key) {
		LoginResponse loginResponse = response.as(LoginResponse.class);

		if (key.equals("token")) {
			token = loginResponse.getToken();
			if (token == null || token.isEmpty()) {
				throw new IllegalStateException("Token extraction failed!");
			}
		} else if (key.equals("userId")) {
			userId = loginResponse.getUserId();
		}
	}

	/**
	 * Step: Add a product using API
	 **/
	@Given("Add a product {string} with price {string}")
	public void add_a_product_with_price(String productName, String price) throws IOException {
		if (token == null || token.isEmpty()) {
			throw new IllegalStateException(
					"Token is missing. Ensure login API is called successfully before adding a product.");
		}

		req = given().spec(requestSpecification(ContentType.MULTIPART)).header("Authorization", token)
				.formParams(testDataBuilder.addProductPayload(productName, price, userId))
				.multiPart("productImage", new File(IMAGE_PATH));

		the_user_calls_using_http_method("addProductAPI", "POST");
		commonStepDefinations.setResponse(response);
		commonStepDefinations.api_call_is_successful_with_status_code(201);
		in_response_body("message", "Product Added Successfully");

		productId = getJsonPath(response, "productId");
		addedProductName = productName;

		if (productId == null || productId.isEmpty()) {
			throw new IllegalStateException("Product ID extraction failed!");
		}
	}

	/**
	 * Step: Navigate to the website
	 **/
	@Given("Navigate to the website")
	public void navigate_to_the_website() throws IOException {
		BaseClass baseClass = new BaseClass();
		baseClass.setUp();
		driver = baseClass.getDriver();
	}

	/**
	 * Step: Enter stored user credentials and log in
	 **/
	@When("Enter {string} and {string} in the respective fields and click on login")
	public void enter_and_in_the_respective_fields_and_click_on_login(String email, String password)
			throws InterruptedException {
		driver.findElement(By.id("userEmail")).sendKeys(email);
		driver.findElement(By.id("userPassword")).sendKeys(password);
		driver.findElement(By.id("login")).click();
		Thread.sleep(2000);
	}

	/**
	 * Step: Validate successful login
	 **/
	@Then("User is logged in successfully")
	public void user_is_logged_in_successfully() {
		assertEquals("https://rahulshettyacademy.com/client/dashboard/dash", driver.getCurrentUrl());
	}

	/**
	 * Step: Fetch product names listed on the website
	 **/
	@Given("Fetch all product names listed on the website")
	public void fetch_all_product_names_listed_on_the_website() {
		List<WebElement> productNames = driver.findElements(By.cssSelector("#products h5 b"));
		productNames.stream().map(WebElement::getText).forEach(System.out::println);
		boolean productFound = productNames.stream()
				.anyMatch(product -> product.getText().equalsIgnoreCase(addedProductName));

		if (!productFound) {
			throw new AssertionError("Added product not found on the website!");
		}
		driver.quit();
	}

	/**
	 * Step: Retrieve the product list from API
	 **/
//	@When("Retrieve the product list from API")
//	public void retrieve_the_product_list_from_api() throws IOException {
//		response = given().spec(requestSpecification(ContentType.JSON)).header("Authorization", token).when()
//				.get("/api/ecom/product/get-products").then().extract().response();
//	}
//
//	/**
//	 * Step: Compare API-added products with website-listed products
//	 **/
//	@Then("Compare the API-added products with website-listed products")
//	public void compare_the_api_added_products_with_website_listed_products() {
//		List<String> apiProductNames = response.jsonPath().getList("products.name");
//		boolean productExists = apiProductNames.contains(addedProductName);
//		assertEquals("Product added via API is not found!", true, productExists);
//	}

	/**
	 * Step: Delete product using stored productId (Optimized for consistent
	 * productId usage)
	 **/
	@Given("Delete product Payload")
	public void delete_product_payload() throws IOException {
		if (productId == null || productId.isEmpty()) {
			throw new IllegalStateException("Product ID is missing. Cannot proceed with deletion.");
		}

		req = given().spec(requestSpecification(ContentType.JSON)).header("Authorization", token).pathParam("productId",
				productId);

		the_user_calls_using_http_method("deleteProductAPI", "DELETE");
		commonStepDefinations.api_call_is_successful_with_status_code(200);
		in_response_body("message", "Product Deleted Successfully");
	}
}
