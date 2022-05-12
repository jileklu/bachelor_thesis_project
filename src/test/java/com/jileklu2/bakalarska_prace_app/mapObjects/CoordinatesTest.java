package com.jileklu2.bakalarska_prace_app.mapObjects;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class CoordinatesTest {
    static double epsilon = 0.000001d;
    private Coordinates testCoordinates01;
    private Coordinates testCoordinates02;
    private Coordinates testCoordinates03;
    private Coordinates testCoordinates04;
    private Coordinates testCoordinates05;
    private Coordinates testCoordinates06;

    @BeforeEach
    public void setUp() throws CoordinatesOutOfBoundsException {
        testCoordinates01 = new Coordinates(10.0,8.0);
        testCoordinates02 = new Coordinates(8.0,10.0);
        testCoordinates03 = new Coordinates(5.0,10.0);
        testCoordinates04 = new Coordinates(testCoordinates03);
        testCoordinates05 = new Coordinates(51.970390, 0.979328);
        testCoordinates06 = new Coordinates(51.9702667, 0.9794579000000001);
    }

    @Test
    public void basicFunctionalitiesTest() throws CoordinatesOutOfBoundsException {
        Assertions.assertEquals(10.0, testCoordinates01.getLat(), epsilon);
        Assertions.assertEquals(8.0, testCoordinates01.getLng(), epsilon);

        testCoordinates01.setLat(8.0);
        testCoordinates01.setLng(10.0);

        Assertions.assertEquals(10.0, testCoordinates01.getLng(), epsilon);
        Assertions.assertEquals(8.0, testCoordinates01.getLat(), epsilon);

        Assertions.assertEquals(testCoordinates01, testCoordinates01);
        Assertions.assertEquals(testCoordinates01, testCoordinates02);
        Assertions.assertEquals(testCoordinates04, testCoordinates03);
        Assertions.assertNotEquals(testCoordinates01, testCoordinates03);
    }

    @Test
    public void baseConstructorExceptionsTest() {
        // Incorrect boundaries test
        try {
            Coordinates testCoordinates05 = new Coordinates(-190.0,8.0);
            Assertions.fail("No exception was thrown");
        }
        catch (CoordinatesOutOfBoundsException e) {
            if(!Objects.equals(e.getMessage(), "Point is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }

        try {
            Coordinates testCoordinates05 = new Coordinates(190.0,8.0);
            Assertions.fail("No exception was thrown");
        }
        catch (CoordinatesOutOfBoundsException e) {
            if(!Objects.equals(e.getMessage(), "Point is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }

        try {
            Coordinates testCoordinates05 = new Coordinates(0.0,-190.0);
            Assertions.fail("No exception was thrown");
        }
        catch (CoordinatesOutOfBoundsException e) {
            if(!Objects.equals(e.getMessage(), "Point is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }

        try {
            Coordinates testCoordinates05 = new Coordinates(0.0,190.0);
            Assertions.fail("No exception was thrown");
        }
        catch (CoordinatesOutOfBoundsException e) {
            if(!Objects.equals(e.getMessage(), "Point is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals("{lat:10.0, lng:8.0, ele:0.0}", testCoordinates01.toString());
    }

    @Test
    public void toFormattedStringTest(){
        Assertions.assertEquals("lat: 10.0, lng: 8.0, ele: 0.0", testCoordinates01.toFormattedString());
    }

    @Test
    public void jsonConstructorTest() throws CoordinatesOutOfBoundsException {
        JSONObject jsonObject = new JSONObject()
                                .put("lat", "10")
                                .put("lng", "8");
        Coordinates testCoordinates = new Coordinates(jsonObject);
        Assertions.assertEquals(testCoordinates, testCoordinates01);
    }

    @Test
    public void jsonConstructorAssertionsTest() throws CoordinatesOutOfBoundsException {
        JSONObject jsonObject = new JSONObject()
                .put("lat", "10");

        try {
            Coordinates testCoordinates = new Coordinates(jsonObject);
            Assertions.fail("No exception was thrown");
        }
        catch (JSONException e) {
            if(!Objects.equals(e.getMessage(), "Wrong JSON file structure."))
                Assertions.fail("Wrong exception message.");
        }
    }

    @Test
    public void distanceTest() {
        Assertions.assertEquals(0, Coordinates.distanceBetween(testCoordinates01, testCoordinates01));
        Assertions.assertEquals(0, Coordinates.distanceBetween(testCoordinates05, testCoordinates05));
        Assertions.assertEquals(16.345, Coordinates.distanceBetween(testCoordinates05, testCoordinates06));
    }
}
