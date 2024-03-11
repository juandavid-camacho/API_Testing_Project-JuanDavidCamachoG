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

  @smoke
  Scenario: Update the last resource
    Given there are registered resources in the system
    And I retrieve the last resource
    When I send a PUT request to update the last resource
    """
    {
      "name":"Convertible Car",
      "trademark":"Juan",
      "stock":1000,
      "price":99.99,
      "description":"This is a description",
      "tags":"auto",
      "active":true
    }
    """
    Then the response should have status code 200
    And the response should have the following details
      | name             | trademark | stock | price | description           | tags | active |
      | Convertible Car  | Juan      | 1000  | 99.99 | This is a description | auto | True   |
    And validates the response with the resource JSON schema