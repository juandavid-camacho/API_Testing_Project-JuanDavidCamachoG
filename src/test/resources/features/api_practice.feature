@active
Feature: Testing APIs

  @smoke
  Scenario: Get list of clients
    Given there are registered clients in the system
    When I send a GET request to retrieve the full list of clients
    Then the response should have a status code 200
    And validates the response with the client list JSON schema

  @smoke
  Scenario: Get list of resources
    Given there are registered resources in the system
    When I send a GET request to retrieve the full list of resources
    Then the response should have status code 200
    And validates the response with the resource list JSON schema

  @smoke
  Scenario: Create a new client
    Given I have a client with the following details:
      | name  | lastName  | country  | city   | email         | phone      |
      | Juan  | Milan     | Colombia | Bogota | juan@test.com | 4885233857 |
    When I send a POST request to create a new client
    Then the response should have a status code 201
    And the response should include the details of the new client
    And validates the response with the client JSON schema
