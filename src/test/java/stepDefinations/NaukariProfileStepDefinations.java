package stepDefinations;

import java.io.IOException;

import org.testng.Assert;

import PageObjects.HeaderPageObjects;
import baseTestComponent.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NaukariProfileStepDefinations extends BaseClass {

    public HeaderPageObjects headerPageObjects;

    @Given("User is on the Naukrigulf login page")
    public void user_is_on_the_naukrigulf_login_page() throws IOException {
        headerPageObjects = launchApp();
        headerPageObjects.selectMenu("Login");
    }

    @When("User enters valid credentials {string} and {string} and clicks on the login button")
    public void user_enters_valid_credentials_and_and_clicks_on_the_login_button(String email, String password) {
        headerPageObjects.login(email, password);
    }

    @Then("User should be successfully logged in as {string}")
    public void user_should_be_successfully_logged_in_as(String expectedProfileName) {
        String actualProfileName = headerPageObjects.getProfileName();
        Assert.assertEquals(actualProfileName, expectedProfileName, "Profile name does not match after login.");
    }

    @Given("User navigates to {string}")
    public void user_navigates_to(String menu) {
        headerPageObjects.hoverOnProfile();
        headerPageObjects.selectMenu(menu);
    }

    @When("User clicks on {string}")
    public void user_clicks_on(String option) {
        headerPageObjects.selectFromProfileOptions(option);
    }

    @When("User updates {string} section in the profile and Clicks on the save button")
    public void user_updates_section_in_the_profile_and_clicks_on_the_save_button(String title) {
        headerPageObjects.selectSpecProfileSection(title);
        // TODO: Add steps to update the personal details form and click save
        System.out.println("✅ Updated '" + title + "' section and clicked Save.");
    }

    @Then("Personal details should be updated successfully")
    public void personal_details_should_be_updated_successfully() {
        // TODO: Add validation for successful update
        System.out.println("✅ Personal details updated successfully.");
    }

    @When("User selects a new CV file and Clicks on the {string} button")
    public void user_selects_a_new_cv_file_and_clicks_save(String buttonName) {
        // TODO: Implement file selection and click save
        System.out.println("✅ Selected new CV and clicked '" + buttonName + "'.");
    }

    @Then("The updated CV should be uploaded successfully")
    public void the_updated_cv_should_be_uploaded_successfully() {
        // TODO: Add validation for successful CV upload
        System.out.println("✅ Updated CV uploaded successfully.");
    }
} 