package com.emotion.emotiondesktop.Unit;

import com.emotion.emotiondesktop.CitiesCrudController;
import com.emotion.emotiondesktop.City;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitiesCrudControllerTest {

    @BeforeAll
    public static void initJFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {
            });
        }
    }

    @Mock
    private HttpURLConnection mockConnection;

    private CitiesCrudController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CitiesCrudController();
    }

    @Test
    public void testFetchCityRecords() {
        List<City> cities = new ArrayList<>();
        cities.add(new City(1, "Warsaw", 52.2297, 21.0122, 1 ));
        cities.add(new City(2, "Berlin", 52.5200, 13.4050, 2 ));

        controller.fetchCityRecords();

        ObservableList<City> cityList = controller.getCitiesData();
        assertEquals(2, cityList.size());
        assertEquals("Warsaw", cityList.get(0).getName());
        assertEquals("Berlin", cityList.get(1).getName());
    }

    @Test
    void testSaveCity_InvalidInput() {
        controller.setSaveNameField(new TextField(""));
        controller.setSaveLongitudeField(new TextField("abc"));
        controller.setSaveLatitudeField(new TextField(""));
        controller.setSaveCountryIdField(new TextField("def"));

        controller.saveCity();

        assertEquals("Please fill in all fields", controller.getSaveInfo().getText());
    }

    // Podobnie testy dla pozostałych metod...

}