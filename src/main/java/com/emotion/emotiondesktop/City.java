package com.emotion.emotiondesktop;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class City {
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty longitude;
    private final DoubleProperty latitude;
    private final IntegerProperty countryId;

    public City(int id, String name, double longitude, double latitude, int countryId) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.longitude = new SimpleDoubleProperty(longitude);
        this.latitude = new SimpleDoubleProperty(latitude);
        this.countryId = new SimpleIntegerProperty(countryId);
    }
}
