package com.jileklu2.bakalarska_prace_app.mapObjects;

import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates.CoordinatesOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.AverageSpeedOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DistanceOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.exceptions.strings.BlankStringException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

/**
 *
 */
public class RouteStep {
    private Coordinates origin;
    private Coordinates destination;
    private Double distance;
    private Double duration;
    private int stepNumber;
    private Double averageSpeed;

    private HashSet<Variable> variables;

    /**
     *
     * @param origin
     * @param destination
     * @param distance
     * @param duration
     * @param stepNumber
     * @param variables
     * @throws DistanceOutOfBoundsException
     * @throws DurationOutOfBoundsException
     */
    @SafeVarargs
    public RouteStep(Coordinates origin, Coordinates destination, Double distance, Double duration,
                     int stepNumber, Collection<Variable>... variables)
        throws DistanceOutOfBoundsException, DurationOutOfBoundsException {
        if(origin == null || destination == null || distance == null || duration == null)
            throw new NullPointerException("Arguments can't be null");

        checkDistance(distance);
        checkDuration(duration);

        this.origin = new Coordinates(origin);
        this.destination = new Coordinates(destination);
        this.distance = distance;
        this.duration = duration;
        this.stepNumber = stepNumber;
        this.averageSpeed = (distance / 1000) / ((duration / 60) / 60);

        this.variables = variables.length > 0 ? new HashSet<>(variables[0]) : new HashSet<>();
    }

    /**
     *
     * @param jsonObject
     * @throws CoordinatesOutOfBoundsException
     * @throws DistanceOutOfBoundsException
     * @throws AverageSpeedOutOfBoundsException
     * @throws DurationOutOfBoundsException
     * @throws BlankStringException
     */
    public RouteStep(JSONObject jsonObject)
        throws CoordinatesOutOfBoundsException, DistanceOutOfBoundsException,
        AverageSpeedOutOfBoundsException, DurationOutOfBoundsException, BlankStringException {
        if(jsonObject == null)
            throw new NullPointerException("Arguments can't be null");

        try{
            Double distance = jsonObject.getDouble("distance");
            Double duration = jsonObject.getDouble("duration");
            Double averageSpeed = jsonObject.getDouble("averageSpeed");

            checkNumericalArguments(distance, duration, averageSpeed);

            this.origin = new Coordinates(jsonObject.getJSONObject("origin"));
            this.destination = new Coordinates(jsonObject.getJSONObject("destination"));
            this.distance = distance;
            this.duration = duration;
            this.stepNumber = jsonObject.getInt("stepNumber");
            this.averageSpeed = averageSpeed;
            this.variables = new HashSet<>();
            JSONArray variablesJsonArr = jsonObject.getJSONArray("variables");
            for(int i = 0; i < variablesJsonArr.length(); i++) {
                JSONObject variable = variablesJsonArr.getJSONObject(i);
                String key = variable.keys().next();
                String value = variable.getString(key);
                this.variables.add(new Variable(key, value));
            }
        } catch (JSONException e) {
            throw new JSONException("Wrong JSON file structure.");
        }
    }

    /**
     *
     * @param distance
     * @param duration
     * @param averageSpeed
     * @throws DistanceOutOfBoundsException
     * @throws DurationOutOfBoundsException
     * @throws AverageSpeedOutOfBoundsException
     */
    private void checkNumericalArguments(Double distance, Double duration, Double averageSpeed)
        throws DistanceOutOfBoundsException, DurationOutOfBoundsException, AverageSpeedOutOfBoundsException {
        checkDistance(distance);
        checkDuration(duration);
        checkAverageSpeed(averageSpeed);
    }

    /**
     *
     * @param distance
     * @throws DistanceOutOfBoundsException
     */
    private void checkDistance(Double distance) throws DistanceOutOfBoundsException {
        if(distance < 0)
            throw new DistanceOutOfBoundsException("Distance can be either 0 or a positive number");
    }

