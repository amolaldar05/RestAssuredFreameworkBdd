Feature: Validating Place API

@AddPlace @Regression
Scenario Outline: Verify that place is added successfully using addPlace

Given Add place Payload with "<name>" "<language>" "<address>"
When user calls "addPlaceAPI" with "POST" http request
Then API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And Verify place_id created maps to "<name>" using "getPlaceAPI"

Examples:
    | name | language | address |
    | Saheb| English  | Solapur |
#    | Sneha| German   | Pune    |
    
@DeletePlace  @Regression
Scenario: delete added place
Given Delete place Payload 
When user calls "deletePlaceAPI" with "POST" http request
Then API call got success with status code 200
And "status" in response body is "OK"    