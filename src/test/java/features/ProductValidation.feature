# Feature: Product Management Validation using RestAssured and Selenium
# This feature validates product management functionality on the website using API and UI automation.

Feature: Product Management Validation on Website  

  @Login
  Scenario: Verify User Login via API  
    Given Login to the website using "userEmail" and "userPassword"  
    When The user calls "logInAPI" using "POST" HTTP method  
    Then API call is successful with status code 200  
    And "message" in response body "Login Successfully"  
    And "token" in response body  
    And "userId" in response body  

  @AddProduct
  Scenario Outline: Verify Adding a Product via API  
    Given Add a product "<productName>" with price "<price>"  
    When The user calls "AddProductAPI" using "POST" HTTP method  
    Then API call is successful with status code 201  
    And "message" in response body "Product Added Successfully"  

    Examples:  
      | productName | price |  
      | Shoes       | 2000  |  
     # | Goggles     | 1000  | 
     
  

  @LoginUsingSelenium
  Scenario Outline: Verify User Login on the Website Using Selenium  
    Given Navigate to the website  
    When Enter "<userEmail>" and "<userPassword>" in the respective fields and click on login   
    Then User is logged in successfully 
    Examples: 
     | userEmail        | userPassword |  
     | jivan@gmail.com  | Jivan@9900   |

 
    
    @DeleteProduct  
Scenario: delete added place
Given Delete product Payload 
When The user calls "deleteProductAPI" using "DELETE" HTTP method 
Then API call is successful with status code 200 
And "status" in response body is "OK"
And "message" in response body "Product Deleted Successfully" 