    /**
     *
     * @param duration
     * @throws DurationOutOfBoundsException
     */
    private void checkDuration(Double duration) throws DurationOutOfBoundsException {
        if(duration < 0)
            throw new DurationOutOfBoundsException("Duration can be either 0 or a positive number");

    }

    /**
     *
     * @param averageSpeed
     * @throws AverageSpeedOutOfBoundsException
     */
    private void checkAverageSpeed(Double averageSpeed) throws AverageSpeedOutOfBoundsException {
        if(averageSpeed < 0)
            throw new AverageSpeedOutOfBoundsException("AverageSpeed can be only a positive number");
    }

    /**
     *
     * @return
     */
    public int getStepNumber() {
        return stepNumber;
    }

    /**
     *
     * @return
     */
    public Coordinates getOrigin() {
        return origin;
    }

    /**
     *
     * @return
     */
    public Coordinates getDestination() {
        return destination;
    }

    /**
     *
     * @return
     */
    public Double getDuration(){return duration;}

    /**
     *
     * @return
     */
    public Double getDistance() {
        return distance;
    }

    /**
     *
     * @return
     */
    public Double getAverageSpeed() {
        return averageSpeed;
    }

    /**
     *
     * @return
     */
    public HashSet<Variable> getVariables() {
        return variables;
    }

    /**
     *
     * @param stepNumber
     */
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    /**
     *
     * @param origin
     */
    public void setOrigin(Coordinates origin) {
        if(origin == null)
            throw new NullPointerException("Arguments can't be null");

        this.origin = origin;
    }

    /**
     *
     * @param destination
     */
    public void setDestination(Coordinates destination) {
        if(distance == null)
            throw new NullPointerException("Arguments can't be null");

        this.destination = destination;
    }

    /**
     *
     * @param distance
     * @throws DistanceOutOfBoundsException
     */
    public void setDistance(Double distance) throws DistanceOutOfBoundsException {
        if(distance == null)
            throw new NullPointerException("Arguments can't be null");

        checkDistance(distance);
        this.distance = distance;
    }

    /**
     *
     * @param duration
     * @throws DurationOutOfBoundsException
     */
    public void setDuration(Double duration) throws DurationOutOfBoundsException {
        if(duration == null)
            throw new NullPointerException("Arguments can't be null");

        checkDuration(duration);
        this.duration = duration;
    }

    /**
     *
     * @param averageSpeed
     * @throws AverageSpeedOutOfBoundsException
     */
    public void setAverageSpeed(Double averageSpeed) throws AverageSpeedOutOfBoundsException {
        if(averageSpeed == null)
            throw new NullPointerException("Arguments can't be null");

        checkAverageSpeed(averageSpeed);
        this.averageSpeed = averageSpeed;
    }

    /**
     *
     * @param variables
     */
    public void setVariables(HashSet<Variable> variables) {
        if(variables == null)
            throw new NullPointerException("Arguments can't be null");

        this.variables = variables;
    }

    /**
     *
     * @param variable
     */
    public void addVariable(Variable variable) {
        if(variable == null)
            throw new NullPointerException("Arguments can't be null");

        variables.add(variable);
    }

    /**
     *
     * @param variable
     */
    public void removeVariable(Variable variable) {
        if(variable == null)
            throw new NullPointerException("Arguments can't be null");

        variables.remove(variable);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("{stepNumber:%s,origin:%s,destination:%s,distance:%s,duration:%s,averageSpeed:%s}",
            stepNumber, origin, destination, String.format("%.0f", distance),
            String.format("%.0f", duration), String.format(Locale.US, "%.2f", averageSpeed)
        );
    }

