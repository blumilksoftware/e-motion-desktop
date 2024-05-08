package com.emotion.emotiondesktop;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Provider {
    private final StringProperty name;
    private final StringProperty url;
    private final StringProperty androidUrl;
    private final StringProperty iosUrl;
    private final StringProperty file;
    private final StringProperty color;

    public Provider(String name, String url, String androidUrl, String iosUrl,String file, String color) {
        this.name = new SimpleStringProperty(name);
        this.url = new SimpleStringProperty(url);
        this.androidUrl = new SimpleStringProperty(androidUrl);
        this.iosUrl = new SimpleStringProperty(iosUrl);
        this.file = new SimpleStringProperty(file);
        this.color = new SimpleStringProperty(color);
    }
}
