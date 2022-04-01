package com.jileklu2.bakalarska_prace_app;

import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
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

    @BeforeEach
    public void setUp(){
        testCoordinates01 = new Coordinates(10.0,8.0);
        testCoordinates02 = new Coordinates(8.0,10.0);
        testCoordinates03 = new Coordinates(5.0,10.0);
        testCoordinates04 = new Coordinates(testCoordinates03);
    }

    @Test
    public void basicFunctionalitiesTest(){
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
    public void exceptionsTest() {
        // Incorrect boundaries test
        try {
            Coordinates testCoordinates05 = new Coordinates(-190.0,8.0);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }

        try {
            Coordinates testCoordinates05 = new Coordinates(190.0,8.0);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }

        try {
            Coordinates testCoordinates05 = new Coordinates(0.0,-190.0);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }

        try {
            Coordinates testCoordinates05 = new Coordinates(0.0,190.0);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                Assertions.fail("Wrong exception message.");
        }
        catch (Exception e) {
            Assertions.fail("Unexpected exception was thrown");
        }
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals("{lat: 10.0, lng: 8.0}", testCoordinates01.toString());
    }

    @Test
    public void toFormattedStringTest(){
        Assertions.assertEquals("lat: 10.0, lng: 8.0", testCoordinates01.toFormattedString());
    }
}
