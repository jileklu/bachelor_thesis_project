package com.jileklu2.bakalarska_prace_app;

import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class RouteTest {
    @Test
    public void basicFunctionalitiesTest(){
        Coordinates testOrigin01 = new Coordinates(51.970390, 0.979328);
        Coordinates testOrigin02 = new Coordinates(51.956771,1.268376);

        Coordinates testDestination01 = new Coordinates(52.038683,0.668408);
        Coordinates testDestination02 = new Coordinates(52.097693,0.667931);

        Coordinates testWaypoint01 = new Coordinates(52.007232, 0.902119);
        Coordinates testWaypoint02 = new Coordinates(51.983350, 1.158283);

        List<Coordinates> testCoordinatesList01 = Arrays.asList(testWaypoint01, testWaypoint02);

        LinkedHashSet<Coordinates> testCoordinatesSet01 = new LinkedHashSet<>(testCoordinatesList01);

        Route testRoute01 = new Route(testOrigin01, testDestination01);
        Route testRoute02 = new Route(testOrigin01, testDestination01, testCoordinatesList01);
        Route testRoute03 = new Route(testOrigin01, testDestination01, testCoordinatesList01);
        Route testRoute04 = new Route(testOrigin01, testDestination01);

        // Correct origin and destination initial test
        assertEquals(testRoute01.getOrigin(), testOrigin01);
        assertEquals(testRoute02.getOrigin(), testOrigin01);
        assertEquals(testRoute03.getOrigin(), testOrigin01);
        assertEquals(testRoute01.getDestination(), testDestination01);
        assertEquals(testRoute02.getDestination(), testDestination01);
        assertEquals(testRoute03.getDestination(), testDestination01);

        // Origin,destination and waypoints set test
        testRoute01.setOrigin(testOrigin02);
        testRoute01.setDestination(testDestination02);
        testRoute01.setWaypoints(testCoordinatesSet01);

        assertEquals(testRoute01.getOrigin(), testOrigin02);
        assertEquals(testRoute01.getDestination(), testDestination02);
        assertEquals(testRoute01.getWaypoints(), testCoordinatesSet01);

        // Routes equality test
        testRoute01.setOrigin(testOrigin01);
        testRoute01.setDestination(testDestination01);

        assertEquals(testRoute02, testRoute03);
        assertEquals(testRoute01, testRoute03);
        assertNotEquals(testRoute01, testRoute04);
    }

    @Test
    public void routeWaypointsTest(){
        Coordinates testOrigin01 = new Coordinates(51.970390, 0.979328);
        Coordinates testDestination01 = new Coordinates(52.038683,0.668408);
        Coordinates testWaypoint01 = new Coordinates(52.007232, 0.902119);
        Coordinates testWaypoint02 = new Coordinates(51.983350, 1.158283);

        LinkedHashSet<Coordinates> testWaypointsSet01 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02));
        Route testRoute01 = new Route(testOrigin01, testDestination01);
        Route testRoute02 = new Route(testOrigin01, testDestination01);
        Route testRoute03 = new Route(testOrigin01, testDestination01, testWaypointsSet01);

        // Equality of adding waypoints one by one and by set test
        testRoute01.addWaypoint(testWaypoint01);
        testRoute01.addWaypoint(testWaypoint02);
        testRoute02.addWaypoints(testWaypointsSet01);

        assertEquals(testRoute01.getWaypoints(), testWaypointsSet01);
        assertEquals(testRoute02.getWaypoints(), testWaypointsSet01);

        assertEquals(testRoute01.getWaypoints(), testRoute03.getWaypoints());
        assertEquals(testRoute02.getWaypoints(), testRoute03.getWaypoints());

        assertEquals(testRoute01, testRoute02);
        assertEquals(testRoute02, testRoute03);
    }

    @Test
    public void routeChainingTest(){
        Coordinates testOrigin01 = new Coordinates(51.970390, 0.979328);
        Coordinates testOrigin02 = new Coordinates(51.956771,1.268376);

        Coordinates testDestination01 = new Coordinates(52.038683,0.668408);
        Coordinates testDestination02 = new Coordinates(52.097693,0.667931);

        Coordinates testWaypoint01 = new Coordinates(52.007232, 0.902119);
        Coordinates testWaypoint02 = new Coordinates(51.983350, 1.158283);
        Coordinates testWaypoint03 = new Coordinates(52.007232, 0.902119);
        Coordinates testWaypoint04 = new Coordinates(52.028706, 0.895637);

        LinkedHashSet<Coordinates> testWaypointsSet01 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02));
        LinkedHashSet<Coordinates> testWaypointsSet02 = new LinkedHashSet<>(Arrays.asList(testWaypoint03, testWaypoint04));
        /*
         *      I) A.origin != (B.origin, B.destination)
         *         A.destination != (B.origin, B.destination)
         *              * C.origin = A.origin
         *              * C.destination = B.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.destination + B.origin
         */
        LinkedHashSet<Coordinates> testWaypointsSet03 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testOrigin02, testDestination01));

        Route testRoute01 = new Route(testOrigin01, testDestination01, testWaypointsSet01);
        Route testRoute02 = new Route(testOrigin02, testDestination02, testWaypointsSet02);
        Route testRoute03 = new Route(testOrigin01, testDestination02, testWaypointsSet03);

        Route chainedTestRoute01 = testRoute01.chainWithRoute(testRoute02);
        assertEquals(chainedTestRoute01, testRoute03);
        /*
         *      II) A.origin != (B.origin, B.destination),
         *          A.destination = B.origin, A.destination != B.destination
         *              * C.origin = A.origin
         *              * C.destination = B.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.destination
         */
        Coordinates testDestination03 = new Coordinates(51.956771,1.268376); // equals testOrigin02

        LinkedHashSet<Coordinates> testWaypointsSet04 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testDestination03));

        Route testRoute04 = new Route(testOrigin01, testDestination03, testWaypointsSet01);
        Route testRoute05 = new Route(testOrigin02, testDestination02, testWaypointsSet02);
        Route testRoute06 = new Route(testOrigin01, testDestination02, testWaypointsSet04);

        Route chainedTestRoute02 = testRoute04.chainWithRoute(testRoute05);
        assertEquals(chainedTestRoute02, testRoute06);
        /*
         *      III) A.origin = B.destination, A.origin != B.origin,
         *           A.destination != (B.origin, B.destination)
         *              * C.origin = B.origin
         *              * C.destination = A.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.origin
         */
        Coordinates testOrigin03 = new Coordinates(52.097693,0.667931); // equals testDestination02

        LinkedHashSet<Coordinates> testWaypointsSet05 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testOrigin03));

        Route testRoute07 = new Route(testOrigin03, testDestination01, testWaypointsSet01);
        Route testRoute08 = new Route(testOrigin02, testDestination02, testWaypointsSet02);
        Route testRoute09 = new Route(testOrigin02, testDestination01, testWaypointsSet05);

        Route chainedTestRoute03 = testRoute07.chainWithRoute(testRoute08);
        assertEquals(chainedTestRoute03, testRoute09);
        /*
         *      IV) A.origin = B.origin, A.origin != B.destination,
         *          A.destination != (B.origin, B.destination)
         *              * C.origin = A.destination
         *              * C.destination = B.destination
         *              * C.waypoints = A.waypoints + B.waypoints + A.origin
         */
        LinkedHashSet<Coordinates> testWaypointsSet06 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testOrigin01));

        Route testRoute10 = new Route(testOrigin01, testDestination01, testWaypointsSet01);
        Route testRoute11 = new Route(testOrigin01, testDestination02, testWaypointsSet02);
        Route testRoute12 = new Route(testDestination01, testDestination02, testWaypointsSet06);

        Route chainedTestRoute04 = testRoute10.chainWithRoute(testRoute11);
        assertEquals(chainedTestRoute04, testRoute12);
        /*
         *      V) A.origin != (B.origin, B.destination),
         *         A.destination = B.destination, A.destination != B.origin
         *              * C.origin = A.origin
         *              * C.destination = B.origin
         *              * C.waypoints = A.waypoints + B.waypoints + A.destination
         */
        LinkedHashSet<Coordinates> testWaypointsSet07 = new LinkedHashSet<>(Arrays.asList(testWaypoint01, testWaypoint02,
                testWaypoint03, testWaypoint04, testDestination01));

        Route testRoute13 = new Route(testOrigin01, testDestination01, testWaypointsSet01);
        Route testRoute14 = new Route(testOrigin02, testDestination01, testWaypointsSet02);
        Route testRoute15 = new Route(testOrigin01, testOrigin02, testWaypointsSet07);

        Route chainedTestRoute05 = testRoute13.chainWithRoute(testRoute14);
        assertEquals(chainedTestRoute05, testRoute15);
        //Multiple routes chaining
        //Set chaining is nondeterministic, so it cannot be tested properly

    }
}
