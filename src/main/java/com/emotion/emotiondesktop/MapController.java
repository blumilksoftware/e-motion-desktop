package com.emotion.emotiondesktop;

import java.io.IOException;
import com.sothawo.mapjfx.*;
import javafx.fxml.FXML;
public class MapController {

    private static final int ZOOM_DEFAULT = 14;

    @FXML
    private MapView mapView;

    public void initialize() {
        mapView.initialize(Configuration.builder()
                .projection(Projection.WEB_MERCATOR)
                .showZoomControls(true)
                .build());

        mapView.setCenter(new Coordinate(40.416775, -3.703790));
        mapView.setZoom(ZOOM_DEFAULT);

        // Creating custom markers
        Marker markerBerlin = Marker.createProvided(Marker.Provided.BLUE)
                .setPosition(new Coordinate(52.520008, 13.404954))
                .setVisible(true);
        Marker markerMadrid = Marker.createProvided(Marker.Provided.RED)
                .setPosition(new Coordinate(40.416775, -3.703790))
                .setVisible(true);
        Marker markerParis = Marker.createProvided(Marker.Provided.GREEN)
                .setPosition(new Coordinate(48.8566, 2.3522))
                .setVisible(true);

        // Adding custom markers to the map
        mapView.addMarker(markerBerlin);
        mapView.addMarker(markerMadrid);
        mapView.addMarker(markerParis);
    }

    public MapController() {

    }

    public void showCitiesCrudView() throws IOException {
        EmotionApplication.showCitiesCrudView();
    }
    public void showCountriesCrudView() throws IOException {
        EmotionApplication.showCountriesCrudView();
    }

    public void showProvidersCrudView() throws IOException {
        EmotionApplication.showProvidersCrudView();
    }

    public void showOpinionsCrudView() throws IOException {
        EmotionApplication.showOpinionsCrudView();
    }

    public void showUsersCrudView() throws IOException {
        EmotionApplication.showUsersCrudView();
    }

    public void showImportersView() throws IOException {
        EmotionApplication.showImportersView();
    }

}