    /**
     *
     * @param prefix
     * @return
     */
    public String toGPX(String prefix) {
        if(prefix == null)
            throw new NullPointerException("Arguments can't be null");

        StringBuilder gpxStringBuilder = new StringBuilder(prefix + "<trkseg>\n");
        gpxStringBuilder.append(origin.toGPX(prefix + " ")).append("\n");
        gpxStringBuilder.append(destination.toGPX(prefix + " ")).append("\n");
        gpxStringBuilder.append(prefix).append("</trkseg>");

        return gpxStringBuilder.toString();
    }

    /**
     *
     * @return
     */
    public JSONObject toJSON(){
        return new JSONObject(this.toString());
    }

    /**
     *
     * @return
     */
    public String toFormattedString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append("\t\tStep number: ").append(stepNumber).append("\n");
        formattedString.append("Origin: \n");
        formattedString.append("\tLat: ").append(origin.getLat()).append("\n");
        formattedString.append("\tLng: ").append(origin.getLng()).append("\n");
        formattedString.append("\tElevation: ").append(String.format("%.2f", origin.getElevation())).append(" mamsl\n");
        formattedString.append("Destination: \n");
        formattedString.append("\tLat: ").append(destination.getLat()).append("\n");
        formattedString.append("\tLng: ").append(destination.getLng()).append("\n");
        formattedString.append("\tElevation: ").append(String.format("%.2f", destination.getElevation())).append(" mamsl\n");
        formattedString.append("Distance: \n");
        formattedString.append("\t").append(String.format("%.0f", distance)).append(" m\n");
        formattedString.append("Duration: \n");
        formattedString.append("\t").append(String.format("%.0f", duration)).append(" sec\n");
        formattedString.append("Average speed: \n");
        formattedString.append("\t").append(String.format("%.2f", averageSpeed)).append(" km/h\n");
        for(Variable variable : variables) {
            formattedString.append(variable.getName()).append(": \n");
            formattedString.append("\t").append(variable.getValue()).append("\n");
        }

        return formattedString.toString();
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RouteStep other = (RouteStep) obj;

        int switchvar = 0;
        if (duration == null) {switchvar += 1;}
        if (distance == null) {switchvar += 10;}
        if (averageSpeed == null) {switchvar += 100;}

        boolean durationEq = true;
        boolean distanceEq = true;
        boolean averageSpeedEq = true;

        switch (switchvar) {
            case(1):
                durationEq = other.duration == null;
                break;
            case(10):
                distanceEq = other.distance == null;
                break;
            case(11):
                durationEq = distanceEq = other.duration == null && other.distance == null;
                break;
            case(100):
                averageSpeedEq = other.averageSpeed == null;
                break;
            case(101):
                averageSpeedEq = durationEq = other.averageSpeed == null && other.duration == null;
                break;
            case(110):
                distanceEq = averageSpeedEq = other.averageSpeed == null && other.distance == null;
                break;
            case(111):
                distanceEq = averageSpeedEq = durationEq =
                        other.duration == null && other.distance == null && other.averageSpeed == null;
                break;
        }

        return this.destination.equals(other.destination) &&
                this.origin.equals(other.origin) &&
                durationEq && distanceEq && averageSpeedEq &&
                this.variables.equals(other.variables) &&
                this.stepNumber == other.stepNumber;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 11;
        int result = 1;
        int add = 0;

        int switchvar = 0;
        if (destination == null) {switchvar += 1;}
        if (duration == null) {switchvar += 10;}
        if (distance == null) {switchvar += 100;}
        if (averageSpeed == null) {switchvar += 1000;}

        if(origin != null) {
            add = origin.hashCode();
        } else {
            switch (switchvar) {
                case(1):
                case(101):
                case(1001):
                case(1101):
                    add = duration.hashCode();
                    break;
                case(11):
                case(1011):
                    add = distance.hashCode();
                    break;
                case(111):
                    add = averageSpeed.hashCode();
                    break;
                case(1111):
                    add = 0;
                    break;
                default:
                    add = destination.hashCode();
                    break;
            }
        }

        result = prime * result + add;
        return result;
    }
}
