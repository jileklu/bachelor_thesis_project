package com.jileklu2.bakalarska_prace_app.gui.routeHandling;

import com.jileklu2.bakalarska_prace_app.gui.MapViewContext;
import com.jileklu2.bakalarska_prace_app.gui.RouteInfoPanelContext;
import com.jileklu2.bakalarska_prace_app.handlers.FileHandler;
import com.jileklu2.bakalarska_prace_app.routesLogic.RouteInfoFinder;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.enums.RouteStepArrayRole;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import static com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.enums.RouteStepArrayRole.*;


public class RoutesHandler implements RoutesContext {
    private Route defaultRoute;

    private MapViewContext mapViewContext;

    private RouteInfoPanelContext routeInfoPanelContext;

    private HashSet<LocalDateTime> timeStamps;

    public RoutesHandler() {
        timeStamps = new HashSet<>();
    }

    @Override
    public void setDefaultRoute(Route defaultRoute){
        this.defaultRoute = defaultRoute;
    }

    @Override
    public void setTimeStamps(HashSet<LocalDateTime> timeStamps) {
        this.timeStamps = new HashSet<>(timeStamps);
    }

    @Override
    public Route getDefaultRoute() {
        return defaultRoute;
    }

    @Override
    public void findRouteInfo(Route route) {
        RouteInfoFinder.findRouteInfo(route, true, timeStamps);
    }

    @Override
    public void loadJsonRoute(String path) throws FileNotFoundException {
        JSONObject routeJson = FileHandler.readJsonFile(Paths.get(path).toString());
        Route newRoute = new Route(routeJson);
        findRouteInfo(newRoute);
        setDefaultRoute(newRoute);
    }

    @Override
    public int getDefaultRouteStepIndex(RouteStep routeStep) {
        return getDefaultRoute().getRouteSteps().indexOf(routeStep);
    }

    @Override
    public int getDefaultRouteStepsNum() {
        return getDefaultRoute().getRouteSteps().size();
    }

    @Override
    public RouteStep getDefaultRouteStepOnIndex(int index) {
        return getDefaultRoute().getRouteSteps().get(index);
    }

