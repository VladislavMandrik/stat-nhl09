package com.example.demo.integration;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Управление доступом")
@Feature("Аутентификация")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthApiTest extends BaseApiTest {

    @Test
    @Story("Успешная аутентификация")
    @DisplayName("Успешный вход в систему с валидными учетными данными")
    @Order(1)
    public void shouldLoginSuccessfullyWithValidCredentials() {
        String sessionId = loginAndGetSessionId(adminUsername, adminPassword);
        Assertions.assertNotNull(sessionId);
        Assertions.assertFalse(sessionId.isEmpty());
    }

    @Test
    @Story("Неудачная аутентификация")
    @DisplayName("Ошибка при неверном пароле")
    @Order(2)
    void shouldFailLoginWithInvalidPassword() {
        Map<String, String> cookies = given()
                .contentType(ContentType.URLENC)
                .formParam("username", adminUsername)
                .formParam("password", "wrongpassword")
                .when()
                .post("/login")
                .then()
                .statusCode(302)
                .header("Location", containsString("error"))
                .extract()
                .cookies();

        String sessionId = cookies.get("JSESSIONID");

        given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get("/statistic/downloadP")
                .then()
                .statusCode(200)
                .body(containsString("Please sign in"));
    }

    @Test
    @Story("Доступ без аутентификации")
    @DisplayName("Публичные endpoint'ы доступны без аутентификации")
    @Order(3)
    void shouldAccessPublicEndpointsWithoutAuth() {
        given()
                .when()
                .get("/statistic/stats")
                .then()
                .statusCode(200);
//        добавить проверку заголовка
        given()
                .when()
                .get("/table/standings")
                .then()
                .statusCode(200);
        //        добавить проверку заголовка
    }

    @Test
    @Story("Доступ с аутентификацией")
    @DisplayName("Защищенные endpoint'ы доступны после логина")
    @Order(4)
    void shouldAccessProtectedEndpointsWithAuth() {
        String sessionId = loginAndGetSessionId(adminUsername, adminPassword);

        given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get("/statistic/uploaded")
                .then()
                .statusCode(200);
        //        добавить проверку заголовка
    }

    @Test
    @Story("Доступ без аутентификации")
    @DisplayName("Защищенные endpoint'ы недоступны без аутентификации")
    @Order(5)
    void shouldNotAccessProtectedEndpointsWithoutAuth() {
        given()
                .when()
                .get("/statistic/uploaded")
                .then()
                .statusCode(200)
                .body(containsString("Please sign in"));
    }

    @Test
    @Story("Logout")
    @DisplayName("После logout сессия становится недействительной")
    @Order(6)
    void shouldInvalidateSessionAfterLogout() {
        String sessionId = loginAndGetSessionId(adminUsername, adminPassword);

        given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .post("/logout")
                .then()
                .statusCode(302);

        given()
                .cookie("JSESSIONID", sessionId)
                .when()
                .get("/statistic/uploaded")
                .then()
                .statusCode(200)
                .body(containsString("Please sign in"));
    }
//    @Test
//    @Story("Ролевой доступ")
//    @DisplayName("Пользователь с ролью USER не может создавать матчи")
//    void shouldReturnForbiddenWhenUserTriesToCreateMatch() {
////        String userToken = getAuthToken("user", "user123");
//
//        given()
////                .auth().oauth2(userToken)
//                .contentType(ContentType.JSON)
//                .body("{\n" +
//                        "    \"homeTeam\": \"Ак Барс\",\n" +
//                        "    \"awayTeam\": \"Салават Юлаев\",\n" +
//                        "    \"homeScore\": 3,\n" +
//                        "    \"awayScore\": 2,\n" +
//                        "    \"matchDate\": \"2024-01-15T19:00:00Z\",\n" +
//                        "    \"tournament\": \"Регулярный чемпионат\",\n" +
//                        "    \"season\": \"2023-2024\"\n" +
//                        "}")
//                .when()
//                .post("/api/matches")
//                .then()
//                .statusCode(HttpStatus.FORBIDDEN.value());
//    }
}