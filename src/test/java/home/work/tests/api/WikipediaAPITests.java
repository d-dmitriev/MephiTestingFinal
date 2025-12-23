package home.work.tests.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class WikipediaAPITests {

    public static final String TITLE = "title";
    public static final String LANG = "lang";
    public static final String MAIN_PAGE = "Main Page";
    public static final String JAVA_RU = "Ява";
    public static final String RU = "ru";
    public static final String EN = "en";
    public static final String APPIUM = "Appium";
    public static final String USER_AGENT_NAME = "User-Agent";
    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";
    public static final String BASE_URL = "https://en.wikipedia.org/api/rest_v1";
    public static final String BASE_URL_RU = "https://ru.wikipedia.org/api/rest_v1";
    public static final String PAGE_SUMMARY = "/page/summary/";

    @BeforeSuite
    public void setup() {
        // Добавление заголовка User-Agent ко всем запросам чтобы не выдавало 403 Forbidden
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader(USER_AGENT_NAME, USER_AGENT_VALUE)
                .build();
    }

    @Test(description = "API: Search for '" + APPIUM + "' returns relevant results")
    public void testSearchAppium() {
        given()
                .when()
                .get(BASE_URL + PAGE_SUMMARY + APPIUM)
                .then()
                .statusCode(200)
                .body(LANG, equalTo(EN))
                .body(TITLE, containsString(APPIUM));

    }

    @Test(description = "API: Search for '" + JAVA_RU + "' on Russian Wikipedia")
    public void testSearchJavaRu() {
        given()
                .when()
                .get(BASE_URL_RU + PAGE_SUMMARY + JAVA_RU)
                .then()
                .statusCode(200)
                .body(LANG, equalTo(RU))
                .body(TITLE, containsString(JAVA_RU));
    }

    @Test(description = "API: Main page exists and has correct title")
    public void testMainPageExists() {
        given()
                .when()
                .get(BASE_URL + PAGE_SUMMARY + URLEncoder.encode(MAIN_PAGE, StandardCharsets.UTF_8))
                .then()
                .statusCode(200)
                .body(TITLE, equalTo(MAIN_PAGE));
    }
}
