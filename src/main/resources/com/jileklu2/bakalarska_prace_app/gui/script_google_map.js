let map;
let directionsService;
let markers = [];
let directionsRenderers = [];

let drawingManager;
let polylines = [];
let snappedCoordinates = [];
let apiKey = 'AIzaSyBwJIjpDQrNDJG0Z-DFtb7hc9M1eaZAmP4';

// Initializes map window
function initMap() {
    directionsService = new google.maps.DirectionsService();
    const prague = { lat: 50.073658, lng: 14.418540 };
    map = new google.maps.Map(document.getElementById("map"), {
        mapId: "9ccba9a851ff1929",
        zoom: 6,
        center: prague,
        disableDefaultUI: true,
        backgroundColor: '#0f1114'
    });

    // Enables the polyline drawing control. Click on the map to start drawing a
    // polyline. Each click will add a new vertice. Double-click to stop drawing.
    drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.POLYLINE,
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [
                google.maps.drawing.OverlayType.POLYLINE
            ]
        },
        polylineOptions: {
            strokeColor: '#696969',
            strokeWeight: 2,
            strokeOpacity: 0.7,
        }
    });
    drawingManager.setMap(map);

    // Snap-to-road when the polyline is completed.
    drawingManager.addListener('polylinecomplete', function(poly) {
        var path = poly.getPath();
        polylines.push(poly);
        runSnapToRoad(path);
    });

    google.maps.event.addListener(map, "dblclick", (event) => {
        routesContext.collectNewWaypoint(JSON.stringify(event.latLng));
    });
}

// Snap a user-created polyline to roads and draw the snapped path
function runSnapToRoad(path) {
    var pathValues = [];
    for (var i = 0; i < path.getLength(); i++) {
        pathValues.push(path.getAt(i).toUrlValue());
    }

    $.get('https://roads.googleapis.com/v1/snapToRoads', {
        interpolate: false,
        key: apiKey,
        path: pathValues.join('|')
    }, function(data) {
        processSnapToRoadResponse(data);
        drawSnappedPolyline();
    });
}

// Store snapped polyline returned by the snap-to-road service.
function processSnapToRoadResponse(data) {
    snappedCoordinates = [];
    for (var i = 0; i < data.snappedPoints.length; i++) {
        var latlng = new google.maps.LatLng(
            data.snappedPoints[i].location.latitude,
            data.snappedPoints[i].location.longitude);
        snappedCoordinates.push(latlng);
    }
}

// Draws the snapped polyline (after processing snap-to-road response).
function drawSnappedPolyline() {
    var snappedPolyline = new google.maps.Polyline({
        path: snappedCoordinates,
        strokeColor: '#add8e6',
        strokeWeight: 4,
        strokeOpacity: 1,
    });

    snappedPolyline.setMap(map);
    removePolyLines();
    polylines.push(snappedPolyline);
    routesContext.collectCoordinates(JSON.stringify(snappedCoordinates));
}

// Removes all existing PolyLines
function removePolyLines() {
    for (var i = 0; i < polylines.length; ++i) {
        polylines[i].setMap(null);
    }
    polylines = [];
}

// Adds marker at given lat,lng position with given title
function addMarker(lat, lng, title) {
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(lat, lng),
        label: title,
        draggable: true
    });

    marker.addListener("dragend", () => {
        const jsonString = JSON.stringify({coordinates: marker.getPosition(), arrPos: markers.indexOf(marker)})
        routesContext.collectChangedMarker(jsonString)
    });

    marker.setMap(map);
    markers.push(marker);
}

// Clears given marker
function clearMarker(marker) {
    marker.setMap(null);
}

// Clears all markers
function clearMarkers() {
    markers.forEach( function(marker) {
        clearMarker(marker);
    })

    markers = [];
}

// Renders given route onto the map
function addRoute(origin, waypoints, destination, directionsRenderer) {
    const originLatLng =  new google.maps.LatLng(origin.lat, origin.lng);
    const destinationLatLng = new google.maps.LatLng(destination.lat, destination.lng);
    console.log(waypoints);
    let waypts;
    if (waypoints === []) {
        directionsService
            .route({
                origin: originLatLng.toUrlValue(),
                destination: destinationLatLng.toUrlValue(),
                travelMode: google.maps.TravelMode.DRIVING,
            })
            .then((response) => {
                directionsRenderer.setDirections(response);
            })
            .catch((e) => window.alert("Directions request failed due to " + e));
    } else {
        waypts = []
        for (let i = 0; i < waypoints.length; i++) {
            waypts.push({
                location: (new google.maps.LatLng(waypoints[i].lat, waypoints[i].lng)).toUrlValue(),
                stopover: true,
            });
        }
        directionsService
            .route({
                origin: originLatLng.toUrlValue(),
                waypoints: waypts,
                destination: destinationLatLng.toUrlValue(),
                travelMode: google.maps.TravelMode.DRIVING,
                unitSystem: google.maps.UnitSystem.METRIC
            })
            .then((response) => {
                directionsRenderer.setDirections(response);
            })
            .catch((e) => window.alert("Directions request failed due to " + e));
    }
}

// Creates new route renderer
function addRenderer() {
    var newRenderer = new google.maps.DirectionsRenderer({
        suppressMarkers : true,
        map : map
    });

    directionsRenderers.push(newRenderer);

    return newRenderer;
}

// Deletes all currently used renderers
function clearRenderers() {
    directionsRenderers.forEach( function(directionRenderer) {
        clearRenderer(directionRenderer);
    })

    directionsRenderers = [];
}

// Deletes given renderer
function clearRenderer(directionsRenderer) {
    directionsRenderer.setMap(null);
    directionsRenderer = null;
}

// Shows given route
function viewRoute(route) {
    initMap();
    clearRenderers();

    var directionsRenderer = addRenderer();

    addRoute(route.origin, route.waypoints, route.destination, directionsRenderer);
}

// Show given routes
function viewRoutes(routes) {
    initMap();
    clearRenderers();

    routes.forEach( function(route) {
        var directionsRenderer = addRenderer();

        addRoute(route.origin, route.waypoints, route.destination, directionsRenderer);
    } );
}

$(window).load(initMap);
