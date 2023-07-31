@crudOps
Feature: Testing on GitLab CRUD operations with OAuth2.0

  Scenario: Generation of OAuth 2.0 Authorization token
    Given I have prepared the headers for the request
    When I send the POST request for the token
    Then I should see the status code 200
    And I should see the access token in response
    And I should see the "expires_in" as "7200" in response

  # POST operation
  Scenario: Creation of GitLab project - POST request
    Given I have parameterized the access token in the request header
    When I send a POST request for "NewProject" with
      | name        | TestProject     |
      | description | TestDescription |
      | path        | new_project     |
    Then I should see the status code 201
    And I should see the project id in the response
    And I should see the "name" partially equals "TestProject" in response
    And I should see the "name_with_namespace" partially equals "John Walter" in response
    And I should see the "issues_access_level" partially equals "enabled" in response

 # GET operation
  Scenario: Get project with the created Project ID - GET request
    Given I have parameterized the access token in the request header
    When I send the get request for the project
    Then I should see the status code 200
    And I should see the "name" partially equals "TestProject" in response
    And I should see the "name_with_namespace" partially equals "John Walter" in response
    And I should see the "issues_access_level" partially equals "enabled" in response

 # POST operation
  Scenario: Creation of Issues to the project
    Given I have parameterized the access token in the request header
    When I send a POST request for "NewIssues" with
      | title       | TestTitle       |
      | description | TestDescription |
    Then I should see the status code 201
    And I should see the issue id in the response
    And I should see the "state" as "opened" in response
    And I should see the "severity" as "UNKNOWN" in response
    And I should see the "type" as "ISSUE" in response

  # PUT operation
  Scenario: Update of the issue created
    Given I have parameterized the access token in the request header
    When I send a PUT request with "add_labels" as "newLabel"
    Then I should see the status code 200
    And I should see the "severity" as "UNKNOWN" in response
    And I should see the "type" as "ISSUE" in response
    And I should see the "title" partially equals "TestTitle" in response

#  # Upload image --> getting 403
#    Given I have parameterized the access token in the request header
#    When I upload the metric image for the issues
#    Then I should see the status code 200

  # Subscribe the issue which is already subscribed - Negative testing
  Scenario: Subscribe to an issue - Negative validation
    Given I have parameterized the access token in the request header
    When I send a POST request for "Subscribe"
    Then I should see the status code 304

  # DELETE operation --> Issue
  Scenario: Delete the created issue
    Given I have parameterized the access token in the request header
    When I send the delete request for the issues
    Then I should see the status code 204

  # DELETE operation --> Project
  Scenario: Delete the created issue
    Given I have parameterized the access token in the request header
    When I send the delete request for the project
    Then I should see the status code 202
