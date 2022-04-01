package com.example.bakalarska_prace_app;

import com.example.bakalarska_prace_app.mapObjects.Coordinates;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class CoordinatesTest {
    double epsilon = 0.000001d;

    @Test
    public void basicFunctionalitiesTest(){
        Coordinates testCoordinates01 = new Coordinates(10.0,8.0);
        Coordinates testCoordinates02 = new Coordinates(8.0,10.0);
        Coordinates testCoordinates03 = new Coordinates(5.0,10.0);
        Coordinates testCoordinates04 = new Coordinates(testCoordinates03);

        assertEquals(10.0, testCoordinates01.getLat(), epsilon);
        assertEquals(8.0, testCoordinates01.getLng(), epsilon);

        testCoordinates01.setLat(8.0);
        testCoordinates01.setLng(10.0);

        assertEquals(10.0, testCoordinates01.getLng(), epsilon);
        assertEquals(8.0, testCoordinates01.getLat(), epsilon);

        assertEquals(testCoordinates01, testCoordinates01);
        assertEquals(testCoordinates01, testCoordinates02);
        assertEquals(testCoordinates04, testCoordinates03);
        assertNotEquals(testCoordinates01, testCoordinates03);
    }

    @Test
    public void exceptionsTest() {
        // Incorrect boundaries test
        try {
            Coordinates coordinates = new Coordinates(-190.0,8.0);
            fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                fail("Wrong exception message.");
        }
        catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            Coordinates coordinates = new Coordinates(190.0,8.0);
            fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                fail("Wrong exception message.");
        }
        catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            Coordinates coordinates = new Coordinates(0.0,-190.0);
            fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                fail("Wrong exception message.");
        }
        catch (Exception e) {
            fail("Unexpected exception was thrown");
        }

        try {
            Coordinates coordinates = new Coordinates(0.0,190.0);
            fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Lat or Lng is out of expected bounds."))
                fail("Wrong exception message.");
        }
        catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }
}
