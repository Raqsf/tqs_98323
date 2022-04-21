package com.rest_assured;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final String URL = "https://jsonplaceholder.typicode.com/todos";

    @Test
    public void whenListAllTodos() {
        when().get(URL).then().statusCode(200);
    }

    @Test
    public void getSpecificQuery() {
        when().get(URL + "/4").then().body("id", equalTo(4)).body("title", equalTo("et porro tempora"));
    }

    @Test
    public void listAllTodos_GetSpecificIds() {
        when().get(URL).then().body("id", hasItems(198, 199));
    }

}