    public void getChangedCoordinatesElevations(RouteStep routeStep) {
        RouteStepArrayRole routeStepRole = getRouteStepRole(routeStep);
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(routeStep.getOrigin());
        coordinatesList.add(routeStep.getDestination());

        List<Coordinates> waypoints = new ArrayList<>(
            getDefaultRoute().getWaypoints()
        );

        int routeStepIndex = getDefaultRouteStepIndex(routeStep);

        if(routeStepRole == SOLO) {
            coordinatesList.add(getDefaultRoute().getOrigin());
            coordinatesList.add(getDefaultRoute().getDestination());
        } else if (routeStepRole == FIRST) {
            coordinatesList.add(getDefaultRoute().getOrigin());
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex + 1).getOrigin());
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex + 1).getDestination());
            coordinatesList.add(waypoints.get(routeStepIndex));
        } else if(routeStepRole == LAST) {
            coordinatesList.add(getDefaultRoute().getDestination());
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex - 1).getOrigin());
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex - 1).getDestination());
            coordinatesList.add(waypoints.get(routeStepIndex - 1));
        } else {
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex + 1).getOrigin());
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex + 1).getDestination());
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex - 1).getOrigin());
            coordinatesList.add(getDefaultRouteStepOnIndex(routeStepIndex - 1).getDestination());

            coordinatesList.add(waypoints.get(routeStepIndex - 1));
            coordinatesList.add(waypoints.get(routeStepIndex));
        }

        RouteInfoFinder.findCoordinatesElevationInfo(coordinatesList);
    }

    @Override
    public void changeRouteCoordinates(RouteStep routeStep, Pair<Coordinates,Coordinates>coordinatesPair) {
        int routeStepIndex = getDefaultRouteStepIndex(routeStep);

        Coordinates newOrg = new Coordinates(coordinatesPair.getKey());
        Coordinates newDest = new Coordinates(coordinatesPair.getValue());

        RouteStepArrayRole routeStepRole = getRouteStepRole(routeStep);

        routeStep.setOrigin(newOrg);
        routeStep.setDestination(newDest);

        if (routeStepRole == SOLO) {
            getDefaultRoute().setOrigin(new Coordinates(newOrg));
            getDefaultRoute().setDestination(new Coordinates(newDest));
        } else if (routeStepRole == FIRST) {
            getDefaultRoute().setOrigin(new Coordinates(newOrg));
            getDefaultRouteStepOnIndex(routeStepIndex + 1).setOrigin(new Coordinates(newDest));

            changePostWaypointCoordinates(routeStepIndex, newDest);
        } else if (routeStepRole == LAST) {
            getDefaultRoute().setDestination(new Coordinates(newDest));
            getDefaultRouteStepOnIndex(routeStepIndex - 1).setDestination(new Coordinates(newOrg));

            changePreWaypointCoordinates(routeStepIndex, newOrg);
        } else {
            getDefaultRouteStepOnIndex(routeStepIndex - 1).setDestination(new Coordinates(newOrg));
            getDefaultRouteStepOnIndex(routeStepIndex + 1).setOrigin(new Coordinates(newDest));

            changePreWaypointCoordinates(routeStepIndex, newOrg);
            changePostWaypointCoordinates(routeStepIndex, newDest);
        }
    }

    @Override
    public void findRouteStepInfo(RouteStep routeStep) {
        Route wrapRoute = new Route(routeStep.getOrigin(),routeStep.getDestination());
        findRouteInfo(wrapRoute);
        routeStep.setDistance(wrapRoute.getRouteSteps().get(0).getDistance());
        routeStep.setDuration(wrapRoute.getRouteSteps().get(0).getDuration());
        routeStep.setAverageSpeed((routeStep.getDistance() / 1000) / ((routeStep.getDuration() / 60) / 60));

        getChangedCoordinatesElevations(routeStep);
    }

    private RouteStepArrayRole getRouteStepRole(RouteStep routeStep) {
        int routeStepIndex = getDefaultRouteStepIndex(routeStep);

        if (routeStepIndex == getDefaultRouteStepsNum() - 1 && routeStepIndex == 0)
            return SOLO;
        else if (routeStepIndex == 0)
            return FIRST;
        else if (routeStepIndex == getDefaultRouteStepsNum() - 1)
            return LAST;
        else
            return MID;
    }

    private void changePreWaypointCoordinates(int routeStepIndex, Coordinates newOrg) {
        List<Coordinates> waypoints = new ArrayList<>(
            getDefaultRoute().getWaypoints()
        );

        waypoints.get(routeStepIndex - 1).setLng(Double.valueOf(newOrg.getLng()));
        waypoints.get(routeStepIndex - 1).setLat(Double.valueOf(newOrg.getLat()));
    }

    private void changePostWaypointCoordinates(int routeStepIndex, Coordinates newDest) {
        List<Coordinates> waypoints = new ArrayList<>(
            getDefaultRoute().getWaypoints()
        );

        waypoints.get(routeStepIndex).setLng(Double.valueOf(newDest.getLng()));
        waypoints.get(routeStepIndex).setLat(Double.valueOf(newDest.getLat()));
    }

    public void collectCoordinates(String coordinates) {
        JSONArray coordinatesJsonArr = new JSONArray(coordinates);
        JSONObject newOriginJson = (JSONObject) coordinatesJsonArr.get(0);
        JSONObject newDestinationJson = (JSONObject) coordinatesJsonArr.get(coordinatesJsonArr.length() - 1);
        Coordinates newOrigin = new Coordinates(newOriginJson);
        Coordinates newDestination = new Coordinates(newDestinationJson);
        LinkedHashSet<Coordinates> newWaypoints = new LinkedHashSet<>();

        for(int i = 1; i < coordinatesJsonArr.length() - 1; i++){
            JSONObject newWaypointJson =  (JSONObject) coordinatesJsonArr.get(i);
            Coordinates newWaypoint = new Coordinates(newWaypointJson);
            newWaypoints.add(newWaypoint);
        }

        Route newRoute = new Route(newOrigin, newDestination, newWaypoints);
        findRouteInfo(newRoute);
        setDefaultRoute(newRoute);
        mapViewContext.showDefaultRoute();
        routeInfoPanelContext.showDefaultRouteInfo();
    }

    public void collectChangedMarker(String marker) {
        JSONObject markerJSON = new JSONObject(marker);
        Double lat = ((JSONObject) markerJSON.get("coordinates")).getDouble("lat");
        Double lng = ((JSONObject) markerJSON.get("coordinates")).getDouble("lng");
        int arrPos = markerJSON.getInt("arrPos");
        int index;
        RouteStep routeStep;
        Pair<Coordinates, Coordinates> coordinatesPair;

        if(arrPos == getDefaultRoute().getWaypoints().size() + 1) {
            index = getDefaultRoute().getRouteSteps().size() - 1;
            routeStep = getDefaultRouteStepOnIndex(index);
            Coordinates origin = routeStep.getOrigin();
            Coordinates destination = new Coordinates(lat, lng);
            coordinatesPair = new Pair<>(origin, destination);
        } else {
            index = arrPos;
            routeStep = getDefaultRouteStepOnIndex(index);
            Coordinates origin = new Coordinates(lat, lng);
            Coordinates destination = routeStep.getDestination();
            coordinatesPair = new Pair<>(origin, destination);
        }

        changeRouteCoordinates(routeStep, coordinatesPair);
        findRouteStepInfo(routeStep);
        mapViewContext.showDefaultRoute();

        routeInfoPanelContext.showDefaultRouteInfo();
    }

    public void collectNewWaypoint(String waypoint) {
        JSONObject markerJSON = new JSONObject(waypoint);
        Double lat = markerJSON.getDouble("lat");
        Double lng = markerJSON.getDouble("lng");

        Coordinates newWaypoint = new Coordinates(lat, lng);
        Route newRoute = new Route(getDefaultRoute());
        newRoute.addWaypoint(newWaypoint);
        findRouteInfo(newRoute);
        setDefaultRoute(newRoute);
        mapViewContext.showDefaultRoute();
        routeInfoPanelContext.showDefaultRouteInfo();
    }

    @Override
    public void setMapViewContext(MapViewContext mapViewContext) {
        this.mapViewContext = mapViewContext;
    }

    @Override
    public void setRouteInfoPanelContext(RouteInfoPanelContext routeInfoPanelContext) {
        this.routeInfoPanelContext = routeInfoPanelContext;
    }
}
