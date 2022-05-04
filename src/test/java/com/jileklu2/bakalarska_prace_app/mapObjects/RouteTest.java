package com.jileklu2.bakalarska_prace_app.mapObjects;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.*;

public class RouteTest {
    private Coordinates testOrigin01;
    private Coordinates testOrigin02;
    private Coordinates testOrigin03;

    private Coordinates testDestination01;
    private Coordinates testDestination02;
    private Coordinates testDestination03;

    private Coordinates testWaypoint01;
    private Coordinates testWaypoint02;
    private Coordinates testWaypoint03;
    private Coordinates testWaypoint04;

    private List<Coordinates> testWaypointsList01;

    private LinkedHashSet<Coordinates> testWaypointsSet01;
    private LinkedHashSet<Coordinates> testWaypointsSet02;
    private LinkedHashSet<Coordinates> testWaypointsSet03;
    private LinkedHashSet<Coordinates> testWaypointsSet04;
    private LinkedHashSet<Coordinates> testWaypointsSet05;
    private LinkedHashSet<Coordinates> testWaypointsSet06;
    private LinkedHashSet<Coordinates> testWaypointsSet07;

    private Route testRoute01;
    private Route testRoute02;
    private Route testRoute03;
    private Route testRoute04;
    private Route testRoute05;
    private Route testRoute06;
    private Route testRoute07;
    private Route testRoute08;
    private Route testRoute09;
    private Route testRoute10;
    private Route testRoute11;
    private Route testRoute12;
    private Route testRoute13;
    private Route testRoute14;
    private Route testRoute15;
    private Route testRoute16;
    private Route testRoute17;
    private Route testRoute18;

    private JSONObject testJsonObj01;

    private JSONObject testJsonObj02;

