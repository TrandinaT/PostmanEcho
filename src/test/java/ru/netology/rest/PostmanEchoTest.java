package ru.netology.rest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.json.JSONObject;


public class PostmanEchoTest {

    @Test
    void sendingRequest() {
        given()
                .baseUri("https://postman-echo.com")
                .body("Test")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data", equalTo("Test"));
    }

    @Test
    void SendPostRequest() {
        JSONObject requestBody = new JSONObject()
                .put("name", "test name")
                .put("age", 18)
                .put("hobby", "sport");

        given()
                .baseUri("https://postman-echo.com")
                .body(requestBody.toString())
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data", containsString("test name"));
    }

    @Test
    void returnGet() {
        given()
                .baseUri("https://postman-echo.com")
                .param("foo1", "bar1")
                .param("foo2", "bar2")
                .when()
                .get("/get")
                // .get("/get?foo1=bar1&foo2=bar2") или вот так вместо параметров
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("args.foo1", equalTo("bar1"))
                .body("headers.x-forwarded-proto", equalTo("https"));
    }

    @Test
    void requestingHeaders() {
        given()
                .baseUri("https://postman-echo.com")
                .contentType("text/plain; charset=UTF-8")
                .body("тест")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .header("content-type", "application/json; charset=utf-8")
                .body("data", equalTo("тест"));
    }
}
