package com.emotion.emotiondesktop;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Country {
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty longitude;
    private final DoubleProperty latitude;
    private final StringProperty iso;

    public Country(int id, String name, double longitude, double latitude, String iso) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.longitude = new SimpleDoubleProperty(longitude);
        this.latitude = new SimpleDoubleProperty(latitude);
        this.iso = new SimpleStringProperty(iso);
    }
}
