package com.jileklu2.bakalarska_prace_app.mapObjects;

import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.mapObjects.Variable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class RouteStepTest {

    private Coordinates testOrigin01;

    private Coordinates testDest01;
    private RouteStep testRouteStep01;

    private RouteStep testRouteStep02;

    private Variable testVariable01;
    private HashSet<Variable> testVariables01;


    @BeforeEach
    public void setUp(){
        testOrigin01 = new Coordinates(1.0,1.0);
        testDest01 = new Coordinates(2.0,2.0);
        testRouteStep01 = new RouteStep(testOrigin01, testDest01, 10.0,5.0,3);

        testVariable01 = new Variable("test", "0");
        testVariables01 = new HashSet<>(List.of(testVariable01));

        testRouteStep02 = new RouteStep(testOrigin01, testDest01, 10.0,
                                5.0,3,testVariables01);
    }

    @Test
    public void equalsTest() {
        Assertions.assertEquals(testRouteStep01, testRouteStep01);
        Assertions.assertEquals(testRouteStep02, testRouteStep02);
        Assertions.assertNotEquals(testRouteStep01, testRouteStep02);
    }

    @Test
    public void jsonConstructorTest(){
        JSONObject jsonOrigin = new JSONObject()
                .put("lat", "1")
                .put("lng", "1");

        JSONObject jsonDest = new JSONObject()
                .put("lat", "2")
                .put("lng", "2");

        JSONObject jsonVariable = new JSONObject()
                .put("test","0");

        JSONObject jsonObject = new JSONObject()
                .put("origin", jsonOrigin)
                .put("destination", jsonDest)
                .put("distance", 10.0)
                .put("duration", 5.0)
                .put("stepNumber", 3)
                .put("averageSpeed", 15.4)
                .put("variables", new JSONArray());

        RouteStep testRouteStep = new RouteStep(jsonObject);
        Assertions.assertEquals(testRouteStep01,testRouteStep);

        jsonObject = new JSONObject()
                .put("origin", jsonOrigin)
                .put("destination", jsonDest)
                .put("distance", 10.0)
                .put("duration", 5.0)
                .put("stepNumber", 3)
                .put("averageSpeed", 15.4)
                .put("variables", new JSONArray().put(jsonVariable));

        testRouteStep = new RouteStep(jsonObject);
        Assertions.assertEquals(testRouteStep02,testRouteStep);
    }

    @Test
    public void jsonConstructorAssertionsTest(){
        JSONObject jsonObject = new JSONObject()
                .put("origin", "10");

        try {
            RouteStep testRouteStep = new RouteStep(jsonObject);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Wrong JSON file structure."))
                Assertions.fail("Wrong exception message.");
        }
    }
}
