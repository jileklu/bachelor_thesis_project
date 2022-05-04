package com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects;

import org.json.JSONException;
import org.json.JSONObject;

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
}
