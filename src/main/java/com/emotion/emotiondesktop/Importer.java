package com.emotion.emotiondesktop;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Importer {
    private final IntegerProperty id;
    private final StringProperty who;
    private final StringProperty status;
    private final StringProperty providerName;
    private final IntegerProperty code;
    private final StringProperty when;

    public Importer(int id, String who, String status, String providerName, int code, String when) {
        this.id = new SimpleIntegerProperty(id);
        this.who = new SimpleStringProperty(who);
        this.status = new SimpleStringProperty(status);
        this.providerName = new SimpleStringProperty(providerName);
        this.code = new SimpleIntegerProperty(code);
        this.when = new SimpleStringProperty(when);
    }
}
