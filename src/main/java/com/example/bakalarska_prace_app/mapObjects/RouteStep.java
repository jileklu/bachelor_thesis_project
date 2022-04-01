package com.example.bakalarska_prace_app.mapObjects;

public class RouteStep {
    private Coordinates origin;
    private Coordinates destination;
    private Double distance;
    private Double duration;
    private int stepNumber;
    private Double averageSpeed;

    public RouteStep(Coordinates origin, Coordinates destination, Double distance, Double duration, int stepNumber) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.duration = duration;
        this.stepNumber = stepNumber;
        this.averageSpeed = (distance / 1000) / ((duration / 60) / 60);
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public Coordinates getOrigin() {
        return origin;
    }

    public Coordinates getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "{origin: " + origin +
                ", destination: " + destination +
                ", distance: {" + String.format("%.0f", distance) + " m}" +
                ", duration: {" + String.format("%.0f", duration) + " sec}" +
                ", average_speed: {" + String.format("%.2f", averageSpeed) + " km/h" +
                "}";
    }

    public String toFormattedString() {
        return "\t\tStep number: " + stepNumber + "\n" +
                "Origin: " +
                "\n\tLat: " + origin.getLat() +
                "\n\tLng: " + origin.getLng() + "\n" +
                "Destination: " +
                "\n\tLat: " + destination.getLat() +
                "\n\tLng: " + destination.getLng() + "\n" +
                "Distance: " +
                "\n\t" + String.format("%.0f", distance) + " m\n" +
                "Duration: " +
                "\n\t" + String.format("%.0f", duration) + " sec\n" +
                "Average speed: " +
                "\n\t" + String.format("%.2f", averageSpeed) + "km/h";
    }
}
