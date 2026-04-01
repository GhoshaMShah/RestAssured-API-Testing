# features/products.feature
Feature: Products API
  As a QA engineer
  I want to test the Products API
  So that all CRUD operations work correctly

  Background:
    Given the base URL is “https://dummyjson.com”

  # ═══════════════════════════════════════
  # GET — Positive Scenarios
  # ═══════════════════════════════════════

  @GET @positive
  Scenario: Get a single product successfully
    When I send a GET request to “/products/1"
    Then the response status code should be 200
    And the response should contain field “title”
    And the response should contain field “price”
    And the response field “id” should equal “1"

  @GET @positive
  Scenario: Get list of products successfully
    When I send a GET request to “/products?limit=6&skip=0”
    Then the response status code should be 200
    And the response should contain field “products”
    And the response array “products” should not be empty

  @GET @positive
  Scenario: Get products on page 2
    When I send a GET request to “/products?limit=6&skip=6"
    Then the response status code should be 200
    And the response array “products” should not be empty

  @GET @positive
  Scenario: Search products successfully
    When I send a GET request to “/products/search?q=phone”
    Then the response status code should be 200
    And the response should contain field “products”
    And the response array “products” should not be empty

  @GET @positive
  Scenario: Get products by category successfully
    When I send a GET request to “/products/category/smartphones”
    Then the response status code should be 200
    And the response should contain field “products”
    And the response array “products” should not be empty

  # ═══════════════════════════════════════
  # GET — Negative Scenarios
  # ═══════════════════════════════════════

  @GET @negative
  Scenario: Get a non-existent product returns 404
    When I send a GET request to “/products/0"
    Then the response status code should be 404

  @GET @negative
  Scenario: Get products on non-existent page returns empty list
    When I send a GET request to “/products?limit=6&skip=9999”
    Then the response status code should be 200
    And the response array “products” should be empty

  # ═══════════════════════════════════════
  # POST — Positive Scenarios
  # ═══════════════════════════════════════

  @POST @positive
  Scenario: Create a new product successfully
    When I send a POST request to “/products/add” with body:
      “”"
      {
        “title”: “Test Product”,
        “price”: 99.99,
        “stock”: 50,
        “brand”: “QA Brand”
      }
      “”"
    Then the response status code should be 201
    And the response should contain field “id”
    And the response field “title” should equal “Test Product”

  @POST @positive
  Scenario: Create another product successfully
    When I send a POST request to “/products/add” with body:
      “”"
      {
        “title”: “Another Product”,
        “price”: 49.99,
        “stock”: 100,
        “brand”: “Test Brand”
      }
      “”"
    Then the response status code should be 201
    And the response should contain field “id”

  # ═══════════════════════════════════════
  # POST — Negative Scenarios
  # ═══════════════════════════════════════

  @POST @negative
  Scenario: Create product with empty body
    When I send a POST request to “/products/add” with body:
      “”"
      {}
      “”"
    Then the response status code should be 201

  # ═══════════════════════════════════════
  # PUT — Positive Scenarios
  # ═══════════════════════════════════════

  @PUT @positive
  Scenario: Full update of a product successfully
    When I send a PUT request to “/products/1” with body:
      “”"
      {
        “title”: “Updated Product”,
        “price”: 199.99
      }
      “”"
    Then the response status code should be 200
    And the response field “title” should equal “Updated Product”

  # ═══════════════════════════════════════
  # PATCH — Positive Scenarios
  # ═══════════════════════════════════════

  @PATCH @positive
  Scenario: Partial update of a product successfully
    When I send a PATCH request to “/products/1” with body:
      “”"
      {
        “price”: 149.99
      }
      “”"
    Then the response status code should be 200
    And the response should contain field “price”

  # ═══════════════════════════════════════
  # DELETE — Positive Scenarios
  # ═══════════════════════════════════════

  @DELETE @positive
  Scenario: Delete an existing product successfully
    When I send a DELETE request to “/products/1"
    Then the response status code should be 200
    And the response should contain field “isDeleted”