Feature: Receive jokes from the Joke service

  Scenario: Get a random joke
    When I make a successful GET request to "/v1/jokes/random"
    Then the response body should be "application/json"
    Then the response status code should be 200
    And the response body should contain a joke

  Scenario: Get a joke by a category
    When I make a successful GET request to "/v1/jokes/category?category=dark"
    Then the response body should be "application/json"
    Then the response status code should be 200
    And the response body should contain a joke

  Scenario: Get a joke by a unknown category
    When I make a failure GET request to "/v1/jokes/category?category=dark1"
    Then the failure response body should be "application/json"
    Then the failure response status code should be 400
    And the response body should contain an error

  Scenario: Get a joke by search
    When I make a successful GET request to "/v1/jokes/search?query=java"
    Then the response body should be "application/json"
    Then the response status code should be 200
    And the response body should contain a joke

  Scenario: Make too many requests for service to trigger rate limiter
    When I make a rate limiter GET request to "/v1/jokes/random"
    And the rate limiter response status code should be 200
    Then I make another rate limiter GET request to "/v1/jokes/random"
    And the rate limiter response status code should be 200
    Then I make another rate limiter GET request to "/v1/jokes/random"
    And the rate limiter response status code should be 200
    Then I make another rate limiter GET request to "/v1/jokes/random"
    And the rate limiter response status code should be 200
    Then I make another rate limiter GET request to "/v1/jokes/random"
    And the rate limiter response status code should be 429