    @BeforeEach
    public void setUp() {
        testOrigin01 = new Coordinates(1.0, 1.0);
        testOrigin02 = new Coordinates(2.0,2.0);

        testDestination01 = new Coordinates(3.0,3.0);
        testDestination02 = new Coordinates(4.0,4.0);

        testDestination03 = new Coordinates(testOrigin02);
        testOrigin03 = new Coordinates(testDestination02);

        testWaypoint01 = new Coordinates(5.0, 5.0);
        testWaypoint02 = new Coordinates(6.0, 6.0);
        testWaypoint03 = new Coordinates(5.0, 5.0);
        testWaypoint04 = new Coordinates(7.0, 7.0);

        testWaypointsList01 = Arrays.asList(testWaypoint01, testWaypoint02);

        testWaypointsSet01 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02));
        testWaypointsSet02 = new LinkedHashSet<>(Arrays.asList(testWaypoint03, testWaypoint04));
        testWaypointsSet03 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testOrigin02, testDestination01));
        testWaypointsSet04 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testDestination03));
        testWaypointsSet05 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testOrigin03));
        testWaypointsSet06 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testOrigin01));
        testWaypointsSet07 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testDestination01));

        testRoute01 = new Route(testOrigin01, testDestination01);
        testRoute02 = new Route(testOrigin01, testDestination01, testWaypointsList01);
        testRoute03 = new Route(testRoute01);
        testRoute04 = new Route(testOrigin01, testDestination01, testWaypointsSet01);
        testRoute05 = new Route(testOrigin02, testDestination02, testWaypointsSet02);
        testRoute06 = new Route(testOrigin01, testDestination02, testWaypointsSet03);
        testRoute07 = new Route(testOrigin01, testDestination03, testWaypointsSet01);
        testRoute08 = new Route(testOrigin02, testDestination02, testWaypointsSet02);
        testRoute09 = new Route(testOrigin01, testDestination02, testWaypointsSet04);
        testRoute10 = new Route(testOrigin03, testDestination01, testWaypointsSet01);
        testRoute11 = new Route(testOrigin02, testDestination02, testWaypointsSet02);
        testRoute12 = new Route(testOrigin02, testDestination01, testWaypointsSet05);
        testRoute13 = new Route(testOrigin01, testDestination01, testWaypointsSet01);
        testRoute14 = new Route(testOrigin01, testDestination02, testWaypointsSet02);
        testRoute15 = new Route(testDestination01, testDestination02, testWaypointsSet06);
        testRoute16 = new Route(testOrigin01, testDestination01, testWaypointsSet01);
        testRoute17 = new Route(testOrigin02, testDestination01, testWaypointsSet02);
        testRoute18 = new Route(testOrigin01, testOrigin02, testWaypointsSet07);

        testJsonObj01 = new JSONObject()
                        .put("origin", testOrigin01.toJSON())
                        .put("destination", testDestination01.toJSON())
                        .put("steps", new JSONArray())
                        .put("waypoints", new JSONArray());

        testJsonObj02 = new JSONObject()
                        .put("origin", testOrigin01.toJSON())
                        .put("destination", testDestination01.toJSON())
                        .put("steps", new JSONArray())
                        .put("waypoints", new JSONArray(testWaypointsSet01));
    }

    @Test
    public void basicFunctionalitiesTest(){
        // Correct origin and destination initial test
        Assertions.assertEquals(testRoute01.getOrigin(), testOrigin01);
        Assertions.assertEquals(testRoute02.getOrigin(), testOrigin01);
        Assertions.assertEquals(testRoute01.getDestination(), testDestination01);
        Assertions.assertEquals(testRoute02.getDestination(), testDestination01);

        //Copy constructor test
        Assertions.assertEquals(testRoute01, testRoute03);

        // Origin,destination and waypoints set test
        testRoute01.setOrigin(testOrigin02);
        testRoute01.setDestination(testDestination02);
        testRoute01.setWaypoints(testWaypointsSet01);

        Assertions.assertEquals(testRoute01.getOrigin(), testOrigin02);
        Assertions.assertEquals(testRoute01.getDestination(), testDestination02);
        Assertions.assertEquals(testRoute01.getWaypoints(), testWaypointsSet01);


        // Routes equality test
        testRoute01.setOrigin(testOrigin01);
        testRoute01.setDestination(testDestination01);

        Assertions.assertEquals(testRoute02, testRoute02);
        Assertions.assertEquals(testRoute01, testRoute02);
        Assertions.assertNotEquals(testRoute01, testRoute03);
    }

    @Test
    public void routeWaypointsTest(){
        Route testRouteLocal01 = new Route(testRoute01);
        Route testRouteLocal02 = new Route(testRoute01);

        // Equality of adding waypoints one by one and by set test
        testRouteLocal01.addWaypoint(testWaypoint01);
        testRouteLocal01.addWaypoint(testWaypoint02);
        testRouteLocal02.addWaypoints(testWaypointsSet01);

        Assertions.assertEquals(testRouteLocal01.getWaypoints(), testWaypointsSet01);
        Assertions.assertEquals(testRouteLocal02.getWaypoints(), testWaypointsSet01);

        Assertions.assertEquals(testRouteLocal01.getWaypoints(), testRoute04.getWaypoints());
        Assertions.assertEquals(testRouteLocal02.getWaypoints(), testRoute04.getWaypoints());

        Assertions.assertEquals(testRouteLocal01, testRouteLocal02);
        Assertions.assertEquals(testRouteLocal02, testRoute04);
    }

    @Test
    public void routeChainingTest(){
        /*
         *      I) A.origin != (B.origin, B.destination)
         *         A.destination != (B.origin, B.destination)
         *              * C.origin = A.origin
         *              * C.destination = B.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.destination + B.origin
         */
        Route chainedTestRoute01 = testRoute04.chainWithRoute(testRoute05);
        Assertions.assertEquals(chainedTestRoute01, testRoute06);

        /*
         *      II) A.origin != (B.origin, B.destination),
         *          A.destination = B.origin, A.destination != B.destination
         *              * C.origin = A.origin
         *              * C.destination = B.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.destination
         */
        Route chainedTestRoute02 = testRoute07.chainWithRoute(testRoute08);
        Assertions.assertEquals(chainedTestRoute02, testRoute09);

        /*
         *      III) A.origin = B.destination, A.origin != B.origin,
         *           A.destination != (B.origin, B.destination)
         *              * C.origin = B.origin
         *              * C.destination = A.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.origin
         */
        Route chainedTestRoute03 = testRoute10.chainWithRoute(testRoute11);
        Assertions.assertEquals(chainedTestRoute03, testRoute12);

        /*
         *      IV) A.origin = B.origin, A.origin != B.destination,
         *          A.destination != (B.origin, B.destination)
         *              * C.origin = A.destination
         *              * C.destination = B.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.origin
         */
        Route chainedTestRoute04 = testRoute13.chainWithRoute(testRoute14);
        Assertions.assertEquals(chainedTestRoute04, testRoute15);

        /*
         *      V) A.origin != (B.origin, B.destination),
         *         A.destination = B.destination, A.destination != B.origin
         *              * C.origin = A.origin
         *              * C.destination = B.origin
         *              * C.waypoints = A.waypoints + B.waypoints + A.destination
         */
        Route chainedTestRoute05 = testRoute16.chainWithRoute(testRoute17);
        Assertions.assertEquals(chainedTestRoute05, testRoute18);
        //Multiple routes chaining
        //todo
    }

    @Test
    public void toStringTest() {
        String string01 = "{origin:{lat:1.0, lng:1.0, ele:0.0},waypoints:[],destination:{lat:3.0, lng:3.0, ele:0.0},steps:[]}";
        String string02 = "{origin:{lat:1.0, lng:1.0, ele:0.0},waypoints:[{lat:5.0, lng:5.0, ele:0.0}, {lat:6.0, lng:6.0, ele:0.0}]," +
                          "destination:{lat:3.0, lng:3.0, ele:0.0},steps:[]}";
        Assertions.assertEquals(string01,testRoute01.toString());
        Assertions.assertEquals(string02,testRoute02.toString());
    }

    @Test
    public void toJsonTest(){
        JSONAssert.assertEquals(testJsonObj01,testRoute01.toJSON(), false);
        JSONAssert.assertEquals(testJsonObj02,testRoute02.toJSON(), false);
    }

    @Test
    public void jsonConstructorTest(){
    }
    @Test
    public void jsonConstructorAssertionsTest(){
        JSONObject jsonObject = new JSONObject()
                .put("origin", "10");

        try {
            Route testRouteStep = new Route(jsonObject);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Wrong JSON file structure."))
                Assertions.fail("Wrong exception message.");
        }
    }


}
