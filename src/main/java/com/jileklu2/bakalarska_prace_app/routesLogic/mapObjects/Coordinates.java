package com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Coordinates {
    private Double lat;
    private Double lng;

    private Double elevation;

    public Coordinates(Double lat, Double lng) {
        if( lat < -180.0 || lat > 180.0 || lng < -180.0 || lng > 180.0)
            throw new IllegalArgumentException("Lat or Lng is out of expected bounds.");

        this.lat = lat;
        this.lng = lng;
        this.elevation = 0.0;
    }

    public Coordinates(Double lat, Double lng, Double elevation) {
        if( lat < -180.0 || lat > 180.0 || lng < -180.0 || lng > 180.0)
            throw new IllegalArgumentException("Lat or Lng is out of expected bounds.");

        this.lat = lat;
        this.lng = lng;
        this.elevation = elevation;
    }

    public Coordinates(Coordinates other) {
        this.lat = Double.valueOf(other.getLat());
        this.lng = Double.valueOf(other.getLng());
        this.elevation = Double.valueOf(other.getElevation());
    }

    public Coordinates(JSONObject jsonObject) {
        try{
            this.lat = jsonObject.getDouble("lat");
            this.lng = jsonObject.getDouble("lng");
            this.elevation = 0.0;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Wrong JSON file structure.");
        }

    }

    @Override
    public String toString() {
        return String.format("{lat:%s, lng:%s, ele:%s}", lat,lng,elevation);
    }

    public JSONObject toJSON(){
        return new JSONObject(this.toString());
    }

    public String toGPX(String prefix){
        StringBuilder gpxStringBuilder = new StringBuilder(prefix + "<trkpt ");
        gpxStringBuilder.append(String.format("lat=%s ", lat));
        gpxStringBuilder.append(String.format("lon=%s>\n", lng));
        gpxStringBuilder.append(prefix).append("\t").append(String.format("<ele>%s</ele>\n",elevation));
        gpxStringBuilder.append(prefix).append("</trkpt>");

        return gpxStringBuilder.toString();
    }

    public String toFormattedString() {
        return "lat: " + lat + ", lng: " + lng + ", ele: " + elevation;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Double getElevation(){
        return elevation;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int add = 0;
        if(lat == null && lng == null && elevation == null) {
            add = 0;
        } else if(lat == null) {
            add = lng.hashCode();
        } else if(lng == null){
            add = lat.hashCode();
        } else {
            add = lat.hashCode();
        }

        result = prime * result + add;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinates other = (Coordinates) obj;

        int switchvar = 0;
        if (lat == null) {
            switchvar += 1;
        }
        if (lng == null) {
            switchvar += 10;
        }
        if (elevation == null) {
            switchvar += 100;
        }

        switch (switchvar) {
            case (1):
                return other.lat == null;
            case (10):
                return other.lng == null;
            case (11):
                return other.lat == null && other.lng == null;
            case (100):
                return other.elevation == null;
            case (101):
                return other.elevation == null && other.lat == null;
            case (110):
                return other.elevation == null && other.lng == null;
            case (111):
                return other.lat == null && other.lng == null && other.elevation == null;
        }

        return Double.compare(lat, other.lat) == 0 &&
            Double.compare(lng, other.lng) == 0 &&
            Double.compare(elevation, other.elevation) == 0;
    }

    public boolean isWithinDistance(Coordinates other, Double distance) {
        return distanceBetween(this, other) <= distance;
    }

    public boolean isSame(Coordinates other) {
        return isWithinDistance(other, 3.0);
    }

    public static Double distanceBetween(Coordinates start, Coordinates target) {
        //https://www.movable-type.co.uk/scripts/latlong.html

        double toRads = Math.PI/180;

        double r = 6371e3; // Earth radius in meters
        double startPhi = start.lat * toRads;
        double targetPhi = target.lat * toRads;

        double diffPhi = (target.lat - start.lat) * toRads;
        double diffLambda = (target.lng - start.lng) * toRads;

        double a = Math.sin(diffPhi / 2) * Math.sin(diffPhi / 2) +
                   Math.cos(startPhi) * Math.cos(targetPhi) *
                   Math.sin(diffLambda / 2) * Math.sin(diffLambda / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        Double dist = Double.valueOf(String.format(Locale.US,"%.3f",r * c)); // in meters, rounded to 3 decimal places to account for error

        return dist;
    }
}
