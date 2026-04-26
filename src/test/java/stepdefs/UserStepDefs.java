package stepdefs;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class UserStepDefs {

    private Response response;

    // ── Background ────────────────────────────────────────────────────────────
    @Given("the base URL is {string}")
    public void setBaseUrl(String url) {
        RestAssured.baseURI = url;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        System.out.println("Base URL set to: " + url);
    }

    // ── GET ───────────────────────────────────────────────────────────────────
    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = given()
            .contentType(ContentType.JSON)
        .when()
            .get(endpoint)
        .then()
            .extract().response();

        System.out.println("GET " + endpoint + " → " + response.statusCode());
    }

    // ── POST ──────────────────────────────────────────────────────────────────
    @When("I send a POST request to {string} with body:")
    public void sendPostRequest(String endpoint, String body) {
        response = given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post(endpoint)
        .then()
            .extract().response();

        System.out.println("POST " + endpoint + " → " + response.statusCode());
    }

    // ── PUT ───────────────────────────────────────────────────────────────────
    @When("I send a PUT request to {string} with body:")
    public void sendPutRequest(String endpoint, String body) {
        response = given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .put(endpoint)
        .then()
            .extract().response();

        System.out.println("PUT " + endpoint + " → " + response.statusCode());
    }

    // ── PATCH ─────────────────────────────────────────────────────────────────
    @When("I send a PATCH request to {string} with body:")
    public void sendPatchRequest(String endpoint, String body) {
        response = given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .patch(endpoint)
        .then()
            .extract().response();

        System.out.println("PATCH " + endpoint + " → " + response.statusCode());
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    @When("I send a DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        response = given()
            .contentType(ContentType.JSON)
        .when()
            .delete(endpoint)
        .then()
            .extract().response();

        System.out.println("DELETE " + endpoint + " → " + response.statusCode());
    }

    // ════════════════════════════════════════════════════════════════════════
    // ASSERTIONS using TestNG 
    // ════════════════════════════════════════════════════════════════════════

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        int actual = response.statusCode();
        Assert.assertEquals(actual, expectedStatusCode,
            "Expected status " + expectedStatusCode + " but got " + actual);
        System.out.println("Status code: " + actual);
    }

    @And("the response should contain field {string}")
    public void verifyFieldExists(String fieldPath) {
        Object value = response.jsonPath().get(fieldPath);
        Assert.assertNotNull(value,
            "Expected field '" + fieldPath + "' to exist but was null");
        System.out.println("Field '" + fieldPath + "' exists: " + value);
    }

    @And("the response field {string} should equal {string}")
    public void verifyFieldValue(String fieldPath, String expected) {
        Object raw = response.jsonPath().get(fieldPath);
        String actual = (raw == null) ? "null" : String.valueOf(raw);
        Assert.assertEquals(actual, expected,
            "Field '" + fieldPath + "' expected '" + expected + "' got '" + actual + "'");
        System.out.println("Field '" + fieldPath + "' = " + actual);
    }

    @And("the response field {string} should not be empty")
    public void verifyFieldNotEmpty(String fieldPath) {
        Object raw = response.jsonPath().get(fieldPath);
        String value = (raw == null) ? "null" : String.valueOf(raw);
        Assert.assertFalse(
            value.isEmpty() || value.equals("null"),
            "Field '" + fieldPath + "' should not be empty"
        );
        System.out.println("Field '" + fieldPath + "' is not empty: " + value);
    }

    @And("the response array {string} should not be empty")
    public void verifyArrayNotEmpty(String fieldPath) {
        java.util.List<?> list = response.jsonPath().getList(fieldPath);
        Assert.assertNotNull(list, "Array '" + fieldPath + "' is null");
        Assert.assertFalse(list.isEmpty(), "Array '" + fieldPath + "' is empty");
        System.out.println("Array '" + fieldPath + "' has " + list.size() + " items");
    }

    @And("the response array {string} should be empty")
    public void verifyArrayEmpty(String fieldPath) {
        java.util.List<?> list = response.jsonPath().getList(fieldPath);
        Assert.assertNotNull(list, "Array '" + fieldPath + "' is null");
        Assert.assertTrue(list.isEmpty(), "Array '" + fieldPath + "' should be empty");
        System.out.println("Array '" + fieldPath + "' is empty as expected");
    }

    @And("the response body should be empty")
    public void verifyBodyEmpty() {
        String body = response.getBody().asString();
        Assert.assertTrue(
            body == null || body.trim().isEmpty(),
            "Expected empty body but got: " + body
        );
        System.out.println("Response body is empty");
    }
}
