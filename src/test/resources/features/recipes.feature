# features/recipes.feature
Feature: Recipes API
  As a QA engineer
  I want to test the Recipes API
  So that all read operations work correctly

  Background:
    Given the base URL is “https://dummyjson.com”

  # ═══════════════════════════════════════
  # GET — Positive Scenarios
  # ═══════════════════════════════════════

  @GET @positive @recipes
  Scenario: Get a single recipe successfully
    When I send a GET request to “/recipes/1”
    Then the response status code should be 200
    And the response should contain field “name”
    And the response should contain field “ingredients”
    And the response field “id” should equal “1”

  @GET @positive @recipes
  Scenario: Get list of recipes successfully
    When I send a GET request to “/recipes?limit=6&skip=0"
    Then the response status code should be 200
    And the response should contain field “recipes”
    And the response array “recipes” should not be empty

  @GET @positive @recipes
  Scenario: Get recipes on page 2
    When I send a GET request to “/recipes?limit=6&skip=6”
    Then the response status code should be 200
    And the response array “recipes” should not be empty

  @GET @positive @recipes
  Scenario: Search recipes successfully
    When I send a GET request to “/recipes/search?q=salad”
    Then the response status code should be 200
    And the response should contain field “recipes”
    And the response array “recipes” should not be empty

  @GET @positive @recipes
  Scenario: Get recipes by meal type successfully
    When I send a GET request to “/recipes/meal-type/dinner”
    Then the response status code should be 200
    And the response should contain field “recipes”
    And the response array “recipes” should not be empty

  # ═══════════════════════════════════════
  # GET — Negative Scenarios
  # ═══════════════════════════════════════

  @GET @negative @recipes
  Scenario: Get a non-existent recipe returns 404
    When I send a GET request to “/recipes/0”
    Then the response status code should be 404

  @GET @negative @recipes
  Scenario: Get recipes on non-existent page returns empty list
    When I send a GET request to “/recipes?limit=6&skip=9999"
    Then the response status code should be 200
    And the response array “recipes” should be empty

  @GET @negative @recipes
  Scenario: Search recipes with no match returns empty list
    When I send a GET request to “/recipes/search?q=xyznonexistent”
    Then the response status code should be 200
    And the response array “recipes” should be empty