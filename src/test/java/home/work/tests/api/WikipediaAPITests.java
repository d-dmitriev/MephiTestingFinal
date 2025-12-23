package home.work.tests.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class WikipediaAPITests {

    private static final String BASE_URL = "https://en.wikipedia.org/api/rest_v1";
    public static final String TITLE = "title";
    public static final String LANG = "lang";
    public static final String MAIN_PAGE = "Main Page";
    public static final String JAVA_RU = "Ява";
    public static final String RU = "ru";
    public static final String EN = "en";
    public static final String APPIUM = "Appium";
    public static final String USER_AGENT_NAME = "User-Agent";
    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";

    @BeforeSuite
    public void setup() {
        // Добавление заголовка User-Agent ко всем запросам чтобы не выдавало 403 Forbidden
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader(USER_AGENT_NAME, USER_AGENT_VALUE)
                .build();
    }

    @Test(description = "API: Search for '" + APPIUM + "' returns relevant results")
    public void testSearchAppium() {
        Response response = given()
                .when()
                .get(BASE_URL + "/page/summary/" + APPIUM)
                .then()
                .statusCode(200)
                .extract().response();

        String title = response.jsonPath().getString(TITLE);
        String lang = response.jsonPath().getString(LANG);

        Assert.assertEquals(lang, EN, String.format("Language is not '%s'", EN));
        Assert.assertTrue(title.toLowerCase().contains(APPIUM.toLowerCase()), String.format("Title does not contain '%s'", APPIUM));
    }

    @Test(description = "API: Search for '" +  JAVA_RU + "' on Russian Wikipedia")
    public void testSearchJavaRu() {
        String ruApiUrl = "https://ru.wikipedia.org/api/rest_v1/page/summary/" + JAVA_RU;

        given()
                .when()
                .get(ruApiUrl)
                .then()
                .statusCode(200)
                .body(LANG, equalTo(RU))
                .body(TITLE, containsString(JAVA_RU));
    }

    @Test(description = "API: Main page exists and has correct title")
    public void testMainPageExists() {
        given()
                .when()
                .get(BASE_URL + "/page/summary/Main_Page")
                .then()
                .statusCode(200)
                .body(TITLE, equalTo(MAIN_PAGE));
    }
}
