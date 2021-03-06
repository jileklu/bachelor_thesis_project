package com.jileklu2.bakalarska_prace_app.mapObjects;

/**
 *
 */
public class Marker {
    private final Coordinates coordinates;
    private final String title;

    /**
     *
     * @param coordinates
     * @param title
     */
    public Marker(Coordinates coordinates, String title) {
        if(coordinates == null || title == null)
            throw new NullPointerException("Arguments can't be null");

        this.coordinates = coordinates;
        this.title = title;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return coordinates.toString() + ", '" + title + '\'';
    }
}
