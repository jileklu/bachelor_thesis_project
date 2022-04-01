package com.example.bakalarska_prace_app.mapObjects;

public class Marker {
    private Coordinates coordinates;
    private String title;

    public Marker(Coordinates coordinates, String title) {
        this.coordinates = coordinates;
        this.title = title;
    }

    @Override
    public String toString() {
        return coordinates.toString() + ", '" + title + '\'';
    }
}
