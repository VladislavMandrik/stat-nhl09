package com.example.demo.integration;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public abstract class BaseApiTest {

    @Value("${admin.username}")
    protected String adminUsername;
    @Value("${admin.password}")
    protected String adminPassword;
    @Value("${user1.username}")
    protected String user1Username;
    @Value("${user1.password}")
    protected String user1Password;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    protected String loginAndGetSessionId(String username, String password) {
        ValidatableResponse response = given()
                .contentType(ContentType.URLENC)
                .formParam("username", username)
                .formParam("password", password)
                .when()
                .post("/login")
                .then()
                .statusCode(302);

        return response.extract().cookie("JSESSIONID");
    }

    protected String getAdminSessionId() {
        return loginAndGetSessionId(adminUsername, adminPassword);
    }

    protected String getUserSessionId() {
        return loginAndGetSessionId(user1Username, user1Password);
    }
}