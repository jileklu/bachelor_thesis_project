package com.example.bakalarska_prace_app.mapObjects;

public class Coordinates {
    private Double lat;
    private Double lng;

    public Coordinates(Double lat, Double lng) {
        if( lat < -180.0 || lat > 180.0 || lng < -180.0 || lng > 180.0)
            throw new IllegalArgumentException("Lat or Lng is out of expected bounds.");

        this.lat = lat;
        this.lng = lng;
    }

    public Coordinates(Coordinates other) {
        this.lat = other.getLat();
        this.lng = other.getLng();
    }

    @Override
    public String toString() {
        return "{lat: " + lat + ", lng: " + lng +"}";
    }

    public String toFormattedString() {
        return "lat: " + lat + ", lng: " + lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int add = 0;
        if(lat == null && lng == null) {
            add = 0;
        } else if(lat == null) {
            add = lng.hashCode();
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
        if (lat == null && lng == null) {
            return other.lat == null && other.lng == null;
        } else if (Double.compare(lat,other.lat) != 0 ||
                    Double.compare(lng, other.lng) != 0) {
            return false;
        } else if (lat == null && lng != null) {
            return other.lat == null;
        } else if (lat != null && lng == null) {
            return other.lng == null;
        }

        return true;
    }
}
