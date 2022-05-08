package com.jileklu2.bakalarska_prace_app.builders.scriptBuilders;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class HttpRequestStringBuilderTest {
    private Route testRoute01;

    private Coordinates testOrigin01;

    private Coordinates testDestination01;

    private Coordinates testWaypoint01;
    private Coordinates testWaypoint02;

    private LinkedHashSet<Coordinates> testWaypointsSet01;

    @BeforeEach
    public void setUp() {
        testOrigin01 = new Coordinates(1.0, 1.0);
        testDestination01 = new Coordinates(3.0,3.0);

        testWaypoint01 = new Coordinates(5.0, 5.0);
        testWaypoint02 = new Coordinates(6.0, 6.0);

        testWaypointsSet01 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02));
        testRoute01 = new Route(testOrigin01, testDestination01, testWaypointsSet01);
    }

    @Test
    public void HereWaypointsOptimizationRequestTest() {
        System.out.println(HttpRequestStringBuilder.hereWaypointsOptimizationRequest(testRoute01));
    }
}
