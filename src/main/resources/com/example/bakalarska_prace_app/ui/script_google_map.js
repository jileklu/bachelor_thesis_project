let map;
let directionsService;
let markers = [];
let directionsRenderers = [];

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
}

function addMarker(lat, lng, title) {
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(lat, lng),
        title: title,
    });
    marker.setMap(map);
    markers.push(marker);
}

function clearMarker(marker) {
    marker.setMap(null);
}

function clearMarkers() {
    markers.forEach( function(marker) {
        clearMarker(marker);
    })

    markers = [];
}

function addRoute(origin, waypoints, destination, directionsRenderer) {
    const originLatLng =  new google.maps.LatLng(origin.lat, origin.lng);
    const destinationLatLng = new google.maps.LatLng(destination.lat, destination.lng);
    console.log(waypoints);
    if(waypoints === []) {
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
                stopover: false,
            });
        }
        directionsService
            .route({
                origin: originLatLng.toUrlValue(),
                waypoints: waypts,
                destination: destinationLatLng.toUrlValue(),
                travelMode: google.maps.TravelMode.DRIVING,
            })
            .then((response) => {
                directionsRenderer.setDirections(response);
            })
            .catch((e) => window.alert("Directions request failed due to " + e));
    }
}

function addRenderer() {
    var newRenderer = new google.maps.DirectionsRenderer();
    newRenderer.setMap(map);
    directionsRenderers.push(newRenderer);
    return newRenderer;
}

function clearRenderers() {
    directionsRenderers.forEach( function(directionRenderer) {
        clearRenderer(directionRenderer);
    })

    directionsRenderers = [];
}

function clearRenderer(directionsRenderer) {
    directionsRenderer.setMap(null);
    directionsRenderer = null;
}

function viewRoute(route) {
    initMap();
    clearRenderers();

    var directionsRenderer = addRenderer();

    addRoute(route.origin, route.waypoints, route.destination, directionsRenderer);
}

function viewRoutes(routes) {
    initMap();
    clearRenderers();

    routes.forEach( function(route) {
        var directionsRenderer = addRenderer();

        addRoute(route.origin, route.waypoints, route.destination, directionsRenderer);
    } );
}