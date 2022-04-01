package com.jileklu2.bakalarska_prace_app.mapObjects;

public class Marker {
    private final Coordinates coordinates;
    private final String title;

    public Marker(Coordinates coordinates, String title) {
        this.coordinates = coordinates;
        this.title = title;
    }

    @Override
    public String toString() {
        return coordinates.toString() + ", '" + title + '\'';
    }
}
