Feature: Naukrigulf Login and Profile Update

  Background: User is logged into Naukrigulf
    Given User is on the Naukrigulf login page
    When User enters valid credentials "amolaldar05@gmail.com" and "Naukarigulf@7799" and clicks on the login button
    Then User should be successfully logged in as "Amol Popat Aldar"

@Regression
  Scenario: Update personal details in the profile
    Given User navigates to "My Profile"
    When User clicks on "View and Edit Profile"
    And User updates "Personal Details" section in the profile and Clicks on the save button
    Then Personal details should be updated successfully
    
@Regression
  Scenario: Upload and save updated CV
    Given User navigates to "My Profile"
    When User clicks on "Update CV"
    And User selects a new CV file and Clicks on the "Save" button
    Then The updated CV should be uploaded successfully
